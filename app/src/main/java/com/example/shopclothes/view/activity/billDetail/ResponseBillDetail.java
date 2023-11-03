package com.example.shopclothes.view.activity.billDetail;

import com.example.shopclothes.model.BillDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBillDetail {
    private String status;
    @SerializedName("billList")
    private List<BillDetail> billDetailList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BillDetail> getBillDetailList() {
        return billDetailList;
    }

    public void setBillDetailList(List<BillDetail> billDetailList) {
        this.billDetailList = billDetailList;
    }
}
