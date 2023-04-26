package com.example.tankapp.ui.stats;

public class TankolasokSzamaBontasban {
    private String idoszak;
    private int tankolasokSzama;

    public TankolasokSzamaBontasban(String idoszak, int tankolasokSzama) {
        this.idoszak = idoszak;
        this.tankolasokSzama = tankolasokSzama;
    }

    public String getIdoszak() {
        return idoszak;
    }

    public int getTankolasokSzama() {
        return tankolasokSzama;
    }
}
