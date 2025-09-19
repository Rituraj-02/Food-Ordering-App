package com.example.pureplates;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pureplates.Models.cartModels;
import com.example.pureplates.databinding.ActivityDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    String foodName, foodImageUrl, foodPrice, foodDescription, foodIngredients;
    int foodImageResource = 0; // If using local resource image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        // Get data from Intent
        foodName = getIntent().getStringExtra("MenuItemName");
        foodImageUrl = getIntent().getStringExtra("MenuItemImage");
        foodPrice = getIntent().getStringExtra("MenuItemPrice");
        foodDescription = getIntent().getStringExtra("MenuItemDescription");
        foodIngredients = getIntent().getStringExtra("MenuItemIngredients");

        // Set data to views
        binding.detailFoodname.setText(foodName);

        if (foodImageUrl != null && !foodImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(foodImageUrl)
                    .into(binding.detailFoodImage);
        }

        binding.ingredientTextView.setText(foodDescription);
        binding.IngredientsTextView.setText(foodIngredients);

        binding.addItemButton.setText("Add to Cart - ₹" + foodPrice);

        // Handle back button
        binding.imageButton.setOnClickListener(v -> finish());

        // Add to cart logic
        binding.addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();
            }
        });
    }

    private void addItemToCart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(DetailsActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        // Read user name from "Users/{userId}/name"
        DatabaseReference userRef = database.getReference("Users").child(userId).child("name");

        userRef.get().addOnSuccessListener(dataSnapshot -> {
            String userName = dataSnapshot.exists() ? dataSnapshot.getValue(String.class) : "Unknown";

            // Save cart item to "Users/{userId}/CartItems"
            DatabaseReference cartRef = database.getReference()
                    .child("Users")
                    .child(userId)
                    .child("CartItems");

            cartModels item = new cartModels(foodImageUrl, 1, foodName, foodPrice, foodDescription, userName);

            cartRef.push().setValue(item)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(DetailsActivity.this, "Item added to cart successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(DetailsActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show());

        }).addOnFailureListener(e -> {
            Toast.makeText(DetailsActivity.this, "Could not fetch user name", Toast.LENGTH_SHORT).show();
        });
    }



}
