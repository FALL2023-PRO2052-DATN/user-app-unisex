package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class Banner {
    @SerializedName("anh_banner")
    private String anh;

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "anh='" + anh + '\'' +
                '}';
    }
}
