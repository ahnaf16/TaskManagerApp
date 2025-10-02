package com.ahnaf.taskmanager;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ahnaf.taskmanager.model.BaseTask;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final List<BaseTask> tasks = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean running = false;

    public boolean isRunning() {
        return running;
    }

    public void addTask(BaseTask task) {
        tasks.add(task);
    }

    public void startReminderChecker(Context context) {
        if (running) return;
        running = true;

        scheduler.scheduleWithFixedDelay(() -> {
            long now = System.currentTimeMillis();

            synchronized (tasks) {
                for (BaseTask task : tasks) {
                    LocalDateTime deadline = task.getDeadline();
                    long taskMillis = deadline.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    // check if deadline is within next 1 minute
                    if (taskMillis > now && taskMillis - now <= 60000 && !task.isReminded()) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            task.setReminded(true);
                            String info = task.reminderText();
                            Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                            Log.d("RS", "Reminder: " + task.getTitle());
                        });
                    }

                    // when task ends
                    if ((taskMillis - now) <= 3) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            String info = "Task Ended: " + task.getTitle();
                            Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                            Log.d("RS", "Task End: " + task.getTitle());
                            tasks.remove(task);
                        });
                    }
                }
            }
        }, 0, 3, TimeUnit.SECONDS);

        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(context, "Reminder service started", Toast.LENGTH_SHORT).show()
        );
    }

    public void stopReminderChecker() {
        running = false;
        scheduler.shutdownNow();
        Log.d("ReminderService", "Reminder service shutdown requested");
    }


}
