package com.professionallawnservices.app.models.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDay {

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("temp_max")
    private double tempMax;

    @JsonProperty("temp_min")
    private double tempMin;

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }
}
