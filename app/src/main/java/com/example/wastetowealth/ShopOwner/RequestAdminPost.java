package com.example.wastetowealth.ShopOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.wastetowealth.R;
import com.example.wastetowealth.adapter.RequestUserRecycler;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.UserPostCards;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        list();
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
    private void list() {
        postData = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        apiService.getPost().enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    postData.clear();
                    List<Object> responseData = response.body();
                    for (Object obj : responseData) {
                        System.out.println(obj);
                        if (obj instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) obj;
                            // Access properties from the map
                            ArrayList<String> imagesList = (ArrayList<String>) map.get("images");
                            String imageUrl = imagesList.isEmpty() ? "" : imagesList.get(0);
                            String postName = (String) map.get("brand");
                            String model = (String) map.get("model");
                            String maxAmount = String.valueOf((Double) map.get("maxAmount"));
                            String category = ((LinkedTreeMap<String, Object>) map.get("ecategory")).get("name").toString();
                            // Assuming you have getters in the UserPostCards class to retrieve the values
                            postData.add(new PostData(category, postName, model,maxAmount));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
            }
        });
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