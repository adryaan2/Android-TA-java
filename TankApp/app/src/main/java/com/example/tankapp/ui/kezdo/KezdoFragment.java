package com.example.tankapp.ui.kezdo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tankapp.R;
import com.example.tankapp.databinding.FragmentKezdoBinding;

public class KezdoFragment extends Fragment{
    private FragmentKezdoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KezdoViewModel kezdoViewModel =
                new ViewModelProvider(this).get(KezdoViewModel.class);

        binding = FragmentKezdoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        kezdoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button jarmuvekBtn = root.findViewById(R.id.aktJarmuBtn);
        jarmuvekBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_kezdo_to_nav_jarmuvek));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
