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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


public class FirebaseController {

    private static final int MAX_DOGS = 3;

    private static boolean FB_Init = false;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static int LoggedIn = 0;
    private static ProfileData profileData;
    private static List<DogProfile> dogProfileList;
    private static DogProfile dogProfile;
    private static List<ProfileData> list;

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

    //Not implemented
    //public static int getWeights(){};

    public static void login(String phone, String password, Callback callback) {
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
                        try {
                            callback.onComplete(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }


                        profileData = dataSnapshot.child("public").getValue(ProfileData.class);
                        if(profileData == null){

                            try {
                                callback.onComplete(2);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    } else {

                        Log.d("Login", "Failure: Incorrect password");
                        try {
                            callback.onComplete(-1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    // Phone number not found
                    Log.d("Login", "Failure: Phone number not registered");
                    try {
                        callback.onComplete(-2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handl possible errors
                Log.d("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }

    public static ProfileData pullUser(Context context, String phone){
        final CountDownLatch latch = new CountDownLatch(1);

        myRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    profileData = dataSnapshot.child("public").getValue(ProfileData.class);
                }
                else{
                    profileData = null;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profileData = null;
            }
        });

        return profileData;
    }

    public static void pullUserList(String exclude, Callback userListCallback){
//        ImageController imageController = new ImageController(null);

        list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        if (ds.getKey().equals(exclude)) {
                            continue;
                        }

                        ProfileData pf = new ProfileData(ds.getKey(), "");
                        //                    pf.setPhoto(imageController.downloadUserImage(ds.getKey()));

                        list.add(pf);
                    }
                }

                userListCallback.onUserListComplete(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FAILED","CANCELLED");
            }
        });


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

    public static void pullDogs(String phone, Callback callback){

//        ImageController imageController = new ImageController(context);


        myRef.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    dogProfileList = new ArrayList<>();
                    for(int index = 1; index <= MAX_DOGS; index++ ) {
                        Log.d("PULLDOG","Dog_" + index);
                        dogProfile = dataSnapshot.child("Dog_" + index).getValue(DogProfile.class);


                        //adds dog photo to dog profile object
                        //not used as unnecessary data loading in some cases.
                        //dogProfile.setPhoto(imageController.downloadDogImage(phone, String.valueOf(index)));

                        dogProfileList.add(dogProfile);
                    }
                    callback.onDogListComplete(dogProfileList);
                }
                else{
                    callback.onDogListComplete(null);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onDogListComplete(null);
            }
        });
    }

    public static void CreateDogProfile(String PhoneID,int index, String newName, String Breed, String dogActivity, String dogSize, String Bio) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("/Dog_"+index+"/name", newName);
        updateData.put("/Dog_"+index+"/breed", Breed);
        updateData.put("/Dog_"+index+"/activity", dogActivity);
        updateData.put("/Dog_"+index+"/size", dogSize);
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