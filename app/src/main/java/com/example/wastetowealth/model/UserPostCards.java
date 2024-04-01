package com.example.wastetowealth.model;

public class UserPostCards {

    private int postImage;
    private String postName;
    private String category;
    private int likeOrUnlike;
    private int share;
    private int options;

    public UserPostCards() {
    }

    public UserPostCards(int postImage, String postName, String category, int likeOrUnlike, int share, int options) {
        this.postImage = postImage;
        this.postName = postName;
        this.category = category;
        this.likeOrUnlike = likeOrUnlike;
        this.share = share;
        this.options = options;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLikeOrUnlike() {
        return likeOrUnlike;
    }

    public void setLikeOrUnlike(int likeOrUnlike) {
        this.likeOrUnlike = likeOrUnlike;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }
}
