package com.example.poppylove;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class FirebaseController {

    private static boolean FB_Init = false;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    public static void initialise(Context context){
        if(!FB_Init){
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://gc-puppylove-default-rtdb.europe-west1.firebasedatabase.app")
                    .setApplicationId("com.example.poppylove")
                    .build();

            database = FirebaseDatabase.getInstance("https://gc-puppylove-default-rtdb.europe-west1.firebasedatabase.app");
            FirebaseApp.initializeApp(context, options, "Puppylove");
            myRef = database.getReference("userdata");
            FB_Init = true;
        }
    }


    public static void login(String phone, String password) {
        myRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ProfileData profileData = dataSnapshot.child("private").getValue(ProfileData.class);

                    if (profileData != null && profileData.getPassword().equals(password)) {

                        Log.d("Login", "You have Success Login ");

                    } else {

                        Log.d("Login", "Failure: Incorrect password");
                    }
                } else {
                    // Phone number not found
                    Log.d("Login", "Failure: Phone number not registered");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handl possible errors
                Log.d("Firebase", "Error: " + databaseError.getMessage());
            }
        });

    }


    static int Register(String phone, String password){

        Map<String, ProfileData> profileDataMap = new HashMap<>();
        profileDataMap.put("private", new ProfileData(phone.toString(), password.toString()));

        DatabaseReference profileRef = myRef.child(phone);
        profileRef.setValue(profileDataMap).addOnFailureListener(e ->
                //Error
                System.out.println("Error writing file: " + e.getMessage())
        );



        return 0;
    }
}