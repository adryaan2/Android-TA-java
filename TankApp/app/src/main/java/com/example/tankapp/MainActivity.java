package com.example.tankapp;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.tankapp.data.models.AutoModel;
import com.example.tankapp.data.DatabaseHelper;
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

    private static MainActivity instance;
    public static MainActivity getContext(){ return instance; }

    public static AutoModel aktivJarmu;

    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //deleteDatabase("TankolasKonyvelesek.db");


        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_kezdo, R.id.nav_tankolasok,
                R.id.nav_stats, R.id.nav_importExport)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        dbh =DatabaseHelper.getInstance(getContext());
        if(dbh.getJarmuvekSzama()==0){
            aktivJarmu=null;
        }else {
            aktivJarmu = dbh.getAutok().get(0);
            //MAJD KIVENNI----------------------------------------------
            Log.d("AKTIV_JARMU id",String.valueOf(aktivJarmu.getAutoId()));
        }

        //dbh.dbTest();
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

    public void hideUjtankolasBtn(){
        Button ujTankolasBtn = findViewById(R.id.ujTankolasBtn);
        ujTankolasBtn.setVisibility(View.GONE);
    }

    public void showUjtankolasBtn(){
        Button ujTankolasBtn = findViewById(R.id.ujTankolasBtn);
        ujTankolasBtn.setVisibility(View.VISIBLE);
    }
}