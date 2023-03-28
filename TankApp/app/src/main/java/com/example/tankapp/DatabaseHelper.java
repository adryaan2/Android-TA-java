package com.example.tankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(@Nullable Context context) {
        super(context, "TankolasKonyvelesek.db", null, 1);
        context.deleteDatabase("TankolasKonyvelesek.db");
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Autok (autoid INTEGER PRIMARY KEY AUTOINCREMENT, rendszam TEXT)"); //2db
        db.execSQL("CREATE TABLE Valutak (id INTEGER PRIMARY KEY AUTOINCREMENT, valuta TEXT)"); //huf, Usd
        db.execSQL("CREATE TABLE Urmertekek (id INTEGER PRIMARY KEY AUTOINCREMENT, urmertek TEXT)"); //l,gl
        db.execSQL("CREATE TABLE Tavolsagok (id INTEGER PRIMARY KEY AUTOINCREMENT, tavolsag TEXT)"); //km, miles
        db.execSQL("CREATE TABLE Uzemanyagok (uzemanyagId INTEGER PRIMARY KEY AUTOINCREMENT, megnev TEXT)"); //benzin, diesel
        db.execSQL("CREATE TABLE Tankolasok (tankId INTEGER PRIMARY KEY AUTOINCREMENT, datum DATE, autoId INTEGER, megtett_tav INTEGER, tavolsagId INTEGER, ar FLOAT, valutaId INTEGER, menny FLOAT, uzemanyagId INTEGER, urmertekId INTEGER, FOREIGN KEY (autoId) REFERENCES Autok(autoId), FOREIGN KEY (tavolsagId) REFERENCES Tavolsagok(id), FOREIGN KEY (valutaId) REFERENCES Valutak(id), FOREIGN KEY (uzemanyagId) REFERENCES Uzemanyagok(uzemanyagId), FOREIGN KEY (urmertekId) REFERENCES Urmertekek(id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public void addAutok(String Rendszam) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rendszam", Rendszam);


        db.insert("Autok", null, values);
    }

    public void addValutak(String Valuta) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("valuta", Valuta);

        db.insert("Valutak", null, values);
    }

    public void addUrmertekek(String Urmertek) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("urmertek", Urmertek);

        db.insert("Urmertekek", null,values);
    }

    public void addTavolsagok(String Tavolsag) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tavolsag", Tavolsag);

        db.insert("Tavolsagok", null, values);
    }

    public void addUzemanyagok(String Megnev) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("megnev", Megnev);

        db.insert("Uzemanyagok",null,values);
    }

    public void addTankolasok(String Datum, int AutoId, int Megtett_tav, int TavolsagId, float Ar, int ValutaId, float Menny, int UzemanyagId, int UrmertekId ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("datum", Datum);
        values.put("autoId", AutoId);
        values.put("megtett_tav", Megtett_tav);
        values.put("tavolsagId", TavolsagId);
        values.put("ar", Ar);
        values.put("valutaId", ValutaId);
        values.put("menny", Menny);
        values.put("uzemanyagId", UzemanyagId);
        values.put("urmertekId", UrmertekId);

        db.insert("Tankolasok",null,values);

    }
}
