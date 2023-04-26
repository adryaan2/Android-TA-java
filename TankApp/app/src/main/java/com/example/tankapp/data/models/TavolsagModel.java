package com.example.tankapp.data.models;

public class TavolsagModel {
    private int id;
    private String tavolsag;

    public TavolsagModel(int id, String tavolsag) {
        this.id = id;
        this.tavolsag = tavolsag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTavolsag() {
        return tavolsag;
    }

    public void setTavolsag(String tavolsag) {
        this.tavolsag = tavolsag;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", tavolsag='" + tavolsag;
    }
}
