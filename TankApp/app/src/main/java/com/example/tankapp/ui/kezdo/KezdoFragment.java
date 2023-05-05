package com.example.tankapp.ui.kezdo;

import static com.example.tankapp.MainActivity.aktivJarmu;
import static java.time.temporal.ChronoUnit.DAYS;

import android.opengl.Visibility;
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
import androidx.navigation.fragment.NavHostFragment;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.databinding.FragmentKezdoBinding;
import com.example.tankapp.util.Stat;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class KezdoFragment extends Fragment{
    private FragmentKezdoBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KezdoViewModel kezdoViewModel =
                new ViewModelProvider(this).get(KezdoViewModel.class);

        binding = FragmentKezdoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;


   }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.getContext().showUjtankolasBtn();
        View view = getView();
        if (view != null) {
            //új tankolás gomb navigáljon a felületre
            Button ujTankolasBtn = view.getRootView().findViewById(R.id.ujTankolasBtn);
            Button aktJarmuBtn = view.findViewById(R.id.aktJarmuBtn);

            TextView eltelt = (TextView) view.findViewById(R.id.eltelt_ido);
            TextView mennyiseg = (TextView) view.findViewById(R.id.uzemanyag_mennyiseg);
            TextView tipus = (TextView) view.findViewById(R.id.uzemanyag_tipus);
            TextView ar = (TextView) view.findViewById(R.id.uzemanyag_ar);
            TextView utan = (TextView) view.findViewById(R.id.km_utan);

            TextView atlag = (TextView) view.findViewById(R.id.atlagfogyasztas_text);
            TextView osszes = (TextView) view.findViewById(R.id.osszesut_text);

            aktJarmuBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_kezdo_to_nav_jarmuvek));
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(MainActivity.getContext());

            //ha nincs jármű jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbHelper.getJarmuvekSzama()==0){
                eltelt.setVisibility(View.GONE);
                mennyiseg.setVisibility(View.GONE);
                tipus.setVisibility(View.GONE);
                ar.setVisibility(View.GONE);
                utan.setVisibility(View.GONE);
                atlag.setVisibility(View.GONE);
                osszes.setVisibility(View.GONE);
                TextView osszesut = (TextView) view.findViewById(R.id.osszesut);
                osszesut.setVisibility(View.GONE);
                TextView atlagfogy = (TextView) view.findViewById(R.id.atlagfogyasztas);
                atlagfogy.setVisibility(View.GONE);
                TextView cim = view.findViewById(R.id.utolsoTankCimTxt);
                cim.setVisibility(View.GONE);
                ujTankolasBtn.setVisibility(View.GONE);

                binding.nincsAutoTxtKezdo.setVisibility(View.VISIBLE);
                aktJarmuBtn.setText("Járművek oldal");
                return;
            }

            ujTankolasBtn.setOnClickListener(v->NavHostFragment.findNavController(this).navigate(R.id. action_nav_kezdo_to_nav_tankolasFelvetel));
            //ha nincs tankolás jelenítsük meg a megfelelő szöveget és legyen vége az onStart()-nak
            if(dbHelper.getTankolasokSzama()==0){
                eltelt.setVisibility(View.GONE);
                mennyiseg.setVisibility(View.GONE);
                tipus.setVisibility(View.GONE);
                ar.setVisibility(View.GONE);
                utan.setVisibility(View.GONE);
                atlag.setVisibility(View.GONE);
                osszes.setVisibility(View.GONE);
                TextView osszesut = (TextView) view.findViewById(R.id.osszesut);
                osszesut.setVisibility(View.GONE);
                TextView atlagfogy = (TextView) view.findViewById(R.id.atlagfogyasztas);
                atlagfogy.setVisibility(View.GONE);
                TextView cim = view.findViewById(R.id.utolsoTankCimTxt);
                cim.setVisibility(View.GONE);

                aktJarmuBtn.setText("Jelenlegi jármű: "+aktivJarmu.getRendszam());
                
                TextView nincsTank = view.findViewById(R.id.nincsTankTxt);
                nincsTank.setVisibility(View.VISIBLE);
                return;
            }
            TankolasOsszetett tankolasOsszetett = dbHelper.getTankolasokByAutoId(aktivJarmu.getAutoId()).get(0);
            Stat stat = new Stat();
            String megtettUt = tankolasOsszetett.xMegtettUt();
            String tankoltMennyiseg = tankolasOsszetett.xTankoltMennyiseg();
            String uzemanyag = tankolasOsszetett.getUzemanyag();
            LocalDate datum = tankolasOsszetett.getDatum();
            String egysegar = tankolasOsszetett.xEgysegar();
            float atlagFogy = stat.atlagFogy100kmen();
            float osszesUt = stat.osszesMegtettKm();

            eltelt.setText(DAYS.between(datum, LocalDate.now())+" napja");
            mennyiseg.setText(tankoltMennyiseg);
            tipus.setText(uzemanyag);
            ar.setText(egysegar);
            utan.setText(megtettUt+" után");

            DecimalFormat df = new DecimalFormat("#.####");
            atlag.setText(df.format(atlagFogy)+ " l/100km");
            osszes.setText(osszesUt + " km");


        }
    }

}
