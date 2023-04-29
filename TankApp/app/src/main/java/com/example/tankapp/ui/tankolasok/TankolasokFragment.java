package com.example.tankapp.ui.tankolasok;

import static com.example.tankapp.MainActivity.aktivJarmu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;

import java.util.ArrayList;

public class TankolasokFragment extends Fragment {

    ArrayList<TankolasOsszetett> lista;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tankolasok, container, false);

        lista = DatabaseHelper.getInstance(MainActivity.getContext()).getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerV);

        RecyclerAdapter adapter = new RecyclerAdapter(lista);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null){
            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);
            aktJarmuBtn.setText("Jelenlegi jármű: "+ aktivJarmu.getRendszam());
            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_tankolasok_to_nav_jarmuvek));
        }
    }
}