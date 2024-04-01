package com.example.poppylove;

public class ProfileData {
    String Name;
    String Password;
    String Bio;


    public ProfileData() {
        // 无需做任何事情
    }
    public ProfileData(String Name, String Password){
        this.Name = Name;
        this.Password = Password;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}
