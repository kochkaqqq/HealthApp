package com.example.health.trains_configuration;

import com.example.health.R;
import com.example.health.domain.Exercise;
import com.example.health.domain.Train;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Abs {
    public static Train GetAbsTrain(Context context)
    {
        Train res = new Train("hello");
        res.Name = context.getString(R.string.AbsWorkout);
        res.CreatorId = 0;

        res.Exercises = new ArrayList<Exercise>();

        Exercise exercise1 = new Exercise();
        exercise1.Id = -1;
        exercise1.Name = context.getString(R.string.LyingLegRaises);
        res.Exercises.add(exercise1);
        Exercise exercise2 = new Exercise();
        exercise2.Id = -2;
        exercise2.Name = context.getString(R.string.Crunches);
        res.Exercises.add(exercise2);
        Exercise exercise3 = new Exercise();
        exercise3.Id = -3;
        exercise3.Name = context.getString(R.string.MountainClimber);
        res.Exercises.add(exercise3);
        Exercise exercise4 = new Exercise();
        exercise4.Id = -4;
        exercise4.Name = context.getString(R.string.Bicycle);
        res.Exercises.add(exercise4);

        Exercise exercise5 = new Exercise();
        exercise5.Id = -5;
        exercise5.Name = context.getString(R.string.LyingLegRaises);
        res.Exercises.add(exercise5);
        Exercise exercise6 = new Exercise();
        exercise6.Id = -6;
        exercise6.Name = context.getString(R.string.Crunches);
        res.Exercises.add(exercise6);
        Exercise exercise7 = new Exercise();
        exercise7.Id = -7;
        exercise7.Name = context.getString(R.string.MountainClimber);
        res.Exercises.add(exercise7);
        Exercise exercise8 = new Exercise();
        exercise8.Id = -8;
        exercise8.Name = context.getString(R.string.Bicycle);
        res.Exercises.add(exercise8);

        res.Points = 40;
        return res;
    }
}
