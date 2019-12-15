package com.evasler.dokkanbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, CardWikiFragment.OnFragmentInteractionListener,
    TeamBuilderFragment.OnFragmentInteractionListener, UserBoxFragment.OnFragmentInteractionListener, CardPreview.OnFragmentInteractionListener {

    HomeFragment homeFragment;
    CardWikiFragment cardWikiFragment;
    TeamBuilderFragment teamBuilderFragment;
    UserBoxFragment userBoxFragment;

    LruCache<String, Bitmap> memoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInstallOrUpdate();
        initializeCache();

        if (savedInstanceState != null) {
            cardWikiFragment = (CardWikiFragment) getSupportFragmentManager().getFragment(savedInstanceState, "cardWikiFragment");
        } else {
            cardWikiFragment = new CardWikiFragment();
        }
        homeFragment = new HomeFragment();

        teamBuilderFragment = new TeamBuilderFragment();
        userBoxFragment = new UserBoxFragment();

        loadFragment(R.id.homeButton);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "cardWikiFragment", cardWikiFragment);
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

        if (viewTag.equals("User Box")) {
            LongOperation test = new LongOperation();
            test.execute(this);
        }
    }

    public void loadFragment(int buttonId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (buttonId) {
            case R.id.homeButton:
                transaction.replace(R.id.fragmentContainer, homeFragment);
                findViewById(R.id.fragmentContainer).setTag("Home");
                break;
            case R.id.cardWikiButton:
                transaction.replace(R.id.fragmentContainer, cardWikiFragment);
                findViewById(R.id.fragmentContainer).setTag("Card Wiki");
                break;
            case R.id.teamBuilderButton:
                transaction.replace(R.id.fragmentContainer, teamBuilderFragment);
                findViewById(R.id.fragmentContainer).setTag("Team Builder");
                break;
            case R.id.userBoxButton:
                transaction.replace(R.id.fragmentContainer, userBoxFragment);
                findViewById(R.id.fragmentContainer).setTag("User Box");
                break;
        }
        transaction.commit();
    }

    private void initializeCache() {

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int availableMemoryInBytes = 40 * 1024 * 1024;//am.getMemoryClass() * 1024 * 1024;

        memoryCache = new LruCache<String, Bitmap>(availableMemoryInBytes) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public LruCache<String, Bitmap> getCache() {
        return memoryCache;
    }

    public void populate_grid(View view) {
        cardWikiFragment.populate_grid();
    }

    final class LongOperation extends AsyncTask<Context, Void, String> {

        Context context;

        @Override
        protected String doInBackground(Context... params) {
            context = params[0];
            /*MyDao myDao = AppDatabase.getDatabase(Objects.requireNonNull(context)).myDao();
            List<card> card_previews_data = myDao.getCardsForPreview();
            int bitmapByteSizeTotal = 0;
            for (card current_card : card_previews_data) {
                int resourceId = getResourceId("card_icon_" + current_card.getCard_id());
                if (resourceId != -1) {
                    memoryCache.put(current_card.getCard_id(), getBitmap(resourceId));
                    System.out.println("Bitmap size: " + getBitmap(resourceId).getByteCount());
                    bitmapByteSizeTotal += getBitmap(resourceId).getByteCount();
                }
            }
            System.out.println("Current Cache Size: " + memoryCache.size());
            System.out.println("Bitmap Total Size: " + bitmapByteSizeTotal);*/
            memoryCache.put("agl_background", getBitmap(R.drawable.agl_background));
            memoryCache.put("teq_background", getBitmap(R.drawable.teq_background));
            memoryCache.put("int_background", getBitmap(R.drawable.int_background));
            memoryCache.put("phy_background", getBitmap(R.drawable.phy_background));
            memoryCache.put("str_background", getBitmap(R.drawable.str_background));
            memoryCache.put("super_agl", getBitmap(R.drawable.super_agl));
            memoryCache.put("super_teq", getBitmap(R.drawable.super_teq));
            memoryCache.put("super_int", getBitmap(R.drawable.super_int));
            memoryCache.put("super_phy", getBitmap(R.drawable.super_phy));
            memoryCache.put("super_str", getBitmap(R.drawable.super_str));
            memoryCache.put("extreme_agl", getBitmap(R.drawable.extreme_agl));
            memoryCache.put("extreme_teq", getBitmap(R.drawable.extreme_teq));
            memoryCache.put("extreme_int", getBitmap(R.drawable.extreme_int));
            memoryCache.put("extreme_phy", getBitmap(R.drawable.extreme_phy));
            memoryCache.put("extreme_str", getBitmap(R.drawable.extreme_str));
            memoryCache.put("n", getBitmap(R.drawable.n));
            memoryCache.put("r", getBitmap(R.drawable.r));
            memoryCache.put("sr", getBitmap(R.drawable.sr));
            memoryCache.put("ssr", getBitmap(R.drawable.ssr));
            memoryCache.put("ur", getBitmap(R.drawable.ur));
            memoryCache.put("lr", getBitmap(R.drawable.lr));
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        public int getResourceId(@NonNull String pVariableName)
        {
            try {
                return getResources().getIdentifier(pVariableName, "drawable", Objects.requireNonNull(context).getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        private Bitmap getBitmap(int resourceId) {
            return BitmapFactory.decodeResource(context.getResources(), resourceId);
        }
    }

    @Override
    public void onFragmentInteraction(@NonNull Uri uri) {}
}
