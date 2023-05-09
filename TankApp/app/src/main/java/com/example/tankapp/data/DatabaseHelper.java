package com.example.tankapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tankapp.MainActivity;
import com.example.tankapp.data.models.AutoModel;
import com.example.tankapp.data.models.TankolasModel;
import com.example.tankapp.data.models.TavolsagModel;
import com.example.tankapp.data.models.UrmertekModel;
import com.example.tankapp.data.models.UzemanyagModel;
import com.example.tankapp.data.models.ValutaModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DEFAULT_DBNAME = "TankolasKonyvelesek.db";
    protected SQLiteDatabase db;

    public DatabaseHelper() {
        super(MainActivity.getContext(), DEFAULT_DBNAME, null, 1);
    }
    public DatabaseHelper(String dbName) {
        super(MainActivity.getContext(), dbName, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Autok (autoId INTEGER PRIMARY KEY AUTOINCREMENT, rendszam TEXT UNIQUE, megj TEXT)"); //2db
        db.execSQL("CREATE TABLE Valutak (id INTEGER PRIMARY KEY AUTOINCREMENT, valuta TEXT)"); //huf, Usd
        db.execSQL("CREATE TABLE Urmertekek (id INTEGER PRIMARY KEY AUTOINCREMENT, urmertek TEXT)"); //l,gl
        db.execSQL("CREATE TABLE Tavolsagok (id INTEGER PRIMARY KEY AUTOINCREMENT, tavolsag TEXT)"); //km, miles
        db.execSQL("CREATE TABLE Uzemanyagok (uzemanyagId INTEGER PRIMARY KEY AUTOINCREMENT, megnev TEXT)"); //benzin, diesel
        db.execSQL("CREATE TABLE Tankolasok (tankId INTEGER PRIMARY KEY AUTOINCREMENT, datum INTEGER, autoId INTEGER, megtett_tav INTEGER, tavolsagId INTEGER, ar FLOAT, valutaId INTEGER, menny FLOAT, uzemanyagId INTEGER, urmertekId INTEGER, FOREIGN KEY (autoId) REFERENCES Autok(autoId), FOREIGN KEY (tavolsagId) REFERENCES Tavolsagok(id), FOREIGN KEY (valutaId) REFERENCES Valutak(id), FOREIGN KEY (uzemanyagId) REFERENCES Uzemanyagok(uzemanyagId), FOREIGN KEY (urmertekId) REFERENCES Urmertekek(id) )");

        this.db = db;
        init();
        feltolt();
    }

    private void feltolt(){
        addAutok("ABC-123", "szürke");
        addAutok("DEF-456", "kék");

        addTankolasok(LocalDate.of(2023,4,18).toEpochDay(), 2, 150, 2, 20, 2, 23,1, 1);
        addTankolasok(LocalDate.of(2023,4,2).toEpochDay(), 2, 276, 1, 2000, 1, 18,1, 1);

        addTankolasok(LocalDate.of(2023,3,11).toEpochDay(), 1, 350, 1, 2560, 1, 27,2, 1);
        addTankolasok(LocalDate.of(2023,3,26).toEpochDay(), 1, 220, 2, 50, 2, 10,2, 2);
        addTankolasok(LocalDate.of(2023,2,2).toEpochDay(), 1, 220, 2, 50, 2, 10,2, 2);

        addTankolasok(LocalDate.of(2022,2,2).toEpochDay(), 1, 220, 2, 50, 2, 10,2, 2);
        addTankolasok(LocalDate.of(2022,2,2).toEpochDay(), 1, 220, 2, 50, 2, 10,2, 2);
        addTankolasok(LocalDate.of(2022,2,2).toEpochDay(), 1, 220, 2, 50, 2, 10,2, 2);
    }

    private void init(){
        addValutak("HUF");
        addValutak("USD");

        addUrmertekek("liter");
        addUrmertekek("gallon");

        addTavolsagok("km");
        addTavolsagok("mf");

        addUzemanyagok("benzin");
        addUzemanyagok("diesel");
    }

    public void dbTest(){
        ArrayList<TankolasOsszetett> ossz = getOsszesTankolas();
        for(TankolasOsszetett akt : ossz)
            Log.d("PROBA", akt.toString());

        /// --------------------------------

        for(AutoModel akt : getAutok())
            Log.d("AUTOK", akt.toString());

        Log.d("UTOLSO", getUtolsoTankolas().toString());

        Log.d("SZAMA", String.valueOf(getTankolasokSzama()));

        for(UzemanyagModel akt : getUzemanyagok())
            Log.d("UZEMA", akt.toString());

        for(ValutaModel akt : getValutak())
            Log.d("VALUTAK", akt.toString());

        for(UrmertekModel akt : getUrmertekek())
            Log.d("URMERT", akt.toString());

        for(TavolsagModel akt : getTavolsagok())
            Log.d("TAV", akt.toString());

        for(TankolasOsszetett akt : getTankolasokByAutoId(2))
            Log.d("byID", akt.toString());

        Log.d("AUTOK_SZAMA", String.valueOf(getJarmuvekSzama()));
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public void addAutok(String Rendszam, String megj) {
        ContentValues values = new ContentValues();
        values.put("rendszam", Rendszam);
        values.put("megj", megj);
        db.insertOrThrow("Autok", null, values);
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

    public void addTankolasok(long Datum, int AutoId, int Megtett_tav, int TavolsagId, float Ar, int ValutaId, float Menny, int UzemanyagId, int UrmertekId ) {
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

    public void addTankolasModel(@NonNull TankolasModel tankolasModel){
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

    public ArrayList<TankolasOsszetett> getOsszesTankolas(){
        String sql = "SELECT rendszam, valuta, urmertek, tavolsag, megnev, datum, megtett_tav, ar, menny ";
        sql+= "FROM Tankolasok INNER JOIN Autok ON Autok.autoId = Tankolasok.autoId ";
        sql+= "INNER JOIN Valutak ON Valutak.id = Tankolasok.valutaId ";
        sql+= "INNER JOIN Urmertekek ON Urmertekek.id = Tankolasok.urmertekId ";
        sql+= "INNER JOIN Tavolsagok ON Tavolsagok.id = Tankolasok.tavolsagId ";
        sql+= "INNER JOIN Uzemanyagok ON Uzemanyagok.uzemanyagId = Tankolasok.uzemanyagId ";
        sql+= "ORDER BY datum DESC";

        db = this.getReadableDatabase();
        ArrayList<TankolasOsszetett> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String rendsz = cursor.getString(0);
                String valuta = cursor.getString(1);
                String urmertek = cursor.getString(2);
                String tavolsagEgyseg = cursor.getString(3);
                String uzemanyag = cursor.getString(4);
                long datum = cursor.getLong(5);
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

    public ArrayList<AutoModel> getAutok(){
        String sql = "SELECT * FROM Autok";
        db = this.getReadableDatabase();
        ArrayList<AutoModel> autokLista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String rendsz = cursor.getString(1);
                String megj = cursor.getString(2);
                autokLista.add(new AutoModel(id ,rendsz, megj));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return autokLista;
    }

    public TankolasOsszetett getUtolsoTankolas(){
        String sql = "SELECT rendszam, valuta, urmertek, tavolsag, megnev, datum, megtett_tav, ar, menny ";
        sql+= "FROM Tankolasok INNER JOIN Autok ON Autok.autoId = Tankolasok.autoId ";
        sql+= "INNER JOIN Valutak ON Valutak.id = Tankolasok.valutaId ";
        sql+= "INNER JOIN Urmertekek ON Urmertekek.id = Tankolasok.urmertekId ";
        sql+= "INNER JOIN Tavolsagok ON Tavolsagok.id = Tankolasok.tavolsagId ";
        sql+= "INNER JOIN Uzemanyagok ON Uzemanyagok.uzemanyagId = Tankolasok.uzemanyagId ";
        sql+= "ORDER BY datum DESC LIMIT 1";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            String rendsz = cursor.getString(0);
            String valuta = cursor.getString(1);
            String urmertek = cursor.getString(2);
            String tavolsagEgyseg = cursor.getString(3);
            String uzemanyag = cursor.getString(4);
            long datum = cursor.getLong(5);
            int megtett_tav = cursor.getInt(6);
            float ar = cursor.getFloat(7);
            float menny = cursor.getFloat(8);
            cursor.close();
            return new TankolasOsszetett(datum,
                    rendsz, megtett_tav, tavolsagEgyseg,
                    valuta, uzemanyag, urmertek, ar, menny);
        }else{
            cursor.close();
            return null;
        }
    }

    /**
     * @return Az aktivJarmu tankolásainak száma.
     */
    public int getTankolasokSzama(){
        String sql="SELECT COUNT(*) FROM Tankolasok WHERE autoId="+ MainActivity.aktivJarmu.getAutoId();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            int x = cursor.getInt(0);
            cursor.close();
            return x;
        }else{
            cursor.close();
            return -1;
        }
    }

    public ArrayList<UzemanyagModel> getUzemanyagok(){
        String sql = "SELECT * FROM Uzemanyagok";
        db = this.getReadableDatabase();
        ArrayList<UzemanyagModel> uzemanyagLista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String megnev = cursor.getString(1);
                uzemanyagLista.add(new UzemanyagModel(id ,megnev));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return uzemanyagLista;
    }

    public ArrayList<ValutaModel> getValutak(){
        String sql = "SELECT * FROM Valutak";
        db = this.getReadableDatabase();
        ArrayList<ValutaModel> valutaLista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String megnev = cursor.getString(1);
                valutaLista.add(new ValutaModel(id ,megnev));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return valutaLista;
    }

    public ArrayList<UrmertekModel> getUrmertekek(){
        String sql = "SELECT * FROM Urmertekek";
        db = this.getReadableDatabase();
        ArrayList<UrmertekModel> urmertekLista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String megnev = cursor.getString(1);
                urmertekLista.add(new UrmertekModel(id ,megnev));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return urmertekLista;
    }

    public ArrayList<TavolsagModel> getTavolsagok(){
        String sql = "SELECT * FROM Tavolsagok";
        db = this.getReadableDatabase();
        ArrayList<TavolsagModel> tavLista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String megnev = cursor.getString(1);
                tavLista.add(new TavolsagModel(id ,megnev));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return tavLista;
    }

    public ArrayList<TankolasOsszetett> getTankolasokByAutoId(int autoId){
        String sql = "SELECT rendszam, valuta, urmertek, tavolsag, megnev, datum, megtett_tav, ar, menny ";
        sql+= "FROM Tankolasok INNER JOIN Autok ON Autok.autoId = Tankolasok.autoId ";
        sql+= "INNER JOIN Valutak ON Valutak.id = Tankolasok.valutaId ";
        sql+= "INNER JOIN Urmertekek ON Urmertekek.id = Tankolasok.urmertekId ";
        sql+= "INNER JOIN Tavolsagok ON Tavolsagok.id = Tankolasok.tavolsagId ";
        sql+= "INNER JOIN Uzemanyagok ON Uzemanyagok.uzemanyagId = Tankolasok.uzemanyagId ";
        sql+= "WHERE Tankolasok.autoId="+autoId+" ";
        sql+= "ORDER BY datum DESC";

        db = this.getReadableDatabase();
        ArrayList<TankolasOsszetett> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String rendsz = cursor.getString(0);
                String valuta = cursor.getString(1);
                String urmertek = cursor.getString(2);
                String tavolsagEgyseg = cursor.getString(3);
                String uzemanyag = cursor.getString(4);
                long datum = cursor.getLong(5);
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

    public ArrayList<LocalDate> getDatumokByAutoId(int autoId){
        String sql = "SELECT datum ";
        sql+= "FROM Tankolasok ";
        sql+= "WHERE Tankolasok.autoId="+autoId+" ";
        sql+= "ORDER BY datum DESC";

        db = this.getReadableDatabase();
        ArrayList<LocalDate> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                long datum = cursor.getLong(0);
                lista.add(LocalDate.ofEpochDay(datum));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public int getJarmuvekSzama(){
        String sql="SELECT COUNT(*) FROM Autok";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            int x = cursor.getInt(0);
            cursor.close();
            return x;
        }else{
            cursor.close();
            return -1;
        }
    }

    public void kiurit(){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM Tankolasok");
        db.execSQL("DELETE FROM Autok");
    }

    public String getDatabaseName(){return this.getReadableDatabase().getPath().substring(db.getPath().lastIndexOf('/')+1);}

    public String getDbDirectory(){
        return db.getPath().substring(0,this.getReadableDatabase().getPath().lastIndexOf('/')+1);
    }

}
