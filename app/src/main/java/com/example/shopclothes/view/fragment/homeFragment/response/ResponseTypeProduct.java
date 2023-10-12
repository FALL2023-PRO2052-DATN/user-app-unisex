package com.example.shopclothes.view.fragment.homeFragment.response;

import com.example.shopclothes.model.TypeProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTypeProduct {
    private String status;
    @SerializedName("loaiSanPham")
    private List<TypeProduct> typeProductList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TypeProduct> getTypeProductList() {
        return typeProductList;
    }

    public void setTypeProductList(List<TypeProduct> typeProductList) {
        this.typeProductList = typeProductList;
    }
}
