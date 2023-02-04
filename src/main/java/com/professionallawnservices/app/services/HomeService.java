package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.openweather.OpenWeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HomeService {

    public void getCalendarWeather() {

        final String uri = "http://api.openweathermap.org/data/2.5/forecast?lat=34.19&lon=-79.76&" +
                "appid=cb80dc8fdd6d29382fe2a9c100b6f7f8&units=imperial";

        RestTemplate restTemplate = new RestTemplate();
        String reqBody = "";

        String result = restTemplate.postForObject(uri, reqBody, String.class);
        OpenWeatherResponse result1 = restTemplate.postForObject(uri, reqBody, OpenWeatherResponse.class);

        /*
        for (Interval dayInterval:
             result1.getDaysList()) {
            long test = dayInterval.getDateTime();
            java.sql.Date date = new java.sql.Date(test + 1000000000);
            System.out.println(date);
        }

         */

    }

}
