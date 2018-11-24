package com.example.malut.clother.Model.Currently;

public class Currently {

    private double time;
    private String summsry;
    private String icon;
    private double precipProbability;
    private double temperature;
    private double apparentTemperature;
    private double humidity;
    private double pressure;
    private double windSpeed;

    public Currently(double time, String summsry, String icon, double precipProbability, double temperature, double apparentTemperature, double humidity, double pressure, double windSpeed) {
        this.time = time;
        this.summsry = summsry;
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

    public String getSummsry() {
        return summsry;
    }

    public String getIcon() {
        return icon;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
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
