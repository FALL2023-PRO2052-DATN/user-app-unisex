package com.example.shopclothes.view.fragment.notificationFragment;

import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Discount;
import com.example.shopclothes.model.Notification;

import java.util.List;

public interface NotificationContract {
    interface View {
        void onListNotification(List<Notification> notificationList);
        void onListDiscount(List<Discount> discountList);
        void onMessage(String message);
        void onListCartByIdUser(List<Cart> cartList);

    }
    interface Presenter {
        void readListNotification(String userId);
        void readListDiscount();
        void deleteNotification(int id);
        void readListCartByIdUser(String id);
    }
}
