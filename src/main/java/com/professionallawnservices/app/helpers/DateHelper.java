package com.professionallawnservices.app.helpers;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class DateHelper {

    public static Time stringToSqlTime(String timeString) {

        Time time;

        try {

            if (timeString == null) {
                return null;
            }

            if (timeString.length() < 8) {
                timeString += ":00";
            }

            time = Time.valueOf(timeString);
        } catch (Exception e) {
            throw(e);
        }

        return time;
    }

    public static Date stringToSqlDate(String dateString) {

        Date date = null;

        if (dateString != "") {
            date = Date.valueOf(dateString);
        }

        return date;
    }

    /*
    Html formats their dates in this manner: 2023-02-05T13:00
     */

    public static String sqlTimeToString(Date scheduledDate, Time time) {

        String htmlDate = "";

        if (time != null) {
            htmlDate = scheduledDate.toString() + "T" + time.toString().substring(0, 5);
        }

        return htmlDate;
    }

    public static String sqlDateToString(Date scheduledDate) {

        String htmlDate = scheduledDate.toString();

        return htmlDate;
    }

    public static Date sqlDateAddDays(Date date, int days) {

        return Date.valueOf(date.toLocalDate().plusDays(days));
    }

    /*
    public static Date sqlTimetoSqlDate(Time time) {


        return date;
    }

     */

}
