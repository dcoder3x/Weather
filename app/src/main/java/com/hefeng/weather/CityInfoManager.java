package com.hefeng.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hefeng.weather.db.AppSQLiteHelper;
import com.hefeng.weather.db.WeatherContract;
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
    private AppSQLiteHelper mAppSQLiteHelper;

    public CityInfoManager(Context context) {
        mDataFetcher = new DataFetcher();
        mAppSQLiteHelper = new AppSQLiteHelper(context);
    }

    public void StoreChinaCityInfo() {
        SQLiteDatabase db = mAppSQLiteHelper.getWritableDatabase();
        ArrayList<ContentValues> arrayList = mDataFetcher.downloadAllCityInfo();
        // TODO: store city info to SQLite.
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues values = arrayList.get(i);
            db.insert(WeatherContract.CityColumns.TABLE_NAME, null, values);
        }
        db.close();
        Log.i(TAG, "insert city info to sqlite.");
    }

    /**
     * search city
     */
    public ArrayList<City> getSearchResult(String cityName) {
        Cursor cursor = mAppSQLiteHelper.queryCity(cityName);
        ArrayList<City> cities = new ArrayList<>();
        if (cursor.getCount() <= 0) {
            return null;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            City city = new City(cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.ID)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.CITY_EN)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.CITY_ZH)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.PROVINCE_EN)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.PROVINCE_ZH)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.LEADER_EN)),
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.LEANDER_ZH)));
            cities.add(city);
        }
        return cities;
    }
}
