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


    @Override
    public void showInfo(Context context) {
        Toast.makeText(context, "Work Task: " + getTitle() + " [Project: " + projectName + "]", Toast.LENGTH_SHORT).show();
    }
}
