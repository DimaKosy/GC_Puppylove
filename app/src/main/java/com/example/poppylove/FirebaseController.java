package com.example.poppylove;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import java.util.concurrent.atomic.AtomicBoolean;


public class FirebaseController {

    private static boolean FB_Init = false;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static int LoggedIn = 0;

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


    public static int login(String phone, String password) {
        /*LoggedIn values
        * 2 success but profile incomplete
        * 1 success
        * -1 failure incorrect password
        * -2 failure not registered
        */

        myRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ProfileData profileData = dataSnapshot.child("private").getValue(ProfileData.class);

                    if (profileData != null && profileData.getPassword().equals(password)) {

                        Log.d("LOGIN_CHECK", "You have Success Login ");
                        LoggedIn = 1;


                        profileData = dataSnapshot.child("public").getValue(ProfileData.class);
                        if(profileData == null){

                            LoggedIn = 2;
                        }

                    } else {

                        Log.d("Login", "Failure: Incorrect password");
                        LoggedIn = -1;
                    }
                } else {
                    // Phone number not found
                    Log.d("Login", "Failure: Phone number not registered");
                    LoggedIn = -2;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handl possible errors
                Log.d("Firebase", "Error: " + databaseError.getMessage());
            }
        });
        return LoggedIn;
    }


    public static boolean Register(Context context,String phone, String password){
        AtomicBoolean registered = new AtomicBoolean(false);
        Map<String, ProfileData> profileDataMap = new HashMap<>();
        profileDataMap.put("private", new ProfileData(phone.toString(), password.toString()));

        DatabaseReference profileRef = myRef.child(phone);

        profileRef.setValue(profileDataMap).addOnSuccessListener(e -> {
                //Error
                    Intent intent = new Intent(context, UpdateUserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("PhoneID",phone);

                    intent.putExtras(bundle);

                    context.startActivity(intent);
//                registered.set(false);
//                System.out.println("Error writing file: " + e.getMessage());
            }
        );



        return true;
    }

//    public static void CreateProfile(String PhoneID, String Name){
//        Map<String, ProfileData> profileDataMap = new HashMap<>();
//
//        profileDataMap.put("public", new ProfileData(Name.toString()));
//
//        DatabaseReference profileRef = myRef.child(PhoneID);
//        profileRef.setValue(profileDataMap).addOnFailureListener(e ->
//                //Error
//                System.out.println("Error writing file: " + e.getMessage())
//        );
//    }

    public static void CreateDogProfile(String PhoneID,int index, String newName, String Bio) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("/Dog_"+index+"/name", newName);
        updateData.put("/Dog_"+index+"/bio", Bio);

        DatabaseReference profileRef = myRef.child(PhoneID);
        profileRef.updateChildren(updateData)
                .addOnFailureListener(e ->
                        // Error
                        System.out.println("Error updating profile: " + e.getMessage())
                );
    }

    public static void CreateProfile(String PhoneID, String newName, String Bio) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("/public/name", newName);
        updateData.put("/public/bio", Bio);

        DatabaseReference profileRef = myRef.child(PhoneID);
        profileRef.updateChildren(updateData)
                .addOnFailureListener(e ->
                        // Error
                        System.out.println("Error updating profile: " + e.getMessage())
                );
    }

//    public static void UpdateDogProfile(String PhoneID, int index){
//        Map<String, Object> updateData = new HashMap<>();
//        updateData.put("/public/name", newName);
//
//        DatabaseReference profileRef = myRef.child(PhoneID);
//        profileRef.updateChildren(updateData)
//                .addOnFailureListener(e ->
//                        // Error
//                        System.out.println("Error updating profile: " + e.getMessage())
//                );
//    }

}