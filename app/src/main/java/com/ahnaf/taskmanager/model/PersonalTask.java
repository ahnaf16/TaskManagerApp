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

    public static PersonalTask create(String title, String description, int minute, int hour, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.withHour(hour).withMinute(minute);
        return new PersonalTask(title, description, deadline, location);
    }

    public static PersonalTask create(String title, String description, int duration, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.withMinute(now.getMinute() + duration);

        Log.d("TIME", "Min: " + duration);
        Log.d("now", String.format(Locale.getDefault(), "%02d:%02d", now.getHour(), now.getMinute()));
        Log.d("deadline", String.format(Locale.getDefault(), "%02d:%02d", deadline.getHour(), deadline.getMinute()));

        return new PersonalTask(title, description, deadline, location);
    }

    @Override
    public void showInfo(Context context) {
        Toast.makeText(context, "Personal Task: " + getTitle() + " [Location: " + location + "]", Toast.LENGTH_SHORT).show();
    }
}