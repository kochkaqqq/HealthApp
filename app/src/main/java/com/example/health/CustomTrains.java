package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.adapters.MyTrainsListAdapter;
import com.google.gson.Gson;

public class CustomTrains extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_trains);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backBtn = findViewById(R.id.ibBackBtn);
        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        Button newTrainBtn = findViewById(R.id.createNewTrainButton);
        newTrainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCustomTrain.class);
            this.startActivity(intent);
        });

        /*
        Gson gson = new Gson();
        String tmp

        ListView listVew = findViewById(R.id.trainsListView);
        MyTrainsListAdapter adapter = new MyTrainsListAdapter(this, )
        */
    }
}