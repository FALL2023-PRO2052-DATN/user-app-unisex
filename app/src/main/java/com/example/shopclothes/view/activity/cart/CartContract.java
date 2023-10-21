package com.example.shopclothes.view.activity.cart;

import com.example.shopclothes.model.Cart;

import java.util.List;

public interface CartContract {
    interface View{
        void onListCart(List<Cart> cartList);
        void itemCartClick(double sumPrice, boolean check);
    }

    interface Presenter{
        void readListCart();
    }
}
