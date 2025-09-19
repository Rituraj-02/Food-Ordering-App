package com.example.pureplates;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pureplates.databinding.ActivityChooseLocationBinding;

import java.lang.reflect.Array;

public class ChooseLocationActivity extends AppCompatActivity {

    ActivityChooseLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChooseLocationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        //for showing data int the list
        String[] locationList = new String[]{"Jaipur", "Bhopal", "Banglore", "Delhi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList
        );
        AutoCompleteTextView autoCompleteTextView = binding.ListOfLocation;
        autoCompleteTextView.setAdapter(adapter);


    }
}