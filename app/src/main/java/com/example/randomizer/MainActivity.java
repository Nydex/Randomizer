package com.example.randomizer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rollButton = findViewById(R.id.rollButton);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMin(1);
        seekBar.setMax(100);
        seekBar.setProgress(50);

        TextView result = findViewById(R.id.rollResult);
        TextView resultHeader = findViewById(R.id.resultHeader);
        resultHeader.setText(R.string.preClickResultHeaderStandard);

        TextView upperLimitValue = findViewById(R.id.upperLimitValue);
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
        Random random = new Random();
        rollButton.setOnClickListener(v -> {
            resultHeader.setText(R.string.resultHeader);
            int rand = random.nextInt(seekBarValue[0]) + 1;
            result.setText(String.valueOf(rand));
        });


        // Clicking the change mode button will switch to the custom mode:
        FloatingActionButton switchModeButton = findViewById(R.id.switchModeButton);
        switchModeButton.setOnClickListener(v -> changeMode());
    }

    public void changeMode() {
        Intent intent = new Intent(this, SwitchMode.class);
        startActivity(intent);
    }
}