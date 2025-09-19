package com.example.pureplates.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pureplates.DetailsActivity;
import com.example.pureplates.Models.menuModels;
import com.example.pureplates.Models.popularModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.PopularItemBinding;
import java.util.ArrayList;


public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{
Context context;
ArrayList<popularModels> popularModels;

 public PopularAdapter(Context context,ArrayList<popularModels> popularModels){
    this.context=context;
    this.popularModels=popularModels;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.popular_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.binding.popularImage.setImageResource(popularModels.get(position).getImage());
    holder.binding.pricePopular.setText(popularModels.get(position).getPrice());
    holder.binding.FoodNamePopular.setText(popularModels.get(position).getFoodName());


        holder.binding.getRoot().setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                popularModels clickedItem = popularModels.get(adapterPosition);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("MenuItemName", clickedItem.getFoodName());
                intent.putExtra("MenuItemImage", clickedItem.getImage());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularModels.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        PopularItemBinding binding;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            binding=PopularItemBinding.bind(itemView);

        }
    }

}
