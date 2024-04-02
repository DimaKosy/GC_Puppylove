package com.example.poppylove;

public class DogProfile {
    String Name;
    String Picture;

    public DogProfile(String Name, String Picture){
        this.Name = Name;
        this.Picture = Picture;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
