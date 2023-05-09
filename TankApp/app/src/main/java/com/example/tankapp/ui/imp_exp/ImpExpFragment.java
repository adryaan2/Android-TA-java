package com.example.tankapp.ui.imp_exp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.DbManager;
import com.example.tankapp.databinding.FragmentImpExpBinding;

import java.io.File;
import java.util.Objects;

public class ImpExpFragment extends Fragment {
    private FragmentImpExpBinding binding;
    private Button impBtn;
    private Button expBtn;
    private Button torolBtn;
    private TextView aktDbTxt;

    private DbManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ImpExpViewModel impExpViewModel =
                new ViewModelProvider(this).get(ImpExpViewModel.class);

        binding = FragmentImpExpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity.getContext().hideUjtankolasBtn();

        impBtn = binding.importBtn;
        expBtn = binding.exportBtn;
        torolBtn = binding.torlesBtn;
        aktDbTxt = binding.aktDbHint;
        dbManager = DbManager.getInstance();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        torolBtn.setOnClickListener(v->torlesClick(v));
        expBtn.setOnClickListener(v->exportClick(v));
        impBtn.setOnClickListener(v->importClick(v));
        refreshUi();
    }

    private void torlesClick(View v){
        /**
         * Ha a default=mentetlen adatbázis van megnyitva,
         * megerősítés után töröljük a tankolásokat és járműveket.
         */
        if(Objects.equals(dbManager.currentDbName(), DbManager.DEFAULT_DBNAME)){
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Törlés")
                    .setMessage("Az összes jármű és tankolás törlődik az adatbankból. Folytatja?")
                    .setNegativeButton("Mégse", null ) //TESZTELD LEEE
                    .setPositiveButton("Megerősít", (dialog, id)-> dbManager.getDbHelper().kiurit())
                    .setIcon(android.R.drawable.ic_delete)
                    .show();
        }else{
            /**
             * Ha exportált=mentett adatbázis van megnyitva,
             * megerősítés után töröljük az adatbázisfájlt,
             * és betöltődik egy üres, mentetlen adatbázis
             */
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Törli a mentett adatbankot?")
                    .setMessage("A művelet nem visszavonható.")
                    .setNegativeButton("Mégse", null ) //TESZTELD LEEE
                    .setPositiveButton("Törlés", (dialog, id)-> {
                        dbManager.deleteExportedDb();
                        refreshUi();
                    })
                    .setIcon(android.R.drawable.ic_delete)
                    .show();
        }

    }

    private void exportClick(View v){
        /**
         * Ha nincs jármű az adatbázisban, csak tájékoztatjuk
         * a felhasználót, hogy nincs mit menteni.
         */
        if(dbManager.getDbHelper().getJarmuvekSzama()==0){
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Nincs mit menteni ebben az adatbázisban")
                    .setPositiveButton("Értettem", null)
                    .show();
            return;
        }
        /**
         * Egyéb esetben a felhasználótól bekért néven mentjük el
         * az adatbázisfájlt. A név pontot nem tartalmazhat,
         * és nem egyezhet meg a default névvel, ami a "nem mentett" adatbázist jelenti.
         */
        View inputView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_edittext,null);
        EditText input = inputView.findViewById(R.id.editText);
        new AlertDialog.Builder(v.getContext())
                .setTitle("Adja meg, milyen néven legyen mentve az adatbank!")
                .setMessage("Nem tartalmazhat pontot")
                .setView(inputView)
                .setNegativeButton("Mégse", (dialog, id) ->dialog.dismiss() )
                .setPositiveButton("Mentés", (dialog, id) ->{
                    String be= String.valueOf(input.getText());
                    if(be.contains(".") || be.length()==0)
                        Toast.makeText(v.getContext(),"Helytelen név",Toast.LENGTH_SHORT).show();
                    else if(be.equals(DbManager.DEFAULT_DBNAME.substring(0,DbManager.DEFAULT_DBNAME.indexOf('.'))))
                        Toast.makeText(v.getContext(),"Ezt a nevet nem használhatja",Toast.LENGTH_SHORT).show();
                    else{
                        dbManager.exportDb(be);
                        Toast.makeText(v.getContext(),be,Toast.LENGTH_SHORT).show();
                        ///  Felső kiírást frissíteni!
                        refreshUi();
                    }
                })
                .show();

    }

    private void importClick(View v){
        /**
         * Kilistázza a database mappában levő .db kiterjesztésű fájlokat, kivéve a default adatbázist (TankolasKonyvelesek.db).
         * A kiválasztottat pedig importálja.
         */

        File[] files = new File(DbManager.getInstance().getDbHelper().getDbDirectory()).listFiles();
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setTitle("Válassza ki az adatbázis fájlt!");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < files.length; i++) {
            String aktElem = files[i].getName();
            if (!aktElem.equals(DatabaseHelper.DEFAULT_DBNAME) && aktElem.endsWith(".db"))
                arrayAdapter.add(aktElem.substring(0, aktElem.length() - 3));
        }

        builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dbToLoad = arrayAdapter.getItem(which); //ez az amit kiválaszt a listából a felhasználó
                if(Objects.equals(dbManager.currentDbName(), DbManager.DEFAULT_DBNAME)){
                    String finalDbToLoad = dbToLoad;
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Jelenleg betölött adatai elvesznek")
                            .setMessage("Ha később is el szeretné érni adatait, előbb exportálja őket")
                            .setPositiveButton("Folytatás", (dialog2, id)->{
                                dbManager.getDbHelper().kiurit();
                                dbManager.loadDb(finalDbToLoad); // az android studio mondta hogy legyen final
                                refreshUi();
                                MainActivity.aktivJarmu=dbManager.getDbHelper().getAutok().get(0);
                            })
                            .setNegativeButton("Mégse",null)
                            .show();
                }else {
                    dbManager.loadDb(dbToLoad);
                    refreshUi();
                    MainActivity.aktivJarmu=dbManager.getDbHelper().getAutok().get(0);
                }
            }
        });
        builderSingle.show();
    }

    /**
     * felület változásai annak függvényében, hogy mentett adatbázis van-e megnyitva
     */
    private void refreshUi(){
        String aktDb = dbManager.currentDbName();
        if(Objects.equals(aktDb, DbManager.DEFAULT_DBNAME)) {
            aktDbTxt.setText(R.string.unsaved_db_hint);
            expBtn.setText(R.string.make_export);
            expBtn.setEnabled(true);
        }
        else {
            aktDbTxt.setText(getString(R.string.saved_db_hint) + " " + aktDb.substring(0, aktDb.indexOf('.')));
            expBtn.setText(R.string.already_exported);
            expBtn.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
