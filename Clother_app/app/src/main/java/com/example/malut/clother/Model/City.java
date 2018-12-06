package com.example.malut.clother.Model;

public class City {
    private String cityName;
    private double lng;
    private double lat;
    private String country;


    public City(String cityName, double lng, double lat, String country) {
        this.cityName = cityName;
        this.lng = lng;
        this.lat = lat;
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
