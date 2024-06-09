package com.example.health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.health.domain.Exercise;
import com.example.health.domain.Train;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class TrainingCard extends Fragment {
    private String type;
    private String imageName;
    private String text;
    private Train train;
    private Context context;

    public TrainingCard(String imageName, String text, Train _train, Context context) {
        this.imageName = imageName;
        this.text = text;
        this.train = _train;
        this.context = context;
        this.type = "train";
    }

    public TrainingCard(String imageName, String text, String type, Context context) {
        this.imageName = imageName;
        this.text = text;
        this.train = null;
        this.context = context;
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_card, container, false);

        ImageButton imageBtn = view.findViewById(R.id.imageBtn);
        TextView textView = view.findViewById(R.id.textView);

        int imageResId = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        imageBtn.setImageResource(imageResId);
        textView.setText(text);

        imageBtn.setOnClickListener(v -> {
            if (Objects.equals(this.type, "train") && this.train != null) {
                if (Objects.equals(train.Name, context.getString(R.string.AbsWorkout)))
                {
                    Gson gson = new Gson();
                    String json = gson.toJson(train);
                    Intent intent = new Intent(context, DefaultTrain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("TRAIN", json);
                    context.startActivity(intent);
                }
            }
            else if (Objects.equals(this.type, "custom trains")) {
                Intent intent = new Intent(context, CustomTrains.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }
}