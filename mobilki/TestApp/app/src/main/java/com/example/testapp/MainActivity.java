package com.example.testapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.testapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final int notificationId = 1;
    private final String CHANNEL_ID = "my_channel_01";
    private int d = 0;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        binding.togledos.setOnClickListener(view -> onToggleClick());

        binding.kal.setOnClickListener(view -> {
            int a = Integer.parseInt(binding.Summ.getText().toString());
            int b = Integer.parseInt(binding.kal.getText().toString());
            int res = a + b;
            binding.Summ.setText(String.valueOf(res));
        });

        binding.fil.setOnClickListener(view -> {
            int a = Integer.parseInt(binding.Summ.getText().toString());
            int b = Integer.parseInt(binding.fil.getText().toString());
            int res = a + b;
            binding.Summ.setText(String.valueOf(res));
        });

        binding.button1.setOnClickListener(view -> {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, null);
            TextView text = layout.findViewById(R.id.textsale);
            text.setText(binding.yourname.getText());

            toast.setView(layout);
            toast.show();
        });

        binding.button2.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setAutoCancel(false)
                    .setSmallIcon(R.drawable.had)
                    .setContentTitle("Заказ принят")
                    .setContentText("Примерное время ожидания 10 минут")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis());

            createNotificationChannel(notificationManager);
            notificationManager.notify(notificationId, builder.build());
        });
    }

    private void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
    }

    private void onToggleClick() {
        if (binding.togledos.isChecked()) {
            binding.deliver.setText("Курьер в пути");
        } else {
            binding.deliver.setText("Не требуется");
        }
    }
}