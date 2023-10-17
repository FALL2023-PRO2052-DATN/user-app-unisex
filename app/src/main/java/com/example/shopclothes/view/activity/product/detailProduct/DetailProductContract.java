package com.example.shopclothes.view.activity.product.detailProduct;

import com.example.shopclothes.model.Product;

public interface DetailProductContract {
    interface View{
        void onProduct(Product product);
    }
    interface Presenter{
        void getProduct(int id);
    }
}
