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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.DbManager;
import com.example.tankapp.databinding.FragmentStatsBinding;
import com.example.tankapp.util.Stat;

import java.text.DecimalFormat;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;
    private TextView atlFogy;
    private TextView havontaHanyszor;
    private TextView egyTankTav;

    private Stat stat;

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
        DatabaseHelper dbh = DbManager.getInstance().getDbHelper();
        if(view != null){
            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);
            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_stats_to_nav_jarmuvek));

            stat = new Stat();

            atlFogy=binding.atlFogyErtek;
            havontaHanyszor=binding.havontaHanyszorErtek;
            egyTankTav=binding.egyTankTavErtek;

            //ha nincs jármű jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbh.getJarmuvekSzama()==0){
                atlFogy.setVisibility(View.GONE);
                havontaHanyszor.setVisibility(View.GONE);
                egyTankTav.setVisibility(View.GONE);
                binding.bontasLinearL.setVisibility(View.GONE);
                binding.divider2.setVisibility(View.GONE);
                binding.divider3.setVisibility(View.GONE);
                binding.utolsoDivider.setVisibility(View.GONE);
                binding.atlFogyCim.setVisibility(View.GONE);
                binding.havontaHanyszorCim.setVisibility(View.GONE);
                binding.egyTankTavCim.setVisibility(View.GONE);

                binding.nincsAutoTxtStat.setVisibility(View.VISIBLE);
                aktJarmuBtn.setText("Járművek oldal");
                return;
            }
            aktJarmuBtn.setText("Jelenlegi jármű: "+ aktivJarmu.getRendszam());
            //ha nincs tankolás jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbh.getTankolasokSzama()==0){
                atlFogy.setVisibility(View.GONE);
                havontaHanyszor.setVisibility(View.GONE);
                egyTankTav.setVisibility(View.GONE);
                binding.bontasLinearL.setVisibility(View.GONE);
                binding.divider2.setVisibility(View.GONE);
                binding.divider3.setVisibility(View.GONE);
                binding.utolsoDivider.setVisibility(View.GONE);
                binding.atlFogyCim.setVisibility(View.GONE);
                binding.havontaHanyszorCim.setVisibility(View.GONE);
                binding.egyTankTavCim.setVisibility(View.GONE);

                binding.nincsTankTxtStat.setVisibility(View.VISIBLE);
                return;
            }
            DecimalFormat df = new DecimalFormat("#.###");
            atlFogy.setText(df.format(stat.atlagFogy100kmen()) + " l/100km");
            egyTankTav.setText(df.format(stat.atlagUtPerTankolas()) + " km");
            df.applyPattern("#.##");
            havontaHanyszor.setText(df.format(stat.haviAtlagTankolasok()));

            RecyclerView hetiRV = binding.hetiRV;
            RecyclerView haviRV = binding.haviRV;
            hetiRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            haviRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            hetiRV.setAdapter(new BontasAdapter(stat.tankolasokSzamaHetente()));
            haviRV.setAdapter(new BontasAdapter(stat.tankolasokSzamaHavonta()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}