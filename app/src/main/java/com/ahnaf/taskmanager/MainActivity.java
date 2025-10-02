package com.ahnaf.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahnaf.taskmanager.adapter.TaskAdapter;
import com.ahnaf.taskmanager.model.BaseTask;
import com.ahnaf.taskmanager.model.PersonalTask;
import com.ahnaf.taskmanager.model.WorkTask;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<BaseTask> tasks = new ArrayList<>();
    private final ReminderService reminderService = new ReminderService();
    private TaskAdapter adapter;
    private TextView noTaskView;
    private final ActivityResultLauncher<Intent> addTaskLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();

                            if (data != null) {

                                String title = data.getStringExtra("title");
                                String details = data.getStringExtra("details");
                                int sec = data.getIntExtra("sec", 0);
                                String extra = data.getStringExtra("extra");
                                boolean isWork = data.getBooleanExtra("isWork", false);

                                BaseTask task;

                                if (isWork) {
                                    task = WorkTask.create(title, details, sec, extra);
                                } else {
                                    task = PersonalTask.create(title, details, sec, extra);
                                }

                                addTask(task);
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noTaskView = findViewById(R.id.noTask);

        if (tasks.isEmpty()) {
            noTaskView.setVisibility(View.VISIBLE);
        } else {
            noTaskView.setVisibility(View.GONE);
        }


        boolean isRunning = reminderService.isRunning();

        if (!isRunning) {
            reminderService.startReminderChecker(this);
        }

        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(intent);
        });
    }

    @Override
    protected void onDestroy() {
        reminderService.stopReminderChecker();
        super.onDestroy();
    }

    void addTask(BaseTask task) {
        tasks.add(task);
        noTaskView.setVisibility(View.GONE);
        reminderService.addTask(task);
        adapter.notifyItemInserted(tasks.size() - 1);
        Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
    }
}
