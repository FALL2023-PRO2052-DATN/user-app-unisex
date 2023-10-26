package com.example.shopclothes.view.activity.order.response;

import com.example.shopclothes.model.Address;
import com.google.gson.annotations.SerializedName;

public class ResponseAddress {
    private String status;
    @SerializedName("diaChi")
    private Address address;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
