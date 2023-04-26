package com.example.tankapp.data;

import static java.time.format.DateTimeFormatter.ofPattern;

import com.example.tankapp.MainActivity;
import com.example.tankapp.util.Stat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TankolasOsszetett {
    private LocalDate datum; //SQLite Stringként tárolja 2007-01-01 formátumban
    private String auto;
    private int megtettTav;
    private String tavolsagEgyseg;
    private String valuta;
    private String uzemanyag;
    private String urmertek;
    private float ar;
    private float menny;
    private float oszto;

    public TankolasOsszetett(long datum, String auto, int megtettTav, String tavolsagEgyseg, String valuta, String uzemanyag, String urmertek, float ar, float menny) {
        this.datum = LocalDate.ofEpochDay(datum);
        this.auto = auto;
        this.megtettTav = megtettTav;
        this.tavolsagEgyseg = tavolsagEgyseg;
        this.valuta = valuta;
        this.uzemanyag = uzemanyag;
        this.urmertek = urmertek;
        this.ar = ar;
        this.menny = menny;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getAuto() {
        return auto;
    }

    public int getMegtett_tav() {
        return megtettTav;
    }

    public  String getTavolsagEgyseg() {
        return tavolsagEgyseg;
    }

    public String getValuta() {
        return valuta;
    }

    public  String getUzemanyag() {
        return uzemanyag;
    }

    public String getUrmertek() {
        return urmertek;
    }

    public float getAr() {
        return ar;
    }

    public float getMenny() {
        return menny;
    }

    public String xAtlagfogyasztas() {
        oszto = (megtettTav / 100.0f);
        return (menny / oszto + " l/100km");
    }

    public String xFizetve() {
        return (ar * menny) + " " + valuta;
    }


    public String xTankoltMennyiseg() {
        return menny + " " + urmertek;
    }

    public String xMegtettUt() {
        return megtettTav + " " + tavolsagEgyseg;
    }

    public String xEgysegar() {
        return ar + " " + valuta + "/" + urmertek;
    }

    @Override
    public String toString() {
        return datum.format(ofPattern("yyyy.MM.dd")) + ' ' +
                auto + ' ' +
                megtettTav +
                " " + tavolsagEgyseg +
                " " + menny +
                " " + urmertek +
                " " + uzemanyag +
                " " + ar +
                " " + valuta;
    }

}
