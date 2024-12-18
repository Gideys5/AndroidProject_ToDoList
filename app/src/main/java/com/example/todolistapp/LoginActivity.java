package com.example.todolistapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, goToRegisterButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        usernameInput = findViewById(R.id.login_username);
        passwordInput = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        goToRegisterButton = findViewById(R.id.go_to_register_button);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValid = dbHelper.checkUser(username, password);
                if (isValid) {
                    Toast.makeText(this, "Login avvenuto con successo", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Credenziali errate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToRegisterButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }
}
