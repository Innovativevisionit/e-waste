package com.example.wastetowealth.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

public class PostData {
    private String allShop;
    private List<String> shopId;
    private String categories;
    private List<String> images;
    private String brand;
    private String model;
    private String condition;
    private String maxAmount;
    private String minAmount;

    @Override
    public String toString() {
        return "PostData{" +
                "allShop='" + allShop + '\'' +
                ", shopId=" + shopId +
                ", eCategory='" + categories + '\'' +
                ", images=" + images +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", condition='" + condition + '\'' +
                ", maxAmount='" + maxAmount + '\'' +
                ", minAmount='" + minAmount + '\'' +
                '}';
    }

    public PostData() {
    }

    public PostData(String allShop, List<String> shopId, String categories, List<String> images, String brand, String model, String condition, String maxAmount, String minAmount) {
        this.allShop = allShop;
        this.shopId = shopId;
        this.categories = categories;
        this.images = images;
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
    }

    public String getAllShop() {
        return allShop;
    }

    public void setAllShop(String allShop) {
        this.allShop = allShop;
    }

    public List<String> getShopId() {
        return shopId;
    }

    public void setShopId(List<String> shopId) {
        this.shopId = shopId;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }
}
