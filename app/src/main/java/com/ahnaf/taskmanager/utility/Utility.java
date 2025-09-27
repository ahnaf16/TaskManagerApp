package com.ahnaf.taskmanager.utility;

public class Utility {


    public static TimeInfo getAmPmFromHour(int hour) {
        String amPm = (hour < 12 || hour == 24) ? "AM" : "PM";
        int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
        return new TimeInfo(displayHour, amPm);
    }
}
