package com.example.pureplates.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pureplates.Adapter.NotificationAdapter;
import com.example.pureplates.Models.notificationModels;
import com.example.pureplates.R;
import com.example.pureplates.databinding.FragmentNotificationBottomBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class NotificationBottomFragment extends BottomSheetDialogFragment {

    FragmentNotificationBottomBinding binding;
    ArrayList<notificationModels> notificationModels = new ArrayList<>();

    public NotificationBottomFragment(FragmentNotificationBottomBinding binding, ArrayList<notificationModels> notificationModels) {
        this.binding = binding;
        this.notificationModels = notificationModels;
    }

    public NotificationBottomFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = FragmentNotificationBottomBinding.inflate(inflater,container,false);


         notificationModels.add(new notificationModels(R.drawable.sademoji,"Your order has been Cancled Successfully"));
         notificationModels.add(new notificationModels(R.drawable.truck,"order has been taken by the driver"));
         notificationModels.add(new notificationModels(R.drawable.congrats,"Congrats your order places"));

        NotificationAdapter adapter =new NotificationAdapter(requireContext(),notificationModels);
        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}