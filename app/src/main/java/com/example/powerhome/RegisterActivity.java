package com.example.powerhome;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btn_create).setOnClickListener(v -> {
            Toast.makeText(this, "Compte créé (démo)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
