package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

public class BillDetail {
    private String id;
    @SerializedName("ghi_chu")
    private String note;
    @SerializedName("hinh_thuc_thanh_toan")
    private String payments;
    @SerializedName("tinh_trang_giao_hang")
    private String deliveryStatus;
    @SerializedName("so_luong_don_hang")
    private int quantityBill;
    @SerializedName("thanh_tien")
    private Double intoMoney;
    @SerializedName("ten_san_pham")
    private String nameProduct;
    @SerializedName("anh_dai_dien")
    private String imageProduct;
    @SerializedName("don_gia")
    private Double priceProduct;
    @SerializedName("kich_thuoc")
    private String sizeProduct;
    @SerializedName("so_luong")
    private int quantityProduct;
    @SerializedName("ho_va_ten")
    private String nameUser;
    @SerializedName("email")
    private String email;
    @SerializedName("dien_thoai")
    private String phoneNumber;
    @SerializedName("dia_chi")
    private String address;
    @SerializedName("ma_giam_gia")
    private String discountCode;
    @SerializedName("gia_tri")
    private int discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getQuantityBill() {
        return quantityBill;
    }

    public void setQuantityBill(int quantityBill) {
        this.quantityBill = quantityBill;
    }

    public Double getIntoMoney() {
        return intoMoney;
    }

    public void setIntoMoney(Double intoMoney) {
        this.intoMoney = intoMoney;
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

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(String sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public int getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", payments='" + payments + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", quantityBill=" + quantityBill +
                ", intoMoney=" + intoMoney +
                ", nameProduct='" + nameProduct + '\'' +
                ", imageProduct='" + imageProduct + '\'' +
                ", priceProduct=" + priceProduct +
                ", sizeProduct='" + sizeProduct + '\'' +
                ", quantityProduct=" + quantityProduct +
                ", nameUser='" + nameUser + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", discountCode='" + discountCode + '\'' +
                ", discount=" + discount +
                '}';
    }
}
