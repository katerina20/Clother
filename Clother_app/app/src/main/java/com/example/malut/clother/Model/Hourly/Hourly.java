package com.example.malut.clother.Model.Hourly;

import java.util.List;

public class Hourly {

    private List<DataForDay> dataHourlyList;

    public Hourly(List<DataForDay> dataHourlyList) {
        this.dataHourlyList = dataHourlyList;
    }

    public List<DataForDay> getDataHourlyList() {
        return dataHourlyList;
    }
}
