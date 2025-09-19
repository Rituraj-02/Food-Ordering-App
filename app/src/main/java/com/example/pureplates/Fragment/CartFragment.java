package com.example.pureplates.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pureplates.Adapter.cartAdapter;
import com.example.pureplates.Models.cartModels;
import com.example.pureplates.PayOutActivity;
import com.example.pureplates.databinding.FragmentCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    ArrayList<cartModels> cartModelList;
    cartAdapter adapter;

    FirebaseDatabase database;
    FirebaseAuth auth;

    public CartFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        cartModelList = new ArrayList<>();
        adapter = new cartAdapter(requireContext(), cartModelList);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.cartRecyclerView.setAdapter(adapter);

        fetchCartItems();

        binding.proceedButton.setOnClickListener(view -> {
            calculateTotalAndProceed();
        });

        return binding.getRoot();
    }

    private void fetchCartItems() {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference cartRef = database.getReference("Users").child(userId).child("CartItems");

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    cartModels model = dataSnapshot.getValue(cartModels.class);
                    cartModelList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  NEW METHOD TO CALCULATE TOTAL
    private void calculateTotalAndProceed() {
        int totalAmount = 0;

        for (cartModels model : cartModelList) {
            int quantity = model.getQuantity(); //assumes getter is named getQuantity()
            int price = Integer.parseInt(model.getCartItemPrice()); //assumes getter is named getPrice()
            totalAmount += quantity * price;
        }

        Intent intent = new Intent(getActivity(), PayOutActivity.class);
        intent.putExtra("totalAmount", totalAmount);
        startActivity(intent);
    }
}
