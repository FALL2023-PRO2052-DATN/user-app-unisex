package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Size implements Serializable {
    @SerializedName("kich_thuoc_id")
    private int id;
    @SerializedName("ten_kich_thuoc")
    private String name;
    @SerializedName("so_luong_ton_kho")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Size{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
