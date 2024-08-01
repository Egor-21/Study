package com.example.lab12;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View ovalView;
    private View rectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ovalView = findViewById(R.id.ovalView);
        rectView = findViewById(R.id.rectView);

        // Создаем ShapeDrawable для овала
        ShapeDrawable ovalDrawable = new ShapeDrawable(new OvalShape());
        ovalDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_blue_light));
        ovalView.setBackground(ovalDrawable);

        // Создаем ShapeDrawable для прямоугольника
        ShapeDrawable rectDrawable = new ShapeDrawable(new RectShape());
        rectDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_red_light));
        rectView.setBackground(rectDrawable);

        // Применяем анимацию преобразования (translate) к овалу
        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
        ovalView.startAnimation(translateAnimation);


        // Применяем кадровую анимацию к прямоугольнику
        ImageView imageView = findViewById(R.id.rectView);
        imageView.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
        frameAnimation.start();
    }
}