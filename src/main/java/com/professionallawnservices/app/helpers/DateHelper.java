package com.professionallawnservices.app.helpers;

import java.sql.Time;

public class DateHelper {

    public static Time formatTime(String timeString) {

        Time time;

        try {
            int hour = Integer.parseInt(timeString.substring(timeString.indexOf('T') + 1, timeString.indexOf(':')));
            int minute = Integer.parseInt(timeString.substring(timeString.indexOf(':') + 1));
            time = new java.sql.Time(hour, minute, 0);
        } catch (Exception e) {
            time = new Time(0, 0, 0);
        }

        return time;
    }

}
