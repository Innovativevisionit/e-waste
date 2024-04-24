package com.example.wastetowealth.ShopOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wastetowealth.Admin.CategoryPage;
import com.example.wastetowealth.ProfilePage;
import com.example.wastetowealth.R;
import com.example.wastetowealth.RequestUserActivity;
import com.example.wastetowealth.adapter.RequestUserRecycler;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.ApprovalPostModel;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.model.ShopUpdate;
import com.example.wastetowealth.model.UserPostCards;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdminPost extends AppCompatActivity implements RequestUserRecycler.OnItemClickListener {
    private ArrayList<PostUserData> postData;
    RecyclerView request_post_acc_rej;
    Button accept, reject;
    private RequestUserRecycler adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_admin_post);
        request_post_acc_rej = findViewById(R.id.request_post_acc_rej);
        list();
        setupRecyclerView();
    }
    private void list() {
        postData = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(RequestAdminPost.this);
        String email = sharedPreferences.getString("email", "Default");
        apiService.getUserPost(email).enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    postData.clear();
                    System.out.println(response.body());
                    for (Object shop : response.body()) {
                        Gson gson = new Gson();
                        Map<String, Object> productData = gson.fromJson(gson.toJsonTree(shop), new TypeToken<Map<String, Object>>() {}.getType());

                        PostUserData shopFetch = new PostUserData();
                        List<String> imagesList = (List<String>) productData.get("images");
                        shopFetch.setId((int) (double)productData.get("id"));
                        shopFetch.setName((String) productData.get("name"));
                        shopFetch.setEcategoryName((String) productData.get("ecategoryName"));

                        shopFetch.setBrand((String) productData.get("brand"));
                        shopFetch.setModel((String) productData.get("model"));
                        shopFetch.setPostCondition((String) productData.get("postCondition"));
                        Double minAmountDouble = (Double) productData.get("minAmount");
                        if (minAmountDouble != null) {
                            shopFetch.setMinAmount(minAmountDouble.longValue());
                        }
                        Double maxAmountDouble = (Double) productData.get("maxAmount");
                        if (maxAmountDouble != null) {
                            shopFetch.setMaxAmount(maxAmountDouble.longValue());
                        }

                        shopFetch.setImages(imagesList);
                        postData.add(shopFetch);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                t.printStackTrace();
                if (RequestAdminPost.this != null) {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        request_post_acc_rej.setLayoutManager(layoutManager);
        adapter = new RequestUserRecycler(postData, this);
        request_post_acc_rej.setAdapter(adapter);
    }


    @Override
    public void onAcceptClick(int position) {
        PostUserData postUserData = postData.get(position);
        Integer postId = Integer.valueOf(postUserData.getId());
        String status = "approve";
        showDialog(postId, status);
    }

    @Override
    public void onRejectClick(int position) {
        PostUserData postUserData = postData.get(position);
        Integer postId = Integer.valueOf(postUserData.getId());
        String status = "reject";
        showDialog(postId, status);
    }

    private void showDialog(Integer postId, String status) {
        View dialogView = getLayoutInflater().inflate(R.layout.input_approved_post, null);
        final EditText editDeliveryMan = dialogView.findViewById(R.id.editDeliveryMan);
        final EditText editReason = dialogView.findViewById(R.id.editTextReason);

        if (status.equals("approve")) {
            editDeliveryMan.setVisibility(View.VISIBLE);
        } else {
            editDeliveryMan.setVisibility(View.GONE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Approval Post")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle positive button click
                        String deliveryMan = editDeliveryMan.getText().toString().trim();
                        String reason = editReason.getText().toString().trim();
//                        String category = getCategory;

                        if (!deliveryMan.isEmpty() && !reason.isEmpty()) {
                            sendApprovedPost(postId, status, deliveryMan,reason);
                        } else if(!reason.isEmpty()) {
                            sendApprovedPost(postId, status, deliveryMan,reason);
                        }else {
                            Toast.makeText(RequestAdminPost.this, "Please enter a all data", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle negative button click
                        Toast.makeText(RequestAdminPost.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void sendApprovedPost(Integer postId, String status, String deliveryMan, String reason) {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(RequestAdminPost.this);
        String email = sharedPreferences.getString("email", "Default");

        ApprovalPostModel approvalPostModel = new ApprovalPostModel(postId, email, status, deliveryMan, reason);
//        Log.d("Status Check" , "Data : " + postId + " " + status + " " + deliveryMan + " " + reason);
        apiService.approvalPost(approvalPostModel).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    postData.clear();
                    list();
                    Log.d("Status Check" , "Data : " + response.body());

                    request_post_acc_rej.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                if (RequestAdminPost.this != null) {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateShopRequest(ShopRegisterFetch requestUserModel, String status) {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        ShopUpdate statusUpdate = new ShopUpdate(requestUserModel.getId(), status);
        apiService.updateShopRequest(statusUpdate).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    postData.clear();
                    System.out.println(response.body());

                    request_post_acc_rej.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                if (RequestAdminPost.this != null) {
                    Toast.makeText(RequestAdminPost.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}