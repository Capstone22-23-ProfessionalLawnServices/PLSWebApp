package com.professionallawnservices.app.models.json.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class OpenWeatherResponse {

    @JsonProperty("cnt")
    private int count;

    @JsonProperty("list")
    private ArrayList<Interval> daysList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Interval> getDaysList() {
        return daysList;
    }

    public void setDaysList(ArrayList<Interval> daysList) {
        this.daysList = daysList;
    }

}
