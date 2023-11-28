package com.example.shopclothes.view.activity.order.response;

import com.example.shopclothes.model.Discount;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDiscount {
    private String status;
    @SerializedName("discount")
    private Discount discount;
    @SerializedName("discountList")
    private List<Discount> discountList;

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
