package com.example.shopclothes.view.activity.order.response;

import com.example.shopclothes.model.Address;
import com.example.shopclothes.model.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAddress {
    private String status;
    @SerializedName("diaChi")
    private Address address;
    @SerializedName("diaChiList")
    private List<Address> addressList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
