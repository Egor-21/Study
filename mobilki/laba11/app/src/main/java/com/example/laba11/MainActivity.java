
package com.example.laba11;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    private static final int CALL_PHONE_PERMISSION_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим кнопку для вызова SecondActivity и назначаем обработчик клика
        final Button bCallActivity = findViewById(R.id.bCallActivity);
        bCallActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Создаем и запускаем интент для открытия SecondActivity
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        // Находим кнопку для вызова звонка и назначаем обработчик клика
        final Button bCallPhone = findViewById(R.id.bCallPhone);
        bCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Проверяем разрешение на звонок
                checkPermission(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_CODE);
            }
        });

        // Находим кнопку для открытия камеры и назначаем обработчик клика
        final Button bOpenCamera = findViewById(R.id.opencamera);
        bOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Проверяем разрешение на использование камеры
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });

        // Находим кнопку для открытия контактов и назначаем обработчик клика
        final Button bOpenContacts = findViewById(R.id.opencontact);
        bOpenContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Создаем и запускаем интент для открытия списка контактов
                Intent contactsIntent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                startActivity(contactsIntent);
            }
        });

    }

    // Метод для проверки разрешения на использование функций телефона
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Если разрешение не предоставлено, запрашиваем его
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            // Если разрешение уже есть, выполняем соответствующее действие
            if (requestCode == CALL_PHONE_PERMISSION_CODE) {
                callPhone();
            } else if (requestCode == CAMERA_PERMISSION_CODE) {
                openCamera();
            }
        }
    }

    // Метод для осуществления звонка
    public void callPhone() {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "79998882121"));
        startActivity(dialIntent);
    }



    // Метод для открытия камеры
    private void openCamera() {
        if (isCameraAvailable()) {
            // Если камера доступна, открываем камеру
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(cameraIntent);
        } else {
            // Если камера недоступна, выводим сообщение об ошибке
            Toast.makeText(MainActivity.this, "Камера недоступна на этом устройстве", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для проверки доступности камеры на устройстве
    private boolean isCameraAvailable() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // Метод для обработки результатов запроса разрешений
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            // Если разрешение на звонок было запрошено
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Если разрешение предоставлено, осуществляем звонок
                Toast.makeText(MainActivity.this, "Разрешение на звонок предоставлено", Toast.LENGTH_SHORT).show();
                callPhone();
            } else {
                // Если разрешение не предоставлено и пользователь не отменил запрос
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(MainActivity.this, "Разрешение на звонок отклонено!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            // Если разрешение на использование камеры было запрошено
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Если разрешение предоставлено, открываем камеру
                Toast.makeText(MainActivity.this, "Разрешение на использование камеры получено", Toast.LENGTH_SHORT).show();
                openCamera();
            } else {
                // Если разрешение не предоставлено и пользователь не отменил запрос
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(MainActivity.this, "Разрешение на использование камеры не получено !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}