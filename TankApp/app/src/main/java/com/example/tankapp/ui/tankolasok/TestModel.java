package com.example.tankapp.ui.tankolasok;

public class TestModel {
    String datum;
    String tav;
    String egysegar;
    String menny;
    String uzema_tipus;

    public TestModel(String datum, String tav, String egysegar, String menny, String uzema_tipus) {
        this.datum = datum;
        this.tav = tav;
        this.egysegar = egysegar;
        this.menny = menny;
        this.uzema_tipus = uzema_tipus;
    }

    public String getDatum() {
        return datum;
    }

    public String getTav() {
        return tav;
    }

    public String getEgysegar() {
        return egysegar;
    }

    public String getMenny() {
        return menny;
    }

    public String getUzema_tipus() {
        return uzema_tipus;
    }
}
