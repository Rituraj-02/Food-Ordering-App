package com.example.pureplates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.pureplates.Adapter.MenuAdapter;
import com.example.pureplates.Adapter.PopularAdapter;
import com.example.pureplates.Models.menuModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    List<menuModels> menuModelsList = new ArrayList<>();
    MenuAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Setup image slider
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));

        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList, ScaleTypes.FIT);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                String itemMessage = "Selected Image " + position;
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doubleClick(int position) {
                // Optional: handle double click
            }
        });

        // Set up RecyclerView
        adapter = new MenuAdapter(getContext(), menuModelsList);
        binding.popularRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.popularRecyclerView.setAdapter(adapter);

        // Load menu items from Firebase
        retrieveAndDisplayPopularItems();

        // Open bottom sheet when "View All Menu" is clicked
        binding.viewAllMenu.setOnClickListener(v -> {
            MenuBottomSheetFragment menuBottomSheetFragment = new MenuBottomSheetFragment();
            menuBottomSheetFragment.show(getParentFragmentManager(), "Test");
        });

        return binding.getRoot();
    }

    private void retrieveAndDisplayPopularItems() {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("menu");

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuModelsList.clear();
                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    menuModels menuItem = foodSnapshot.getValue(menuModels.class);
                    if (menuItem != null) {
                        menuModelsList.add(menuItem);
                    }
                }

                // Shuffle randomly
                Collections.shuffle(menuModelsList);

                // Notify adapter
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load menu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
