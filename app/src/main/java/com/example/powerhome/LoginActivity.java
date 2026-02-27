package com.example.powerhome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        View register = findViewById(R.id.btn_register);
        register.setOnClickListener(
                e -> {
                    register(register);
                }
        );
        View btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(
                e -> {
                    login(btnLogin);
                }
        );
    }

    private void login(View v) {
        Log.i("login_activity:login", "bouton login clicked");
        EditText email = findViewById(R.id.et_email);
        EditText mdp = findViewById(R.id.et_mdp);

        String eMail = email.getText().toString().trim();
        String mDP = mdp.getText().toString().trim();

        if (!eMail.isEmpty() && !mDP.isEmpty()) {
            if (eMail.equals("abcd") && mDP.equals("EFGH")) {
                // Succès
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", eMail);
                intent.putExtra("mdp", mDP);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();

            if (eMail.isEmpty()) email.setError("Veuillez remplir ce champ");
            if (mDP.isEmpty()) mdp.setError("Veuillez remplir ce champ");
        }
    }

    public void register(View v){
        Log.i("login_activity:login", "bouton register clicked");

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        startActivity(intent);

    }
}