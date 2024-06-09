package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {

    TextView tvUserName;
    TextView tvEmail;
    ImageButton btnBack;
    TextView scoreValue;

    EditText editTextHeight;
    EditText editTextWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvUserName = findViewById(R.id.tv_profile_username);
        tvUserName.setText(PrefManager.getString(this, "user_name", "Unknown user"));

        tvEmail = findViewById(R.id.emailTextView);
        tvEmail.setText(PrefManager.getString(this, "email", "Unknown email address"));

        btnBack = findViewById(R.id.ibBackBtn);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });

        scoreValue = findViewById(R.id.scoreValue);
        String score = PrefManager.getString(this, "score", "0");
        scoreValue.setText(score);
    }
}