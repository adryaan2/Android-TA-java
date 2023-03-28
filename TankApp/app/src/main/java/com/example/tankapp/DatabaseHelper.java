package com.example.tankapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "TankolasKonyvelesek.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Autok (autoId INTEGER PRIMARY KEY AUTOINCREMENT, rendszam TEXT)");
        db.execSQL("CREATE TABLE Valutak (id INTEGER PRIMARY KEY AUTOINCREMENT, valuta TEXT)");
        db.execSQL("CREATE TABLE Urmertekek (id INTEGER PRIMARY KEY AUTOINCREMENT, urmertek TEXT)");
        db.execSQL("CREATE TABLE Tavolsagok (id INTEGER PRIMARY KEY AUTOINCREMENT, tavolsag TEXT)");
        db.execSQL("CREATE TABLE Uzemanyagok (uzemanyagId INTEGER PRIMARY KEY AUTOINCREMENT, megnev TEXT)");
        db.execSQL("CREATE TABLE Tankolasok (tankId INTEGER PRIMARY KEY AUTOINCREMENT, datum DATE, autoId INTEGER, megtett_tav INTEGER, tavolsagId INTEGER, ar FLOAT, valutaId INTEGER, menny FLOAT, uzemanyagId INTEGER, urmertekId INTEGER, FOREIGN KEY (autoId) REFERENCES Autok(autoId), FOREIGN KEY (tavolsagId) REFERENCES Tavolsagok(id), FOREIGN KEY (valutaId) REFERENCES Valutak(id), FOREIGN KEY (uzemanyagId) REFERENCES Uzemanyagok(uzemanyagId), FOREIGN KEY (urmertekId) REFERENCES Urmertekek(id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
