package com.example.wastetowealth.api;

import com.example.wastetowealth.model.LoginModel;
import com.example.wastetowealth.model.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("auth/signin")
    Call<LoginModel> doLogin(@Body LoginModel login);

    @POST("auth/signup")
    Call<Object>register(@Body RegisterModel registerModel);
}
