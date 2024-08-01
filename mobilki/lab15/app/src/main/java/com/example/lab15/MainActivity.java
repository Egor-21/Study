package com.example.lab15;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private LinearLayout sensorLayout;
    private List<Sensor> sensorList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorLayout = findViewById(R.id.sensor_layout);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 0; i < sensorList.size(); i++) {
            Sensor sensor = sensorList.get(i);
            TextView sensorTextView = new TextView(this);
            sensorTextView.setText(sensor.getName() + ": ");
            sensorLayout.addView(sensorTextView);

            TextView sensorDataTextView = new TextView(this);
            sensorLayout.addView(sensorDataTextView);

            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        StringBuilder sensorData = new StringBuilder();
        for (float value : values) {
            sensorData.append(value).append("\n");
        }
        int sensorIndex = sensorList.indexOf(event.sensor);
        if (sensorIndex != -1) {
            TextView sensorDataTextView = (TextView) sensorLayout.getChildAt(sensorIndex * 2 + 1);
            sensorDataTextView.setText(sensorData.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}