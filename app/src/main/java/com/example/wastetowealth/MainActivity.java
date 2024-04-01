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
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeComponents();
        Button registerButton = findViewById(R.id.registerNow);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initializeComponents() {
        EditText getEmail = findViewById(R.id.email);
        EditText getPassword = findViewById(R.id.password);
        MaterialButton submit = findViewById(R.id.loginSubmit);

        RetrofitService retrofitService = new RetrofitService();
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);

        submit.setOnClickListener(v -> {
            String email = getEmail.getText().toString();
            String password = getPassword.getText().toString();

            LoginModel loginModel = new LoginModel();
            loginModel.setEmail(email);
            loginModel.setPassword(password);

            loginApi.doLogin(loginModel)
                            .enqueue(new Callback<LoginModel>() {
                                @Override
                                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                    System.out.println(response.body());
                                    if (response.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent;
                                        if(response.body().getRoles().contains("Employee")) {
                                            intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        }else if(response.body().getRoles().contains("Admin")){
                                            intent = new Intent(MainActivity.this, AdminDashboard.class);
                                        }else{
                                            intent = new Intent(MainActivity.this, DashboardSo.class);
                                        }
                                        assert response.body() != null;

                                        System.out.println(response.body().getUserName());

                                        intent.putExtra("username", response.body().getUserName());
                                        intent.putExtra("email", response.body().getEmail());
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginModel> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            });

        });
    }
}