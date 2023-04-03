package com.example.tankapp;

import android.os.Bundle;
import android.view.Menu;

import com.example.tankapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

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


        TankolasKonyvelesekDb.addAutok("ABC-123");
        TankolasKonyvelesekDb.addAutok("DEF-456");


        TankolasKonyvelesekDb.addValutak("HUF");
        TankolasKonyvelesekDb.addValutak("USD");

        TankolasKonyvelesekDb.addUrmertekek("liter");
        TankolasKonyvelesekDb.addUrmertekek("gallon");

        TankolasKonyvelesekDb.addTavolsagok("méter");
        TankolasKonyvelesekDb.addTavolsagok("mérföld");

        TankolasKonyvelesekDb.addUzemanyagok("benzin");
        TankolasKonyvelesekDb.addUzemanyagok("diesel");

        TankolasKonyvelesekDb.addTankolasok("2023.03.26", 1, 350, 1, 2560, 1, 27,2, 1);
        TankolasKonyvelesekDb.addTankolasok("2023.04.13", 2, 150, 2, 20, 2, 23,1, 1);
        TankolasKonyvelesekDb.addTankolasok("2023.04.23", 2, 276, 1, 2000, 1, 18,1, 1);
        TankolasKonyvelesekDb.addTankolasok("2023.05.17", 1, 220, 2, 50, 2, 10,2, 2);



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