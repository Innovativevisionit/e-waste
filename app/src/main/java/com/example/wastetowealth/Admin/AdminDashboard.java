package com.example.wastetowealth.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.wastetowealth.MainActivity;
import com.example.wastetowealth.R;
import com.example.wastetowealth.RequestUserActivity;

import java.util.Locale;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Intent intentGet = getIntent();

        String usernameIntent = intentGet.getStringExtra("username");
        String emailIntent = intentGet.getStringExtra("email");

        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);

        name.setText(usernameIntent);
        email.setText(emailIntent);

        CardView category = findViewById(R.id.category);
        CardView delivery = findViewById(R.id.deliveryUser);
        Button logout = findViewById(R.id.adminLogoutBtn);
        CardView requestUser = findViewById(R.id.requestUser);
        CardView reportsCard = findViewById(R.id.reports);
        category.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, CategoryPage.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
            startActivity(intent);
        });

        delivery.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, DeliveryPage.class);
            startActivity(intent);
        });
        requestUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, RequestUserActivity.class);
            startActivity(intent);
        });
        reportsCard.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, AdminReport.class);
            startActivity(intent);
        });
    }
}