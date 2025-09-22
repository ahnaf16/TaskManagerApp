package com.ahnaf.taskmanager;

public class PersonalTask extends BaseTask {
    private String location;

    public PersonalTask(String title, String description, long deadlineMillis, String location) {
        super(title, description, deadlineMillis);
        this.location = location;
    }

    @Override
    public void displayTaskDetails() {
        System.out.println("Personal Task: " + getTitle() + " [Location: " + location + "]");
    }
}