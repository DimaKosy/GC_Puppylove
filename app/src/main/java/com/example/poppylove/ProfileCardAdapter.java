package com.example.poppylove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cardstackview.databinding.CardBinding;

import java.util.List;

public class ProfileCardAdapter extends RecyclerView.Adapter<ProfileCardAdapter.myViewHolder>{

    private final List<ProfileCard> cardList;
    private Context context;

    public ProfileCardAdapter(List<ProfileCard> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileCardAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardBinding binding=CardBinding.inflate(li,parent, false);
        return new myViewHolder(binding);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ProfileCardAdapter.myViewHolder holder, int position) {
        ProfileCard cardItem = cardList.get(position);
        holder.binding.nameAge.setText(cardItem.getProfileName());
        holder.binding.occupation.setText(cardItem.getProfileOccupation());
        holder.binding.image.setImageDrawable(cardItem.getProfileImage());

//        // Button click to toggle additional information visibility
//        holder.binding.buttonMoreInfo.setOnClickListener(v -> {
//            Log.d("InfoButton", "Button clicked");
//
//            boolean isVisible = false;
//            Log.d("VisibilityToggle", "Current visibility: " + isVisible);  // Check current state in logs
//            isVisible = holder.binding.profileInfoLayout.getVisibility() == View.VISIBLE;
//            holder.binding.profileInfoLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
//            Log.d("VisibilityToggle", "Toggled visibility to: " + holder.profileInfoLayout.getVisibility());
//        });


        Drawable dogImage = cardItem.getProfileDogImage();
        if (dogImage != null) {
            holder.binding.dogImage.setVisibility(View.VISIBLE);
            holder.binding.dogImage.setImageDrawable(dogImage);
            holder.binding.dogImage.setOnClickListener(v -> {
                cardItem.toggleProfile();
                notifyItemChanged(position);
            });
        } else {
            holder.binding.dogImage.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {
        CardBinding binding;
        ImageButton moreInfoButton;
        View profileInfoLayout;


        public myViewHolder(@NonNull CardBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            //this.moreInfoButton = binding.getRoot().findViewById(R.id.button_more_info);
            this.profileInfoLayout = binding.getRoot().findViewById(R.id.profileInfoLayout);
        }
    }
}

