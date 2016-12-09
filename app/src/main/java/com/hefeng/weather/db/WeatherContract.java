package com.hefeng.weather.db;

import android.provider.BaseColumns;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public final class WeatherContract {

    private WeatherContract() {};

    public static class CityColumns implements BaseColumns {
        public static final String TABLE_NAME = "city";
        public static final String ID = "id";
        public static final String CITY_EN = "cityEn";
        public static final String CITY_ZH = "cityZh";
        public static final String PROVINCE_EN = "provinceEn";
        public static final String PROVINCE_ZH = "provinceZh";
        public static final String LEADER_EN = "leaderEn";
        public static final String LEANDER_ZH = "leaderZh";
    }
}
