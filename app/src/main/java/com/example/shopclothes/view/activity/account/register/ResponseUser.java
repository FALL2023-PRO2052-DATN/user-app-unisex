package com.example.shopclothes.view.activity.account.register;

import com.example.shopclothes.model.User;
import com.google.gson.annotations.SerializedName;

public class ResponseUser {
    private String status;
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
