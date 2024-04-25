package com.example.poppylove;


import android.graphics.drawable.Drawable;

public class ProfileCard {
    // User details
    private String name;
    private int age;
    private String occupation;
    private Drawable image; // User's image

    // Dog details
    private Drawable dogImage; // Dog's image
    private String dogName;

    // Flag to check if dog profile should be shown
    private boolean isDogProfileShown = false;

    // Flag for biography visibility
    private boolean biographyVisible = false;

    public ProfileCard(String name, int age, String occupation, Drawable image, Drawable dogImage, String dogName) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
        this.image = image;
        this.dogImage = dogImage;
        this.dogName = dogName;
    }

    // Method to toggle between user and dog profiles
    public void toggleProfile() {
        isDogProfileShown = !isDogProfileShown;
    }

    // Check if the dog profile is currently shown
    public boolean isDogProfileShown() {
        return isDogProfileShown;
    }

    public boolean isBiographyVisible() {
        return biographyVisible;
    }

    public void toggleBiographyVisibility() {
        biographyVisible = !biographyVisible;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable getDogImage() {
        return dogImage;
    }

    public void setDogImage(Drawable dogImage) {
        this.dogImage = dogImage;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getProfileName() {
        return isDogProfileShown ? dogName : name + ", " + age;
    }

    public String getProfileOccupation() {
        return isDogProfileShown ? "" : occupation; // Assuming dogs don't have an occupation
    }

    public Drawable getProfileImage() {
        return isDogProfileShown ? dogImage : image;
    }

    // Assuming we don't show dog image in the corner if the dog profile is shown
    public Drawable getProfileDogImage() {
        return isDogProfileShown ? null : dogImage;
    }
}

