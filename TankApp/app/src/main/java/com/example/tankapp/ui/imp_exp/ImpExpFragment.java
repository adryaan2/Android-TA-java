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
        new AlertDialog.Builder(v.getContext())
                .setTitle("Adja meg, milyen néven legyen mentve az adatbank!")
                .setMessage("Nem tartalmazhat pontot")
                .setView(inputView)
                .setNegativeButton("Mégse", (dialog, id) ->dialog.cancel() )
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

    /**
     * felület változásai annak függvényében, hogy mentett adatbázis van-e megnyitva
     */
    private void refreshUi(){
        String aktDb = dbManager.currentDbName();
        if(Objects.equals(aktDb, DbManager.DEFAULT_DBNAME))
            aktDbTxt.setText(R.string.unsaved_db_hint);
        else
            aktDbTxt.setText(getString(R.string.saved_db_hint)+" "+aktDb.substring(0,aktDb.indexOf('.')));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
