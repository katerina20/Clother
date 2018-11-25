package com.example.malut.clother.Model;

import com.example.malut.clother.Model.Currently.Currently;
import com.example.malut.clother.Model.Daily.Daily;
import com.example.malut.clother.Model.Hourly.Hourly;

import java.util.List;

public class DarkSkyWeather {

    private Currently currently;
    private List<Daily> dailies;
    private List<Hourly> hourlies;

    public DarkSkyWeather(Currently currently, List<Daily> dailies, List<Hourly> hourlies) {
        this.currently = currently;
        this.dailies = dailies;
        this.hourlies = hourlies;
    }

    public Currently getCurrently() {
        return currently;
    }

    public List<Daily> getDailies() {
        return dailies;
    }

    public List<Hourly> getHourlies() {
        return hourlies;
    }
}
