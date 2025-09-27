package com.ahnaf.taskmanager.model;

import android.content.Context;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class BaseTask {
    private String title;
    private String description;
    private LocalDateTime deadline;

    public BaseTask(String title, String description, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    // Encapsulation: getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


    // Polymorphism: subclasses implement differently
    public abstract void showInfo(Context context);

    public long millisRemaining() {
        return Duration.between(LocalDateTime.now(), deadline).toMillis();
    }

}
