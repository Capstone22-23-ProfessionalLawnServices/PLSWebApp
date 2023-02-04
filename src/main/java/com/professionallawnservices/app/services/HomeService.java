package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.openweather.Interval;
import com.professionallawnservices.app.models.openweather.OpenWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class HomeService {

    @Value("${apikey.openweatherapi}")
    private String apikey;

    public void getCalendarWeather() {

        final String uri = "http://api.openweathermap.org/data/2.5/forecast?lat=34.19&lon=-79.76&" +
                "appid=" + apikey + "&units=imperial";

        RestTemplate restTemplate = new RestTemplate();
        String reqBody = "";

        String result = restTemplate.postForObject(uri, reqBody, String.class);
        OpenWeatherResponse result1 = restTemplate.postForObject(uri, reqBody, OpenWeatherResponse.class);


        for (Interval dayInterval:
             result1.getDaysList()) {
            long test = dayInterval.getDateTime();
            Date date = new Date(test * 1000);
            System.out.println(date);
        }


    }

}
