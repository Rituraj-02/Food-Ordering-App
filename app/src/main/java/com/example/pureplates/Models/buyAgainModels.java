package com.example.pureplates.Models;

public class buyAgainModels {
    private String foodName;
    private String foodPrice;
    private String foodimage; // URL as String now

    // Constructor
    public buyAgainModels(String foodName, String foodPrice, String foodimage) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodimage = foodimage;
    }

    // Getter for image URL


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    // Other getters and setters
}
