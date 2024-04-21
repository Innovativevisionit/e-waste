package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setValues();
    }
    private void setValues() {
        TextView nameText = findViewById(R.id.editTextName);
        TextView ageText = findViewById(R.id.editTextAge);
        TextView emailText = findViewById(R.id.editTextEmail);
        TextView locationText = findViewById(R.id.editTextLocation);
        TextView contactNoText = findViewById(R.id.editTextContactNo);
        TextView pendingText = findViewById(R.id.pending);
        TextView approvedText = findViewById(R.id.approved);

        RetrofitService retrofitService = new RetrofitService();
        UserApi apiService = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(ProfilePage.this);
        String email = sharedPreferences.getString("email", "Default");
        apiService.getUserDetails(email).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                        Gson gson = new Gson();
                        Map<String, Object> res = gson.fromJson(gson.toJsonTree(response.body()), new TypeToken<Map<String, Object>>() {}.getType());
                    nameText.setText((String) res.get("username"));
                    ageText.setText((String) res.get("age"));
                    emailText.setText((String) res.get("email"));
                    locationText.setText((String) res.get("location"));
                    contactNoText.setText((String) res.get("mobileNo"));
                    pendingText.setText(String.valueOf((long) (double)res.get("pendingPostCount")));
                    approvedText.setText(String.valueOf((long) (double)res.get("aprovedPostCount")));

                } else {
                    Toast.makeText(ProfilePage.this, "Failed to fetch categories" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                if (ProfilePage.this != null) {
                    Toast.makeText(ProfilePage.this, "Failed to fetch shop list", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}