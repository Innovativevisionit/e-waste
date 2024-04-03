package com.example.wastetowealth.api;

import com.example.wastetowealth.model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    @GET("ecategory/activeList")
    Call<List<CategoryModel>> listCategory();
}
