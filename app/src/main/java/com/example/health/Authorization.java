package com.example.health;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.webclient.client.Client;

import java.io.IOException;

public class Authorization extends AppCompatActivity {

    TextView warnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authorization);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        warnText = findViewById(R.id.warningText);

        ImageButton imageButton = findViewById(R.id.ibBackBtn);
        imageButton.setOnClickListener(v -> {
            this.onBackPressed();
        });

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button authBtn = findViewById(R.id.btnAuth);
        TextView registerText = findViewById(R.id.registerText);

        registerText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        authBtn.setOnClickListener(v -> {
            Client client = new Client();

            try {
                client.AuthUser(etEmail.getText().toString(), etPassword.getText().toString(), this);
            } catch (IOException e) {
                warnText.setText(getString(R.string.UserNotFound));
            }
        });

        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
        });
    }
}