package com.ahnaf.taskmanager;

import android.content.Context;
import android.util.Log;

import com.ahnaf.taskmanager.model.BaseTask;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderService {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final List<BaseTask> tasks = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean running = false;

    // Add a new task
    public void addTask(BaseTask task) {
        tasks.add(task);
        Log.d("ReminderService", "Task added: " + task.getTitle());
    }

    // Start the background reminder checker
    public void startReminderChecker() {
        if (running) return; // Already running
        running = true;

        executor.execute(() -> {
            Log.d("ReminderService", "Reminder checker started");
            while (running) {
                long now = System.currentTimeMillis();

                synchronized (tasks) { // safe iteration
                    for (BaseTask task : tasks) {
                        LocalDateTime deadline = task.getDeadline();
                        long taskMillis = deadline.toInstant(ZoneOffset.UTC).toEpochMilli();

                        if (now >= taskMillis - 60000 && now < taskMillis) { // 1 minute before
                            Log.d("ReminderService", "Reminder: " + task.getTitle());
                        }
                    }
                }

                try {
                    Thread.sleep(30000); // check every 30 seconds
                } catch (InterruptedException e) {
                    Log.d("ReminderService", "Checker interrupted");
                    break;
                }
            }
            Log.d("ReminderService", "Reminder checker stopped");
        });
    }

    // Stop the reminder service
    public void stopReminderChecker() {
        running = false;
        executor.shutdownNow();
        Log.d("ReminderService", "Reminder service shutdown requested");
    }
}
