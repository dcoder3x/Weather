package com.hefeng.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

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
            CityInfoManager cim = new CityInfoManager();
            cim.StoreChinaCityInfo();
            return null;
        }
    }
}
