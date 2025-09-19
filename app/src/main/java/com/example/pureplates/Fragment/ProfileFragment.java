package com.example.pureplates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pureplates.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Initially disable all fields
        binding.name.setEnabled(false);
        binding.email.setEnabled(false);
        binding.address.setEnabled(false);
        binding.phone.setEnabled(false);

        // Toggle editable state on editButton click
        binding.editButton.setOnClickListener(v -> {
            boolean isEnabled = !binding.name.isEnabled();  // toggle state

            binding.name.setEnabled(isEnabled);
            binding.email.setEnabled(isEnabled);
            binding.address.setEnabled(isEnabled);
            binding.phone.setEnabled(isEnabled);
        });

        // Save user info to Firebase
        binding.saveInfoButton.setOnClickListener(v -> saveUserInfo());

        // Load data from Firebase
        loadUserData();

        return view;
    }

    private void loadUserData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DatabaseReference userRef = database.getReference("Users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.name.setText(snapshot.child("name").getValue(String.class));
                    binding.email.setText(snapshot.child("email").getValue(String.class));

                    String phone = snapshot.child("phone").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    binding.phone.setText(phone != null ? phone : "");
                    binding.address.setText(address != null ? address : "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading user info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserInfo() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) return;

        String userId = currentUser.getUid();
        String phone = binding.phone.getText().toString().trim();
        String address = binding.address.getText().toString().trim();

        if (phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(getContext(), "Please enter phone and address", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = database.getReference("Users").child(userId);

        userRef.child("phone").setValue(phone);
        userRef.child("address").setValue(address).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Information saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
