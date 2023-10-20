package com.example.shopclothes.model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    @SerializedName("ten_san_pham")
    private String nameProduct;
    @SerializedName("anh_dai_dien")
    private String imageProduct;
    @SerializedName("gia_ban")
    private double price;
    @SerializedName("giam_gia")
    private int sale;
    @SerializedName("mo_ta_chi_tiet")
    private String note;
    @SerializedName("ten_danh_muc")
    private String nameTypeProduct;
    @SerializedName("danh_muc_id")
    private int idCategory;

    public String getNameTypeProduct() {
        return nameTypeProduct;
    }

    public void setNameTypeProduct(String nameTypeProduct) {
        this.nameTypeProduct = nameTypeProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", imageProduct='" + imageProduct + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                ", note='" + note + '\'' +
                '}';
    }
}
