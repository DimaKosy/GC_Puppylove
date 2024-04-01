package com.example.poppylove;

public class ProfileData {
    String Phone;
    String Password;
    String Name;


    public ProfileData() {
    }
    public ProfileData(String Phone, String Password){
        this.Phone = Phone;
        this.Password = Password;

    }

    public ProfileData(String Name) {
        this.Name = Name;
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
}
