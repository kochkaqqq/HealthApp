package com.example.health.domain;

public class Exercise
{
    public int Id;
    public String Name;
    public int NameId;
    public int GifId;
    public String Description;
    public int CreatorId;
    public Integer TrainId;
    public int Points;

    public Exercise() {

    }

    public Exercise(int name, int gif) {
        this.NameId = name;
        this.GifId = gif;
    }
}
