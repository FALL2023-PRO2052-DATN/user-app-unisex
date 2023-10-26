package com.example.shopclothes.view.activity.order.response;

import com.example.shopclothes.model.Discount;
import com.google.gson.annotations.SerializedName;

public class ResponseDiscount {
    private String status;
    @SerializedName("discount")
    private Discount discount;

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
