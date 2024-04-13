package com.example.wastetowealth.ShopOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.wastetowealth.Admin.AdminDashboard;
import com.example.wastetowealth.Admin.CategoryPage;
import com.example.wastetowealth.Admin.DeliveryPage;
import com.example.wastetowealth.MainActivity;
import com.example.wastetowealth.R;
import com.example.wastetowealth.retrofit.MySharedPreferences;

public class DashboardSo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_so);

        CardView profile = findViewById(R.id.profile);
        CardView requestPost = findViewById(R.id.requestPost);
        CardView categoryPost = findViewById(R.id.categoryPost);
        CardView post = findViewById(R.id.post);
        Button logout = findViewById(R.id.adminLogout);

        TextView setName = findViewById(R.id.name);
        TextView setEmail = findViewById(R.id.email);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(getApplicationContext());
        String username = sharedPreferences.getString("username", "Default");
        String email = sharedPreferences.getString("email", "Default");

        setName.setText(username);
        setEmail.setText(email);

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSo.this, AdminProfile.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSo.this, MainActivity.class);
            startActivity(intent);
        });

        categoryPost.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSo.this, CategoryAdminPost.class);
            startActivity(intent);
        });

        post.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSo.this, AllAdminPost.class);
            startActivity(intent);
        });

        requestPost.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardSo.this, RequestAdminPost.class);
            startActivity(intent);
        });
    }
}