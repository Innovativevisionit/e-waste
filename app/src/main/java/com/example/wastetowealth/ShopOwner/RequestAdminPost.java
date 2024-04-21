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
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.model.UserPostCards;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdminPost extends AppCompatActivity implements RequestUserRecycler.OnItemClickListener {
    private ArrayList<PostUserData> postData;
    RecyclerView request_post_acc_rej;
    Button accept, reject;
    private RequestUserRecycler adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_admin_post);
        request_post_acc_rej = findViewById(R.id.request_post_acc_rej);
        list();
        setupRecyclerView();
    }
    private void list() {
        postData = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(RequestAdminPost.this);
        String email = sharedPreferences.getString("email", "Default");
        apiService.getUserPost(email).enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    postData.clear();
                    System.out.println(response.body());
                    for (Object shop : response.body()) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(shop), new TypeToken<Map<String, Object>>() {}.getType());

                        PostUserData shopFetch = new PostUserData();
                        List<String> imagesList = (List<String>) productData.get("images");
                        shopFetch.setId((int) (double)productData.get("id"));
                        shopFetch.setName((String) productData.get("name"));
                        shopFetch.setEcategoryName((String) productData.get("ecategoryName"));

                        shopFetch.setBrand((String) productData.get("brand"));
                        shopFetch.setModel((String) productData.get("model"));
                        shopFetch.setPostCondition((String) productData.get("postCondition"));
                        Double minAmountDouble = (Double) productData.get("minAmount");
                        if (minAmountDouble != null) {
                            shopFetch.setMinAmount(minAmountDouble.longValue());
                        }
                        Double maxAmountDouble = (Double) productData.get("maxAmount");
                        if (maxAmountDouble != null) {
                            shopFetch.setMaxAmount(maxAmountDouble.longValue());
                        }

                        shopFetch.setImages(imagesList);
                        postData.add(shopFetch);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                if (RequestAdminPost.this != null) {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        request_post_acc_rej.setLayoutManager(layoutManager);

        adapter = new RequestUserRecycler(this, postData);
        adapter.setOnItemClickListener(this);

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