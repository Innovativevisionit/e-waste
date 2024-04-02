package com.example.wastetowealth.api;

import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.DeliveryModel;
import com.example.wastetowealth.model.ShopRegister;
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

public interface MasterApis {
    @POST("ecategory/store")
    Call<CategoryModel> addCategory(@Body CategoryModel category);
    @POST("ecategory/store")
    Call<DeliveryModel> addDelivery(@Body DeliveryModel category);
    @POST("ecategory/store")
    Call<List<DeliveryModel>> getDelivery();
    @GET("ecategory/list")
    Call<List<CategoryModel>> getCategory();
    @GET("shop/shopList")
    Call<List<ShopRegisterFetch>> getShopList();
    @Multipart
    @POST("shop/register")
    Call<Object> submitFormWithImages(
            @Part("shopName") RequestBody shopName,
            @Part("contactNo") RequestBody contactNo,
            @Part MultipartBody.Part[] images,
            @Part("location") RequestBody location,
            @Part("category") RequestBody category,
            @Part("recycleMethods") RequestBody recycleMethods,
            @Part("hazard") RequestBody hazard,
            @Part("website") RequestBody website,
            @Part("socialLink") RequestBody socialLink
    );
}
