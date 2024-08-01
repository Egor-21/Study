package com.example.resurcy;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout back = findViewById(R.id.root);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            back.setBackgroundResource(R.drawable.background);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            back.setBackgroundResource(R.drawable.back1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final LinearLayout mylayout = findViewById(R.id.root);
        final TextView myText = findViewById(R.id.my_text);

        if (item.getItemId() == R.id.red) {
            mylayout.setBackgroundColor(getResources().getColor(R.color.red));
            return true;
        }

        if (item.getItemId() == R.id.green) {
            mylayout.setBackgroundColor(getResources().getColor(R.color.green));
            return true;
        }

        if (item.getItemId() == R.id.blue) {
            mylayout.setBackgroundColor(getResources().getColor(R.color.blue));
            return true;
        }

        if (item.getItemId() == R.id.bold) {
            myText.setTypeface(null, Typeface.BOLD);
            return true;
        }

        if (item.getItemId() == R.id.normal) {
            myText.setTypeface(null, Typeface.NORMAL);
            return true;
        }

        if (item.getItemId() == R.id.b1) {
            mylayout.setBackgroundResource(R.drawable.back1);
            return true;
        }

        if (item.getItemId() == R.id.b2) {
            mylayout.setBackgroundResource(R.drawable.back2);
            return true;
        }
        if (item.getItemId() == R.id.back) {
            mylayout.setBackgroundResource(R.drawable.background);
            return true;
        }



        if (item.getItemId() == R.id.exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}