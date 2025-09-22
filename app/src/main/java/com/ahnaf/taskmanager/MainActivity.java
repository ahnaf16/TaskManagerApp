package com.ahnaf.taskmanager;


import android.os.Bundle;
import android.widget.Toast;

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

    private List<BaseTask> tasks;
    private TaskAdapter adapter;

    private ReminderService reminderService = new ReminderService();


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

        tasks = new ArrayList<>();
        tasks.add(new WorkTask("Finish report", "Complete the project report", LocalDateTime.now(),"Office"));
        tasks.add(new PersonalTask("Buy groceries", "Milk, Eggs, Bread", LocalDateTime.now(),"Home"));
        tasks.add(new PersonalTask("Workout", "Gym session at 7 PM", LocalDateTime.now(),"Home"));

        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {

            addTask(new PersonalTask("Workout", "Gym session at 7 PM", LocalDateTime.now(),"Home"));

        });
    }

    void addTask(BaseTask task){
        tasks.add(task);
        adapter.notifyItemInserted(tasks.size() - 1);
        Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
    }
}
