package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class TypeProduct {
    private int id;
    @SerializedName("ten_danh_muc")
    private String nameTypeProduct;

    public TypeProduct(int id, String nameTypeProduct) {
        this.id = id;
        this.nameTypeProduct = nameTypeProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTypeProduct() {
        return nameTypeProduct;
    }

    public void setNameTypeProduct(String nameTypeProduct) {
        this.nameTypeProduct = nameTypeProduct;
    }

    @Override
    public String toString() {
        return "TypeProduct{" +
                "id=" + id +
                ", nameTypeProduct='" + nameTypeProduct + '\'' +
                '}';
    }
}
