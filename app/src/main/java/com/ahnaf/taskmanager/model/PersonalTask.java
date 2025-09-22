package com.ahnaf.taskmanager.model;

import java.time.LocalDateTime;

public class PersonalTask extends BaseTask {
    private String location;

    public PersonalTask(String title, String description, LocalDateTime deadline, String location) {
        super(title, description, deadline);
        this.location = location;
    }

    @Override
    public void displayTaskDetails() {
        System.out.println("Personal Task: " + getTitle() + " [Location: " + location + "]");
    }
}