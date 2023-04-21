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
import com.example.tankapp.util.Stat;
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

    private static MainActivity instance;
    public static MainActivity getContext(){ return instance; }

    //MAJD KIVENNI----------------------------------------------
    private Stat stat;

    public static AutoModel aktivJarmu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
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

        aktivJarmu=DatabaseHelper.getInstance(getContext())
                .getAutok().get(0);
        //MAJD KIVENNI----------------------------------------------
        Log.d("AKTIV_JARMU id",String.valueOf(aktivJarmu.getAutoId()));
        stat = new Stat(); stat.statTest();

        DatabaseHelper.getInstance(MainActivity.getContext()).dbTest();
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