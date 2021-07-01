package com.example.randomizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    StringBuilder historyLogEntry = new StringBuilder();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Object instantiation:
        Button rollButton = findViewById(R.id.rollButton);
        Button copyButton = findViewById(R.id.copyButton);
        Button historyButton = findViewById(R.id.historyButton);
        FloatingActionButton switchModeButton = findViewById(R.id.switchModeButton);
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView result = findViewById(R.id.rollResult);
        TextView resultHeader = findViewById(R.id.resultHeader);
        TextView upperLimitValue = findViewById(R.id.upperLimitValue);

        // Setting seek bar values:
        seekBar.setMin(1);
        seekBar.setMax(100);
        seekBar.setProgress(50);

        // Setting result header to show default pre-click text:
        resultHeader.setText(R.string.preClickResultHeaderStandard);

        // Setting initial upper limit value to default 50:
        upperLimitValue.setText(R.string.initialSeekBarValue);

        // Initializing the seek bar value and setting it to the default 50 in case
        // user rolls without touching the seek bar at all
        final int[] seekBarValue = new int[1];
        seekBarValue[0] = seekBar.getProgress();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            // Dynamically showing seek bar value:
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                upperLimitValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            // Capturing the value the seek bar is left at and using that as upper limit:
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarValue[0] = seekBar.getProgress();
            }
        });

        // Initializing random object and onClickListener;
        // When the roll button is clicked, the results header changes, then a new random number
        // is generated from the seek bar value, and the result textView is updated to show that:
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        Random random = new Random();
        rollButton.setOnClickListener(v -> {
            resultHeader.setText(R.string.resultHeader);
            int rand = random.nextInt(seekBarValue[0]) + 1;
            result.setText(String.valueOf(rand));

            // Appending result to the history log:
            historyLogEntry.append("[")
                    .append(LocalDateTime.now().format(dtf))
                    .append("] from 1 to ")
                    .append(seekBarValue[0])
                    .append(" - ")
                    .append(result.getText())
                    .append("\n");
        });


        // Clicking the change mode button will switch to the custom mode:
        switchModeButton.setOnClickListener(v -> changeMode());

        // Copying result to clipboard:
        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy", result.getText());
            clipboard.setPrimaryClip(clip);
        });

        // History log entry:
        historyButton.setOnClickListener(v -> historyLogActivity());
    }

    public void changeMode() {
        Intent intent = new Intent(this, SwitchMode.class);
        startActivity(intent);
    }

    public void historyLogActivity() {
        Intent intent = new Intent(this, History.class).putExtra("log", (Serializable) historyLogEntry);
        startActivity(intent);
    }
}