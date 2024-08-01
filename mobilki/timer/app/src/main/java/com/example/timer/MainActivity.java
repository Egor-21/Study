package com.example.timer;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Chronometer chronometer;
    private long timeWhenStopped = 0;
    private LinearLayout additionalButtonsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        additionalButtonsLayout = findViewById(R.id.additionalButtonsLayout);

        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);
        Button resetButton = findViewById(R.id.resetButton);
        Button additionalButton = findViewById(R.id.additionalButton);


        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAdditionalButtonsVisibility();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int but = v.getId();
        if (but == R.id.startButton) {
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
        } else if (but == R.id.stopButton) {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
        } else if (but == R.id.resetButton) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenStopped = 0;
        }
    }

    private void toggleAdditionalButtonsVisibility() {
        if (additionalButtonsLayout.getChildCount() > 0) {

            additionalButtonsLayout.removeAllViews();
        } else {

            createAdditionalButtons();
        }
    }

    private void createAdditionalButtons() {
        Button add5Button = createAdditionalButton("+5 секунд", -5000);
        Button subtract5Button = createAdditionalButton("-5 секунд", +5000);
        Button add10Button = createAdditionalButton("+10 секунд", -10000);
        Button subtract10Button = createAdditionalButton("-10 секунд", +10000);

        additionalButtonsLayout.addView(add5Button);
        additionalButtonsLayout.addView(subtract5Button);
        additionalButtonsLayout.addView(add10Button);
        additionalButtonsLayout.addView(subtract10Button);
    }

    private Button createAdditionalButton(String buttonText, final long timeChange) {
        Button additionalButton = new Button(MainActivity.this);
        additionalButton.setText(buttonText);
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(chronometer.getBase() + timeChange);
            }
        });
        return additionalButton;
    }
}