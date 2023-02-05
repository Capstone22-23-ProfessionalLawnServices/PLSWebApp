package com.professionallawnservices.app.helpers;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static Time formatTime(String timeString) {

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

}
