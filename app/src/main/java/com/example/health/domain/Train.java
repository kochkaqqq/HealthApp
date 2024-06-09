package com.example.health.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Train
{
    public int Id;
    public boolean IsPublic;

    public String Name;
    public String Description;
    public List<Exercise> Exercises;
    public int CreatorId;
    public int Points;

    public Train(String str)
    {

    }
}
