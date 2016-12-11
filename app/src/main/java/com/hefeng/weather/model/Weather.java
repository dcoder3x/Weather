package com.hefeng.weather.model;

/**
 * Created by Scott on 2016/12/10.
 *
 */

public class Weather {

    private String mCityId;
    private String mCityName;
    private String mLoc;
    private String mUtc;
    private String mCode;
    private String mTxt;
    private String mTmp;
    private String mDir;

    public Weather() {}

    public Weather(String cityId, String cityName, String loc, String utc, String code, String txt, String tmp, String dir) {
        mCityId = cityId;
        mCityName = cityName;
        mLoc = loc;
        mUtc = utc;
        mCode = code;
        mTxt = txt;
        mTmp = tmp;
        mDir = dir;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getLoc() {
        return mLoc;
    }

    public void setLoc(String loc) {
        mLoc = loc;
    }

    public String getUtc() {
        return mUtc;
    }

    public void setUtc(String utc) {
        mUtc = utc;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getTxt() {
        return mTxt;
    }

    public void setTxt(String txt) {
        mTxt = txt;
    }

    public String getTmp() {
        return mTmp;
    }

    public void setTmp(String tmp) {
        mTmp = tmp;
    }

    public String getDir() {
        return mDir;
    }

    public void setDir(String dir) {
        mDir = dir;
    }
}
