package com.example.tankapp.data;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbManager {
    private static DbManager dbManagerSingleton;
    private DatabaseHelper dbHelperSingleton;
    private DatabaseHelper seged;
    private String dbDirectory;
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

    public String dbDirectory(){
        return dbHelperSingleton.getDbDirectory();
    }

    public void exportDb(String dbNev){
        File aktDb = new File(dbHelperSingleton.db.getPath());

        Log.d("DB_PATH", dbHelperSingleton.db.getPath());
        File ujDb = new File(aktDb.getParentFile(),dbNev+".db");
        try{
            copyFile(aktDb, ujDb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbHelperSingleton.close();
        dbHelperSingleton = new DatabaseHelper(dbNev+".db");


        //Log.d("ujdb","darabszam: " + dbHelperSingleton.getJarmuvekSzama());

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
