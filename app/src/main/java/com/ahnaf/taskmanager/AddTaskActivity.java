package com.ahnaf.taskmanager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.ahnaf.taskmanager.utility.TimeInfo;
import com.ahnaf.taskmanager.utility.Utility;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private final boolean timePicked = false;
    private EditText titleField, detailsField, durationField;
    private Button pickTimeBtn;
    private int pickedHour, pickedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Toolbar
        MaterialToolbar topAppBar = findViewById(R.id.addTaskTopAppBar);
        setSupportActionBar(topAppBar);

        titleField = findViewById(R.id.editTitle);
        detailsField = findViewById(R.id.editDetails);
        durationField = findViewById(R.id.duration);
//        pickTimeBtn = findViewById(R.id.btnPickTime);
        Button saveBtn = findViewById(R.id.btnSave);

//        pickTimeBtn.setOnClickListener(v -> {
//            TimePickerDialog dialog = new TimePickerDialog(this, (view, hour, minute) -> {
//                pickedHour = hour;
//                pickedMinute = minute;
//                timePicked = true;
//
//                TimeInfo tInfo = Utility.getAmPmFromHour(pickedHour);
//
//                pickTimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d %s", tInfo.hour(), pickedMinute, tInfo.period()));
//
//            },
//                    12, 0, false
//
//            );
//
//            dialog.show();
//        });

        saveBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString();
            String details = detailsField.getText().toString();
            int duration = Integer.parseInt(durationField.getText().toString());

//            if (!timePicked) {
//                Toast.makeText(this, "Please pick a time", Toast.LENGTH_SHORT).show();
//                return;
//            }

            Intent result = new Intent();
            result.putExtra("title", title);
            result.putExtra("details", details);
            result.putExtra("duration", duration);

//            result.putExtra("hour", pickedHour);
//            result.putExtra("minute", pickedMinute);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
