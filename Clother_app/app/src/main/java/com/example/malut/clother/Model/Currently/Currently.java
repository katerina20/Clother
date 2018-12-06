package com.example.malut.clother.Model.Currently;

import java.io.Serializable;

public class Currently implements Serializable {

    private double time;
    private String summary;
    private String icon;
    private double precipProbability;
    private double temperature;
    private double apparentTemperature;
    private double humidity;
    private double pressure;
    private double windSpeed;

    public Currently(double time, String summary, String icon, double precipProbability, double temperature, double apparentTemperature, double humidity, double pressure, double windSpeed) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public double getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public int getPrecipProbability() {
        return (int)precipProbability;
    }

    public int getTemperature() {
        return (int)temperature;
    }

    public int getApparentTemperature() {
        return (int)apparentTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
