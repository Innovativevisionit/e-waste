package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

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
        setContentView(R.layout.activity_main);
        System.out.println("hello");
        initializeComponents();
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
            loginModel.setEmpId(    email);
            loginModel.setPassword(password);

            loginApi.doLogin(loginModel)
                            .enqueue(new Callback<LoginModel>() {
                                @Override
                                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                    System.out.println(response.body());
                                    if (response.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        assert response.body() != null;
                                        intent.putExtra("username", response.body().getEmpId());
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