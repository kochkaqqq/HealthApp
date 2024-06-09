package com.example.health;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.adapters.AdjustingExerciseListAdapter;
import com.example.health.domain.Exercise;
import com.example.health.domain.Train;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CreateCustomTrain extends AppCompatActivity {

    List<Exercise> exerciseList = new ArrayList<>();

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String chosenExercisesJson = data.getStringExtra("ListOfChosenExercises");
                        Gson gson = new Gson();
                        exerciseList = gson.fromJson(chosenExercisesJson, new TypeToken<List<Exercise>>() {}.getType());
                        ListView listView = findViewById(R.id.listView);
                        AdjustingExerciseListAdapter adapter = new AdjustingExerciseListAdapter(this, exerciseList);
                        listView.setAdapter(adapter);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_custom_train);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.ibBackBtn);
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        Button choseExerciseButton = findViewById(R.id.choseExercisesBtn);
        choseExerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChoseExercise.class);
            activityResultLauncher.launch(intent);
        });

        ListView listView = findViewById(R.id.listView);
        AdjustingExerciseListAdapter adapter = new AdjustingExerciseListAdapter(this, exerciseList);
        listView.setAdapter(adapter);

        Button createButton = findViewById(R.id.createTrainButton);
        createButton.setOnClickListener(v ->
        {
            EditText editText = findViewById(R.id.trainName);
            String trainName = editText.getText().toString();

            int id = Integer.parseInt(PrefManager.getString(this, "id", ""));

            Gson gson = new Gson();
            Train newTrain = new Train("");
            newTrain.Exercises = exerciseList;
            newTrain.CreatorId = id;
            newTrain.Name = trainName;

            String myTrains_ = PrefManager.getString(this, "my_trains", "");

            if(myTrains_.equals("")) {
                String tmp = gson.toJson(new ArrayList<Train>());
                PrefManager.putString(this, "my_trains", tmp);
            }

            myTrains_ = PrefManager.getString(this, "my_trains", "");

            List<Train> myTrains = gson.fromJson(myTrains_, new TypeToken<List<Train>>() {}.getType());

            myTrains.add(newTrain);

            myTrains_ = gson.toJson(myTrains);
            PrefManager.putString(this, "my_trains", myTrains_);
        });
    }
}