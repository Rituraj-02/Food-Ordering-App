package com.example.pureplates.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pureplates.R;
import com.example.pureplates.databinding.RecentBuyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecentBuyAdapter extends RecyclerView.Adapter<RecentBuyAdapter.ViewHolder> {

    Context context;
    List<String> foodNameList;
    List<String> foodPriceList;
    List<String> foodImageList;
    List<Integer> foodQuantityList;


    public RecentBuyAdapter(Context context, List<String> foodNameList, List<String> foodPriceList, List<String> foodImageList, List<Integer> foodQuantityList) {
        this.context = context;
        this.foodNameList = foodNameList;
        this.foodPriceList = foodPriceList;
        this.foodImageList = foodImageList;
        this.foodQuantityList = foodQuantityList;
    }

    @NonNull
    @Override
    public RecentBuyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_buy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentBuyAdapter.ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return foodNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecentBuyItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RecentBuyItemBinding.bind(itemView);

        }

        public void bind(int position) {
            binding.foodName.setText(foodNameList.get(position));
            binding.foodPrice.setText(foodPriceList.get(position));
            binding.foodQuantity.setText(String.valueOf(foodQuantityList.get(position)));
            String uriString = foodImageList.get(position);
            Uri uri = Uri.parse(uriString);

            Glide.with(context)
                    .load(uri)
                    .into(binding.foodImage);
        }
    }
}
