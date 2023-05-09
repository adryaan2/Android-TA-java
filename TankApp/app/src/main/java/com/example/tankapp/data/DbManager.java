package com.example.tankapp.data;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Egyke DatabaseHelper példány kérhető el az osztály egy példányától.
 * A DbManager osztály singleton példányát statikus metódussal kérhetjük el.
 * Az egész adatbázist érintő műveleteket végez.
 */
public class DbManager {
    private static DbManager dbManagerSingleton;
    private DatabaseHelper dbHelperSingleton;
    public static final String DEFAULT_DBNAME = DatabaseHelper.DEFAULT_DBNAME;

    private DbManager(){}

    public static DbManager getInstance(){
        if(dbManagerSingleton ==null) dbManagerSingleton = new DbManager();
        return dbManagerSingleton;
    }

    public DatabaseHelper getDbHelper(){
        if(dbHelperSingleton ==null) dbHelperSingleton =new DatabaseHelper();
        return dbHelperSingleton;
    }

    public String currentDbName(){
        return dbHelperSingleton.getDatabaseName();
    }

    /**
     * A megadott névvel másolja le az aktuálisan megnyitott adatbázisfájlt.
     * A másolás után a getDbHelper-rel kérhető DatabaseHelper objektum
     * már ezt az új adatbázist kezeli.
     * @param dbNev - az új adatbázis neve.
     */
    public void exportDb(String dbNev){
        File aktDb = new File(dbHelperSingleton.db.getPath());

        File ujDb = new File(aktDb.getParentFile(),dbNev+".db");
        try{
            copyFile(aktDb, ujDb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbHelperSingleton.close();
        dbHelperSingleton = new DatabaseHelper(dbNev+".db");
    }

    /**
     * Bezárja és kitörli a megnyitott adatbázisfájlt.
     */
    public void deleteExportedDb(){
        File aktDb = new File(dbHelperSingleton.db.getPath());
        dbHelperSingleton.close();
        SQLiteDatabase.deleteDatabase(aktDb);
        dbHelperSingleton = new DatabaseHelper();
    }

    /**
     * A megadott nevű adatbázist tölti be. Ha nem létezik, létrejön.
     * @param dbNev - A megnyitandó adatbázis neve ('.db' végződés nélkül)
     */
    public void loadDb(String dbNev){
        dbHelperSingleton.close();
        dbHelperSingleton = new DatabaseHelper(dbNev+".db");
    }

    private void copyFile(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}
