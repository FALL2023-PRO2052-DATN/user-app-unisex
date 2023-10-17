package com.example.shopclothes.view.activity.product.detailProduct;

import com.example.shopclothes.model.Product;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailProduct {
    private String status;
    @SerializedName("sanPham")
    private Product product;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
