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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String status = "Approved";
        apiService.getShopList(status).enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    courseModelArrayList.clear();
                    System.out.println(response.body());
                    for (Object shop : response.body()) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(shop), new TypeToken<Map<String, Object>>() {}.getType());

                        ShopRegisterFetch shopFetch = new ShopRegisterFetch();
                        List<String> imagesList = (List<String>) productData.get("images");
                        shopFetch.setShopName((String) productData.get("shopName"));
                        shopFetch.setCategory((String) productData.get("ecategoryName"));
                        Double contactNoDouble = (Double) productData.get("contactNo");
                        if (contactNoDouble != null) {
                            String contactNoString = String.valueOf(contactNoDouble.longValue());
                            shopFetch.setContactNo(contactNoString);
                        }
                        shopFetch.setHazard((String) productData.get("handlingHazard"));
                        shopFetch.setLocation((String) productData.get("location"));
                        shopFetch.setRecycleMethods((String) productData.get("recyclingMethod"));
                        shopFetch.setWebsite((String) productData.get("website"));
                        shopFetch.setSocialLink((String) productData.get("socialLink"));
                        shopFetch.setImages(imagesList);
                        courseModelArrayList.add(shopFetch);
                    }
                    courseRV.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(AllAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                if (AllAdminPost.this != null) {
                    Toast.makeText(AllAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
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
        intent.putExtra("images", clickedItem.getImages().get(0));
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