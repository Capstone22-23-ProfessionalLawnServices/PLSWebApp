package com.professionallawnservices.app.models.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Interval {

    @JsonProperty("dt")
    private long dateTime;

    @JsonProperty("main")
    private WeatherDay weatherDay;

    @JsonProperty("weather")
    private ArrayList<WeatherDescription> weatherDescription;

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

    public ArrayList<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(ArrayList<WeatherDescription> weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
