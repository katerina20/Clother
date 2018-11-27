package com.example.malut.clother.Model.Daily;

import java.io.Serializable;
import java.util.List;

public class Daily implements Serializable {

    private String summary;
    private List<DataForWeek> data;

    public Daily(List<DataForWeek> data, String summary) {
        this.data = data;
        this.summary = summary;
    }

    public List<DataForWeek> getDataHourlyList() {
        return data;
    }
}
