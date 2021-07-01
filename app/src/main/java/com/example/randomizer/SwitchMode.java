package com.example.randomizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
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

        // Object instantiation:
        Button altRollButton = findViewById(R.id.altRollButton);
        Button altCopyButton = findViewById(R.id.altCopyButton);
        FloatingActionButton switchModeButton = findViewById(R.id.switchModeButton);
        TextView altResult = findViewById(R.id.altRollResult);
        TextView altResultHeader = findViewById(R.id.altResultHeader);
        EditText fromValue = findViewById(R.id.fromValue);
        EditText toValue = findViewById(R.id.toValue);

        // Setting result header to show default pre-click text:
        altResultHeader.setText(R.string.preClickResultHeaderCustom);

        // Limiting user input to 18 digits (the length of max value for Long - 1):
        fromValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        toValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        // Clicking the mode change button will open back the standard mode:
        switchModeButton.setOnClickListener(v -> changeMode());

        // When the roll button is clicked we check if both from and to values are set;
        // If at least one is empty, we show an error message
        // If From is bigger than To, we show an error message as well
        // If both are set properly, we use ThreadLocalRandom to return a random number
        altRollButton.setOnClickListener(v -> {
            if (fromValue.getText().length() == 0 || toValue.getText().length() == 0) {
                altResultHeader.setText(R.string.altModeResultHeaderNoValuesError);
            } else if (Long.parseLong(String.valueOf(toValue.getText())) < Long.parseLong(String.valueOf(fromValue.getText()))) {
                altResultHeader.setText(R.string.altModeResultHeaderToSmallerThanFromError);
            } else {
                altResultHeader.setText(R.string.resultHeader);
                @SuppressLint({"NewApi", "LocalSuppress"})
                long rand = ThreadLocalRandom.current().nextLong(Long.parseLong(String.valueOf(fromValue.getText())), Long.parseLong(String.valueOf(toValue.getText())) + 1);
                altResult.setText(String.valueOf(rand));
            }
        });

        // Copying result to clipboard:
        altCopyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy", altResult.getText());
            clipboard.setPrimaryClip(clip);
        });

    }

    public void changeMode() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}