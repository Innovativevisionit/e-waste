package com.example.wastetowealth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.adapter.UserPostCardRecyclerAdapter;
import com.example.wastetowealth.model.DashboardCards;
import com.example.wastetowealth.model.UserPostCards;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UserPostCardRecyclerAdapter.OnItemClickListener  {
    private ArrayList<UserPostCards> userPostCards;
    private RecyclerView courseRV;
    private String IMAGE_URL = "http://192.168.1.8:8081/uploadsProof/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        courseRV = view.findViewById(R.id.mainCards);
        initData();
        setupRecyclerView();
        return view;
    }
    private void initData() {
        userPostCards = new ArrayList<>();
        userPostCards.add(new UserPostCards(IMAGE_URL +"fridge.jpg","Fridge", "Kitchen Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"fan.jpg","Fan", "Home Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"frameLight.jpg","Frame Light", "Decoration"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"grinder.jpg","Grinder", "Kitchen Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"junction.jpg","Junction", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"lap.jpg","Laptop", "Education"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"lcd_tv.jpg","LCD-TV", "Home Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"ledLights.jpg","LED Light", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"oldLap.jpg","Laptop", "Education"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"wm.jpg","Washing Machine", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(IMAGE_URL +"wires.jpg","Wires", "Electronic Appliance"));
    }

    private void setupRecyclerView() {
        UserPostCardRecyclerAdapter courseAdapter = new UserPostCardRecyclerAdapter(getContext(), userPostCards);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        courseAdapter.setOnItemClickListener(this); // Pass 'this' here
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }


    @Override
    public void onItemClick(int position) {
        UserPostCards clickedItem = userPostCards.get(position);
        Intent intent = new Intent(getContext(), DynamicStoreCard.class);
        intent.putExtra("storeName", clickedItem.getPostName());
        intent.putExtra("location", clickedItem.getCategory());
        startActivity(intent);
    }

    @Override
    public void onHeartClick(int position) {

    }

}