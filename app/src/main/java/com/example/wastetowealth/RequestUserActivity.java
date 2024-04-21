package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wastetowealth.Admin.AdminDashboard;
import com.example.wastetowealth.adapter.RequestUserAdapter;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.model.ShopUpdate;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestUserActivity extends AppCompatActivity implements RequestUserAdapter.OnAcceptRejectClickListener {

    private ArrayList<ShopRegisterFetch> userModelArrayList;
    private RequestUserAdapter requestUserAdapter;
    private RecyclerView categoryRV;

    private ImageButton approve, pending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_user);
        initSet();
        if(!(userModelArrayList.size() > 0)) {
            Toast.makeText(RequestUserActivity.this, "Go back to dashboard", Toast.LENGTH_SHORT).show();

        }
    }

    private void initSet() {
        categoryRV = findViewById(R.id.requestCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryRV.setLayoutManager(layoutManager);
        userModelArrayList = new ArrayList<>();

        requestUserAdapter = new RequestUserAdapter(userModelArrayList, this);
        categoryRV.setAdapter(requestUserAdapter);
        fetchUserRequest();
    }

    private void fetchUserRequest() {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        String status = "Pending";
        apiService.getShopList(status).enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    userModelArrayList.clear();
                    System.out.println(response.body());
                    for (Object shop : response.body()) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(shop), new TypeToken<Map<String, Object>>() {}.getType());

                        ShopRegisterFetch shopFetch = new ShopRegisterFetch();
                        List<String> imagesList = (List<String>) productData.get("images");
                        shopFetch.setId((int) (double)productData.get("id"));
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
                        userModelArrayList.add(shopFetch);
                    }
                    categoryRV.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestUserActivity.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                if (RequestUserActivity.this != null) {
                    Toast.makeText(RequestUserActivity.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAcceptClick(int position) {
        ShopRegisterFetch requestUserModel = userModelArrayList.get(position);
        String status = "Approve";
        updateShopRequest(requestUserModel, status);
        Toast.makeText(RequestUserActivity.this, "Approved : " + requestUserModel.getShopName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRejectClick(int position) {
        ShopRegisterFetch requestUserModel = userModelArrayList.get(position);
        String status = "Reject";
        updateShopRequest(requestUserModel, status);
        Toast.makeText(RequestUserActivity.this, "Rejected : " + requestUserModel.getShopName(),Toast.LENGTH_SHORT);
    }

    private void updateShopRequest(ShopRegisterFetch requestUserModel, String status) {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        ShopUpdate statusUpdate = new ShopUpdate(requestUserModel.getId(), status);
        apiService.updateShopRequest(statusUpdate).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    userModelArrayList.clear();
                    System.out.println(response.body());
                    fetchUserRequest();
                    categoryRV.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestUserActivity.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                if (RequestUserActivity.this != null) {
                    Toast.makeText(RequestUserActivity.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}