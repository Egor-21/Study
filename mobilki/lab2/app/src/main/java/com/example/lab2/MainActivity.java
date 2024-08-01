package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addButton1 = findViewById(R.id.addButton1);
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Трек добавлен в плейлист",
                        Toast.LENGTH_SHORT).show();
            }
        });


        Button addButton2 = findViewById(R.id.addButton2);
        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Трек добавлен в плейлист",
                        Toast.LENGTH_SHORT).show();
            }
        });


        Button addButton3 = findViewById(R.id.addButton3);
        addButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Трек добавлен в плейлист",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}