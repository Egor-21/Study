package com.example.lab11;

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

        final Button bCallActivity = findViewById(R.id.bCallActivity);
        bCallActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        final Button bCallPhone = findViewById(R.id.bCallPhone);
        bCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkPermission(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_CODE);
            }
        });

        final Button bOpenCamera = findViewById(R.id.opencamera);
        bOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });

        final Button bOpenContacts = findViewById(R.id.opencontact);
        bOpenContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent contactsIntent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                startActivity(contactsIntent);
            }
        });

        myText = findViewById(R.id.text);
        myText.append("onCreate()\n");
    }

    public void callPhone() {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "79806253633"));
        startActivity(dialIntent);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            if (requestCode == CALL_PHONE_PERMISSION_CODE) {
                callPhone();
            } else if (requestCode == CAMERA_PERMISSION_CODE) {
                openCamera();
            }
        }
    }

    private void openCamera() {
        if (isCameraAvailable()) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(cameraIntent);
        } else {
            Toast.makeText(MainActivity.this, "Камера недоступна на этом устройстве", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCameraAvailable() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Разрешение на звонок предоставлено", Toast.LENGTH_SHORT).show();
                callPhone();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(MainActivity.this, "Разрешение на звонок отклонено! Оно необходимо для совершения звонка!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Разрешение на использование камеры предоставлено", Toast.LENGTH_SHORT).show();
                openCamera();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(MainActivity.this, "Разрешение на использование камеры отклонено! Оно необходимо для открытия камеры!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        myText.append("onRestart()\n");
    }

    @Override
    public void onStart() {
        super.onStart();
        myText.append("onStart()\n");
    }

    @Override
    public void onResume() {
        super.onResume();
        myText.append("onResume()\n");
    }

    @Override
    public void onPause() {
        super.onPause();
        myText.append("onPause()\n");
    }

    @Override
    public void onStop() {
        super.onStop();
        myText.append("onStop()\n");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myText.append("onDestroy()\n");
    }
}

