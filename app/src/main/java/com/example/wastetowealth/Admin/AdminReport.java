package com.example.wastetowealth.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.wastetowealth.ProfilePage;
import com.example.wastetowealth.R;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        setValues();
    }

    private void setValues() {
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        apiService.getAdminCount().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Log.d("Count Log", "Count Check" + response.body());
                    Gson gson = new Gson();
                    Map<String, Object> res = gson.fromJson(gson.toJsonTree(response.body()), new TypeToken<Map<String, Object>>() {}.getType());


                } else {
                    Toast.makeText(AdminReport.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                if (AdminReport.this != null) {
                    Toast.makeText(AdminReport.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}