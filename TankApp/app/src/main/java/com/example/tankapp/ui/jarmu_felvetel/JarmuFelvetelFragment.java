package com.example.tankapp.ui.jarmu_felvetel;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.databinding.FragmentJarmuFelvetelBinding;
import com.example.tankapp.databinding.FragmentTankolasFelvetelBinding;


public class JarmuFelvetelFragment extends Fragment {

    private JarmuFelvetelViewModel mViewModel;

    private FragmentJarmuFelvetelBinding binding;

    private Button hozzaadBtn;
    private EditText rendszEdit;
    private EditText megjEdit;
    private TextView rendszHibaTxt;
    private TextView megjHibaTxt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        JarmuFelvetelViewModel jarmuFelvetelViewModel = new ViewModelProvider(this).get(JarmuFelvetelViewModel.class);

        binding = FragmentJarmuFelvetelBinding.inflate(inflater, container, false);

        MainActivity.getContext().hideUjtankolasBtn();
        View root = binding.getRoot();
        hozzaadBtn = root.findViewById(R.id.saveVehicleButton);
        rendszEdit = root.findViewById(R.id.numberPlateInputField);
        megjEdit = root.findViewById(R.id.carNameInputField);
        rendszHibaTxt = root.findViewById(R.id.numberPlateAlertText);
        megjHibaTxt = root.findViewById(R.id.carNameAlertText);

        hozzaadBtn.setOnClickListener(v->{
            DatabaseHelper dh = DatabaseHelper.getInstance(MainActivity.getContext());
            String rendsz = rendszEdit.getText().toString().trim().toUpperCase();
            String megj = megjEdit.getText().toString();
            if(rendsz.length()<7){
                rendszHibaTxt.setVisibility(View.VISIBLE);
                return;
            }
            Log.d("uj_rendsz: ",rendsz);
            Log.d("uj_megj: ",megj);
            try {
                int autokSzama = dh.getJarmuvekSzama();
                dh.addAutok(rendsz, megj);
                if(autokSzama==0){
                    MainActivity.aktivJarmu=dh.getAutok().get(0);
                    Navigation.findNavController(v).navigate(R.id.action_jarmuFelvetelFragment_to_nav_kezdo2);
                } else Navigation.findNavController(v).navigate(R.id.action_jarmuFelvetelFragment_to_nav_jarmuvek);

                Toast.makeText(getContext(), "Jármű hozzáadva",Toast.LENGTH_SHORT).show();
            }
            catch (SQLiteConstraintException e) {
                Toast.makeText(getContext(), "Ilyen rendszámú jármű már létezik!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardFrom(getContext(),getView());
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}