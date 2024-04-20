package com.example.wastetowealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wastetowealth.adapter.RecyclerAdapter;
import com.example.wastetowealth.adapter.UserPostRecycler;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements UserPostRecycler.OnItemClickListener {
    private ArrayList<PostUserData> courseModelArrayList;
    private RecyclerView courseRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        courseRV = view.findViewById(R.id.dashCards);
        getPostList();
        setupRecyclerView();
        return view;
    }
    private void setupRecyclerView() {
        UserPostRecycler courseAdapter = new UserPostRecycler(getContext(), courseModelArrayList);
        courseAdapter.setOnItemClickListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(courseAdapter);
    }
    private void getPostList() {
        courseModelArrayList = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(getContext());
        String email = sharedPreferences.getString("email", "Default");
        apiService.getUserPost(email).enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    courseModelArrayList.clear();
                    System.out.println(response.body());
                    for (Object shop : response.body()) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(shop), new TypeToken<Map<String, Object>>() {}.getType());

                        PostUserData shopFetch = new PostUserData();
                        List<String> imagesList = (List<String>) productData.get("images");
                        shopFetch.setName((String) productData.get("name"));
                        shopFetch.setEcategoryName((String) productData.get("ecategoryName"));

                        shopFetch.setBrand((String) productData.get("brand"));
                        shopFetch.setModel((String) productData.get("model"));
                        shopFetch.setPostCondition((String) productData.get("postCondition"));
                        shopFetch.setMaxAmount((long) productData.get("minAmount"));
                        shopFetch.setMaxAmount((long) productData.get("maxAmount"));
                        shopFetch.setImages(imagesList);
                        courseModelArrayList.add(shopFetch);
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

    @Override
    public void onItemClick(int position) {

    }
}