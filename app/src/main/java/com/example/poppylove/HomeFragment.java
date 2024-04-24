package com.example.poppylove;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageView proImage;
    Button Dislike, Like;
    int index;
    String phoneID;
    String SelectedID;
    MatchingAlgorithm matchingAlgorithm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        phoneID = this.getArguments().getString("PhoneID");

        index = 0;

        Dislike = view.findViewById(R.id.Dislike);
        Like= view.findViewById(R.id.Like);
        proImage = view.findViewById(R.id.ProfileImage);

        //<ProfileData>

        matchingAlgorithm = new MatchingAlgorithm();

        final List<ProfileData>[] pf_list = new List[1];
        pf_list[0] = new ArrayList<>();


        Log.d("START_USERLIST","Start");

        List<String> exclusion = new ArrayList<>();
        exclusion.add(phoneID);

        FirebaseController.pullDisliked(phoneID, new MatchCallback() {
            @Override
            public void onMatchSortComplete(List<ProfileData> result) {

            }

            @Override
            public void onLinkedComplete(List<String> result) {

                for(String pf:result){
                    exclusion.add(pf);
                }

                FirebaseController.pullLiked(phoneID, new MatchCallback() {
                    @Override
                    public void onMatchSortComplete(List<ProfileData> result) {

                    }

                    @Override
                    public void onLinkedComplete(List<String> result) {
                        Log.d("ENTERED_LINKER","Linker");
                        for(String pf:result){
                            exclusion.add(pf);
                        }

                        FirebaseController.pullUserList(exclusion, new Callback(){
                            @Override
                            public void onComplete(int result) throws InterruptedException {

                            }

                            @Override
                            public void onUserListComplete(List<ProfileData> result) {
                                pf_list[0] = result;
                                MatchingAlgorithm matchingAlgorithm = new MatchingAlgorithm();

                                matchingAlgorithm.SortByAlgorithm(pf_list[0],
                                        new DogProfile()
                                                .setDogSize(2)
                                                .setDogActivity(2),
                                        new MatchCallback() {
                                            @Override
                                            public void onMatchSortComplete(List<ProfileData> result) {
                                                //On matchsort complete

                                                ImageController imageController = new ImageController(getActivity().getApplicationContext());
                                                pf_list[0] = result;

                                                Bitmap bitmap = imageController.downloadUserImage(pf_list[0].get(index).getPhone());
                                                if(bitmap != null){
                                                    proImage.setImageBitmap(bitmap);
                                                }



                                                Log.d("COMP_MATCH","Matching sorted");
                                            }

                                            @Override
                                            public void onLinkedComplete(List<String> result) {

                                            }
                                        }
                                );

                                Log.d("SORTED", pf_list[0].toString());
                                index = 0;
                            }

                            @Override
                            public void onDogListComplete(List<DogProfile> result) {

                            }


                        });
                    }
                });
            }
        });





        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pf_list[0].size() == 0){
                    return;
                }

                String liked = pf_list[0].get(index).getPhone();
                Log.d("LIKED_BY",phoneID+"->"+liked);

                FirebaseController.Like(phoneID,liked);

                if(pf_list[0].size() >= 1){
                    pf_list[0].remove(index);
                }

                index++;
                if(index >= pf_list[0].size()){
                    index = 0;
                }

                if(pf_list[0].size() == 0){
                    return;
                }
                
                ImageController imageController = new ImageController(getActivity().getApplicationContext());

                Bitmap bitmap = imageController.downloadUserImage(pf_list[0].get(index).getPhone());
                if(bitmap != null){
                    proImage.setImageBitmap(bitmap);
                    bitmap = null;
                }
                else{

                    proImage.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/dog_head_profile_svgrepo_com", null, getActivity().getPackageName())));
                }

            }
        });

        Dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pf_list[0].size() == 0){
                    return;
                }

                String liked = pf_list[0].get(index).getPhone();
                Log.d("LIKED_BY",phoneID+"->"+liked);

                FirebaseController.Dislike(phoneID,liked);

                if(pf_list[0].size() >= 1){
                    pf_list[0].remove(index);
                }

                index++;
                if(index >= pf_list[0].size()){
                    index = 0;
                }

                if(pf_list[0].size() == 0){
                    return;
                }

                ImageController imageController = new ImageController(getActivity().getApplicationContext());

                Bitmap bitmap = imageController.downloadUserImage(pf_list[0].get(index).getPhone());
                if(bitmap != null){
                    proImage.setImageBitmap(bitmap);
                    bitmap = null;
                }
                else{

                    proImage.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/dog_head_profile_svgrepo_com", null, getActivity().getPackageName())));
                }
            }
        });

        return view;
    }


}
