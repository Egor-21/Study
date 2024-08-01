package com.example.dialog;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDialogButton = findViewById(R.id.openDialogButton);
        displayedText = findViewById(R.id.displayedText);

        openDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomDialog();
            }
        });
    }

    private void openCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.editText);
        Button closeButton = dialogView.findViewById(R.id.closeDialogButton);

        builder.setPositiveButton("Отобразить текст", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String enteredText = editText.getText().toString();
                displayText(enteredText);
            }
        });

        builder.setNegativeButton("Отмена", null);

        final AlertDialog dialog = builder.create();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    private void displayText(String text) {
        displayedText.setText(text);
    }
}