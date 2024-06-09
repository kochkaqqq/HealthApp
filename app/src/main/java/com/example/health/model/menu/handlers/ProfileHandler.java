package com.example.health.model.menu.handlers;

import android.app.Activity;
import android.content.Intent;

import com.example.health.PrefManager;
import com.example.health.Profile;
import com.example.health.Registration;

public class ProfileHandler
{
    public static void Handle(Activity currentActivity)
    {
        if (PrefManager.getString(currentActivity, "user_name", "").isEmpty()) {
            Intent intent = new Intent(currentActivity, Registration.class);
            currentActivity.startActivity(intent);
        }
        else {
            Intent intent = new Intent(currentActivity, Profile.class);
            currentActivity.startActivity(intent);
        }
    }
}
