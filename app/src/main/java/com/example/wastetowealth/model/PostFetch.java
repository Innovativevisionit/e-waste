package com.example.wastetowealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostFetch {
    private int id;
    @SerializedName("userId")
    private UserDto user;
    @SerializedName("allShop")
    private String allShop;
    private List<Object> shop; // Change Object to the appropriate type
    private ECategoryDto ecategory;
    private List<String> images;
    private String brand;
    private String model;
    @SerializedName("minAmount")
    private int minAmount;
    @SerializedName("maxAmount")
    private int maxAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getAllShop() {
        return allShop;
    }

    public void setAllShop(String allShop) {
        this.allShop = allShop;
    }

    public List<Object> getShop() {
        return shop;
    }

    public void setShop(List<Object> shop) {
        this.shop = shop;
    }

    public ECategoryDto getEcategory() {
        return ecategory;
    }

    public void setEcategory(ECategoryDto ecategory) {
        this.ecategory = ecategory;
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

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
// Getters and setters
    // You can generate them using your IDE or manually write them
}

class UserDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private Object location; // Change Object to the appropriate type
    private List<RoleDto> roles;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}

class RoleDto {
    private int id;
    private String name;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class ECategoryDto {
    private int id;
    private String name;
    private int status;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
