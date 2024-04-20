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
import android.widget.Toast;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.adapter.UserPostCardRecyclerAdapter;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.DashboardCards;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.PostFetch;
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
    private ArrayList<UserPostCards> userPostCards;
    private RecyclerView courseRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        courseRV = view.findViewById(R.id.mainCards);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(getContext());
        String username = sharedPreferences.getString("username", "Default");

        initData();
        list();
        setupRecyclerView();
        return view;
    }
    private void initData() {
        userPostCards = new ArrayList<>();
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"fridge.jpg","Fridge", "Kitchen Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"fan.jpg","Fan", "Home Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"frameLight.jpg","Frame Light", "Decoration"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"grinder.jpg","Grinder", "Kitchen Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"junction.jpg","Junction", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"lap.jpg","Laptop", "Education"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"lcd_tv.jpg","LCD-TV", "Home Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"ledLights.jpg","LED Light", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"oldLap.jpg","Laptop", "Education"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"wm.jpg","Washing Machine", "Electronic Appliance"));
        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL +"wires.jpg","Wires", "Electronic Appliance"));
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

                        String postName = (String) productData.get("name");
                        String category = (String) productData.get("ecategoryName");
                        List<String> imagesList = (List<String>) productData.get("images");
                        String imageUrl = "";
                        if (!imagesList.isEmpty()) {
                            imageUrl = imagesList.get(0);
                        }

                        userPostCards.add(new UserPostCards(ApiConfig.IMAGE_URL + imageUrl, postName, category));
                    }
                    courseRV.getAdapter().notifyDataSetChanged();
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
        UserPostCardRecyclerAdapter courseAdapter = new UserPostCardRecyclerAdapter(getContext(), userPostCards);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        courseAdapter.setOnItemClickListener(this); // Pass 'this' here
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }


    @Override
    public void onItemClick(int position) {
//        UserPostCards clickedItem = userPostCards.get(position);
//        Intent intent = new Intent(getContext(), DynamicStoreCard.class);
//        intent.putExtra("storeName", clickedItem.getPostName());
//        intent.putExtra("location", clickedItem.getCategory());
//        startActivity(intent);
    }

    @Override
    public void onHeartClick(int position) {

    }

}