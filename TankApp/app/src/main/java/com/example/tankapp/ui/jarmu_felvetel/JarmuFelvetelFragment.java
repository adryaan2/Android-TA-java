package com.example.tankapp.ui.jarmu_felvetel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankapp.R;
import com.example.tankapp.databinding.FragmentJarmuFelvetelBinding;
import com.example.tankapp.databinding.FragmentTankolasFelvetelBinding;

public class JarmuFelvetelFragment extends Fragment {

    private JarmuFelvetelViewModel mViewModel;

    private FragmentJarmuFelvetelBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        JarmuFelvetelViewModel jarmuFelvetelViewModel = new ViewModelProvider(this).get(JarmuFelvetelViewModel.class);

        binding = FragmentJarmuFelvetelBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

}