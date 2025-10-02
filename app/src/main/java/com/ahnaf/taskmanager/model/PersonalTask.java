package com.ahnaf.taskmanager.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Locale;

public class PersonalTask extends BaseTask {
    private final String location;

    public PersonalTask(String title, String description, LocalDateTime deadline, String location) {
        super(title, description, deadline);
        this.location = location;
    }

    public static PersonalTask create(String title, String description, int min, int sec, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusSeconds(sec).plusMinutes(min);
        return new PersonalTask(title, description, deadline, location);
    }

    public static PersonalTask create(String title, String description, int durationInSec, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusSeconds(durationInSec);

        return new PersonalTask(title, description, deadline, location);
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String reminderText() {
        return "Personal Task: (" + location + ") " + getTitle();
    }

    @Override
    public String getTaskType() {
        return "Personal";
    }
}