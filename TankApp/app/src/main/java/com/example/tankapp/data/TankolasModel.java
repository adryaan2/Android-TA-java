package com.example.tankapp.data;

public class TankolasModel {
    private Integer tankId;
    private long datum; // napok száma 1970.01.01-től
    private int autoId;
    private int megtett_tav;
    private int tavolsagId;
    private int valutaId;
    private int uzemanyagId;
    private int urmertekId;
    private float ar;
    private float menny;

    public TankolasModel(Integer tankId, long datum, int autoId, int megtett_tav, int tavolsagId, int valutaId, int uzemanyagId, int urmertekId, float ar, float menny) {
        this.tankId = tankId;
        this.datum = datum;
        this.autoId = autoId;
        this.megtett_tav = megtett_tav;
        this.tavolsagId = tavolsagId;
        this.valutaId = valutaId;
        this.uzemanyagId = uzemanyagId;
        this.urmertekId = urmertekId;
        this.ar = ar;
        this.menny = menny;
    }

    public int getTankId() {
        return tankId;
    }

    public void setTankId(int tankId) {
        this.tankId = tankId;
    }

    public long getDatum() {
        return datum;
    }

    public void setDatum(long datum) {
        this.datum = datum;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public int getMegtett_tav() {
        return megtett_tav;
    }

    public void setMegtett_tav(int megtett_tav) {
        this.megtett_tav = megtett_tav;
    }

    public int getTavolsagId() {
        return tavolsagId;
    }

    public void setTavolsagId(int tavolsagId) {
        this.tavolsagId = tavolsagId;
    }

    public int getValutaId() {
        return valutaId;
    }

    public void setValutaId(int valutaId) {
        this.valutaId = valutaId;
    }

    public int getUzemanyagId() {
        return uzemanyagId;
    }

    public void setUzemanyagId(int uzemanyagId) {
        this.uzemanyagId = uzemanyagId;
    }

    public int getUrmertekId() {
        return urmertekId;
    }

    public void setUrmertekId(int urmertekId) {
        this.urmertekId = urmertekId;
    }

    public float getAr() {
        return ar;
    }

    public void setAr(float ar) {
        this.ar = ar;
    }

    public float getMenny() {
        return menny;
    }

    public void setMenny(float menny) {
        this.menny = menny;
    }
}
