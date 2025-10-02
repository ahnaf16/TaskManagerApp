package com.ahnaf.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.appbar.MaterialToolbar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleField, detailsField, secField, projectField, locationField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Toolbar
        MaterialToolbar topAppBar = findViewById(R.id.addTaskTopAppBar);
        setSupportActionBar(topAppBar);

        titleField = findViewById(R.id.editTitle);
        detailsField = findViewById(R.id.editDetails);
        secField = findViewById(R.id.min);

        projectField = findViewById(R.id.project);
        locationField = findViewById(R.id.location);


        RadioGroup radioGroup = findViewById(R.id.taskTypeRG);
        radioGroup.check(R.id.personal);

        Button saveBtn = findViewById(R.id.btnSave);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.work) {
                projectField.setVisibility(View.VISIBLE);
                locationField.setVisibility(View.GONE);
            } else {
                projectField.setVisibility(View.GONE);
                locationField.setVisibility(View.VISIBLE);
            }
        });


        saveBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString();
            String details = detailsField.getText().toString();
            int sec = Integer.parseInt(secField.getText().toString());
            boolean isWork = radioGroup.getCheckedRadioButtonId() == R.id.work;

            String extra;
            if (isWork) {
                extra = projectField.getText().toString();
            } else {
                extra = locationField.getText().toString();
            }


            Intent result = new Intent();
            result.putExtra("title", title);
            result.putExtra("details", details);
            result.putExtra("sec", sec);
            result.putExtra("extra", extra);
            result.putExtra("isWork", isWork);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
