package com.example.randomizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.concurrent.ThreadLocalRandom;

public class SwitchMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode2);

        Button rollButton = findViewById(R.id.rollButton);

        TextView resultHeader = findViewById(R.id.resultHeader);
        resultHeader.setText(R.string.preClickResultHeaderCustom);

        TextView altResult = findViewById(R.id.altRollResult);


        EditText fromValue = findViewById(R.id.fromValue);
        EditText toValue = findViewById(R.id.toValue);

        // Limiting input to 18 digits (the length of max value for Long - 1):
        fromValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        toValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        // Clicking the mode change button will open back the standard mode:
        FloatingActionButton switchModeButton = findViewById(R.id.switchModeButton);
        switchModeButton.setOnClickListener(v -> {
            changeMode();
        });

        // When the roll button is clicked we check if both from and to values are set;
        // If at least one is empty, we show an error message
        // If From is bigger than To, we show an error message as well
        // If both are set properly, we use ThreadLocalRandom to return a random number
        rollButton.setOnClickListener(v -> {
            if (fromValue.getText().length() == 0 || toValue.getText().length() == 0) {
                resultHeader.setText(R.string.altModeResultHeaderNoValuesError);
            } else if (Long.parseLong(String.valueOf(toValue.getText())) < Long.parseLong(String.valueOf(fromValue.getText()))) {
                resultHeader.setText(R.string.altModeResultHeaderToSmallerThanFromError);
            } else {
                resultHeader.setText(R.string.resultHeader);
                @SuppressLint({"NewApi", "LocalSuppress"})
                long rand = ThreadLocalRandom.current().nextLong(Long.parseLong(String.valueOf(fromValue.getText())), Long.parseLong(String.valueOf(toValue.getText())) + 1);
                altResult.setText(String.valueOf(rand));
            }
        });

    }

    public void changeMode() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}