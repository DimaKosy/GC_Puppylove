package com.example.poppylove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.poppylove.databinding.ActivitySwipeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    ActivitySwipeBinding binding;
    BottomNavigationView bottomNavigationView;
    MatchingAlgorithm matchingAlgorithm;
    FrameLayout frameLayout;

    String phoneID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        phoneID = bundle.getString("PhoneID");

        binding = ActivitySwipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.BottomHome) {
                loadFragment(new HomeFragment());

            } else if (itemId == R.id.BottomUser) {
                loadFragment(new UserFragment());

            }

            return false;

        });


    }


    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        Bundle bundle = new Bundle();
        bundle.putString("PhoneID",phoneID);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

}
