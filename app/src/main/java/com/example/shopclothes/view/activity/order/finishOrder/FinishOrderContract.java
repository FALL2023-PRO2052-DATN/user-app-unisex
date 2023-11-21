package com.example.shopclothes.view.activity.order.finishOrder;

import android.app.PendingIntent;
import android.content.Context;

import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Notification;
import com.example.shopclothes.model.Product;

import java.util.List;

public interface FinishOrderContract {
    interface View {
        void onClick();
        void onListCartByIdUser(List<Cart> list);
        void onListProductOutstanding(List<Product> list);
    }
    interface Presenter {
        void getListCartByIdUser(String id);
        void getListProductOutstanding();
        void nextActivity(Context context, Class<?> activity);
        void senNotification(String title, String content, PendingIntent pendingIntent, Context context, Object o);
    }
}
