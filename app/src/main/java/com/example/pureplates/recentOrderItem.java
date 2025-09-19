package com.example.pureplates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pureplates.Adapter.RecentBuyAdapter;
import com.example.pureplates.Models.orderDetails;
import com.example.pureplates.databinding.ActivityRecentOrderItemBinding;

import java.util.ArrayList;
import java.util.List;

public class recentOrderItem extends AppCompatActivity {

    ActivityRecentOrderItemBinding binding;

    // Declare these as class-level variables
    List<String> allFoodNames;
    List<String> allFoodPrices;
    List<String> allFoodImages;
    List<Integer> allFoodQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecentOrderItemBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ArrayList<orderDetails> recentOrderItems = (ArrayList<orderDetails>) getIntent().getSerializableExtra("RecentBuyOrderItem");

        if (recentOrderItems != null && !recentOrderItems.isEmpty()) {
            orderDetails recentOrderItem = recentOrderItems.get(0);

            allFoodNames = recentOrderItem.foodNames;
            allFoodPrices = recentOrderItem.foodPrices;
            allFoodImages = recentOrderItem.foodImages;
            allFoodQuantity = recentOrderItem.foodQuantities;
        }

        setAdapter();
    }

    private void setAdapter() {
        RecyclerView rv = binding.recyclerViewRecentBuy;
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecentBuyAdapter adapter = new RecentBuyAdapter(this, allFoodNames, allFoodPrices, allFoodImages, allFoodQuantity);
        rv.setAdapter(adapter);

    }
}
