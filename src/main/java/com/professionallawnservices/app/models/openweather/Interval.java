package com.professionallawnservices.app.models.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Interval {

    @JsonProperty("dt")
    private long dateTime;

    @JsonProperty("main")
    private WeatherDay weatherDay;


    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public WeatherDay getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(WeatherDay weatherDay) {
        this.weatherDay = weatherDay;
    }
}
