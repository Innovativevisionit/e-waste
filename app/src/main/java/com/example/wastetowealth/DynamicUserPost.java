package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastetowealth.retrofit.ApiConfig;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class DynamicUserPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_user_post);
        setValues();
    }
    private void setValues() {
        TextView nameText = findViewById(R.id.nameText);
        TextView brandText = findViewById(R.id.brandText);
        ImageView image = findViewById(R.id.dynamicImageStore);
        TextView categoryText = findViewById(R.id.categoryText);
        TextView modelText = findViewById(R.id.modelText);
        TextView minAmountText = findViewById(R.id.minAmountText);
        TextView maxAmountText = findViewById(R.id.maxAmountText);
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String brand = intent.getStringExtra("brand");
            String images = intent.getStringExtra("images");
            String minAmountStr = intent.getStringExtra("minAmount");
            String maxAmountStr = intent.getStringExtra("maxAmount");
            String model = intent.getStringExtra("model");
            String category = intent.getStringExtra("category");
            nameText.setText(name != null ? name : "");
            brandText.setText(brand != null ? brand : "");
            categoryText.setText(category != null ? category : "");
            modelText.setText(model != null ? model : "");
            minAmountText.setText(minAmountStr != "" ? minAmountStr : "");
            maxAmountText.setText(maxAmountStr != "" ? maxAmountStr : "");

            // Load image using Picasso
            if (images != "") {
                Picasso.get().load(ApiConfig.IMAGE_URL + images).into(image);
            }

        }
    }

}