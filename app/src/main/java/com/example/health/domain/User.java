package com.example.health.domain;

import java.util.Date;

public class User
{
    public int Id;
    public String Name;
    public String Email;
    public String Password;
    //public Date SubscriptionEnd;
    public int Score;

    public int getScore()
    {
        return Score;
    }
    public String getName() {
        return Name;
    }
    public String getEmail()
    {
        return Email;
    }
}
