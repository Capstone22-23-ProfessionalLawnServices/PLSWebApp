package com.professionallawnservices.app.models.json.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDescription {

    @JsonProperty("main")
    private String mainWeather;

    @JsonProperty("description")
    private String description;

    public String getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(String mainWeather) {
        this.mainWeather = mainWeather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
