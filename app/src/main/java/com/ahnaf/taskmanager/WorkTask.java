package com.ahnaf.taskmanager;

public class WorkTask extends BaseTask {
    private String projectName;

    public WorkTask(String title, String description, long deadlineMillis, String projectName) {
        super(title, description, deadlineMillis);
        this.projectName = projectName;
    }

    @Override
    public void displayTaskDetails() {
        System.out.println("Work Task: " + getTitle() + " [Project: " + projectName + "]");
    }
}
