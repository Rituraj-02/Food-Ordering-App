package com.example.pureplates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pureplates.Adapter.MenuAdapter;
import com.example.pureplates.Models.menuModels;
import com.example.pureplates.databinding.FragmentMenuBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuBottomSheetFragment extends BottomSheetDialogFragment {

    FragmentMenuBottomSheetBinding binding;
    List<menuModels> menuList = new ArrayList<>();
    MenuAdapter adapter;
    FirebaseDatabase database;

    public MenuBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false);

        // Back button dismisses the bottom sheet
        binding.buttonBack.setOnClickListener(view -> dismiss());

        // Set up RecyclerView
        adapter = new MenuAdapter(requireContext(), menuList);
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.menuRecyclerView.setAdapter(adapter);

        // Fetch menu items from Firebase
        retrieveMenuItems();

        return binding.getRoot();
    }

    private void retrieveMenuItems() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference foodRef = database.getReference("menu");

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuList.clear();
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    menuModels item = foodSnapshot.getValue(menuModels.class);
                    if (item != null) {
                        menuList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
