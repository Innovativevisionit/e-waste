package com.example.wastetowealth.model;

import java.util.List;

public class PostUserData {
    private String name;
    private String userIdEmail;
    private String userIdUsername;
    private String ecategoryName;
    private String brand;
    private String model;
    private List<String> images;
    private String postCondition;
    private Long minAmount;
    private Long maxAmount;

    public PostUserData() {
    }

    public PostUserData(String name, String userIdEmail, String userIdUsername, String ecategoryName, String brand, String model, List<String> images, String postCondition, Long minAmount, Long maxAmount) {
        this.name = name;
        this.userIdEmail = userIdEmail;
        this.userIdUsername = userIdUsername;
        this.ecategoryName = ecategoryName;
        this.brand = brand;
        this.model = model;
        this.images = images;
        this.postCondition = postCondition;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserIdEmail() {
        return userIdEmail;
    }

    public void setUserIdEmail(String userIdEmail) {
        this.userIdEmail = userIdEmail;
    }

    public String getUserIdUsername() {
        return userIdUsername;
    }

    public void setUserIdUsername(String userIdUsername) {
        this.userIdUsername = userIdUsername;
    }

    public String getEcategoryName() {
        return ecategoryName;
    }

    public void setEcategoryName(String ecategoryName) {
        this.ecategoryName = ecategoryName;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }
}
