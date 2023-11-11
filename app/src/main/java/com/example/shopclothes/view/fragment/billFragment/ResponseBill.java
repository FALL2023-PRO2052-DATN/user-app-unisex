package com.example.shopclothes.view.fragment.billFragment;

import com.example.shopclothes.model.Bill;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBill {
    private String status;
    @SerializedName("billList")
    private List<Bill> billList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }
}
