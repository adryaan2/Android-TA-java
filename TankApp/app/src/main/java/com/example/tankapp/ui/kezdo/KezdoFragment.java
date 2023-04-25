package com.example.tankapp.ui.kezdo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.util.Stat;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.databinding.FragmentKezdoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class KezdoFragment extends Fragment{
    private FragmentKezdoBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KezdoViewModel kezdoViewModel =
                new ViewModelProvider(this).get(KezdoViewModel.class);

        binding = FragmentKezdoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.texthome;

        kezdoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;


   }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
           String megtettUt = TankolasOsszetett.xMegtettUt();
           String tankoltMennyiseg = TankolasOsszetett.xTankoltMennyiseg();
           String uzemanyag = TankolasOsszetett.getUzemanyag();
           LocalDate datum = TankolasOsszetett.getDatum();
           String egysegar = TankolasOsszetett.xEgysegar();
           float atlagFogy = Stat.atFogy;
           float osszesUt = Stat.osszMeg;

            TextView eltelt = (TextView) view.findViewById(R.id.eltelt_ido);
            TextView mennyiseg = (TextView) view.findViewById(R.id.uzemanyag_mennyiseg);
            TextView tipus = (TextView) view.findViewById(R.id.uzemanyag_tipus);
            TextView ar = (TextView) view.findViewById(R.id.uzemanyag_ar);
            TextView utan = (TextView) view.findViewById(R.id.km_utan);

            TextView atlag = (TextView) view.findViewById(R.id.atlagfogyasztas_text);
            TextView osszes = (TextView) view.findViewById(R.id.osszesut_text);

            eltelt.setText("" + datum);
            mennyiseg.setText(tankoltMennyiseg);
            tipus.setText(uzemanyag);
            ar.setText(egysegar);
            utan.setText(megtettUt);

            atlag.setText(atlagFogy + " l/100km");
            osszes.setText(osszesUt + " km");


        }
    }

}
