package com.example.tankapp.ui.tankolasok;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.databinding.FragmentTankolasokBinding;

import java.util.ArrayList;

public class TankolasokFragment extends Fragment {

    ArrayList<TestModel> dataModels;
    private static TankolasokAdapter adapter;

    private FragmentTankolasokBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TankolasokViewModel tankolasokViewModel =
                new ViewModelProvider(this).get(TankolasokViewModel.class);
        binding = FragmentTankolasokBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.listV;
        /*
        tankolasokViewModel.getItem().removeObservers(this);
        tankolasokViewModel.getItem().observe(getViewLifecycleOwner(), new Observer<TestModel>() {
            @Override
            public void onChanged(TestModel testModel) {

            }
        });*/
        dataModels= new ArrayList<>();
        for(int i=0; i<10; i++){
            dataModels.add(new TestModel("2023.03.28","345 km", "645,29 Ft/l", "24,31 l", "Benzin 95"));
            dataModels.add(new TestModel("2023.04.22","345 km", "700,00 Ft/l", "11,68 l", "Benzin 98 Super"));
        }
        adapter = new TankolasokAdapter(dataModels,getContext());
        listView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}