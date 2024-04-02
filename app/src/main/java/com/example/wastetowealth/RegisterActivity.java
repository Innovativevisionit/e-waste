package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wastetowealth.Admin.AdminDashboard;
import com.example.wastetowealth.ShopOwner.DashboardSo;
import com.example.wastetowealth.api.LoginApi;
import com.example.wastetowealth.model.LoginModel;
import com.example.wastetowealth.model.RegisterModel;
import com.example.wastetowealth.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.loginNow);

        doRegister();

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void doRegister() {
        EditText userName = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText location = findViewById(R.id.location);

        Button register = findViewById(R.id.register);

        register.setOnClickListener(v -> {
            String userNameVal = userName.getText().toString();
            String emailVal = email.getText().toString();
            String passwordVal = password.getText().toString();
            String locationVal = location.getText().toString();

            RegisterModel registerModel = new RegisterModel();
            registerModel.setUsername(userNameVal);
            registerModel.setEmail(emailVal);
            registerModel.setPassword(passwordVal);
            registerModel.setLocation(locationVal);
            RetrofitService retrofitService = new RetrofitService();
            LoginApi registerApi = retrofitService.getRetrofit().create(LoginApi.class);

            registerApi.register(registerModel)
                    .enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }
}