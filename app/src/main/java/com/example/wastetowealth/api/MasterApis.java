package com.example.wastetowealth.api;

import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.DeliveryModel;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.model.ShopUpdate;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MasterApis {
    @POST("ecategory/store")
    Call<CategoryModel> addCategory(@Body CategoryModel category);
    @POST("auth/store-deliveryman")
    Call<DeliveryModel> addDelivery(@Body DeliveryModel deliveryModel);
    @GET("auth/getAllDeliveryMan")
    Call<List<DeliveryModel>> getDelivery();
    @GET("ecategory/list")
    Call<List<CategoryModel>> getCategory();
    @PUT("shop/update")
    Call<Object> updateShopRequest(@Body ShopUpdate shopUpdate);
    @GET("shop/pendingShop")
    Call<List<Object>> getShopList(@Query("status") String status);
    @GET("post/getCategory-based-post")
    Call<List<Object>> getCategoryShopList(@Query("email") String status);

    @Multipart
    @POST("shop/register")
    Call<Object> submitFormWithImages(
            @Part("shopName") RequestBody shopName,
            @Part("email") RequestBody email,
            @Part("contactNo") RequestBody contactNo,
            @Part MultipartBody.Part[] images,
            @Part("location") RequestBody location,
            @Part("category") RequestBody category,
            @Part("recycleMethods") RequestBody recycleMethods,
            @Part("hazard") RequestBody hazard,
            @Part("website") RequestBody website,
            @Part("socialLink") RequestBody socialLink
    );
    @POST("shop/send-post-to-shop")
    Call<Object> sendpostToShop(@Query("shopId") String shopId,@Query("postname") String postname);

}
