package com.example.wastetowealth;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.model.DashboardCards;
import com.example.wastetowealth.model.UserPostCards;

import java.util.ArrayList;

public class PostMainScreen extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    private boolean isLiked = false;
    private ArrayList<UserPostCards> userPostCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_main_screen);

//        initData();
//        ImageView heart = findViewById(R.id.heart);
//
//
//        heart.setOnClickListener(v -> {
//            if (isLiked) {
//                heart.setImageResource(R.drawable.heart__2_);
//            } else {
//                heart.setImageResource(R.drawable.heart__4_);
//            }
//            isLiked = !isLiked;
//        });
    }

//    private void setupRecyclerView() {
//        RecyclerAdapter courseAdapter = new RecyclerAdapter((), userPostCards); // Pass 'this' as the context
//        courseAdapter.setOnItemClickListener(this);
//
//        RecyclerView courseRV = findViewById(R.id.); // Find the RecyclerView by its id
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        courseRV.setLayoutManager(layoutManager);
//        courseRV.setAdapter(courseAdapter);
//    }


//    private void initData() {
//        userPostCards = new ArrayList<>();
//        userPostCards.add(new UserPostCards(R.drawable.purple,"DSA in Java", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"Java Course", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"C++ Course", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"DSA in C++", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"Kotlin for Android", "google", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"Java for Android", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//        userPostCards.add(new UserPostCards(R.drawable.purple,"HTML and CSS", "Cuddalore", R.drawable.heart__2_,R.drawable.send,R.drawable.kebab));
//    }

    @Override
    public void onItemClick(int position) {

    }
}
