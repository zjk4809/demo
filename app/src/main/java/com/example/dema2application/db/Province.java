package com.example.dema2application.db;

import org.litepal.crud.DataSupport;

public class Province  extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getid() {
        return id;
    }
    public void setid(int id){
        this.id = id;
    }
    public String getprovinceName() {
        return provinceName;
    }
    public void setprovinceName(String provinceName){
        this.provinceName = provinceName;
    }
    public int getprovinceCode() {
        return id;
    }
    public void setprovinceCode(int provinceCode){
        this.provinceCode = provinceCode;
    }
}
