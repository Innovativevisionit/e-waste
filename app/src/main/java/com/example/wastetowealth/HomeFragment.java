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
        userPostCards.add(new UserPostCards(R.drawable.purple,"DSA in Java", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"Java Course", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"C++ Course", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"DSA in C++", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"Kotlin for Android", "google", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"Java for Android", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
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