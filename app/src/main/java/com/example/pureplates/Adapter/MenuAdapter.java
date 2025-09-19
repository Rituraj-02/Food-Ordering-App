package com.example.pureplates.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pureplates.DetailsActivity;

import com.example.pureplates.Models.menuModels;
import com.example.pureplates.databinding.MenuItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.viewHolder> {

    Context context;

    List<menuModels> menuList; // for recycler view>
    List<menuModels> fullList; // for filtering

    public MenuAdapter(Context context, List<menuModels> menuList) {
        this.context = context;
        this.menuList = menuList;
        this.fullList = new ArrayList<>(menuList); // backup list for filter
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.pureplates.R.layout.menu_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        menuModels model = menuList.get(position);

        // Set food name and price
        holder.binding.menuFoodName.setText(model.getName());
        holder.binding.menuprice.setText(model.getPrice());

        // Load image using Glide (from URL)
        String imageUri = model.getImageUrl();
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(context)
                    .load(imageUri)
                    .into(holder.binding.menuImage);
        }

        // On item click, open details activity with extras
        holder.binding.getRoot().setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                menuModels clickedItem = menuList.get(adapterPosition);

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("MenuItemName", clickedItem.getName());
                intent.putExtra("MenuItemImage", clickedItem.getImageUrl());
                intent.putExtra("MenuItemPrice", clickedItem.getPrice());
                intent.putExtra("MenuItemDescription", clickedItem.getDescription());
                intent.putExtra("MenuItemIngredients", clickedItem.getIngredients());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    // Filter method for search
    public void filterList(String query) {
        ArrayList<menuModels> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            filteredList.addAll(fullList);
        } else {
            for (menuModels model : fullList) {
                if (model.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(model);
                }
            }
        }

        menuList.clear();
        menuList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        MenuItemBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MenuItemBinding.bind(itemView);
        }
    }
}
