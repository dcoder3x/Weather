package com.hefeng.weather;

import android.util.Log;

import com.hefeng.weather.fetch.DataFetcher;
import com.hefeng.weather.model.City;

import java.util.ArrayList;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public class CityInfoManager {

    private static final String TAG = "CityInfoManager";
    private DataFetcher mDataFetcher;

    public CityInfoManager() {
        mDataFetcher = new DataFetcher();
    }

    public void StoreChinaCityInfo() {
        ArrayList<City> arrayList = mDataFetcher.downloadAllCityInfo();
        Log.d(TAG, arrayList.size() + " city object.");
        // TODO: store city info to SQLite.
    }
}
