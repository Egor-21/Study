package com.example.animation;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FrameLayout container;
    private Button startAnimationButton;
    private CustomDrawableView customDrawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        startAnimationButton = findViewById(R.id.startAnimationButton);
        customDrawableView = new CustomDrawableView(this);

        container.addView(customDrawableView);

        startAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        customDrawableView.startAnimation();
    }

    private class CustomDrawableView extends View {
        private Paint paint = new Paint();
        private float translationY = 0;

        public CustomDrawableView(MainActivity context) {
            super(context);
            paint.setColor(Color.BLUE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(100, 100 + translationY, 50, paint);
        }

        public void startAnimation() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationY", 0, 500);
            animator.setDuration(1000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.start();
        }

        public void setTranslationY(float translationY) {
            this.translationY = translationY;
            invalidate();
        }
    }
}