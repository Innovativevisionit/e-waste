package com.example.wastetowealth.api;

import com.example.wastetowealth.model.LoginModel;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.ShopRegisterFetch;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
//    @Multipart
//    @POST("post/store")
//    Call<PostData> doPostData(@Body PostData data);

    @Multipart
    @POST("post/store")
    Call<Object> doPostData(
            @Part("shop_name") RequestBody shopName,
//            @Part("all_shop") RequestBody allShop,
            @Part("brand") RequestBody brand,
            @Part("model") RequestBody model,
            @Part("condition") RequestBody condition,
            @Part("min_amount") RequestBody minAmount,
            @Part("max_amount") RequestBody maxAmount,
            @Part("categories") RequestBody categories,
            @Part MultipartBody.Part[] images
    );

    @GET("post/postList")
    Call<List<Object>> getPost();

}
