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

    private DatabaseHelper dbh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tankolasok, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbh=DatabaseHelper.getInstance(MainActivity.getContext());
        View view = getView();
        if(view != null){
            RecyclerView recyclerView = view.findViewById(R.id.recyclerV);
            TextView cim = view.findViewById(R.id.titleTxt);
            LinearLayout header = view.findViewById(R.id.header);

            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);
            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_tankolasok_to_nav_jarmuvek));
            //ha nincs jármű jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbh.getJarmuvekSzama()==0){
                recyclerView.setVisibility(View.GONE);
                cim.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                TextView nincsauto = view.findViewById(R.id.nincsAutoTxtTankolasok);
                nincsauto.setVisibility(View.VISIBLE);
                MainActivity.getContext().hideUjtankolasBtn();
                aktJarmuBtn.setText("Járművek oldal");
                return;
            }
            //új tankolás gomb navigáljon a felületre
            Button ujTankolasBtn = view.getRootView().findViewById(R.id.ujTankolasBtn);
            ujTankolasBtn.setOnClickListener(v-> NavHostFragment.findNavController(this).navigate(R.id. action_nav_tankolasok_to_nav_tankolasFelvetel));
            MainActivity.getContext().showUjtankolasBtn();
            aktJarmuBtn.setText("Jelenlegi jármű: "+ aktivJarmu.getRendszam());
            //ha nincs tankolás jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbh.getTankolasokSzama()==0){
                recyclerView.setVisibility(View.GONE);
                cim.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                TextView nincstank = view.findViewById(R.id.nincsTankTxtTankolasok);
                nincstank.setVisibility(View.VISIBLE);
                return;
            }

            lista = dbh.getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());

            RecyclerAdapter adapter = new RecyclerAdapter(lista);

            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}