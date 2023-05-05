package com.example.tankapp.ui.tankolasok;

import static com.example.tankapp.MainActivity.aktivJarmu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerV);

        //ha nincs tankolás jelenítsük meg a megfelelő szöveget és legyen vége az onCreateView()-nak
        if(DatabaseHelper.getInstance(MainActivity.getContext()).getTankolasokSzama()==0){
            TextView cim = view.findViewById(R.id.titleTxt);
            LinearLayout header = view.findViewById(R.id.header);
            TextView nincstank = view.findViewById(R.id.nincsTankTxtTankolasok);
            recyclerView.setVisibility(View.GONE);
            cim.setVisibility(View.GONE);
            header.setVisibility(View.GONE);

            nincstank.setVisibility(View.VISIBLE);
            return view;
        }


        lista = DatabaseHelper.getInstance(MainActivity.getContext()).getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());

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
        MainActivity.getContext().showUjtankolasBtn();
        View view = getView();
        if(view != null){
            //új tankolás gomb navigáljon a felületre
            Button ujTankolasBtn = view.getRootView().findViewById(R.id.ujTankolasBtn);
            ujTankolasBtn.setOnClickListener(v-> NavHostFragment.findNavController(this).navigate(R.id. action_nav_tankolasok_to_nav_tankolasFelvetel));

            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);
            aktJarmuBtn.setText("Jelenlegi jármű: "+ aktivJarmu.getRendszam());
            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_tankolasok_to_nav_jarmuvek));
        }
    }
}