package com.example.wastetowealth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.model.DashboardCards;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener  {
    private ArrayList<DashboardCards> courseModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        RecyclerView courseRV = findViewById(R.id.dashCards);

        // Here, we have created new array list and added data to it
        courseModelArrayList = new ArrayList<DashboardCards>();
        courseModelArrayList.add(new DashboardCards("DSA in Java", "Cuddalore", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("Java Course", "Cuddalore", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("C++ Course", "Cuddalore", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("DSA in C++", "Cuddalore", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("Kotlin for Android", "google", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("Java for Android", "Cuddalore", R.drawable.purple));
        courseModelArrayList.add(new DashboardCards("HTML and CSS", "Cuddalore", R.drawable.purple));

        RecyclerAdapter courseAdapter = new RecyclerAdapter(this, courseModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }

    @Override
    public void onItemClick(int position) {
        DashboardCards clickedItem = courseModelArrayList.get(position);
        // Create an Intent to navigate to the details activity
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        // Pass data to the details activity using Intent extras
        intent.putExtra("storeName", clickedItem.getStoreName());
        intent.putExtra("location", clickedItem.getLocation());
        // Start the details activity
        startActivity(intent);
    }
}
