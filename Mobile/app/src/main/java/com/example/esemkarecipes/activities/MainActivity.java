package com.example.esemkarecipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.esemkarecipes.R;
import com.example.esemkarecipes.databinding.ActivityMainBinding;
import com.example.esemkarecipes.fragments.CategoryFragment;
import com.example.esemkarecipes.fragments.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            changeFragment(new CategoryFragment());
            getSupportActionBar().setTitle("Categories");
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.page_category) {
                    if (getSupportActionBar() != null) {
                        changeFragment(new CategoryFragment());
                        getSupportActionBar().setTitle("Categories");
                        return  true;
                    }
                }else {
                    if (getSupportActionBar() != null) {
                        changeFragment(new MyProfileFragment());
                        getSupportActionBar().setTitle("My Profile");
                        return  true;
                    }
                }
                return false;
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}
