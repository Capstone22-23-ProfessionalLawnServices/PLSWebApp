package com.professionallawnservices.app.helpers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateHelper {

    public static Time stringToSqlDate(String timeString) {

        Time time;
        LocalDateTime dateLocal;

        try {
            dateLocal = LocalDateTime.parse(timeString);
            ZonedDateTime zdt = ZonedDateTime.of(dateLocal, ZoneId.systemDefault());

            long dateZone = zdt.toInstant().toEpochMilli();

            time = new java.sql.Time(dateZone);
        } catch (Exception e) {
            time = new Time(0, 0, 0);
        }

        return time;
    }

    /*
    Html formats their dates in this manner: 2023-02-05T13:00
     */

    public static String sqlDateToString(Date scheduledDate, Time time) {

        String htmlDate = "";

        htmlDate = scheduledDate.toString() + "T" + time.toString().substring(0, 5);

        return htmlDate;
    }

}
