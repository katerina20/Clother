package com.example.malut.clother.Model.Daily;

import java.io.Serializable;

public class DataForWeek implements Serializable {

    private double time;
    private String icon;
    private double sunriseTime;
    private double sunsetTime;
    private double temperatureHigh;
    private double temperatureLow;

    public DataForWeek(double time, String icon, double sunriseTime, double sunsetTime, double temperatureHigh, double temperatureLow) {
        this.time = time;
        this.icon = icon;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.temperatureHigh = temperatureHigh;
        this.temperatureLow = temperatureLow;
    }

    public double getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public double getSunriseTime() {
        return sunriseTime;
    }

    public double getSunsetTime() {
        return sunsetTime;
    }

    public double getTemperatureHigh() {
        return (int)temperatureHigh;
    }

    public double getTemperatureLow() {
        return (int)temperatureLow;
    }
}
