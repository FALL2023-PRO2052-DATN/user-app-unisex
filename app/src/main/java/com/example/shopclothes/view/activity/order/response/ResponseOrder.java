package com.example.shopclothes.view.activity.order.response;

import com.google.gson.annotations.SerializedName;

public class ResponseOrder {
    private String status;

    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
