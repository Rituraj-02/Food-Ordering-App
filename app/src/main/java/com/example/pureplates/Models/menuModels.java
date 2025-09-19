package com.example.pureplates.Models;

import android.content.Context;

import java.util.ArrayList;

public class menuModels {
    private String name;
    private String price;
    private String description;
    private String ingredients;
    private String imageUrl;

    public menuModels() {
        // Required by Firebase
    }

    public menuModels(String name, String price, String description, String ingredients, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
