package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("ten_san_pham")
    private String name;
    @SerializedName("anh_dai_dien")
    private String image;
    @SerializedName("giam_gia")
    private int discount;
    @SerializedName("so_luong")
    private int quantity;
    @SerializedName("don_gia")
    private double price;
    @SerializedName("kich_thuoc")
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
