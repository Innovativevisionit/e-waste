package com.example.wastetowealth.model;

public class ApprovalPostModel {

    private Integer postId;
    private String email;
    private String status;
    private String reason;
    private String deliveryMan;

    public ApprovalPostModel() {
    }

    public ApprovalPostModel(Integer postId, String email, String status, String reason, String deliveryMan) {
        this.postId = postId;
        this.email = email;
        this.status = status;
        this.reason = reason;
        this.deliveryMan = deliveryMan;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
