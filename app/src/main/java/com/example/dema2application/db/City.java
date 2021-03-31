package com.example.dema2application.db;

import org.litepal.crud.DataSupport;

public class City  extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getcityName() {
        return id;
    }

    public void setcityName(String cityName) {
        this.cityName = cityName;
    }

    public int getcityCode() {
        return cityCode;
    }

    public void setcityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getprovinceID() {
        return id;
    }

    public void setprovinceID(int provinceID) {
        this.provinceID = provinceID;
    }
}
