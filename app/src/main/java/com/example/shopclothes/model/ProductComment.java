package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class ProductComment {
    private int id;
    @SerializedName("ten_san_pham")
    private String name;
    @SerializedName("anh_dai_dien")
    private String image;
    @SerializedName("kich_thuoc")
    private String size;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ProductComment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
