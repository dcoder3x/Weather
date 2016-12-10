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

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    public static final String IS_STORED_CITY_INFO = "com.hefeng.weather.IS_STORED_CITY_INFO";
    private FrameLayout mFrameLayout;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        new DownloadChinaCityInfo().execute();
        mFrameLayout = (FrameLayout) findViewById(R.id.activity_main);
        fm.beginTransaction()
                .add(R.id.activity_main, new AddCityFragment())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class DownloadChinaCityInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPreferences = getSharedPreferences(IS_STORED_CITY_INFO, Context.MODE_PRIVATE);
            boolean isStored = sharedPreferences.getBoolean(IS_STORED_CITY_INFO, false);
            if (!isStored) {
                CityInfoManager cim = new CityInfoManager(getApplicationContext());
                cim.StoreChinaCityInfo();
                sharedPreferences.edit()
                        .putBoolean(IS_STORED_CITY_INFO, true)
                        .apply();
            } else {
                Log.i(TAG, "city info already exist!");
            }
            return null;
        }
    }
}
