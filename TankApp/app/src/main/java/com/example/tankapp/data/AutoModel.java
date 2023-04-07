package com.example.tankapp.data;

public class AutoModel {
    private int autoId;
    private String rendszam;

    public AutoModel(int autoId, String rendszam) {
        this.autoId = autoId;
        this.rendszam = rendszam;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }
}
