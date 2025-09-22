package com.ahnaf.taskmanager;

public abstract class BaseTask {
    private String title;
    private String description;
    private long deadlineMillis; // timestamp in milliseconds

    public BaseTask(String title, String description, long deadlineMillis) {
        this.title = title;
        this.description = description;
        this.deadlineMillis = deadlineMillis;
    }

    // Encapsulation: getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getDeadlineMillis() { return deadlineMillis; }
    public void setDeadlineMillis(long deadlineMillis) { this.deadlineMillis = deadlineMillis; }

    // Polymorphism: subclasses implement differently
    public abstract void displayTaskDetails();
}
