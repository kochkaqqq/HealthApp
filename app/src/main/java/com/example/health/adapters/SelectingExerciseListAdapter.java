package com.example.health.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.health.R;
import com.example.health.domain.Exercise;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SelectingExerciseListAdapter extends BaseAdapter {

    Context context;
    Exercise[] allExercise;
    List<Exercise> chosenExercise;
    LayoutInflater inflater;

    public SelectingExerciseListAdapter(Context ctx, Exercise[] exercises, List<Exercise> result) {
        this.context = ctx;
        this.allExercise = exercises;
        inflater = LayoutInflater.from(ctx);
        this.chosenExercise = result;
    }

    @Override
    public int getCount() {
        return allExercise.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_selecting_exercise_list_view, null);

        TextView textView = convertView.findViewById(R.id.textView);
        String exerciseName = context.getResources().getString(allExercise[position].NameId);
        textView.setText(exerciseName);

        GifDrawable gifDrawable = GifDrawable.createFromResource(context.getResources(), allExercise[position].GifId);
        GifImageView imageView = convertView.findViewById(R.id.gifView);
        imageView.setImageDrawable(gifDrawable);

        CheckBox isChosen = convertView.findViewById(R.id.isChosen);

        isChosen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chosenExercise.add(allExercise[position]);
            }
            else {
                chosenExercise.remove(allExercise[position]);
            }
        });

        return convertView;
    }
}
