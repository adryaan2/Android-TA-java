package com.example.tankapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TankolasKonyvelesek.db";
    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        context.deleteDatabase("TankolasKonyvelesek.db");
    }

    /// Csak a Tankolasokat kéne lemodellezni
    /// mert a többi csak egy mező és az AUTOINCREMENT id ???
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Autok (autoId INTEGER PRIMARY KEY AUTOINCREMENT, rendszam TEXT)"); //2db
        db.execSQL("CREATE TABLE Valutak (id INTEGER PRIMARY KEY AUTOINCREMENT, valuta TEXT)"); //huf, Usd
        db.execSQL("CREATE TABLE Urmertekek (id INTEGER PRIMARY KEY AUTOINCREMENT, urmertek TEXT)"); //l,gl
        db.execSQL("CREATE TABLE Tavolsagok (id INTEGER PRIMARY KEY AUTOINCREMENT, tavolsag TEXT)"); //km, miles
        db.execSQL("CREATE TABLE Uzemanyagok (uzemanyagId INTEGER PRIMARY KEY AUTOINCREMENT, megnev TEXT)"); //benzin, diesel
        db.execSQL("CREATE TABLE Tankolasok (tankId INTEGER PRIMARY KEY AUTOINCREMENT, datum DATE, autoId INTEGER, megtett_tav INTEGER, tavolsagId INTEGER, ar FLOAT, valutaId INTEGER, menny FLOAT, uzemanyagId INTEGER, urmertekId INTEGER, FOREIGN KEY (autoId) REFERENCES Autok(autoId), FOREIGN KEY (tavolsagId) REFERENCES Tavolsagok(id), FOREIGN KEY (valutaId) REFERENCES Valutak(id), FOREIGN KEY (uzemanyagId) REFERENCES Uzemanyagok(uzemanyagId), FOREIGN KEY (urmertekId) REFERENCES Urmertekek(id) )");
        //setDefaultLabel(db);
        this.db = db;
        feltolt();
    }

    private void feltolt(){
        addAutok("ABC-123");
        addAutok("DEF-456");


        addValutak("HUF");
        addValutak("USD");

        addUrmertekek("liter");
        addUrmertekek("gallon");

        addTavolsagok("méter");
        addTavolsagok("mérföld");

        addUzemanyagok("benzin");
        addUzemanyagok("diesel");

        addTankolasok("2023.03.26", 1, 350, 1, 2560, 1, 27,2, 1);
        addTankolasok("2023.04.13", 2, 150, 2, 20, 2, 23,1, 1);
        addTankolasok("2023.04.23", 2, 276, 1, 2000, 1, 18,1, 1);
        addTankolasok("2023.05.17", 1, 220, 2, 50, 2, 10,2, 2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public void addAutok(String Rendszam) {
        ContentValues values = new ContentValues();
        values.put("rendszam", Rendszam);
        db.insert("Autok", null, values);
    }

    public void addValutak(String Valuta) {
        ContentValues values = new ContentValues();
        values.put("valuta", Valuta);
        db.insert("Valutak", null, values);
    }

    public void addUrmertekek(String Urmertek) {
        ContentValues values = new ContentValues();
        values.put("urmertek", Urmertek);
        db.insert("Urmertekek", null,values);
    }

    public void addTavolsagok(String Tavolsag) {
        ContentValues values = new ContentValues();
        values.put("tavolsag", Tavolsag);
        db.insert("Tavolsagok", null, values);
    }

    public void addUzemanyagok(String Megnev) {
        ContentValues values = new ContentValues();
        values.put("megnev", Megnev);
        db.insert("Uzemanyagok",null,values);
    }

    public void addTankolasok(String Datum, int AutoId, int Megtett_tav, int TavolsagId, float Ar, int ValutaId, float Menny, int UzemanyagId, int UrmertekId ) {
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

    public void addTankolasModel(TankolasModel tankolasModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("datum", tankolasModel.getDatum());
        contentValues.put("autoId", tankolasModel.getAutoId());
        contentValues.put("megtett_tav", tankolasModel.getMegtett_tav());
        contentValues.put("tavolsagId", tankolasModel.getTavolsagId());
        contentValues.put("valutaId", tankolasModel.getValutaId());
        contentValues.put("uzemanyagId", tankolasModel.getUzemanyagId());
        contentValues.put("urmertekId", tankolasModel.getUrmertekId());
        contentValues.put("ar", tankolasModel.getAr());
        contentValues.put("menny", tankolasModel.getMenny());
        db = this.getWritableDatabase();
        db.insert("Tankolasok", null,contentValues);
    }

    public ArrayList<TankolasOsszetett> osszesTankolas(){
        String sql = "SELECT rendszam, valuta, urmertek, tavolsag, megnev, datum, megtett_tav, ar, menny ";
        sql+= "FROM Tankolasok INNER JOIN Autok ON Autok.autoId = Tankolasok.autoId ";
        sql+= "INNER JOIN Valutak ON Valutak.id = Tankolasok.valutaId ";
        sql+= "INNER JOIN Urmertekek ON Urmertekek.id = Tankolasok.urmertekId ";
        sql+= "INNER JOIN Tavolsagok ON Tavolsagok.id = Tankolasok.tavolsagId ";
        sql+= "INNER JOIN Uzemanyagok ON Uzemanyagok.uzemanyagId = Tankolasok.uzemanyagId ";
        sql+= "ORDER BY datum DESC";

        db = this.getWritableDatabase();
        ArrayList<TankolasOsszetett> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String rendsz = cursor.getString(0);
                String valuta = cursor.getString(1);
                String urmertek = cursor.getString(2);
                String tavolsagEgyseg = cursor.getString(3);
                String uzemanyag = cursor.getString(4);
                String datum = cursor.getString(5);
                int megtett_tav = cursor.getInt(6);
                float ar = cursor.getFloat(7);
                float menny = cursor.getFloat(8);
                lista.add(new TankolasOsszetett(datum,
                        rendsz, megtett_tav, tavolsagEgyseg,
                        valuta, uzemanyag, urmertek, ar, menny));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

}
