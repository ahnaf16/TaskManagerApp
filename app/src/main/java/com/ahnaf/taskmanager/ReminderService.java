package com.ahnaf.taskmanager;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ReminderService {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<BaseTask> tasks = new ArrayList<>();

    public void addTask(BaseTask task) {
        tasks.add(task);
    }

    public void startReminderChecker(Context context) {
        executor.execute(() -> {
            while (true) {
                long now = System.currentTimeMillis();
                for (BaseTask task : tasks) {
                    if (Math.abs(task.getDeadlineMillis() - now) < 60000) { // within 1 minute
                        Log.d("ReminderService", "Reminder for: " + task.getTitle());
                    }
                }
                try {
                    Thread.sleep(30000); // check every 30 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
