package com.example.tankapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tankapp.MainActivity;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;

import java.util.ArrayList;
import java.util.Objects;

public class Stat extends DatabaseHelper {
    public Stat(){
        super(MainActivity.getContext());
    }
    public float kmToMiles(float km){return km*0.62137f;}
    public static float milesToKm(float mi){return mi*1.60934f;}
    public float literToGl(float l){return l*0.26417f;}
    public static float glToLiter(float gl){return gl*3.78541f;}
    public static float osszMeg = 0;
    public static float atFogy = 0;

    public float atlagFogy100kmen(){
        ArrayList<TankolasOsszetett> osszes = getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszL=0; float osszKm=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getTavolsagEgyseg(), "km")) osszKm+=akt.getMegtett_tav();
            else osszKm+=milesToKm(akt.getMegtett_tav());
            if(Objects.equals(akt.getUrmertek(), "liter")) osszL+= akt.getMenny();
            else osszL=glToLiter(akt.getMenny());
        }
        atFogy = 100*osszL/osszKm;
        return atFogy;
    }

    public float osszesMegtettKm(){
        ArrayList<TankolasOsszetett> osszes = getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszKm=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getTavolsagEgyseg(), "km")) osszKm+=akt.getMegtett_tav();
            else osszKm+=milesToKm(akt.getMegtett_tav());
        }
        osszMeg = osszKm;
        return osszMeg;
    }

    /*public float haviAtlagTankolasok(){

    }*/



    public void statTest(){
        Log.d("ATL_FOGY", String.valueOf(atlagFogy100kmen()));
        Log.d("OSSZKM", String.valueOf(osszesMegtettKm()));

        //LocalDate ld;

    }
}
