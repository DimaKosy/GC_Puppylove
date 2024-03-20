package com.example.poppylove;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

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

    int Login(){
        return 0;
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
