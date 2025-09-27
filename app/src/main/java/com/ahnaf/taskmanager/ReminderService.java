package com.ahnaf.taskmanager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    public boolean isRunning() {
        return running;
    }


    public void setInitialList(List<BaseTask> tasks) {
        this.tasks.addAll(tasks);
    }

    public void addTask(BaseTask task) {
        tasks.add(task);
    }


    public void startReminderChecker(Context context) {
        if (running) return;
        running = true;

        Toast.makeText(context, "Reminder service started", Toast.LENGTH_SHORT).show();

        executor.execute(() -> {

            while (running) {
                long now = System.currentTimeMillis();
                
                synchronized (tasks) {
                    for (BaseTask task : tasks) {
                        Log.d("RS", "task: " + task.getTitle());

                        LocalDateTime deadline = task.getDeadline();
                        long taskMillis = deadline.toInstant(ZoneOffset.UTC).toEpochMilli();

                        Log.d("RS", "taskMillis: " + taskMillis);

                        // 1 minute before
                        if (now >= taskMillis - 50000 && now < taskMillis) {
                            task.showInfo(context);
                            Log.d("RS", "Reminder: " + task.getTitle());
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
