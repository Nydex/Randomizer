package com.example.randomizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView log = findViewById(R.id.historyLog);

        // Getting the log data from the respective activity:
        final String[] logText = {getIntent().getStringExtra("log")};

        // If log is empty, show the default message, else show the log:
        if (logText[0].length() == 0) {
            log.setText(R.string.initialHistoryLog);
        } else {
            log.setText(logText[0]);
        }
    }
}