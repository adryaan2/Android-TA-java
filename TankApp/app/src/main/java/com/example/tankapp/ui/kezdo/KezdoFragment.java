package com.example.tankapp.ui.kezdo;

import static com.example.tankapp.MainActivity.aktivJarmu;
import static java.time.temporal.ChronoUnit.DAYS;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.databinding.FragmentKezdoBinding;
import com.example.tankapp.ui.stats.TankolasokSzamaBontasban;
import com.example.tankapp.util.Stat;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class KezdoFragment extends Fragment{
    private FragmentKezdoBinding binding;

    private BarChart barChart;
    private TextView weeklyChart;
    private TextView monthlyChart;

    private Stat statistics;

    private ArrayList<TankolasokSzamaBontasban> weeklyArrayList;

    private ArrayList<TankolasokSzamaBontasban> monthlyArrayList;

    private BarDataSet barDataSet;

    private BarData barData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KezdoViewModel kezdoViewModel =
                new ViewModelProvider(this).get(KezdoViewModel.class);

        binding = FragmentKezdoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        barChart = root.findViewById(R.id.barChart);
        barChart.setBackgroundColor(getResources().getColor(R.color.tankolas_felvetel_doboz));
        barChart.setNoDataText("Válasszon a heti vagy a havi bontás között!");
        barChart.setNoDataTextColor(getResources().getColor(R.color.black2));
        weeklyChart = root.findViewById(R.id.statisztikaHeti);
        monthlyChart = root.findViewById(R.id.statisztikaHavi);
        weeklyArrayList = new ArrayList<>();
        monthlyArrayList = new ArrayList<>();
        statistics = new Stat();
        barData = new BarData();

        weeklyChart.setOnClickListener(new View.OnClickListener() {
            /**
             * Kattrintás hatására, havi bontásban beállítja és megjeleníti a tankolások statisztikáit
             * @param v - Bementeti paramétere egy nézet, ami kiváltotta a kattintás eseményt
             *
             */
            public void onClick(View v) {
                weeklyChart.setTextColor(getResources().getColor(R.color.h1));
                monthlyChart.setTextColor(getResources().getColor(R.color.white2));
                weeklyArrayList.clear();
                weeklyArrayList.addAll(statistics.tankolasokSzamaHetente());
                barChart.getDescription().setEnabled(false);
                barChart.animateXY(1000, 1000);
                barChart.setDrawValueAboveBar(false);
                barChart.setData(createBarData(weeklyArrayList, "Heti bontás"));
                barChart.notifyDataSetChanged();
                barChart.invalidate();
            }
        });

        monthlyChart.setOnClickListener(new View.OnClickListener() {
            /**
             * Kattrintás hatására, havi bontásban beállítja és megjeleníti a tankolások statisztikáit
             * @param v - Bementeti paramétere egy nézet, ami kiváltotta a kattintás eseményt
             *
             */
            public void onClick(View v) {
                monthlyChart.setTextColor(getResources().getColor(R.color.h1));
                weeklyChart.setTextColor(getResources().getColor(R.color.white2));
                monthlyArrayList.clear();
                monthlyArrayList.addAll(statistics.tankolasokSzamaHavonta());
                barChart.getDescription().setEnabled(false);
                barChart.animateXY(1000, 1000);
                barChart.setDrawValueAboveBar(false);
                barChart.setData(createBarData(monthlyArrayList, "Havi bontás"));
                barChart.notifyDataSetChanged();
                barChart.invalidate();


            }
        });


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
        View view = getView();
        MainActivity.refreshAktJarmuBtn();
        if (view != null) {
            DatabaseHelper dbHelper = DatabaseHelper.getInstance(MainActivity.getContext());
            TankolasOsszetett tankolasOsszetett = dbHelper.getTankolasokByAutoId(aktivJarmu.getAutoId()).get(0);
            Stat stat = new Stat();
           String megtettUt = tankolasOsszetett.xMegtettUt();
           String tankoltMennyiseg = tankolasOsszetett.xTankoltMennyiseg();
           String uzemanyag = tankolasOsszetett.getUzemanyag();
           LocalDate datum = tankolasOsszetett.getDatum();
           String egysegar = tankolasOsszetett.xEgysegar();
           float atlagFogy = stat.atlagFogy100kmen();
           float osszesUt = stat.osszesMegtettKm();


            TextView eltelt = (TextView) view.findViewById(R.id.eltelt_ido);
            TextView mennyiseg = (TextView) view.findViewById(R.id.uzemanyag_mennyiseg);
            TextView tipus = (TextView) view.findViewById(R.id.uzemanyag_tipus);
            TextView ar = (TextView) view.findViewById(R.id.uzemanyag_ar);
            TextView utan = (TextView) view.findViewById(R.id.km_utan);

            TextView atlag = (TextView) view.findViewById(R.id.atlagfogyasztas_text);
            TextView osszes = (TextView) view.findViewById(R.id.osszesut_text);

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
    /**
    * Összegyűjti az X és az Y érték adatait az oszlopdiagramhoz
     * @param arrayList - Egy TankolasokSzamaBontasban típusú lista, ami tartalmazza az x és y értékeket
     * @return Egy lista, ami visszaadja az adott oszlophoz szükséges adatokat.
    */
    public ArrayList getDataFromStatistics(ArrayList<TankolasokSzamaBontasban> arrayList){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        final ArrayList<String> xVals = new ArrayList<>();
        float counter = 0f;
        for (TankolasokSzamaBontasban x : arrayList){
            Log.d("t", x.getIdoszak());
            Log.d("c", x.getTankolasokSzama() + "");
            barEntries.add(new BarEntry(counter,x.getTankolasokSzama()));
            xVals.add(x.getIdoszak());
            counter+=1f;
        }
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        return barEntries;
    }
    /**
     * Előállítja a diagram elkészítéséhez szükséges oszlopokat
     * @param arrayList - Lista ami tartalmazza az oszlopok értékeit
     * @param type - A grafikon cimkéjének neve pl:heti bontás
     * @return BarData adathalmaz a grafikon ábrázolásához
     */
    public BarData createBarData(ArrayList arrayList, String type){
        barData.removeDataSet(barDataSet);
        barDataSet = new BarDataSet(getDataFromStatistics(arrayList), type);
        barDataSet.setDrawValues(true);
        barData.addDataSet(barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barData.setValueTextSize(14.0f);
        return barData;
    }

}
