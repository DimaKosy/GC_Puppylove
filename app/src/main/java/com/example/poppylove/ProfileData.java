package com.example.poppylove;

import android.graphics.Bitmap;

public class ProfileData {
    //Private
    String Phone;
    String Password;

    //Public
    String Name;
    String Bio;
    Bitmap Photo;


    public ProfileData() {
    }
    public ProfileData(String Phone, String Password){
        this.Phone = Phone;
        this.Password = Password;

    }

    public ProfileData(String Name, String Bio, Bitmap Photo) {
        this.Name = Name;
        this.Bio = Bio;
        this.Photo = Photo;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String bio) {
        Name = Name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
    }
}
