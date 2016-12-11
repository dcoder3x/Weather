package com.hefeng.weather.db;

import android.provider.BaseColumns;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public final class WeatherContract {

    private WeatherContract() {};

    /**
     * table city.
     */
    public static class CityColumns implements BaseColumns {
        public static final String TABLE_NAME = "city";
        public static final String ID = "id";
        public static final String CITY_EN = "cityEn";
        public static final String CITY_ZH = "cityZh";
        public static final String PROVINCE_EN = "provinceEn";
        public static final String PROVINCE_ZH = "provinceZh";
        public static final String LEADER_EN = "leaderEn";
        public static final String LEANDER_ZH = "leaderZh";
        public static final String IS_ADDED = "isAdded";
    }

    /**
     * table weather.
     */
    public static class WeaColumns implements BaseColumns {
        public static final String TABLE_NAME = "weather";
        public static final String CITY_ID = "cityId";
        public static final String CITY_NAME = "city";
        public static final String LOC = "loc";
        public static final String UTC = "utc";
        public static final String CODE = "code";
        public static final String TXT = "txt";
        public static final String TMP = "tmp";
        public static final String DIR = "dir";
    }
}
