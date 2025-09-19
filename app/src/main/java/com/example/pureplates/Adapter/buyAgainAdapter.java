package com.example.pureplates.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pureplates.Models.buyAgainModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.BuyAgainItemBinding;

import java.util.ArrayList;

public class buyAgainAdapter extends RecyclerView.Adapter<buyAgainAdapter.viewHolder> {

    Context context;
    ArrayList<buyAgainModels> buyAgainModels;

    public buyAgainAdapter(Context context, ArrayList<buyAgainModels> buyAgainModels) {
        this.context = context;
        this.buyAgainModels = buyAgainModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buy_again_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        buyAgainModels item = buyAgainModels.get(position);
        Glide.with(context)
                .load(item.getFoodimage()) // This should be a URL string
                .placeholder(R.drawable.menu2)
                .into(holder.binding.buyAgainFood);

        holder.binding.buyAgainFoodName.setText(item.getFoodName());
        holder.binding.buyAgainFoodPrice.setText(item.getFoodPrice());
    }

    @Override
    public int getItemCount() {
        return buyAgainModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        BuyAgainItemBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BuyAgainItemBinding.bind(itemView);
        }
    }
}
