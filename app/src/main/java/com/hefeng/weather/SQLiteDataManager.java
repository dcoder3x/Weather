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

public class SQLiteDataManager {

    private static final String TAG = "SQLiteDataManager";
    private DataFetcher mDataFetcher;
    private AppSQLiteHelper mAppSQLiteHelper;

    public SQLiteDataManager(Context context) {
        mDataFetcher = new DataFetcher();
        mAppSQLiteHelper = new AppSQLiteHelper(context);
    }

    /**
     * store city info,just once.
     */
    public boolean StoreChinaCityInfo() {
        SQLiteDatabase db = mAppSQLiteHelper.getWritableDatabase();
        ArrayList<ContentValues> arrayList = mDataFetcher.downloadAllCityInfo();
        // TODO: store city info to SQLite.
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues values = arrayList.get(i);
            db.insert(WeatherContract.CityColumns.TABLE_NAME, null, values);
        }
        db.close();
        if (arrayList.size() > 0) {
            Log.i(TAG, "insert city info to sqlite.");
            return true;
        } else {
            return false;
        }
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
                    cursor.getString(cursor.getColumnIndex(WeatherContract.CityColumns.LEANDER_ZH)),
                    cursor.getInt(cursor.getColumnIndex(WeatherContract.CityColumns.IS_ADDED)));
            cities.add(city);
        }
        cursor.close();
        return cities;
    }

    /**
     * change table city, set isAdded 1
     */
    public void changeIsAdded(String id) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.CityColumns.IS_ADDED, 1);
        mAppSQLiteHelper.getWritableDatabase()
                .update(WeatherContract.CityColumns.TABLE_NAME,
                        values,
                        WeatherContract.CityColumns.ID + " = ? ",
                        new String[] {id});
    }

    /**
     * add weather data.
     */
    public boolean insertWeatherInfo(String id) {
        SQLiteDatabase db = mAppSQLiteHelper.getWritableDatabase();
        ContentValues values = mDataFetcher.fetchNowWeather(id);
        if (values != null) {
            db.insert(WeatherContract.WeaColumns.TABLE_NAME, null, values);
            return true;
        } else {
            Log.e(TAG, "no weather info inserted.");
            return false;
        }

    }

    public Cursor queryWeather() {
        return mAppSQLiteHelper.queryWeather();
    }
}
