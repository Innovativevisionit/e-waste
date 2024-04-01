package com.example.wastetowealth.api;

import com.example.wastetowealth.model.LoginModel;
import com.example.wastetowealth.model.PostData;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
    @POST("post/store")
    Call<PostData> doPostData(@Body PostData data);
}
