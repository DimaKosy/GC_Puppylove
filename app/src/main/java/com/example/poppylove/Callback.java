package com.example.poppylove;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public interface Callback {
    void onComplete(int result) throws InterruptedException;
    void onUserListComplete(List<ProfileData> result);
    void onDogListComplete(List<DogProfile> result);
}
