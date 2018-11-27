package com.example.malut.clother.Model;

import com.example.malut.clother.Model.Currently.Currently;
import com.example.malut.clother.Model.Daily.Daily;
import com.example.malut.clother.Model.Hourly.Hourly;

import java.io.Serializable;
import java.util.List;

public class DarkSkyWeather implements Serializable {

    private double latitude;
    private double longitude;
    private Currently currently;
    private Daily daily;
    private Hourly hourly;

    public DarkSkyWeather() {

    }

    public Currently getCurrently() {
        return currently;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Daily getDaily() {
        return daily;
    }

    public Hourly getHourly() {
        return hourly;
    }
}
