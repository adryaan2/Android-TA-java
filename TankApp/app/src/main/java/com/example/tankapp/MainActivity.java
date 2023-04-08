package com.example.tankapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.tankapp.data.AutoModel;
import com.example.tankapp.data.DatabaseHelper;

import com.example.tankapp.data.TankolasModel;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.data.TavolsagModel;
import com.example.tankapp.data.UrmertekModel;
import com.example.tankapp.data.UzemanyagModel;
import com.example.tankapp.data.ValutaModel;
import com.example.tankapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    DatabaseHelper TankolasKonyvelesekDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_kezdo, R.id.nav_jarmuvek, R.id.nav_tankolasok,
                R.id.nav_stats, R.id.nav_importExport)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        deleteDatabase("TankolasKonyvelesekDb");

        TankolasKonyvelesekDb = new DatabaseHelper(this);

        TankolasKonyvelesekDb.addTankolasModel(new TankolasModel(2,"2023.04.07",2, 333,
                2, 2, 2, 2,123.45f,12.96f));

        ArrayList<TankolasOsszetett> ossz = TankolasKonyvelesekDb.getOsszesTankolas();
        for(TankolasOsszetett akt : ossz)
            Log.d("PROBA", akt.toString());

        /// --------------------------------

        for(AutoModel akt : TankolasKonyvelesekDb.getAutok())
            Log.d("AUTOK", akt.toString());

        Log.d("UTOLSO", TankolasKonyvelesekDb.getUtolsoTankolas().toString());

        Log.d("SZAMA", String.valueOf(TankolasKonyvelesekDb.getTankolasokSzama()));

        for(UzemanyagModel akt : TankolasKonyvelesekDb.getUzemanyagok())
            Log.d("UZEMA", akt.toString());

        for(ValutaModel akt : TankolasKonyvelesekDb.getValutak())
            Log.d("VALUTAK", akt.toString());

        for(UrmertekModel akt : TankolasKonyvelesekDb.getUrmertekek())
            Log.d("URMERT", akt.toString());

        for(TavolsagModel akt : TankolasKonyvelesekDb.getTavolsagok())
            Log.d("TAV", akt.toString());

        for(TankolasOsszetett akt : TankolasKonyvelesekDb.getTankolasokByAutoId(2))
            Log.d("byID", akt.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}