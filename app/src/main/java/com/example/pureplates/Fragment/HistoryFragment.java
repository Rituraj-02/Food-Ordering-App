package com.example.pureplates.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pureplates.Adapter.buyAgainAdapter;
import com.example.pureplates.Models.buyAgainModels;
import com.example.pureplates.Models.orderDetails;
import com.example.pureplates.databinding.FragmentHistoryBinding;
import com.example.pureplates.recentOrderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment {

    FragmentHistoryBinding binding;
    ArrayList<orderDetails> listOfOrderItem = new ArrayList<>();
    FirebaseAuth auth;
    FirebaseDatabase database;
    String userId;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userId = auth.getCurrentUser().getUid();

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        retrieveBuyHistory();


// recent and display the User Order History
        binding.recentBuyedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeItemsRecentBuy();
            }
        });
        return binding.getRoot();
    }

    private void seeItemsRecentBuy() {
        if (!listOfOrderItem.isEmpty()) {
            orderDetails recentBuy = listOfOrderItem.get(0); // first item

            Intent intent = new Intent(requireContext(), recentOrderItem.class);
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem);
            startActivity(intent);
        }
    }


    private void retrieveBuyHistory() {
        userId = auth.getCurrentUser().getUid();
        binding.recentBuyedItem.setVisibility(View.GONE);
        DatabaseReference buyItemReference = database.getReference().child("Users").child(userId).child("BuyHistory");

        Query shortingQuery = buyItemReference.orderByChild("currentTime");
        shortingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot buySnapshot : snapshot.getChildren()) {
                    orderDetails buyHistoryItem = buySnapshot.getValue(orderDetails.class);
                    if (buyHistoryItem != null) {
                        listOfOrderItem.add(buyHistoryItem);
                    }
                }

                Collections.reverse(listOfOrderItem); // most recent first

                if (!listOfOrderItem.isEmpty()) {
                    //display the most recent order details
                    setDataInRecentBuyItem();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setDataInRecentBuyItem() {
        binding.recentBuyedItem.setVisibility(View.VISIBLE);

        if (!listOfOrderItem.isEmpty()) {
            orderDetails recentOrderItem = listOfOrderItem.get(0);

            if (recentOrderItem.foodNames != null && !recentOrderItem.foodNames.isEmpty()) {
                binding.buyAgainFoodName.setText(recentOrderItem.foodNames.get(0));
            } else {
                binding.buyAgainFoodName.setText("");
            }

            if (recentOrderItem.foodPrices != null && !recentOrderItem.foodPrices.isEmpty()) {
                binding.buyAgainFoodPrice.setText(recentOrderItem.foodPrices.get(0));
            } else {
                binding.buyAgainFoodPrice.setText("");
            }

            if (recentOrderItem.foodImages != null && !recentOrderItem.foodImages.isEmpty()) {
                String imageUrl = recentOrderItem.foodImages.get(0);
                Uri uri = Uri.parse(imageUrl);
                Glide.with(requireContext()).load(uri).into(binding.buyAgainFoodImage);
            }

            setPreviousBuyItemsRecyclerView(); // show older orders
        }
    }

    private void setPreviousBuyItemsRecyclerView() {
        ArrayList<buyAgainModels> buyAgainModelList = new ArrayList<>();

        for (int i = 1; i < listOfOrderItem.size(); i++) {
            orderDetails item = listOfOrderItem.get(i);

            if (item.foodImages != null && !item.foodImages.isEmpty()) {
                String foodName = "Unknown";
                String foodPrice = "₹0";

                // Check and assign food name if available
                if (item.foodNames != null && !item.foodNames.isEmpty()) {
                    foodName = item.foodNames.get(0);
                }

                // Check and assign food price if available
                if (item.foodPrices != null && !item.foodPrices.isEmpty()) {
                    foodPrice = item.foodPrices.get(0);
                }

                // Create model with available data
                buyAgainModels model = new buyAgainModels(
                        foodName,
                        foodPrice,
                        item.foodImages.get(0)
                );

                buyAgainModelList.add(model);
            }
        }

        // Show RecyclerView if we have any data
        if (!buyAgainModelList.isEmpty()) {
            buyAgainAdapter adapter = new buyAgainAdapter(requireContext(), buyAgainModelList);
            binding.BuyAgainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.BuyAgainRecyclerView.setAdapter(adapter);
            binding.BuyAgainRecyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.BuyAgainRecyclerView.setVisibility(View.GONE);
        }
    }

}
