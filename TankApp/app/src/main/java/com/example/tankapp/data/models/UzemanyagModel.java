package com.example.tankapp.data.models;

public class UzemanyagModel {
    private int uzemanyagId;
    private String megnev;

    public UzemanyagModel(int uzemanyagId, String megnev) {
        this.uzemanyagId = uzemanyagId;
        this.megnev = megnev;
    }

    public int getUzemanyagId() {
        return uzemanyagId;
    }

    public void setUzemanyagId(int uzemanyagId) {
        this.uzemanyagId = uzemanyagId;
    }

    public String getMegnev() {
        return megnev;
    }

    public void setMegnev(String megnev) {
        this.megnev = megnev;
    }

    @Override
    public String toString() {
        return "uzemanyagId=" + uzemanyagId +
                ", megnev='" + megnev;
    }
}
