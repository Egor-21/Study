package com.example.buttons;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private TextView toggleButtonTextView;
    private TextView radioButtonsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = findViewById(R.id.toggleButton2);
        RadioButton radioButton1 = findViewById(R.id.raido1);
        RadioButton radioButton2 = findViewById(R.id.raido2);
        RadioButton radioButton3 = findViewById(R.id.raido3);
        RadioButton radioButton4 = findViewById(R.id.raido4);
        checkBox = findViewById(R.id.checkBox);
        toggleButtonTextView = findViewById(R.id.textView2);
        radioButtonsTextView = findViewById(R.id.textView3);
        ImageView imageView = findViewById(R.id.imageView);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                checkBox.setText("Включено");
            else
                checkBox.setText("Выключено");
        });
        toggleButton.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked)
                toggleButtonTextView.setText("Кнопка включена");
            else
                toggleButtonTextView.setText("Кнопка выключена");
        }));
        radioButton1.setOnClickListener(view -> {
            radioButtonsTextView.setText("Время года: Зима");
            imageView.setImageResource(R.drawable.zima);
        });
        radioButton2.setOnClickListener(view -> {
            radioButtonsTextView.setText("Время года: Лето");
            imageView.setImageResource(R.drawable.leto);
        });
        radioButton3.setOnClickListener(view -> {
            radioButtonsTextView.setText("Время года: Осень");
            imageView.setImageResource(R.drawable.osen);
        });
        radioButton4.setOnClickListener(view -> {
            radioButtonsTextView.setText("Время года: Весна");
            imageView.setImageResource(R.drawable.vesna);
        });
    }
}


    private void handleImageButton() {
        Toast.makeText(this, "Картинка!!!", Toast.LENGTH_SHORT).show();
    }
}