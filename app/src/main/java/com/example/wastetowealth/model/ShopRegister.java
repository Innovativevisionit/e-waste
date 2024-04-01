package com.example.wastetowealth.model;

import okhttp3.MultipartBody;

public class ShopRegister {
    private String shopName;
    private String contactNo;
    private MultipartBody.Part[] images;
    private String location;
    private String category;
    private String recycleMethods;
    private String hazard;
    private String website;
    private String socialLink;

    public ShopRegister() {
    }

    public ShopRegister(String shopName, String contactNo, MultipartBody.Part[] images, String location, String category, String recycleMethods, String hazard, String website, String socialLink) {
        this.shopName = shopName;
        this.contactNo = contactNo;
        this.images = images;
        this.location = location;
        this.category = category;
        this.recycleMethods = recycleMethods;
        this.hazard = hazard;
        this.website = website;
        this.socialLink = socialLink;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public MultipartBody.Part[] getImages() {
        return images;
    }

    public void setImages(MultipartBody.Part[] images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecycleMethods() {
        return recycleMethods;
    }

    public void setRecycleMethods(String recycleMethods) {
        this.recycleMethods = recycleMethods;
    }

    public String getHazard() {
        return hazard;
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(String socialLink) {
        this.socialLink = socialLink;
    }
}