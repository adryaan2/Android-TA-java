package com.example.tankapp.data;

public class UrmertekModel {
    private int id;
    private String urmertek;

    public UrmertekModel(int id, String urmertek) {
        this.id = id;
        this.urmertek = urmertek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrmertek() {
        return urmertek;
    }

    public void setUrmertek(String urmertek) {
        this.urmertek = urmertek;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", urmertek='" + urmertek;
    }
}
