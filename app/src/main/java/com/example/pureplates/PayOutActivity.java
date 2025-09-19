package com.example.pureplates;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pureplates.Fragment.CongratsBottomSheet;
import com.example.pureplates.Models.orderDetails;
import com.example.pureplates.databinding.ActivityPayOutBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class PayOutActivity extends AppCompatActivity {

    ActivityPayOutBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    List<String> foodNames, foodPrices, foodImages;
    List<Integer> foodQuantities;


    EditText nameEditText, phoneEditText, addressEditText, totalAmountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        totalAmountEditText = findViewById(R.id.totalAmount);


        binding.buttonBack.setOnClickListener(view -> finish());

        // Set total amount from Intent
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        totalAmountEditText.setText("₹ " + totalAmount);

        loadUserInfo();

       binding.PlaceMyOrder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String name = binding.name.getText().toString().trim();
               String phone = binding.phone.getText().toString().trim();
               String address = binding.address.getText().toString().trim();
               if(name.isEmpty()&& phone.isEmpty()&& address.isEmpty()){
                   Toast.makeText(PayOutActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
               }else{
                   placeOrder();
               }
           }
       });
    }

    private void placeOrder() {
        String userId = auth.getCurrentUser().getUid();
        String name = binding.name.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        String address = binding.address.getText().toString().trim();
        String totalAmount = binding.totalAmount.getText().toString().replace("₹", "").trim();
        long time = System.currentTimeMillis();

        DatabaseReference itemPushRef = database.getReference().child("OrderDetails").push();
        String itemPushKey = itemPushRef.getKey();

        // 🔽 Get data from CartItems
        DatabaseReference cartRef = databaseReference.child("Users").child(userId).child("CartItems");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodNames = new ArrayList<>();
                foodPrices = new ArrayList<>();
                foodImages = new ArrayList<>();
                foodQuantities = new ArrayList<>();

                for (DataSnapshot itemSnap : snapshot.getChildren()) {
                    Log.d("CART_ITEM_DEBUG", itemSnap.getValue().toString());
                    String foodName = itemSnap.child("cartFoodName").getValue(String.class);
                    String foodPrice = itemSnap.child("cartItemPrice").getValue(String.class);
                    String foodImage = itemSnap.child("imageUrl").getValue(String.class);
                    Integer foodQuantity = itemSnap.child("quantity").getValue(Integer.class);



                    foodNames.add(foodName != null ? foodName : "");
                    foodPrices.add(foodPrice != null ? foodPrice : "");
                    foodImages.add(foodImage != null ? foodImage : "");
                    foodQuantities.add(foodQuantity != null ? foodQuantity : 0);
                }


                // ✅ Create orderDetails only after loading cart data
                orderDetails orderDetail = new orderDetails();
                orderDetail.userUid = userId;
                orderDetail.userName = name;
                orderDetail.phoneNumber = phone;
                orderDetail.address = address;
                orderDetail.totalPrice = totalAmount;
                orderDetail.foodNames = foodNames;
                orderDetail.foodPrices = foodPrices;
                orderDetail.foodImages = foodImages;
                orderDetail.foodQuantities = foodQuantities;
                orderDetail.orderAccepted = false;
                orderDetail.paymentReceived = false;
                orderDetail.itemPushKey = itemPushKey;
                orderDetail.currentTime = time;

                // 🔽 Save to OrderDetails
                DatabaseReference orderReference = databaseReference.child("OrderDetails").child(itemPushKey);
                orderReference.setValue(orderDetail)
                        .addOnSuccessListener(unused -> {
                            CongratsBottomSheet bottomSheet = new CongratsBottomSheet();
                            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
                            removeItemsFromCart();
                            addOrderToHistory(orderDetail);
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(PayOutActivity.this, "Failed to place order: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PayOutActivity.this, "Error loading cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addOrderToHistory(orderDetails orderDetail) {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child("Users")
                .child(userId)
                .child("BuyHistory")
                .child(orderDetail.itemPushKey)
                .setValue(orderDetail)  // 🔄 Save full orderDetails object
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PayOutActivity.this, "Added to History Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PayOutActivity.this, "Failed to add to history: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void removeItemsFromCart() {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference cartItemReference = databaseReference.child("Users").child(userId).child("CartItems");
        cartItemReference.removeValue().addOnSuccessListener(unused ->
                Toast.makeText(this, "Cart Emptied", Toast.LENGTH_SHORT).show());
    }

    private void loadUserInfo() {
        String userId = auth.getCurrentUser().getUid();

        database.getReference("Users")
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.child("name").getValue(String.class);
                            String phone = snapshot.child("phone").getValue(String.class);
                            String address = snapshot.child("address").getValue(String.class);

                            if (name != null) nameEditText.setText(name);
                            if (phone != null) phoneEditText.setText(phone);
                            if (address != null) addressEditText.setText(address);
                        } else {
                            Toast.makeText(PayOutActivity.this, "User info not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(PayOutActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
