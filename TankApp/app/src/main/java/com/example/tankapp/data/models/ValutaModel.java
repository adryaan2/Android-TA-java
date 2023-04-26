package com.example.tankapp.data.models;

public class ValutaModel {
    private int id;
    private String valuta;

    public ValutaModel(int id, String valuta) {
        this.id = id;
        this.valuta = valuta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", valuta='" + valuta;
    }
}
