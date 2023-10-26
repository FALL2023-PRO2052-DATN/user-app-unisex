package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class Discount {
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("gia_tri")

    private int percent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
