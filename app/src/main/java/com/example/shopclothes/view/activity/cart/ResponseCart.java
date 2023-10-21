package com.example.shopclothes.view.activity.cart;

import com.example.shopclothes.model.Cart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCart {
    private String status;
    @SerializedName("cartList")
    private List<Cart> cartList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
