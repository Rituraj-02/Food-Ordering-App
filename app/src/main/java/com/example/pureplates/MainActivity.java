package com.example.pureplates;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pureplates.Fragment.NotificationBottomFragment;
import com.example.pureplates.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController(bottomNav, navController);

        binding.notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationBottomFragment notificationBottomFragment = new NotificationBottomFragment();
                notificationBottomFragment.show(getSupportFragmentManager(),"Test");
            }
        });

    }
}
