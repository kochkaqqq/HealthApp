package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.domain.User;
import com.example.health.webclient.client.Client;

import java.io.IOException;

public class Registration extends AppCompatActivity {

    TextView warnText;
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("databasekochka", "registration activity has created");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        warnText = findViewById(R.id.warningText);
        EditText etName = findViewById(R.id.etUsername);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btRegister = findViewById(R.id.btnRegister);

        btRegister.setOnClickListener(v -> {
            Client client = new Client();

            User user = new User();
            user.Name = etName.getText().toString();
            user.Email = etEmail.getText().toString();
            user.Password = etPassword.getText().toString();

            try {
                client.addEntity("User/Add", user, this);
            } catch (IOException e) {
                warnText.setText(this.getString(R.string.PasswordsNotMatch));
            }
        });

        TextView authText = findViewById(R.id.authText);
        authText.setOnClickListener(v -> {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        });

        ibBack = findViewById(R.id.ibBackBtn);
        ibBack.setOnClickListener(v ->
        {
            this.onBackPressed();
        });
    }
}