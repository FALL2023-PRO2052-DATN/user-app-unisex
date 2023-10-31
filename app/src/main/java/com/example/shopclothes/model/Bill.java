package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class Bill {
    private String id;
    @SerializedName("tinh_trang_giao_hang")
    private String deliveryStatus;
    @SerializedName("ten_san_pham")
    private String nameProduct;
    @SerializedName("anh_dai_dien")
    private String imageProduct;
    @SerializedName("don_gia")
    private double price;
    @SerializedName("kich_thuoc")
    private String size;
    @SerializedName("so_luong")
    private int quantity;
    @SerializedName("so_luong_don_hang")
    private int quantityBill;
    @SerializedName("thanh_tien")
    private double priceBill;

    public int getQuantityBill() {
        return quantityBill;
    }

    public void setQuantityBill(int quantityBill) {
        this.quantityBill = quantityBill;
    }

    public double getPriceBill() {
        return priceBill;
    }

    public void setPriceBill(double priceBill) {
        this.priceBill = priceBill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", imageProduct='" + imageProduct + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
