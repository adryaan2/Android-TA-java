package com.example.tankapp.ui.jarmuvek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.databinding.FragmentJarmuvekBinding;

public class JarmuvekFragment extends Fragment {

    private FragmentJarmuvekBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JarmuvekViewModel jarmuvekViewModel =
                new ViewModelProvider(this).get(JarmuvekViewModel.class);

        binding = FragmentJarmuvekBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        jarmuvekViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}