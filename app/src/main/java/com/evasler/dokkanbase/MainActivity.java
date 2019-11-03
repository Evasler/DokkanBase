package com.evasler.dokkanbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInstallOrUpdate();
    }

    protected void onInstallOrUpdate() {
        try {
            Log.d("myLogs", "Checking if this is a new version...");
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = pInfo.versionCode;
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);

            if (prefs.getInt("versionCode", -1) < versionCode) {
                Log.d("myLogs", "It is a new version");
                transferDatabase();
                prefs.edit().putInt("versionCode", versionCode).apply();
            } else {
                Log.d("myLogs", "It is the current version");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void transferDatabase() {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        helper.transferDatabase();
    }
}
