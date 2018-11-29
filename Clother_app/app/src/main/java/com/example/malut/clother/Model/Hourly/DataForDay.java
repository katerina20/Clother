package com.example.malut.clother.Model.Hourly;

import java.io.Serializable;

public class DataForDay implements Serializable {

    private double time;
    private String icon;
    private double temperature;
    private double apparentTemperature;
    private double precipProbability;

    public DataForDay(double time, String icon, double temperature, double apparentTemperature, double precipProbability) {
        this.time = time;
        this.icon = icon;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.precipProbability = precipProbability;
    }

    public double getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public int getTemperature() {
        return (int)temperature;
    }

    public int getApparentTemperature() {
        return (int)apparentTemperature;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }
}
