package com.example.shopclothes.view.activity.product.productNew;

import android.content.Context;

import com.example.shopclothes.model.Product;

import java.util.List;

public interface ProductNewContract {
    interface View {
        void onList(List<Product> list);
    }
    interface Presenter {
        void getListProductNew();
        void nextActivity(Context context);
    }
}
