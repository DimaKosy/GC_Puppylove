package com.example.poppylove;

import android.graphics.Bitmap;

public class DogProfile {
    private String Name;
    private String dogBreed;
    private int dogActivity;
    private int dogSize;
    private String dogBio;
    private Bitmap Photo;

    public DogProfile(){

    }

    public DogProfile(String Name, Bitmap Photo){
        this.Name = Name;
        this.Photo = Photo;
    }

    public String getName() {
        return Name;
    }

    public DogProfile setName(String name) {
        Name = name;
        return this;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public DogProfile setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
        return this;
    }

    public int getDogActivity() {
        return dogActivity;
    }

    public DogProfile setDogActivity(int dogActivity) {
        this.dogActivity = dogActivity;
        return this;
    }

    public int getDogSize() {
        return dogSize;
    }

    public DogProfile setDogSize(int dogSize) {
        this.dogSize = dogSize;
        return this;
    }

    public String getDogBio() {
        return dogBio;
    }

    public DogProfile setDogBio(String dogBio) {
        this.dogBio = dogBio;
        return this;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public DogProfile setPhoto(Bitmap photo) {
        Photo = photo;
        return this;
    }
}
