package com.example.movieticketapp.model;

import com.google.firebase.Timestamp;

public class User {
    private String uid;
    private String fullName;
    private String email;
    private String avatarUrl;
    private Timestamp createdAt;

    public User() {
    }

    public User(String uid, String fullName, String email, String avatarUrl, Timestamp createdAt) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
