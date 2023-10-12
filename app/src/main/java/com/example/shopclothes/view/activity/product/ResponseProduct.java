package com.example.shopclothes.view.activity.product;

import com.example.shopclothes.model.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProduct {
    private String status;
    @SerializedName("sanPham")
    private List<Product> productList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
