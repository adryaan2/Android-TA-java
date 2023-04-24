package com.example.tankapp.ui.kezdo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.R;
import com.example.tankapp.databinding.FragmentKezdoBinding;

public class KezdoFragment extends Fragment{
    private FragmentKezdoBinding binding;
    private TextView eltelt;
    private TextView liter;




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
            TextView eltelt = (TextView) view.findViewById(R.id.eltelt_ido);
            TextView mennyiseg = (TextView) view.findViewById(R.id.uzemanyag_mennyiseg);
            TextView tipus = (TextView) view.findViewById(R.id.uzemanyag_tipus);
            TextView ar = (TextView) view.findViewById(R.id.uzemanyag_ar);
            TextView utan = (TextView) view.findViewById(R.id.km_utan);

            TextView atlag = (TextView) view.findViewById(R.id.atlagfogyasztas_text);
            TextView osszes = (TextView) view.findViewById(R.id.osszesut_text);

            eltelt.setText("23 napja");
            mennyiseg.setText("23,48 liter");
            tipus.setText("Benzin 95");
            ar.setText("621,43 Ft/liter");
            utan.setText("231 km után");

            atlag.setText("5,63 l/100km");
            osszes.setText("746km");

        }
    }
}
