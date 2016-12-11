package com.hefeng.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener, WeatherFragment.OnFragmentInteractionListener {

    public static final String BUNDLE_FROM = "com.hefeng.weather.from";
    public static final String BUNDLE_INFO = "com.hefeng.weather.cityId";
    private static final String TAG = "MainActivity";
    public static final String IS_STORED_CITY_INFO = "com.hefeng.weather.IS_STORED_CITY_INFO";
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        new DownloadChinaCityInfo().execute();
        fm.beginTransaction()
                .add(R.id.activity_main, new WeatherFragment())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {
        if (bundle.getString(BUNDLE_FROM, "").equals(AddCityFragment.TAG)) {
            fm.beginTransaction()
                    .hide(fm.findFragmentById(R.id.activity_main))
                    .add(R.id.activity_main, WeatherFragment.newInstance(bundle.getString(BUNDLE_INFO)))
                    .commit();
        }
        if (bundle.getString(BUNDLE_FROM, "").equals(WeatherFragment.TAG)) {
            fm.beginTransaction()
                    .hide(fm.findFragmentById(R.id.activity_main))
                    .add(R.id.activity_main, new AddCityFragment())
                    .commit();
        }
    }

    private class DownloadChinaCityInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPreferences = getSharedPreferences(IS_STORED_CITY_INFO, Context.MODE_PRIVATE);
            boolean isStored = sharedPreferences.getBoolean(IS_STORED_CITY_INFO, false);
            if (!isStored) {
                SQLiteDataManager cim = new SQLiteDataManager(getApplicationContext());
                if (cim.StoreChinaCityInfo()) {
                    sharedPreferences.edit()
                            .putBoolean(IS_STORED_CITY_INFO, true)
                            .apply();
                } else {
                    Log.i(TAG, "can't store city info.");
                }

            } else {
                Log.i(TAG, "city info already exist!");
            }
            return null;
        }
    }
}
