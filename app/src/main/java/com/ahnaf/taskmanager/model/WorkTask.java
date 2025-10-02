package com.ahnaf.taskmanager.model;

import android.content.Context;
import android.widget.Toast;

import java.time.LocalDateTime;

public class WorkTask extends BaseTask {
    private final String projectName;

    public WorkTask(String title, String description, LocalDateTime deadline, String projectName) {
        super(title, description, deadline);
        this.projectName = projectName;
    }

    public static WorkTask create(String title, String description, int min, int sec, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusSeconds(sec).plusMinutes(min);
        return new WorkTask(title, description, deadline, location);
    }

    public static WorkTask create(String title, String description, int durationInSec, String location) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusSeconds(durationInSec);

        return new WorkTask(title, description, deadline, location);
    }

    public String getProjectName() {
        return projectName;
    }


    @Override
    public String reminderText() {
        return "Work Task: (" + projectName + ") " + getTitle();
    }

    @Override
    public String getTaskType() {
        return "Work";
    }
}
