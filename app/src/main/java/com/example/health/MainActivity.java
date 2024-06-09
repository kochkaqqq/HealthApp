package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.health.adapters.ViewPageAdapter;
import com.example.health.databinding.ActivityMainBinding;
import com.example.health.model.menu.handlers.ProfileHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.train_page);
        ReplaceFragment(new HomePageFragment(this));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.train_page:
                    ReplaceFragment(new HomePageFragment(this));
                    break;
                case R.id.rank_page:
                    ReplaceFragment(new RatingFragment());
                    break;

            }

            return true;
        });

        ImageButton openMenuBtn = findViewById(R.id.open_menu_btn);
        openMenuBtn.setOnClickListener(v ->
        {
            DrawerLayout drawerLayout = findViewById(R.id.main);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        NavigationView navigationView = findViewById(R.id.navigation_view_id);
        navigationView.setNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId())
            {
                case R.id.nav_profile:
                    ProfileHandler.Handle(this);
                    break;
                case R.id.nav_subscription:
                    break;
                case R.id.nav_training:
                    break;
                case R.id.nav_rating:
                    break;
                case R.id.nav_settings:
                    Intent intent = new Intent(this, Settings.class);
                    this.startActivity(intent);
                    break;
                case R.id.nav_logout:
                    PrefManager.putString(this, "user_name", "");
                    PrefManager.putString(this, "email", "");
                    PrefManager.putString(this, "subscription_end", "");
                    intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_invite_friends:
                    break;
                case R.id.nav_report_problem:
                    break;
                default:
                    return false;
            }
            DrawerLayout drawerLayout = findViewById(R.id.main);
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}