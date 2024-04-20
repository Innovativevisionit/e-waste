package com.example.wastetowealth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wastetowealth.Admin.CategoryPage;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class DynamicStoreCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_store_page);
        setValues();

        Button approach = findViewById(R.id.approach);

        approach.setOnClickListener(v -> {
            showDialog();
        });

    }
    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.input_category, null);

        // Find the TextInputEditText in the custom layout
        final TextInputEditText editTextCategory = dialogView.findViewById(R.id.editTextCategory);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle positive button click
                        String category = editTextCategory.getText().toString().trim();
                        if (!category.isEmpty()) {
//                            addCategory(category);
                        } else {
                            Toast.makeText(DynamicStoreCard.this, "Please enter a category", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle negative button click
                        Toast.makeText(DynamicStoreCard.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void setValues() {
        TextView storeName = findViewById(R.id.storeText);
        TextView location = findViewById(R.id.locationText);
        ImageView image = findViewById(R.id.dynamicImageStore);
        TextView rating = findViewById(R.id.rating);
        TextView categoryDes = findViewById(R.id.textCategoryDescription);
        TextView contactDes = findViewById(R.id.textContactNumberDescription);
        TextView hazardDes = findViewById(R.id.textHandlingHazardDescription);
        TextView recycleDes = findViewById(R.id.textRecyclingMethodDescription);
        TextView websiteDes = findViewById(R.id.textWebsiteDescription);
        TextView socialDes = findViewById(R.id.textSocialLinkDescription);
        Intent intent = getIntent();
        if (intent != null) {
            String getStore = intent.getStringExtra("storeName");
            String getLocation = intent.getStringExtra("location");
            String getImageUrl = intent.getStringExtra("images");
            String getCategory = intent.getStringExtra("category");
            String getHazard = intent.getStringExtra("hazard");
            String getRecycle = intent.getStringExtra("recycle");
            String getSocial = intent.getStringExtra("social");
            String getWebsite = intent.getStringExtra("website");
            String getContact = intent.getStringExtra("contact");
            // Set values to TextViews
            storeName.setText(getStore != null ? getStore : "");
            location.setText(getLocation != null ? getLocation : "");
            categoryDes.setText(getCategory != null ? getCategory : "");
            contactDes.setText(getContact != null ? getContact : "");
            hazardDes.setText(getHazard != null ? getHazard : "");
            recycleDes.setText(getRecycle != null ? getRecycle : "");
            websiteDes.setText(getWebsite != null ? getWebsite : "");
            socialDes.setText(getSocial != null ? getSocial : "");

            // Load image using Picasso
            if (getImageUrl != "") {
                System.out.println("Image" + getImageUrl);
                Picasso.get().load(ApiConfig.IMAGE_URL + getImageUrl).into(image);
            }

            // Generate a random rating
            Random random = new Random();
            double min = 0.0;
            double max = 10.0;
            double randomDecimal = min + (max - min) * random.nextDouble();
            rating.setText(String.format("%.1f", randomDecimal) + "/" + max);
        }
    }
}
