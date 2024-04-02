package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.wastetowealth.Admin.CategoryPage;
import com.example.wastetowealth.adapter.Category;
import com.example.wastetowealth.adapter.RequestUserAdapter;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.RequestUserModel;
import com.example.wastetowealth.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestUserActivity extends AppCompatActivity {

    private ArrayList<RequestUserModel> userModelArrayList;

    private RequestUserAdapter requestUserAdapter;
    private RecyclerView categoryRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_user);
        initSet();

    }

    private void initSet() {
        categoryRV = findViewById(R.id.requestCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryRV.setLayoutManager(layoutManager);
        userModelArrayList = new ArrayList<>();
        RequestUserModel request = new RequestUserModel("aks", "cuddalore", "123");
        userModelArrayList.add(request);
        System.out.println("list --- " +userModelArrayList);
        requestUserAdapter = new RequestUserAdapter(userModelArrayList);
        categoryRV.setAdapter(requestUserAdapter);
        fetchUserRequest();
    }

    private void fetchUserRequest() {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);


    }
}