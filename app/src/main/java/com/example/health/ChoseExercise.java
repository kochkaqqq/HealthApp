package com.example.health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.adapters.SelectingExerciseListAdapter;
import com.example.health.domain.Exercise;
import com.example.health.trains_configuration.ExerciseList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChoseExercise extends AppCompatActivity {
    ListView listView;

    List<Exercise> chosenExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chose_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.ibBackBtn);
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        listView = findViewById(R.id.selectingExerciseListView);
        SelectingExerciseListAdapter adapter = new SelectingExerciseListAdapter(this, new ExerciseList().GetList(), chosenExercises);
        listView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Gson gson = new Gson();
            String result = gson.toJson(chosenExercises);
            Intent intent = new Intent();
            intent.putExtra("ListOfChosenExercises", result);
            setResult(Activity.RESULT_OK, intent);

            finish();
        });
    }
}