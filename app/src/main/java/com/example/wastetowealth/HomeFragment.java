package com.example.wastetowealth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.adapter.UserPostCardRecyclerAdapter;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.DashboardCards;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.PostFetch;
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

public class HomeFragment extends Fragment implements UserPostCardRecyclerAdapter.OnItemClickListener  {
    private ArrayList<PostUserData> userPostCards;
    private RecyclerView courseRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        courseRV = view.findViewById(R.id.mainCards);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(getContext());
        String username = sharedPreferences.getString("username", "Default");
        list();
        setupRecyclerView();
        return view;
    }

    private void list() {
        userPostCards = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        apiService.getPost().enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    userPostCards.clear();
                    List<Object> responseData = response.body();
                    for (Object obj : responseData) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(obj), new TypeToken<Map<String, Object>>() {}.getType());

                        PostUserData shopFetch = new PostUserData();
                        List<String> imagesList = (List<String>) productData.get("images");
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
                        userPostCards.add(shopFetch);
                    }
                    if (courseRV.getAdapter() != null) {
                        courseRV.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void setupRecyclerView() {
        Log.d("Home","Check List" + userPostCards);

        UserPostCardRecyclerAdapter courseAdapter = new UserPostCardRecyclerAdapter(getContext(), userPostCards);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        courseAdapter.setOnItemClickListener(this); // Pass 'this' here
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }


    @Override
    public void onItemClick(int position) {
        PostUserData clickedItem = userPostCards.get(position);
        Intent intent = new Intent(getContext(), DynamicUserPost.class);
        intent.putExtra("name", clickedItem.getName());
        intent.putExtra("brand", clickedItem.getBrand());
        intent.putExtra("images", clickedItem.getImages().get(0));
        intent.putExtra("minAmount", String.valueOf(clickedItem.getMinAmount()));
        intent.putExtra("maxAmount", String.valueOf(clickedItem.getMaxAmount()));
        intent.putExtra("model", clickedItem.getModel());
        intent.putExtra("category", clickedItem.getEcategoryName());
        startActivity(intent);
    }

    @Override
    public void onHeartClick(int position) {

    }

}