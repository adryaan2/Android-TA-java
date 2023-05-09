package com.example.tankapp.ui.imp_exp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.tankapp.data.DbManager;
import com.example.tankapp.databinding.FragmentImpExpBinding;

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
        new AlertDialog.Builder(v.getContext())
                .setTitle("Törlés")
                .setMessage("Az összes jármű és tankolás törlődik az adatbankból. Folytatja?")
                .setNegativeButton("Mégse", null ) //TESZTELD LEEE
                .setPositiveButton("Megerősít", (dialog, id)-> dbManager.getDbHelper().kiurit())
                .show();
    }

    private void exportClick(View v){
        View inputView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_edittext,null);
        EditText input = inputView.findViewById(R.id.editText);
        if(dbManager.getDbHelper().getJarmuvekSzama()==0){
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Nincs mit menteni ebben az adatbázisban")
                    .setPositiveButton("Értettem", null)
                    .show();
            return;
        }
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
         * To-Do: a dbToLoad az legyen amit kiválasztott
         * a dbManager.dbDirectory() helyen levő .db-re végződő fájlok listájából
         * a listában legyen levágva róluk a .db végződés
         * a dbToLoad-ba is pont ahogy kiválasztotta, .db nélkül kerüljön a név
         */
        String dbToLoad = "bbdb"; //ez az amit kiválaszt a listából a felhasználó
        //ne rakd hozzá a .db végződést
        //dbToLoad+=".db";
        if(Objects.equals(dbManager.currentDbName(), DbManager.DEFAULT_DBNAME)){
            String finalDbToLoad = dbToLoad;
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Jelenleg betölött adatai elvesznek")
                    .setMessage("Ha később is el szeretné érni adatait, előbb exportálja őket")
                    .setPositiveButton("Folytatás", (dialog, id)->{
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
