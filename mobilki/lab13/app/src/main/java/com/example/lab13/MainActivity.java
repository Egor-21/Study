package com.example.lab13;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView gLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gLView = new MyGLSurfaceView(this);
        setContentView(gLView);
    }
}