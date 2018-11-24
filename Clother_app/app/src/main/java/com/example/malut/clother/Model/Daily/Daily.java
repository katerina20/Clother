package com.example.malut.clother.Model.Daily;

import java.util.List;

public class Daily {

    private List<DataForWeek> dataHourlyList;

    public Daily(List<DataForWeek> dataHourlyList) {
        this.dataHourlyList = dataHourlyList;
    }

    public List<DataForWeek> getDataHourlyList() {
        return dataHourlyList;
    }
}
