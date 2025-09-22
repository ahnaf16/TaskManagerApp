package com.ahnaf.taskmanager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;




public class MainActivity extends AppCompatActivity {

    private List<BaseTask> taskList = new ArrayList<>();
    private ReminderService reminderService;
    private LinearLayout taskContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskContainer = findViewById(R.id.taskContainer);
        FloatingActionButton fab = findViewById(R.id.addTaskFab);

        reminderService = new ReminderService();

        // Add initial tasks
        addTask(new WorkTask("Finish Report", "Write Q3 Report", System.currentTimeMillis() + 60000, "Office Project"));
        addTask(new PersonalTask("Buy Milk", "From nearby shop", System.currentTimeMillis() + 120000, "Supermarket"));

        fab.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_add_task, null);
            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Initialize views inside bottom sheet
            TextInputEditText titleEdit = sheetView.findViewById(R.id.titleEditText);
            TextInputEditText descEdit = sheetView.findViewById(R.id.descEditText);
            Button pickTimeBtn = sheetView.findViewById(R.id.pickTimeBtn);
            Button addTaskBtn = sheetView.findViewById(R.id.addTaskBtn);

            final Calendar calendar = Calendar.getInstance();

            // Time picker
            pickTimeBtn.setOnClickListener(btn -> {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(
                        MainActivity.this,
                        (view, selectedHour, selectedMinute) -> {
                            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calendar.set(Calendar.MINUTE, selectedMinute);
                            pickTimeBtn.setText(String.format("Deadline: %02d:%02d", selectedHour, selectedMinute));
                        },
                        hour, minute, true
                );
                timePicker.show();
            });

            // Add Task button
            addTaskBtn.setOnClickListener(btn -> {
                String title = titleEdit.getText().toString().trim();
                String desc = descEdit.getText().toString().trim();
                String deadline = pickTimeBtn.getText().toString();

                if (title.isEmpty()) {
                    titleEdit.setError("Title required");
                    return;
                }

                // Add to your task list (for now just log or Toast)
                Toast.makeText(MainActivity.this,
                        "Task Added:\n" + title + "\n" + desc + "\n" + deadline,
                        Toast.LENGTH_LONG).show();

                addTask(new PersonalTask(title, desc, System.currentTimeMillis() + 60000, "Home"));

                bottomSheetDialog.dismiss();
            });
        });

        // Start background reminder checker
        reminderService.startReminderChecker(this);
    }

    private void addTask(BaseTask task) {
        taskList.add(task);
        reminderService.addTask(task);

        // Create a Material Card for each task
        MaterialCardView card = new MaterialCardView(this);
        card.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        card.setContentPadding(24, 24, 24, 24);
        card.setCardElevation(8);
        card.setUseCompatPadding(true);
        card.setRadius(16);

        TextView title = new TextView(this);
        title.setText(task.getTitle());
        title.setTextSize(18f);
        title.setPadding(0, 0, 0, 8);

        TextView description = new TextView(this);
        description.setText(task.getDescription());
        description.setTextSize(14f);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(title);
        layout.addView(description);

        card.addView(layout);
        taskContainer.addView(card);

        // Polymorphism demo
        task.displayTaskDetails();
    }
}



