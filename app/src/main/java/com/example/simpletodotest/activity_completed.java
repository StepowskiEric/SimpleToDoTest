package com.example.simpletodotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class activity_completed extends AppCompatActivity {

    RecyclerView completedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        //completedItems = findViewById(R.id.completedRV);
        getSupportActionBar().setTitle("Completed Items");
    }
}