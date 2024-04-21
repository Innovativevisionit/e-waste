package com.example.wastetowealth.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wastetowealth.R;
import com.example.wastetowealth.adapter.Category;
import com.example.wastetowealth.adapter.DeliveryUser;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.DeliveryModel;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPage extends AppCompatActivity {
    private RecyclerView categoryRV;
    private DeliveryUser categoryAdapter;
    private List<DeliveryModel> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);
        initSet();

        FloatingActionButton button = findViewById(R.id.addDelivery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.input_delivery_user, null);

        final TextInputEditText editTextName = dialogView.findViewById(R.id.editTextName);
        final TextInputEditText editTextAge = dialogView.findViewById(R.id.editTextAge);
        final TextInputEditText editTextLocation = dialogView.findViewById(R.id.editTextLocation);
        final TextInputEditText editTextContactNo = dialogView.findViewById(R.id.editTextContactNo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Delivery Person")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle positive button click
                        String name = editTextName.getText().toString().trim();
                        String age = editTextAge.getText().toString().trim();
                        String location = editTextLocation.getText().toString().trim();
                        String contactNo = editTextContactNo.getText().toString().trim();
                        DeliveryModel deliveryModel = new DeliveryModel();
                        deliveryModel.setName(name);
                        deliveryModel.setAge(age);
                        deliveryModel.setLocation(location);
                        deliveryModel.setContactNo(contactNo);
                        if (deliveryModel != null) {
                            addDelivery(deliveryModel);
                            fetchCategories();
                        } else {
                            Toast.makeText(DeliveryPage.this, "Please enter a category", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle negative button click
                        Toast.makeText(DeliveryPage.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void addDelivery(DeliveryModel deliveryModel) {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis add = retrofitService.getRetrofit().create(MasterApis.class);

        add.addDelivery(deliveryModel).enqueue(new Callback<DeliveryModel>() {
            @Override
            public void onResponse(Call<DeliveryModel> call, Response<DeliveryModel> response) {
                if (response.isSuccessful()) {
                    fetchCategories();
                    Toast.makeText(DeliveryPage.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    fetchCategories();
                    // Check if the error body is not null
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.d("Del","user : " + errorBody);
                            Toast.makeText(DeliveryPage.this, errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        fetchCategories();
                        Toast.makeText(DeliveryPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DeliveryPage.this, "Category Adding failed", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void initSet() {
        categoryRV = findViewById(R.id.deliveryCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryRV.setLayoutManager(layoutManager);
        categoryList = new ArrayList<>();

        categoryAdapter = new DeliveryUser(categoryList);
        categoryRV.setAdapter(categoryAdapter);
        fetchCategories();
    }

    private void fetchCategories() {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        apiService.getDelivery().enqueue(new Callback<List<DeliveryModel>>() {
            @Override
            public void onResponse(Call<List<DeliveryModel>> call, Response<List<DeliveryModel>> response) {
                if (response.isSuccessful()) {
                    categoryList.clear();
                    List<DeliveryModel> responseData = response.body();
                    if (responseData != null) {
                        categoryList.addAll(responseData);
                    }
                    Log.d("DeliveryPage", "Category List: " + categoryList); // Add this log
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DeliveryPage.this, "Failed to fetch delivery persons", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DeliveryPage.this, "Failed to fetch delivery persons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}