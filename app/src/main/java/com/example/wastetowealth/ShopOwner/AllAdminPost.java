package com.example.wastetowealth.ShopOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wastetowealth.DynamicStoreCard;
import com.example.wastetowealth.R;
import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAdminPost extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    private ArrayList<ShopRegisterFetch> courseModelArrayList;
    private RecyclerView courseRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_admin_post);
        courseRV = findViewById(R.id.adminDashCards);
        getShopList();
        setupRecyclerView();
    }
    private void getShopList() {
        courseModelArrayList = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        apiService.getShopList().enqueue(new Callback<List<ShopRegisterFetch>>() {
            @Override
            public void onResponse(Call<List<ShopRegisterFetch>> call, Response<List<ShopRegisterFetch>> response) {
                if (response.isSuccessful()) {
                    courseModelArrayList.clear();
                    for (ShopRegisterFetch shop : response.body()) {
                        ShopRegisterFetch shopFetch = new ShopRegisterFetch();
                        shopFetch.setShopName(shop.getShopName());
                        shopFetch.setCategory(shop.getCategory());
                        shopFetch.setContactNo(shop.getContactNo());
                        shopFetch.setImages(shop.getImages());
                        shopFetch.setHazard(shop.getHazard());
                        shopFetch.setLocation(shop.getLocation());
                        shopFetch.setRecycleMethods(shop.getRecycleMethods());
                        shopFetch.setWebsite(shop.getWebsite());
                        shopFetch.setSocialLink(shop.getSocialLink());
                        courseModelArrayList.add(shopFetch);
                    }
                    courseRV.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(AllAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ShopRegisterFetch>> call, Throwable t) {
                t.printStackTrace();
                    Toast.makeText(AllAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerView() {
        RecyclerAdapter courseAdapter = new RecyclerAdapter(this, courseModelArrayList);
        courseAdapter.setOnItemClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }
    @Override
    public void onItemClick(int position) {
        ShopRegisterFetch clickedItem = courseModelArrayList.get(position);
        // Create an Intent to navigate to the details activity
        Intent intent = new Intent(AllAdminPost.this, DynamicStoreCard.class);
        // Pass data to the details activity using Intent extras
        intent.putExtra("storeName", clickedItem.getShopName());
        intent.putExtra("location", clickedItem.getLocation());
        intent.putExtra("images", clickedItem.getImages()[0]);
        intent.putExtra("category", clickedItem.getCategory());
        intent.putExtra("hazard", clickedItem.getHazard());
        intent.putExtra("recycle", clickedItem.getRecycleMethods());
        intent.putExtra("social", clickedItem.getSocialLink());
        intent.putExtra("website", clickedItem.getWebsite());
        intent.putExtra("contact", clickedItem.getContactNo());
        // Start the details activity
        startActivity(intent);
    }
}