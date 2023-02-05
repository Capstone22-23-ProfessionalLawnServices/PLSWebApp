package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.openweather.Interval;
import com.professionallawnservices.app.models.openweather.OpenWeatherResponse;
import com.professionallawnservices.app.models.PlsWeather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

@Service
public class HomeService {

    @Value("${apikey.openweatherapi}")
    private String apikey;

    public ArrayList<PlsWeather> getCalendarWeather() {

        final String uri = "http://api.openweathermap.org/data/2.5/forecast?lat=34.19&lon=-79.76&" +
                "appid=" + apikey + "&units=imperial";

        RestTemplate restTemplate = new RestTemplate();
        String reqBody = "";

        String result = restTemplate.postForObject(uri, reqBody, String.class);
        OpenWeatherResponse openWeatherResponse = restTemplate.postForObject(uri, reqBody, OpenWeatherResponse.class);

        Date date = new Date();
        int dateDay = -1;
        int highTemp = -100;
        int lowTemp = 200;
        int humidity = 0;
        String description = "";
        ArrayList<PlsWeather> weatherList = new ArrayList<PlsWeather>();


        for (Interval dayInterval:
             openWeatherResponse.getDaysList()) {
            long test = dayInterval.getDateTime();
            date = new Date(test * 1000);
            int currentHigh = (int) Math.round(dayInterval.getWeatherDay().getTempMax());
            int currentLow = (int) Math.round(dayInterval.getWeatherDay().getTempMin());

            if (dateDay == -1) {
                highTemp = currentHigh;
                lowTemp = currentLow;
                humidity = dayInterval.getWeatherDay().getHumidity();
                description = dayInterval.getWeatherDescription().get(0).getDescription();
            }
            else if (dateDay != date.getDay()) {
                weatherList.add(
                        new PlsWeather(
                                highTemp,
                                lowTemp,
                                humidity,
                                description,
                                date
                                )
                );

                highTemp = currentHigh;
                lowTemp = currentLow;
                humidity = dayInterval.getWeatherDay().getHumidity();
                description = dayInterval.getWeatherDescription().get(0).getDescription();
            }

            if (currentHigh > highTemp) {
                highTemp = currentHigh;
                humidity = dayInterval.getWeatherDay().getHumidity();
                description = dayInterval.getWeatherDescription().get(0).getDescription();
            }

            if (currentLow < lowTemp) {
                lowTemp = currentLow;
            }

            dateDay = date.getDay();
        }

        weatherList.add(
                new PlsWeather(
                        highTemp,
                        lowTemp,
                        humidity,
                        description,
                        date
                )
        );

        return weatherList;
    }

}
