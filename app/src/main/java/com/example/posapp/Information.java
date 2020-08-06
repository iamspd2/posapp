package com.example.posapp;

public class Information {
    private String Name;
    private String Phone;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public Information() {
    }

    public Information(String name, String phone) {
        this.Name = name;
        this.Phone = phone;
    }
}
