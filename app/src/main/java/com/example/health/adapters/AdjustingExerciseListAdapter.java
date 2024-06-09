package com.example.health.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.health.R;
import com.example.health.domain.Exercise;

import java.util.List;
import java.util.zip.Inflater;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AdjustingExerciseListAdapter extends BaseAdapter {

    List<Exercise> exercises;
    Context context;
    LayoutInflater inflater;

    public AdjustingExerciseListAdapter(Context ctx, List<Exercise> exercises) {
        this.exercises = exercises;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return exercises.size();
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
        convertView = inflater.inflate(R.layout.activity_adjusting_exercise_list_view, null);

        TextView textView = convertView.findViewById(R.id.textView);
        String exerciseName = context.getResources().getString(exercises.get(position).NameId);
        textView.setText(exerciseName);

        GifDrawable gifDrawable = GifDrawable.createFromResource(context.getResources(), exercises.get(position).GifId);
        GifImageView imageView = convertView.findViewById(R.id.gifView);
        imageView.setImageDrawable(gifDrawable);

        return convertView;
    }
}
