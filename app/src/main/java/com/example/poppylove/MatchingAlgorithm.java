package com.example.poppylove;

import android.health.connect.datatypes.WeightRecord;
import android.util.Log;

import com.google.android.material.color.utilities.Score;
import com.google.firebase.database.DataSnapshot;

import java.util.Collections;
import java.util.List;

public class MatchingAlgorithm {

    private static float wSize = -2;
    private static float wActivity = -2;

    private static DogProfile Filter;
    private static MatchCallback matchCallback;


    public static void SortByAlgorithm(List<ProfileData> userList, DogProfile filter, MatchCallback callback){

        Filter = filter;
        matchCallback = callback;

        ScoreProfile(userList);

        return;
    }

    private static void ScoreProfile(List<ProfileData> userList){
        final float[] score = new float[1];
        score[0] = 0;

        final int[] userCount = new int[1];
        userCount[0] = userList.size();

        for(ProfileData pd : userList) {

            FirebaseController.pullDogs(pd.getPhone(),new Callback(){
                @Override
                public void onComplete(int result) throws InterruptedException {

                }

                @Override
                public void onUserListComplete(List<ProfileData> result) {

                }

                @Override
                public void onDogListComplete(List<DogProfile> result) {
                    Log.d("PRE_SORTER",""+pd.getScore());

                    userCount[0]--;

                    for (DogProfile dp : result) {
                        if(dp == null){
                            continue;
                        }
                        score[0] += ScoreDog(dp);
                    }


                    pd.setScore(score[0]);



                    if(userCount[0] <= 0){
                        Collections.sort(userList, new ProfileComparator());

                        matchCallback.onMatchSortComplete(userList);
                        for(ProfileData pf : userList){

                            Log.d("SORTER","" + pf.getScore());
                        }
                    }
                }
            });

        }
    }

    private static float ScoreDog(DogProfile dp){
        float score;
        score = 0;

        score += wSize * (Math.abs(dp.getDogSize() - Filter.getDogSize()));
        score += wActivity * (Math.abs(dp.getDogActivity() - Filter.getDogActivity()));

        return score;
    }

    //referance: "https://www.freecodecamp.org/news/how-to-sort-a-list-in-java/#:~:text=One%20of%20the%20most%20common,in%20ascending%20order%20by%20default.&text=The%20above%20code%20creates%20a,sorted%20list%20to%20the%20console."
    private static class ProfileComparator implements java.util.Comparator<ProfileData> {
        @Override
        public int compare(ProfileData a, ProfileData b) {
            return Math.round(a.getScore() - b.getScore());
        }
    }
}
