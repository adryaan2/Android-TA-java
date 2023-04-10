package com.example.tankapp.data;

public class AutoModel {
    private int autoId;
    private String rendszam;
    private String megj;

    public AutoModel(int autoId, String rendszam, String megj) {
        this.autoId = autoId;
        this.rendszam = rendszam;
        this.megj = megj;
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
    public String getMegj() {
        return megj;
    }

    @Override
    public String toString() {
        return "autoId=" + autoId +
                ", rendszam='" + rendszam + '\'' +
                ", megj='" + megj;
    }
}
