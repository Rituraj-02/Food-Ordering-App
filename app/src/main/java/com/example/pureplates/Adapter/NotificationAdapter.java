package com.example.pureplates.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pureplates.Models.buyAgainModels;
import com.example.pureplates.Models.notificationModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.NotificationitemBinding;

import java.util.ArrayList;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    Context context;
    ArrayList<notificationModels> notificationList;

    public NotificationAdapter(Context context, ArrayList<notificationModels> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    public NotificationAdapter(ArrayList<notificationModels> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificationitem,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {
        notificationModels notification = notificationList.get(position);
        holder.binding.NotificationTextView.setText(notification.getNotification());
        holder.binding.notificationImageView.setImageResource(notification.getNotificationImage());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        NotificationitemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationitemBinding.bind(itemView);
        }
    }
}
