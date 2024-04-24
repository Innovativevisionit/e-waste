package com.example.wastetowealth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wastetowealth.Admin.CategoryPage;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DynamicStoreCard extends AppCompatActivity {

    String getPost;
    private List<String> postList;
    Spinner editTextCategory;
    String shopId;
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
        View dialogView = getLayoutInflater().inflate(R.layout.input_spinner, null);

        editTextCategory = dialogView.findViewById(R.id.editPostText);
        getPendingUserPost();

        editTextCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPost = postList.get(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Approach your post to shop")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle positive button click
                        String post = getPost;

                        if (!post.isEmpty()) {
                            sendPostToShop(post);
//                            Toast.makeText(DynamicStoreCard.this, post, Toast.LENGTH_SHORT).show();
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

    private void getPendingUserPost(){
        postList = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(DynamicStoreCard.this);
        String email = sharedPreferences.getString("email", "Default");

        apiService.getPendingPostList(email)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful()) {
                            postList.addAll(response.body());
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DynamicStoreCard.this,
                                    android.R.layout.simple_spinner_item,
                                    postList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            editTextCategory.setAdapter(adapter);
                        }else{

                            Toast.makeText(DynamicStoreCard.this, "Something error happened", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });

    }

    private void sendPostToShop(String postname) {

        RetrofitService retrofitService = new RetrofitService();
        MasterApis add = retrofitService.getRetrofit().create(MasterApis.class);

        add.sendpostToShop(shopId,postname)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DynamicStoreCard.this, "send sucessfully", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(DynamicStoreCard.this, "Something error happened", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
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
            shopId = intent.getStringExtra("id");
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
