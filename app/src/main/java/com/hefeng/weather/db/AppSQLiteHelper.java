package com.hefeng.weather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public class AppSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_CITY = "create table " +
            WeatherContract.CityColumns.TABLE_NAME + "(" +
            WeatherContract.CityColumns._ID + " integer primary key autoincrement, " +
            WeatherContract.CityColumns.ID + " text, " +
            WeatherContract.CityColumns.CITY_EN + " text, " +
            WeatherContract.CityColumns.CITY_ZH + " text, " +
            WeatherContract.CityColumns.PROVINCE_EN + " text, " +
            WeatherContract.CityColumns.PROVINCE_ZH + " text, " +
            WeatherContract.CityColumns.LEADER_EN + " text, " +
            WeatherContract.CityColumns.LEANDER_ZH + " text, " +
            WeatherContract.CityColumns.IS_ADDED + " integer default 0)";
    private static final String SQL_CREATE_WEATHER = "";

    public AppSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CITY);
        db.execSQL(SQL_CREATE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor queryCity(String city) {
        return getReadableDatabase().query(WeatherContract.CityColumns.TABLE_NAME,
                null,
                WeatherContract.CityColumns.CITY_EN + " = ? or " + WeatherContract.CityColumns.CITY_ZH + " = ? ",
                new String[] {city, city},
                null,
                null,
                null);
    }
}
