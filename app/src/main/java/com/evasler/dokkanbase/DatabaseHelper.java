package com.evasler.dokkanbase;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DatabaseHelper {

    private Context context;

    private static final String DB_NAME = "DokkanBattle.db";
    //private static final int DB_VERSION = 1;

    private final File dbPath;

    DatabaseHelper(Context context) {
        setContext(context);
        dbPath = context.getDatabasePath(DB_NAME);
    }

    private void setContext(Context context) {
        this.context = context;
    }

    /*void checkDb() {
        Log.d("myLogs", "Checking database...");
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);

        if (prefs.getInt("dbVersion", -1) < DB_VERSION) {
            Log.d("myLogs", "New version of database found");
            transferDatabase();
            prefs.edit().putInt("dbVersion", DB_VERSION).apply();
        } else {
            Log.d("myLogs", "Database is up to date");
        }
    }*/

    public void transferDatabase() {
        Log.d("myLogs", "Transfering database...");

        if(dbPath.exists()) {
            Log.d("myLogs", "Database already exists");
            deleteDbFile();
        } else if (!dbPath.getParentFile().exists()) {
            Log.d("myLogs", "Creating directory...");
            if (dbPath.getParentFile().mkdirs()) {
                Log.d("myLogs", "Successfully created directory");
            } else {
                Log.d("myLogs", "Failed to create directory");
            }
        }

        try {
            Log.d("myLogs", "Copying database...");
            final InputStream inputStream = context.getAssets().open(DB_NAME);
            final OutputStream outputStream = new FileOutputStream(dbPath);

            byte[] buffer = new byte[2048];
            int length;

            while ((length = inputStream.read(buffer, 0, 2048)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.d("myLogs", "Successfully copied database");
        } catch (IOException e) {
            Log.d("myLogs", "Failed to copy database");
            e.printStackTrace();
        }
    }

    private void deleteDbFile() {
        Log.d("myLogs", "Deleting database...");
        if (dbPath.delete()) {
            Log.d("myLogs", "Successfully deleted database");
        } else {
            Log.d("myLogs", "Failed to delete database");
        }
    }
}
