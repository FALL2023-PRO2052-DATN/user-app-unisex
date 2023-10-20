package com.example.shopclothes.view.activity.product.response;

import com.example.shopclothes.model.Size;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSize {
    private String status;
    @SerializedName("sizeProduct")
    private List<Size> sizeList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }
}
