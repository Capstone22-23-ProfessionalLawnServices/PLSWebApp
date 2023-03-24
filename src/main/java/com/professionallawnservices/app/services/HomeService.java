package com.professionallawnservices.app.services;

import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.json.openweather.Interval;
import com.professionallawnservices.app.models.json.openweather.OpenWeatherResponse;
import com.professionallawnservices.app.models.json.openweather.PlsWeather;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
@PropertySource("classpath:/application.properties")
public class HomeService {

    @Value("${apikey.openweatherapi}")
    private String apikey;
    private final JobRepo jobRepo;

    public HomeService(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

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

        if (date.getDay() != (new Date(openWeatherResponse.getDaysList().get(0).getDateTime() * 1000)).getDay()) {
            weatherList.add(null);
            date = new Date(openWeatherResponse.getDaysList().get(0).getDateTime() * 1000);
        }

        for (Interval dayInterval:
             openWeatherResponse.getDaysList()) {
            long dateInteger = dayInterval.getDateTime();
            date = new Date(dateInteger * 1000);
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

    public Result getCalendarJobs() {

        Result result = new Result();

        try {

            ArrayList<ArrayList<Job>> calendarList = new ArrayList<ArrayList<Job>>();

            for (int i = 0; i < 12; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, i);
                java.sql.Date scheduldeDate = new java.sql.Date(calendar.getTimeInMillis());
                calendarList.add(jobRepo.getAllByScheduledDateIsNotNullAndScheduledDate(scheduldeDate));
            }

            result.setData(calendarList);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding calendar jobs.");
        }

        return result;
    }

    public Result rescheduleJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));
            job.setScheduledDateFromString(jobRequest.getScheduledDate());

            jobRepo.save(job);

            result.setData(job);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue rescheduling the job.");
        }

        return result;
    }



}
