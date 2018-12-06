package com.example.malut.clother.Model.Hourly;

import java.io.Serializable;
import java.util.List;

public class Hourly implements Serializable {

    private List<DataForDay> data;

    public Hourly(List<DataForDay> data) {
        this.data = data;
    }

    public List<DataForDay> getDataHourlyList() {
        return data;
    }
}
