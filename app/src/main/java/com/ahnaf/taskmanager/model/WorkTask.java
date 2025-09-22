package com.ahnaf.taskmanager.model;

import java.time.LocalDateTime;

public class WorkTask extends BaseTask {
    private String projectName;

    public WorkTask(String title, String description, LocalDateTime deadline, String projectName) {
        super(title, description, deadline);
        this.projectName = projectName;
    }

    @Override
    public void displayTaskDetails() {
        System.out.println("Work Task: " + getTitle() + " [Project: " + projectName + "]");
    }
}
