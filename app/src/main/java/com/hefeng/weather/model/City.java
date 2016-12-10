package com.hefeng.weather.model;

/**
 * Created by Scott on 2016/12/9.
 *
 */

public class City {

    private String mId;
    private String mCityEn;
    private String mCityZh;
    private String mProvinceEn;
    private String mProvinceZh;
    private String mLeaderEn;
    private String mLeaderZh;
    private int mIsAdded;


    public City() {}

    public City(String id, String cityEn, String cityZh, String provinceEn, String provinceZh, String leaderEn, String leaderZh, int isAdded) {
        mId = id;
        mCityEn = cityEn;
        mCityZh = cityZh;
        mProvinceEn = provinceEn;
        mProvinceZh = provinceZh;
        mLeaderEn = leaderEn;
        mLeaderZh = leaderZh;
        mIsAdded = isAdded;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCityEn() {
        return mCityEn;
    }

    public void setCityEn(String cityEn) {
        mCityEn = cityEn;
    }

    public String getCityZh() {
        return mCityZh;
    }

    public void setCityZh(String cityZh) {
        mCityZh = cityZh;
    }

    public String getProvinceEn() {
        return mProvinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        mProvinceEn = provinceEn;
    }

    public String getProvinceZh() {
        return mProvinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        mProvinceZh = provinceZh;
    }

    public String getLeaderEn() {
        return mLeaderEn;
    }

    public void setLeaderEn(String leaderEn) {
        mLeaderEn = leaderEn;
    }

    public String getLeaderZh() {
        return mLeaderZh;
    }

    public void setLeaderZh(String leaderZh) {
        mLeaderZh = leaderZh;
    }

    public int getIsAdded() {
        return mIsAdded;
    }

    public void setIsAdded(int isAdded) {
        mIsAdded = isAdded;
    }
}
