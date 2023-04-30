package com.example.tankapp.ui.stats;

import static com.example.tankapp.MainActivity.aktivJarmu;

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

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.databinding.FragmentStatsBinding;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        MainActivity.getContext().hideUjtankolasBtn();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null){
            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);
            aktJarmuBtn.setText("Jelenlegi jármű: "+ aktivJarmu.getRendszam());
            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_stats_to_nav_jarmuvek));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}