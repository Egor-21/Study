package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    ProgressBar progressBar;
    RatingBar ratingBar;
    TextView text;
    ToggleButton toggleButton;
    Button buttonReset, buttonIncrease;

    boolean isRunning = false;
    static final int NUM_STARS = 5;
    float step = 0.5f;
    float rating = 1.0f;
    Thread background;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable updateProgress = new Runnable() {
        public void run() {
            progressBar.incrementProgressBy(1);
            text.setText("Progress: " + progressBar.getProgress() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        text = findViewById(R.id.text);
        ratingBar = findViewById(R.id.rating);
        toggleButton = findViewById(R.id.toggle_button);
        buttonReset = findViewById(R.id.button_reset);
        buttonIncrease = findViewById(R.id.button_increase_rating);

        TextView label = findViewById(R.id.text_value);
        label.setText(String.valueOf(rating));

        ratingBar.setNumStars(NUM_STARS);
        ratingBar.setRating(rating);
        ratingBar.setStepSize(step);

        progressBar.setProgress(0);

        ratingBar.setOnRatingBarChangeListener(this);
        toggleButton.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonIncrease.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toggle_button) {
            if (toggleButton.isChecked()) {
                startProgress();
            } else {
                stopProgress();
            }
        } else if (id == R.id.button_reset) {
            resetProgress();
        } else if (id == R.id.button_increase_rating) {
            increaseRating();
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        TextView label = findViewById(R.id.text_value);
        label.setText(String.valueOf(rating));
    }

    private void startProgress() {
        isRunning = true;
        background = new Thread(new Runnable() {
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep((long) (1000 / ratingBar.getRating()));
                        handler.post(updateProgress);
                    } catch (InterruptedException e) {
                        Log.e("ERROR", "Thread Interrupted");
                    }
                }
            }
        });
        background.start();
    }

    private void stopProgress() {
        isRunning = false;
    }

    private void resetProgress() {
        progressBar.setProgress(0);
        text.setText("Progress: 0%");
    }

    private void increaseRating() {
        rating += step;
        if (rating > NUM_STARS)
            rating = NUM_STARS;
        ratingBar.setRating(rating);
    }

    public void onUpButtonClick(View view){
        increaseRating();
    }

    public void onDownButtonClick(View view){
        decreaseRating();
    }
    private void decreaseRating(){
        rating -= step;
        if(rating < 0){
            rating = 0;
        }
        ratingBar.setRating(rating);
    }
}