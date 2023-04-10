package com.example.tankapp.data;

import androidx.annotation.NonNull;

import java.security.PublicKey;

public class TankolasOsszetett {
    private String datum; //SQLite Stringként tárolja 2007-01-01 formátumban
    private String auto;
    private int megtett_tav;
    private String tavolsagEgyseg;
    private String valuta;
    private String uzemanyag;
    private String urmertek;
    private float ar;
    private float menny;

    public TankolasOsszetett(String datum, String auto, int megtett_tav, String tavolsagEgyseg, String valuta, String uzemanyag, String urmertek, float ar, float menny) {
        this.datum = datum;
        this.auto = auto;
        this.megtett_tav = megtett_tav;
        this.tavolsagEgyseg = tavolsagEgyseg;
        this.valuta = valuta;
        this.uzemanyag = uzemanyag;
        this.urmertek = urmertek;
        this.ar = ar;
        this.menny = menny;
    }

    public String getDatum() {
        return datum;
    }

    public String getAuto() {
        return auto;
    }

    public int getMegtett_tav() {
        return megtett_tav;
    }

    public String getTavolsagEgyseg() {
        return tavolsagEgyseg;
    }

    public String getValuta() {
        return valuta;
    }

    public String getUzemanyag() {
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

    public String xFizetve(){
        return (ar*menny)+" "+valuta;
    }
    public String xTankoltMennyiseg(){
        return menny+" "+urmertek;
    }
    public String xMegtettUt(){
        return megtett_tav+" "+tavolsagEgyseg;
    }

    public String xEgysegar(){
        return ar+" "+valuta+"/"+urmertek;
    }

    @Override
    public String toString() {
        return datum + ' ' +
                auto + ' ' +
                megtett_tav +
                " " + tavolsagEgyseg +
                " " + menny +
                " " + urmertek +
                " " + uzemanyag +
                " " + ar +
                " " + valuta;
    }
}
