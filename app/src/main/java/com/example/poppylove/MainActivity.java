package com.example.poppylove;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        img = findViewById(R.id.imgID);
        //
        img.animate().alpha(4000).setDuration(0);
        handler = new Handler();

        FirebaseController.initialise(getApplicationContext());


        //TESTING
        //<ProfileData>
        final List<ProfileData>[] pf = new List[1];
        pf[0] = new ArrayList<>();

        Log.d("START_USERLIST","Start");
        FirebaseController.pullUserList("0", new Callback(){
            @Override
            public void onComplete(int result) throws InterruptedException {

            }

            @Override
            public void onUserListComplete(List<ProfileData> result) {
                pf[0] = result;
                MatchingAlgorithm.SortByAlgorithm(pf[0],
                        new DogProfile()
                                .setDogSize(2)
                                .setDogActivity(2),
                        new MatchCallback() {
                            @Override
                            public void onMatchSortComplete(List<ProfileData> result) {
                                //On matchsort complete
                            }
                        }
                );

                Log.d("SORTED", pf[0].toString());
            }

            @Override
            public void onDogListComplete(List<DogProfile> result) {

            }


        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(dsp);
                finish();
            }
        },4000);

    }
}