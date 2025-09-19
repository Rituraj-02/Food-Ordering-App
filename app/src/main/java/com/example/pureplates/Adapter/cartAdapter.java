package com.example.pureplates.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pureplates.Models.cartModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.CartItemBinding;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> {

    Context context;
    ArrayList<cartModels> cartModels;

    public cartAdapter(Context context, ArrayList<cartModels> cartModels) {
        this.context = context;
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public cartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter.viewHolder holder, int position) {
        cartModels model = cartModels.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.binding.cartImage);

        holder.binding.cartFoodName.setText(model.getCartFoodName());
        holder.binding.cartItemPrice.setText("₹" + model.getCartItemPrice());
        holder.binding.quantity.setText(String.valueOf(model.getQuantity()));

        // PLUS button
        holder.binding.plusButton.setOnClickListener(v -> {
            if (model.getQuantity() < 10) {
                model.setQuantity(model.getQuantity() + 1);
                holder.binding.quantity.setText(String.valueOf(model.getQuantity()));
                notifyItemChanged(position);
            }
        });

        // MINUS button
        holder.binding.minusButton.setOnClickListener(v -> {
            if (model.getQuantity() > 1) {
                model.setQuantity(model.getQuantity() - 1);
                holder.binding.quantity.setText(String.valueOf(model.getQuantity()));
                notifyItemChanged(position);
            }
        });

        // DELETE button
        holder.binding.deleteButton.setOnClickListener(v -> {
            cartModels.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartModels.size());
        });
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CartItemBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemBinding.bind(itemView);
        }
    }
}
