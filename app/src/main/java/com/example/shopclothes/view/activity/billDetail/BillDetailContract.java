package com.example.shopclothes.view.activity.billDetail;

import com.example.shopclothes.model.BillDetail;

import java.util.List;

public interface BillDetailContract {
    interface View {
        void onClick();
        void initPresenter();
        void onListBillDetail(List<BillDetail> list);
        void setValuesBillDetail(BillDetail billDetail);
    }

    interface Presenter {
        void readListBillDetail(String id);
    }
}
