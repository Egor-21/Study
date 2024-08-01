package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.setTitle("Second Activity");
        final Button back = (Button)findViewById(R.id.bReturnToMain);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                SecondActivity.this.finish();
            }
        });
    }
}
