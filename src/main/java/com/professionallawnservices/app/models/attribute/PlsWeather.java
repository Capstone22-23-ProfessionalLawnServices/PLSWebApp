package com.professionallawnservices.app.models.attribute;

import java.util.Date;

public class PlsWeather {

    private int high;

    private int low;

    private int humidity;

    private String description;

    private Date date;

    public PlsWeather() {

    }

    public PlsWeather(int high, int low, int humidity, String description, Date date) {
        this.high = high;
        this.low = low;
        this.humidity = humidity;
        this.description = description;
        this.date = date;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHighAndLow() {
        return high + " " + low;
    }
}
