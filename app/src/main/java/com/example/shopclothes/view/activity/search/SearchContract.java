package com.example.shopclothes.view.activity.search;

import android.content.Context;

import com.example.shopclothes.model.Product;

import java.util.List;

public interface SearchContract {
    interface View{
        void onListProductAll(List<Product> list);
        void nextDetailActivity(int id);
    }
    interface Presenter{
        void getListProductAll();
        void nextActivity(Context context);
    }
}
