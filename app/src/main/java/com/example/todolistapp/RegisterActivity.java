package com.example.todolistapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput, confirmPasswordInput;
    private Button registerButton, goToLoginButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        usernameInput = findViewById(R.id.register_username);
        passwordInput = findViewById(R.id.register_password);
        confirmPasswordInput = findViewById(R.id.register_confirm_password);
        registerButton = findViewById(R.id.register_button);
        goToLoginButton = findViewById(R.id.go_to_login_button);

        registerButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Le password non coincidono", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = dbHelper.registerUser(username, password);
                if (success) {
                    Toast.makeText(this, "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Nome utente già esistente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}