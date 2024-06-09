package com.example.health;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.health.trains_configuration.Abs;


public class HomePageFragment extends Fragment
{
    private Context context;

    public HomePageFragment(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        //FIRST CONTAINER
        TrainingCard card = new TrainingCard("abs_workout", context.getString(R.string.AbsWorkout), Abs.GetAbsTrain(context), context);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container, card);
        fragmentTransaction.commit();

        TrainingCard card2 = new TrainingCard("butt_and_thigh", context.getString(R.string.butt_and_thigh), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container, card2);
        fragmentTransaction.commit();

        //SECOND CONTAINER
        TrainingCard card3 = new TrainingCard("lower_body", context.getString(R.string.LowerBody), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container2, card3);
        fragmentTransaction.commit();

        TrainingCard card4 = new TrainingCard("upper_body", context.getString(R.string.UpperBody), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container2, card4);
        fragmentTransaction.commit();

        //THIRD CONTAINER
        TrainingCard card5 = new TrainingCard("fat_burning", context.getString(R.string.FatBurning), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container3, card5);
        fragmentTransaction.commit();

        TrainingCard card6 = new TrainingCard("ideal_body", context.getString(R.string.IdealBody), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container3, card6);
        fragmentTransaction.commit();

        //FOURTH CONTAINER
        TrainingCard card7 = new TrainingCard("create_your_train", context.getString(R.string.CreateYouTrain), "custom trains", context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container4, card7);
        fragmentTransaction.commit();

        TrainingCard card8 = new TrainingCard("community_trains", context.getString(R.string.CommunityTrains), Abs.GetAbsTrain(context), context);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.image_container4, card8);
        fragmentTransaction.commit();

        return view;
    }
}