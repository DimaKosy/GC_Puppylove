package com.example.poppylove;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.cardstackview.databinding.ActivityMainBinding;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;
import java.util.List;

public class ProfileSwipeActivity extends AppCompatActivity {
    ActivityProfileSwipeBinding binding;

        binding=ActivityProfileSwipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_profile_swipe);

        List<ProfileCard> cards=new ArrayList<>();
        // Loading images on Main Thread is causing the system to crash I should use an image loading library to do so.
        cards.add(new ProfileCard("Be good for no reason", ResourcesCompat.getDrawable(getResources(), R.drawable.card1, null)));
        cards.add(new ProfileCard("Don't worry, be happy", ResourcesCompat.getDrawable(getResources(), R.drawable.card2, null)));
        cards.add(new ProfileCard("I will arise and go now, and go to Innisfree", ResourcesCompat.getDrawable(getResources(), R.drawable.card3, null)));
        cards.add(new ProfileCard("I got a pocket full of sunshine", ResourcesCompat.getDrawable(getResources(), R.drawable.card4, null)));
        cards.add(new ProfileCard("There must have been an angel by my side", ResourcesCompat.getDrawable(getResources(), R.drawable.card5, null)));
        cards.add(new ProfileCard("You gave me the kiss of life", ResourcesCompat.getDrawable(getResources(), R.drawable.card6, null)));
        ProfileCardAdapter adapter=new ProfileCardAdapter(cards);
        binding.cardStack.setLayoutManager(new CardStackLayoutManager(getApplicationContext()));
        binding.cardStack.setAdapter(adapter);

    // Here, instantiate the CardStackLayoutManager with vertical and horizontal directions
    CardStackLayoutManager manager = new CardStackLayoutManager(this);
        manager.setDirections(Direction.HORIZONTAL); // Enable both horizontal and vertical swiping
    //manager.setDirections(Direction.VERTICAL);
        binding.cardStack.setLayoutManager(new CardStackLayoutManager(getApplicationContext()));
        binding.cardStack.setLayoutManager(manager);
        binding.cardStack.setAdapter(adapter);

    // Load images asynchronously using Glide
    loadImagesWithGlide(cards, adapter);

    private void loadImagesWithGlide(List<card> cards, card_adapter adapter) {
        int[] userImageResources = new int[] {
                R.drawable.card1, // Replace these with your actual drawable resources
                R.drawable.card2,
                R.drawable.card3,
                R.drawable.card4,
                R.drawable.card5,
                R.drawable.card6
        };

        int[] dogImageResources = new int[] {
                R.drawable.dog1, // Replace these with your actual drawable resources
                R.drawable.dog2,
                R.drawable.dog3,
                R.drawable.dog4,
                R.drawable.dog5,
                R.drawable.dog6
        };

        for (int i = 0; i < cards.size(); i++) {
            final int index = i;

            // Load user images
            Glide.with(this)
                    .load(userImageResources[index])
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            cards.get(index).setImage(resource);
                            adapter.notifyItemChanged(index);
                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {
                        }
                    });

            // Load dog images
            Glide.with(this)
                    .load(dogImageResources[index])
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            cards.get(index).setDogImage(resource);
                            // No need to notify the adapter here as the dog images are only shown when a user clicks on the dog icon
                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {
                        }
                    });
        }
    }
}



