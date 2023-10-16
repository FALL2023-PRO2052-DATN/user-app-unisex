package com.example.shopclothes.view.activity.product.productOutstanding;

import android.content.Context;

import com.example.shopclothes.model.Product;

import java.util.List;

public interface ProductOutstandingContract {
    interface View {
        void onList(List<Product> list);
    }
    interface Presenter {
        void getListProductOutstanding();
        void nextActivity(Context context);
    }
}
