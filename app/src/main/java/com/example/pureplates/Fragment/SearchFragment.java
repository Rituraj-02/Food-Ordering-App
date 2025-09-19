package com.example.pureplates.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pureplates.Adapter.MenuAdapter;
import com.example.pureplates.Models.menuModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.FragmentSearchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;
    ArrayList<menuModels> menuModels = new ArrayList<>();
    MenuAdapter adapter;
    FirebaseDatabase database;
    ArrayList<menuModels> originalMenuItems;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        //retrieve menu item from the database
        retrieveMenuItems();

        // Sample data
//        menuModels.add(new menuModels(R.drawable.menu1, "Burger", "$5"));
//        menuModels.add(new menuModels(R.drawable.menu2, "Momo", "$8"));
//        menuModels.add(new menuModels(R.drawable.menu3, "Sandwich", "$10"));
//        menuModels.add(new menuModels(R.drawable.menu4, "Pizza", "$12"));

//        adapter = new MenuAdapter(requireContext(), menuModels);
//        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.menuRecyclerView.setAdapter(adapter);

       setupSearchView();

        return binding.getRoot();
    }

    private void retrieveMenuItems() {
        database = FirebaseDatabase.getInstance();
        //reference to the menu node
        DatabaseReference menuRef = database.getReference("menu");
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    menuModels menu = dataSnapshot.getValue(menuModels.class);
                    menuModels.add(menu);
                }
                showAllMenu();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showAllMenu() {
        adapter = new MenuAdapter(requireContext(), menuModels);
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.menuRecyclerView.setAdapter(adapter);
    }

   private void setupSearchView() {
       binding.searcView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               adapter.filterList(query);
                return true;
        }

          @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(newText);
                return true;
            }
        });
    }
}
