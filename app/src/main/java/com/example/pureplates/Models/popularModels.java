package com.example.pureplates.Models;

public class popularModels {
    int image;
    String foodName,price;

    public popularModels(int image, String price, String foodName) {
        this.image = image;
        this.price = price;
        this.foodName = foodName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
