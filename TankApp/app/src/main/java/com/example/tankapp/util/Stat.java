package com.example.tankapp.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tankapp.MainActivity;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.data.UzemanyagModel;
import com.example.tankapp.ui.stats.TankolasokSzamaBontasban;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class Stat extends DatabaseHelper {
    public Stat(){
        super(MainActivity.getContext());
    }
    public float kmToMiles(float km){return km*0.62137f;}
    public float milesToKm(float mi){return mi*1.60934f;}
    public float literToGl(float l){return l*0.26417f;}
    public float glToLiter(float gl){return gl*3.78541f;}

    public float atlagFogy100kmen(){
        ArrayList<TankolasOsszetett> osszes = getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszL=0; float osszKm=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getTavolsagEgyseg(), "km")) osszKm+=akt.getMegtett_tav();
            else osszKm+=milesToKm(akt.getMegtett_tav());
            if(Objects.equals(akt.getUrmertek(), "liter")) osszL+= akt.getMenny();
            else osszL=glToLiter(akt.getMenny());
        }
        return 100*osszL/osszKm;
    }

    public float osszesMegtettKm(){
        ArrayList<TankolasOsszetett> osszes = getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszKm=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getTavolsagEgyseg(), "km")) osszKm+=akt.getMegtett_tav();
            else osszKm+=milesToKm(akt.getMegtett_tav());
        }
        return osszKm;
    }

    public double haviAtlagTankolasok(){
        List<LocalDate> datumLista = getDatumokByAutoId(MainActivity.aktivJarmu.getAutoId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM");
        String[] honapok = datumLista.stream()
                .map(x -> x.format(dateTimeFormatter))
                .toArray(String[]::new);

        Map<String,Long> elofordulasok = Arrays.stream(honapok)
                .collect(Collectors.groupingBy((x->x), Collectors.counting()));

        OptionalDouble eredm = elofordulasok.values().stream()
                .mapToInt(Long::intValue)
                .average();

        return eredm.getAsDouble();
    }

    public float atlagUtPerTankolas(){
        int tankolasokSzama;
        String sql = "SELECT COUNT(*) FROM Tankolasok WHERE autoId="+MainActivity.aktivJarmu.getAutoId();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tankolasokSzama = cursor.getInt(0);
        cursor.close();
        return osszesMegtettKm()/tankolasokSzama;
    }

    public ArrayList<TankolasokSzamaBontasban> tankolasokSzamaHavonta(){
        List<LocalDate> datumLista = getDatumokByAutoId(MainActivity.aktivJarmu.getAutoId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM");
        List<String> honapokOsszes = datumLista.stream()
                .map(x -> x.format(dateTimeFormatter))
                .collect(Collectors.toList());
        List<String> honapokDistinct = honapokOsszes.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        ArrayList<TankolasokSzamaBontasban> arrayList = new ArrayList<>();
        for(String honap : honapokDistinct){
            int gyakorisag = Collections. frequency(honapokOsszes, honap);
            arrayList.add(new TankolasokSzamaBontasban(honap,gyakorisag));
        }
        return arrayList;
    }

    public void statTest(){
        Log.d("ATL_FOGY", String.valueOf(atlagFogy100kmen()));
        Log.d("OSSZKM", String.valueOf(osszesMegtettKm()));
        Log.d("HAVIATL", String.valueOf(haviAtlagTankolasok()));
        Log.d("ATLAGUT", String.valueOf(atlagUtPerTankolas()));
        for(TankolasokSzamaBontasban akt : tankolasokSzamaHavonta())
            Log.d("HAVIBONTAS",akt.getIdoszak()+": "+akt.getTankolasokSzama());


        //LocalDate ld;

    }
}
