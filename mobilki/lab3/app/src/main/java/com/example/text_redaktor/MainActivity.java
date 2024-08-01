package com.example.text_redaktor;


import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private float mTextSize = 20;
    private EditText mEdit;
    private TextView tSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit = findViewById(R.id.edit_text);
        tSize = findViewById(R.id.size);

        Button buttonB = findViewById(R.id.button_b);
        Button buttonI = findViewById(R.id.button_i);
        Button buttonSans = findViewById(R.id.button_sans);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonSerif = findViewById(R.id.button_serif);
        Button buttonMonospace = findViewById(R.id.button_monospace);

        buttonB.setOnClickListener(this);
        buttonI.setOnClickListener(this);
        buttonSans.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonSerif.setOnClickListener(this);
        buttonMonospace.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_plus){
            if(mTextSize < 72){
                mTextSize += 2;
                mEdit.setTextSize(mTextSize);
                tSize.setText(String.format("%.0f", mTextSize));
            }
        }

        if(v.getId() == R.id.button_minus){
            if(mTextSize > 10){
                mTextSize -= 2;
                mEdit.setTextSize(mTextSize);
                tSize.setText(String.format("%.0f", mTextSize));
            }
        }
        if (v.getId() == R.id.button_b) {
            if (mEdit.getTypeface().getStyle() == Typeface.ITALIC)
                mEdit.setTypeface(mEdit.getTypeface(),
                        Typeface.BOLD_ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD_ITALIC)
                mEdit.setTypeface(mEdit.getTypeface(), Typeface.ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD)
                mEdit.setTypeface(Typeface.create(mEdit.getTypeface(),
                        Typeface.NORMAL));
            else mEdit.setTypeface(mEdit.getTypeface(), Typeface.BOLD);
        }

        if(v.getId() == R.id.button_i){
            if (mEdit.getTypeface().getStyle() == Typeface.BOLD)
                mEdit.setTypeface(mEdit.getTypeface(),
                        Typeface.BOLD_ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD_ITALIC)
                mEdit.setTypeface(mEdit.getTypeface(), Typeface.BOLD);
            else if (mEdit.getTypeface().getStyle() == Typeface.ITALIC)
                mEdit.setTypeface(Typeface.create(mEdit.getTypeface(),
                        Typeface.NORMAL));
            else mEdit.setTypeface(mEdit.getTypeface(), Typeface.ITALIC);
        }


        if (v.getId() == R.id.button_sans) {
            if (mEdit.getTypeface().getStyle() == Typeface.ITALIC)
                mEdit.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD_ITALIC)
                mEdit.setTypeface(Typeface.SANS_SERIF,
                        Typeface.BOLD_ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD)
                mEdit.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            else mEdit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        }
        if (v.getId() == R.id.button_serif) {
            if (mEdit.getTypeface().getStyle() == Typeface.ITALIC)
                mEdit.setTypeface(Typeface.SERIF, Typeface.ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD_ITALIC)
                mEdit.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD)
                mEdit.setTypeface(Typeface.SERIF, Typeface.BOLD);
            else mEdit.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        }
        if (v.getId() == R.id.button_monospace) {
            if (mEdit.getTypeface().getStyle() == Typeface.ITALIC)
                mEdit.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD_ITALIC)
                mEdit.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
            else if (mEdit.getTypeface().getStyle() == Typeface.BOLD)
                mEdit.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            else mEdit.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        }


    }
}