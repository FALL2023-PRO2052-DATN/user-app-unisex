package com.example.shopclothes.view.fragment.billFragment;

import android.content.Context;

import com.example.shopclothes.model.Bill;

import java.util.List;

public interface BillContract {
    interface View {
        void onListBill(List<Bill> billList);
        void nextScreenDetailBill(String id);
    }

    interface Presenter {
        void readListBill(String iUser, String deliveryStatus);
        void nextActivity(Context context, Class<?> activity, String id);
    }
}
