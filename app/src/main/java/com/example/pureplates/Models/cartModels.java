package com.example.pureplates.Models;

public class cartModels {
    String imageUrl;
    int quantity;
    String cartFoodName;
    String cartItemPrice;
    String foodDescription;
    String userName;

    public cartModels() {}

    public cartModels(String imageUrl, int quantity, String cartFoodName, String cartItemPrice, String foodDescription, String userName) {
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.cartFoodName = cartFoodName;
        this.cartItemPrice = cartItemPrice;
        this.foodDescription = foodDescription;
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCartFoodName() {
        return cartFoodName;
    }

    public void setCartFoodName(String cartFoodName) {
        this.cartFoodName = cartFoodName;
    }

    public String getCartItemPrice() {
        return cartItemPrice;
    }

    public void setCartItemPrice(String cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    // Getters and setters...
}
