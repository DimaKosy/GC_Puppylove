package com.example.poppylove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.poppylove.databinding.ActivitySwipeBinding;

public class SwipeActivity extends AppCompatActivity {


    ActivitySwipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemReselectedListener(item -> {

            if (item.getItemId() == R.id.BottomHome) {


            } else if (item.getItemId() == R.id.BottomMess) {

                replaceFragment(new MessageFragment());


            } else if (item.getItemId() == R.id.BottomUser) {
                replaceFragment(new UserFragment());

            }
        });
    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}