package com.example.poppylove;

import android.health.connect.datatypes.WeightRecord;
import android.util.Log;

import com.google.android.material.color.utilities.Score;
import com.google.firebase.database.DataSnapshot;

import java.util.Collections;
import java.util.List;

public class MatchingAlgorithm {

    private float wSize = -2;
    private float wActivity = -2;

    private DogProfile Filter;
    private MatchCallback matchCallback;


    public void SortByAlgorithm(List<ProfileData> userList, DogProfile filter, MatchCallback callback){

        Filter = filter;
        matchCallback = callback;

        ScoreProfile(userList);

        return;
    }

    private void ScoreProfile(List<ProfileData> userList){

        final int[] userCount = new int[1];
        userCount[0] = userList.size();

        for(ProfileData pd : userList) {
            Log.d("USER_OPEN",""+pd.getPhone());

            FirebaseController.pullDogs(pd.getPhone(),new Callback(){
                @Override
                public void onComplete(int result) throws InterruptedException {

                }

                @Override
                public void onUserListComplete(List<ProfileData> result) {

                }

                @Override
                public void onDogListComplete(List<DogProfile> result) {
                    Log.d("PRE_SORTER",""+result.size());
                    int score;
                    score = 0;

                    userCount[0]--;

                    for (DogProfile dp : result) {
                        if(dp == null){
                            continue;
                        }
                        score += ScoreDog(dp);
                    }


                    pd.setScore(score);


                    if(userCount[0] <= 0){
                        Collections.sort(userList, new ProfileComparator());

                        matchCallback.onMatchSortComplete(userList);
                        for(ProfileData pf : userList){

                            Log.d("SORTER",pf.getPhone() + " : " + pf.getScore());
                        }
                    }
                }
            });

        }
    }

    private float ScoreDog(DogProfile dp){
        float score;
        score = 0;

        Log.d("TRAITS", "{" + dp.getDogSize()+" : " +dp.getDogActivity()+"},{" + Filter.getDogSize()+" : " +Filter.getDogActivity()+"}");
        score += wSize * (Math.abs(dp.getDogSize() - Filter.getDogSize()));
        score += wActivity * (Math.abs(dp.getDogActivity() - Filter.getDogActivity()));

        Log.d("TRAITS_SCORE", ""+score);

        return score;
    }

    //referance: "https://www.freecodecamp.org/news/how-to-sort-a-list-in-java/#:~:text=One%20of%20the%20most%20common,in%20ascending%20order%20by%20default.&text=The%20above%20code%20creates%20a,sorted%20list%20to%20the%20console."
    private class ProfileComparator implements java.util.Comparator<ProfileData> {
        @Override
        public int compare(ProfileData a, ProfileData b) {
            return Math.round(a.getScore() - b.getScore());
        }
    }
}
