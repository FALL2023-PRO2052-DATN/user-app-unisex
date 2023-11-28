package com.example.shopclothes.view.fragment.settingsFragment;

import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.User;

import java.util.List;

public interface SettingContract {
    interface View{
        void updateUI(User user);
        void onListCartByIdUser(List<Cart> cartList);
    }

    interface Presenter{
        void getUser(String id);
        void readListCartByIdUser(String id);
    }
}
