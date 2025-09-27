package com.ahnaf.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private final ActivityResultLauncher<Intent> addTaskLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();

                            if (data != null) {

                                String title = data.getStringExtra("title");
                                String details = data.getStringExtra("details");
                                int duration = data.getIntExtra("duration", 0);

//                                int hour = data.getIntExtra("hour", 0);
//                                int minute = data.getIntExtra("minute", 0);

//                                Log.d("TIME", hour + ":" + minute);


                                BaseTask task = PersonalTask.create(title, details, duration, "Home");
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


//        tasks.add(new PersonalTask("Workout", "Gym session at 7 PM", LocalDateTime.now().plusMinutes(1).plusSeconds(10), "Home"));


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
        reminderService.addTask(task);
        adapter.notifyItemInserted(tasks.size() - 1);
        Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
    }
}
