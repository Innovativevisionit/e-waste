package com.example.wastetowealth.model;

public class ShopUpdate {
    private int id;
    private String status;

    private String reason;

    public  ShopUpdate(int id, String status) {
        this.id = id;
        this.status = status;
    }

    // Getters and setters if needed
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
