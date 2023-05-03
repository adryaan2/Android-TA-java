package com.example.tankapp.ui.jarmuvek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.models.AutoModel;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.databinding.FragmentJarmuvekBinding;

import java.util.List;

public class JarmuvekFragment extends Fragment {

    private FragmentJarmuvekBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Button addCarButton;

    private List<AutoModel> autoModelList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JarmuvekViewModel jarmuvekViewModel =
                new ViewModelProvider(this).get(JarmuvekViewModel.class);

        binding = FragmentJarmuvekBinding.inflate(inflater, container, false);
        MainActivity.getContext().hideUjtankolasBtn();
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        autoModelList = DatabaseHelper.getInstance(MainActivity.getContext()).getAutok();

        adapter = new JarmuvekAdapter(autoModelList,this.getContext());
        recyclerView.setAdapter(adapter);
        addCarButton = root.findViewById(R.id.addCarButton);
        addCarButton.setOnClickListener(v->{
            Navigation.findNavController(v).getGraph().setStartDestination(R.id.nav_kezdo);
            Navigation.findNavController(v).navigate(R.id.action_nav_jarmuvek_to_jarmuFelvetelFragment);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}