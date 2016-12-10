package com.hefeng.weather.fetch;

import android.content.ContentValues;
import android.util.Log;

import com.hefeng.weather.db.WeatherContract;
import com.hefeng.weather.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public class DataFetcher {
    private static final String TAG = "DataFetcher";

    private static final String CITY_INFO_URL = "http://files.heweather.com/china-city-list.json";
    private static final String WEATHER_INFO_URL = "https://free-api.heweather.com/v5/";
    private static final String API_KEY = "";

    public DataFetcher() {}

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<ContentValues> downloadAllCityInfo() {
        String result = "";
        ArrayList<ContentValues> cityContainer = new ArrayList<>();
        try {
            result = getUrl(CITY_INFO_URL);
        } catch (IOException e) {
            Log.e(TAG, "can't get data from internet!", e);
        }
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(WeatherContract.CityColumns.ID, jsonObject.getString(WeatherContract.CityColumns.ID));
                values.put(WeatherContract.CityColumns.CITY_EN, jsonObject.getString(WeatherContract.CityColumns.CITY_EN));
                values.put(WeatherContract.CityColumns.CITY_ZH, jsonObject.getString(WeatherContract.CityColumns.CITY_ZH));
                values.put(WeatherContract.CityColumns.PROVINCE_EN, jsonObject.getString(WeatherContract.CityColumns.PROVINCE_EN));
                values.put(WeatherContract.CityColumns.PROVINCE_ZH, jsonObject.getString(WeatherContract.CityColumns.PROVINCE_ZH));
                values.put(WeatherContract.CityColumns.LEADER_EN, jsonObject.getString(WeatherContract.CityColumns.LEADER_EN));
                values.put(WeatherContract.CityColumns.LEANDER_ZH, jsonObject.getString(WeatherContract.CityColumns.LEANDER_ZH));

                cityContainer.add(values);
            }
        } catch (JSONException e) {
            Log.e(TAG, "can't parse json data!", e);
        }
        return cityContainer;
    }
}
