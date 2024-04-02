package com.example.wastetowealth.ShopOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.wastetowealth.R;
import com.example.wastetowealth.adapter.RequestUserRecycler;
import com.example.wastetowealth.model.PostData;

import java.util.ArrayList;

public class RequestAdminPost extends AppCompatActivity implements RequestUserRecycler.OnItemClickListener {
    private ArrayList<PostData> postData;
    RecyclerView request_post_acc_rej;
    Button accept, reject;
    private RequestUserRecycler adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_admin_post);
        request_post_acc_rej = findViewById(R.id.request_post_acc_rej);

        initData();
        setupRecyclerView();
    }
    private void initData() {
        postData = new ArrayList<>();
        postData.add(new PostData("Category","Brand","Condition","1200"));
        postData.add(new PostData("Home","Brand","Condition","1200"));
        postData.add(new PostData("Kitchen","Brand","Condition","1200"));
        postData.add(new PostData("OutDoor","Brand","Condition","1200"));
        postData.add(new PostData("Electronic","Brand","Condition","1200"));
        postData.add(new PostData("Cables","Brand","Condition","1200"));
        postData.add(new PostData("Glass","Brand","Condition","1200"));
    }
    private void setupRecyclerView() {
        adapter = new RequestUserRecycler(this, postData);
        adapter.setOnItemClickListener(this);
        // Create a button click listener instance
        RequestUserRecycler.OnButtonClickListener buttonClickListener = new RequestUserRecycler.OnButtonClickListener() {
            @Override
            public void onAcceptClick(int position) {
                // Handle accept click action here
                postData.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(RequestAdminPost.this, "Accepted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRejectClick(int position) {
                // Handle reject click action here
                postData.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };

        adapter.setOnButtonClickListener(buttonClickListener);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        request_post_acc_rej.setLayoutManager(layoutManager);
        request_post_acc_rej.setAdapter(adapter);
    }
    @Override
    public void onAcceptClick(int position) {
        postData.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRejectClick(int position) {
        postData.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(int position) {
        return;
    }
}