package com.example.wastetowealth.model;

public class DashboardCards {
    private String storeName;
    private String location;
    private int imageUrl;

    public DashboardCards() {
    }

    public DashboardCards(String storeName, String location, int imageUrl) {
        this.storeName = storeName;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
