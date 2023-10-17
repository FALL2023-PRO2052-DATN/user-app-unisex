package com.example.shopclothes.view.fragment.homeFragment;

import android.content.Context;

import androidx.viewpager2.widget.ViewPager2;

import com.example.shopclothes.model.Banner;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.TypeProduct;

import java.util.List;

public interface HomeContract {

    interface View {
        void onListBanner(List<Banner> list);
        void onListProductNew(List<Product> list);
        void onListProductOutstanding(List<Product> list);
        void onListProductAll(List<Product> list);
        void onListTypeProduct(List<TypeProduct> list);
        void onItemClickListener(int id);
        void onListProductById(List<Product> list);
    }
    interface Presenter {
        void getListBanner();
        void getListProductNew();
        void getListProductOutstanding();
        void getListProductAll();
        void getListTypeProduct();
        void getListProductByIdCategory(int id);
        void autoNextBanner(ViewPager2 pager2,  List<Banner> list);
        void nextActivity(Context context, Class<?> activity);
    }
}
