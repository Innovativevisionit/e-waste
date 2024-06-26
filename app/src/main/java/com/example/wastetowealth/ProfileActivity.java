package com.example.wastetowealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        String username = getIntent().getStringExtra("username");
        String photoUriString = getIntent().getStringExtra("photoUri");
        TextView name = findViewById(R.id.profileName);
        if (photoUriString != null) {
            Uri photoUri = Uri.parse(photoUriString);
            ImageView image = findViewById(R.id.imageView2);
            image.setImageURI(photoUri);
        }
        if (username != null) {
            name.setText(username);
        }

        ImageButton back = findViewById(R.id.backDashboard);
        back.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
        });

        AppCompatButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        });

    }
}
