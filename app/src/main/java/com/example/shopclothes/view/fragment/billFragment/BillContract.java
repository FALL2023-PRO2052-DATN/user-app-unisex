package com.example.shopclothes.view.fragment.billFragment;

import android.content.Context;

import com.example.shopclothes.model.Bill;
import com.example.shopclothes.model.Cart;

import java.util.List;

public interface BillContract {
    interface View {
        void onListBill(List<Bill> billList);
        void nextScreenDetailBill(String id);
        void nextScreenComment(String id);
       interface ViewParents {
           void onListCartByIdUser(List<Cart> cartList);
       }
    }

    interface Presenter {
        void readListBill(String iUser, String deliveryStatus);
        void nextActivity(Context context, Class<?> activity, String id);
        void readListCartByIdUser(String id);
    }
}
