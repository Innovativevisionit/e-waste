package com.example.wastetowealth.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastetowealth.ProfilePage;
import com.example.wastetowealth.R;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReport extends AppCompatActivity {

    TextView tvR, tvPython, tvCPP, tvJava;
    PieChart pieChart;
    String pPost,Apost,pShop,Ashop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        setValues();

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCPP);
        tvJava = findViewById(R.id.tvJava);
        pieChart = findViewById(R.id.piechart);
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
//                    Integer pendingPostCount = (Integer) res.get("pendingPostCount");
//                    Log.d("Count Lo11g", "pendingPostCount" + res.get("pendingPostCount"));
                    pPost = getStringValue(res.get("pendingPostCount"));
                    Apost = getStringValue(res.get("approvedPostCount"));
                    Ashop = getStringValue(res.get("approvedShopCount"));
                    pShop = getStringValue(res.get("pendingShopCount"));
                    setData();
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

    private String getStringValue(Object obj) {
        return obj != null ? String.valueOf(obj) : "0";
    }
    private void setData(){

        int pendingPostCount = (int) Double.parseDouble(pPost);
        int approvedPostCount = (int) Double.parseDouble(Apost);
        int pendingShopCount = (int) Double.parseDouble(pShop);
        int approvedShopCount = (int) Double.parseDouble(Ashop);

        tvR.setText(String.valueOf(pendingPostCount));
        tvPython.setText(String.valueOf(approvedPostCount));
        tvCPP.setText(String.valueOf(pendingShopCount));
        tvJava.setText(String.valueOf(approvedShopCount));

        pieChart.addPieSlice(
                new PieModel(
                        "Pending Post Count",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Approved Post Count",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Pending Shop Count",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Approved Shop Count",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }
}