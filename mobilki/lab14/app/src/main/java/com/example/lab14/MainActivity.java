package com.example.lab14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float lastTouchX;
    private float lastTouchY;

    private GestureDetector gestureDetector;
    private boolean isSwiped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TouchTrackingView(this));

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(getApplicationContext(), "Двойное нажатие", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (!isSwiped) {
                    isSwiped = true;
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else {
                    isSwiped = false;
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
                return true;
            }
        });
    }

    public class TouchTrackingView extends View {

        private Paint paint;

        public TouchTrackingView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawText("Координаты последнего касания: " + lastTouchX + ", " + lastTouchY, 100, 100, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            lastTouchX = event.getX();
            lastTouchY = event.getY();
            invalidate();

            gestureDetector.onTouchEvent(event);

            return true;
        }
    }
}