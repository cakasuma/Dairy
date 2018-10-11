package com.example.amam.dairy;

public class Veteran {
    private String name, price, title, phone;
    private float rating;

    public Veteran() {
    }

    public Veteran(String name, String price, float rating, String title, String phone) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.title = title;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}