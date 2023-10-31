package com.example.shopclothes.view.fragment.billFragment;

import com.example.shopclothes.model.Bill;

import java.util.List;

public interface BillContract {
    interface View {
        void onListBill(List<Bill> billList);
    }

    interface Presenter {
        void readListBill(String iUser, String deliveryStatus);
    }
}
