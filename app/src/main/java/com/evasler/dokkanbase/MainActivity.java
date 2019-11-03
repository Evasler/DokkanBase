package com.evasler.dokkanbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, CardWikiFragment.OnFragmentInteractionListener,
    TeamBuilderFragment.OnFragmentInteractionListener, UserBoxFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInstallOrUpdate();
        loadFragment(R.id.homeButton);
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

    public void changeFragment(@NonNull View view) {
        String viewTag = (String) view.getTag();
        String fragmentTag = (String) findViewById(R.id.fragmentContainer).getTag();

        if (!viewTag.equals(fragmentTag)) {
            loadFragment(view.getId());
        }
    }

    public void loadFragment(int buttonId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (buttonId) {
            case R.id.homeButton:
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.fragmentContainer, homeFragment);
                findViewById(R.id.fragmentContainer).setTag("Home");
                break;
            case R.id.cardWikiButton:
                CardWikiFragment cardWikiFragment = new CardWikiFragment();
                transaction.replace(R.id.fragmentContainer, cardWikiFragment);
                findViewById(R.id.fragmentContainer).setTag("Card Wiki");
                break;
            case R.id.teamBuilderButton:
                TeamBuilderFragment teamBuilderFragment = new TeamBuilderFragment();
                transaction.replace(R.id.fragmentContainer, teamBuilderFragment);
                findViewById(R.id.fragmentContainer).setTag("Team Builder");
                break;
            case R.id.userBoxButton:
                UserBoxFragment userBoxFragment = new UserBoxFragment();
                transaction.replace(R.id.fragmentContainer, userBoxFragment);
                findViewById(R.id.fragmentContainer).setTag("User Box");
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(@NonNull Uri uri) {}
}
