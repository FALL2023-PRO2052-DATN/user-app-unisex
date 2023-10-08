package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("ho_va_ten")
    private String name;
    @SerializedName("anh_dai_dien")
    private String anh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
