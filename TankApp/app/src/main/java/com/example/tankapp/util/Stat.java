package com.example.tankapp.util;

import android.database.Cursor;
import android.util.Log;

import com.example.tankapp.MainActivity;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.TankolasOsszetett;
import com.example.tankapp.ui.stats.TankolasokSzamaBontasban;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Stat {
    private DatabaseHelper dbh;
    public Stat(){
        dbh=DatabaseHelper.getInstance(MainActivity.getContext());
    }
    public float kmToMiles(float km){return km*0.62137f;}
    public float milesToKm(float mi){return mi*1.60934f;}
    public float literToGl(float l){return l*0.26417f;}
    public float glToLiter(float gl){return gl*3.78541f;}

    /**
     * @return liter/100km
     */
    public float atlagFogy100kmen(){
        ArrayList<TankolasOsszetett> osszes = dbh.getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszL=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getUrmertek(), "liter")) osszL+= akt.getMenny();
            else osszL+=glToLiter(akt.getMenny());
        }
        return 100*osszL/osszesMegtettKm();
    }

    public float osszesMegtettKm(){
        ArrayList<TankolasOsszetett> osszes = dbh.getTankolasokByAutoId(MainActivity.aktivJarmu.getAutoId());
        float osszKm=0;
        for(TankolasOsszetett akt : osszes){
            if(Objects.equals(akt.getTavolsagEgyseg(), "km")) osszKm+=akt.getMegtett_tav();
            else osszKm+=milesToKm(akt.getMegtett_tav());
        }
        return osszKm;
    }

    /**
     * @return Az aktivJarmu-nek átlagosan hány tankolása van egy hónapban
     */
    public double haviAtlagTankolasok(){
        List<LocalDate> datumLista = dbh.getDatumokByAutoId(MainActivity.aktivJarmu.getAutoId());
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

    /**
     * @return Az aktivJarmu egy tankolása átlagosan ennyi kilométer megtételére elég
     */
    public float atlagUtPerTankolas(){
        return osszesMegtettKm()/dbh.getTankolasokSzamaAktivJarmu();
    }

    public ArrayList<TankolasokSzamaBontasban> tankolasokSzamaHavonta(){
        List<LocalDate> datumLista = dbh.getDatumokByAutoId(MainActivity.aktivJarmu.getAutoId());
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

    public ArrayList<TankolasokSzamaBontasban> tankolasokSzamaHetente(){
        List<LocalDate> datumLista = dbh.getDatumokByAutoId(MainActivity.aktivJarmu.getAutoId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/");
        List<String> hetekOsszes = datumLista.stream()
                .sorted(Comparator.reverseOrder())
                .map(x -> x.format(dateTimeFormatter) + x.get(WeekFields.ISO.weekOfYear())+".hét")
                .collect(Collectors.toList());
        List<String> hetekDistinct = hetekOsszes.stream()
                .distinct()
                .collect(Collectors.toList());
        ArrayList<TankolasokSzamaBontasban> arrayList = new ArrayList<>();
        for(String honap : hetekDistinct){
            int gyakorisag = Collections. frequency(hetekOsszes, honap);
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
        for(TankolasokSzamaBontasban akt : tankolasokSzamaHetente())
            Log.d("HETIBONTAS",akt.getIdoszak()+": "+akt.getTankolasokSzama());

    }
}
