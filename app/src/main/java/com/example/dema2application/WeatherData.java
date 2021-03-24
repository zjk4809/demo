package com.example.dema2application;



public class WeatherData{

    private String city;        //城市
    private String temp;        //温度
    private String text;        //天气
    private String windScale;   //风力

    public  WeatherData(){
    }

    public WeatherData(String city, String temp, String text, String windScale) {
        this.city = city;
        this.temp = temp;
        this.text = text;
        this.windScale = windScale;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }
}
