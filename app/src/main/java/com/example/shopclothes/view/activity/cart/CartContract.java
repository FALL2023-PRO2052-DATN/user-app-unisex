package com.example.shopclothes.view.activity.cart;

import com.example.shopclothes.model.Cart;

import java.util.List;

public interface CartContract {
    interface View{
        void onClick();
        void onListCartByIdUser(List<Cart> cartList);
        void itemCartClick(double sumPrice, boolean check, boolean checkItem);
        void listCartClick(List<Integer> cartList);
        void onMessage(String message);
//        void onListUpdate();
        void selectedItemsCount(int selectedItemsCount);
    }

    interface Presenter{
        void readListCartByIdUser(String id);
        void deleteCart(int id);
        void updateCart(int id, int quantity);
    }
}
