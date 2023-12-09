package com.example.shopclothes.view.activity.order.order;

import android.content.Context;
import android.content.Intent;

import com.example.shopclothes.model.Address;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Discount;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.List;

public class OrderContract {
    interface View{
        void onClick();
        void initPresenter();
        void onListProduct();
        void setAdapter(List<Cart> cartList);
        void onAddress(Address address);
        void onDiscount(Discount discount);
        void onInsertOrder(String idOrder);
        void insertOrderActivity();
        void setValue(Intent intent, List<Cart> cartList);
        void onMessage(String message);
        void onInsertDetailOrder(boolean check);
        void paymentFlow(String customerId, String ephemeralKey, String clientSelect);
        void onPaymentSheetResult(PaymentSheetResult paymentSheetResult);

    }
    interface Presenter{
        void readListProductByListId(String idUser, String idList);
        void readAddress(String id);
        void readDiscountById(String code);
        void insertOrder(String id, String note, String payments, String deliveryStatus,
                         String reasonCancel, double price, String discount, int idAddress, String peacefulState, int quantityCart);
        void insertOrderDetail(String size, int quantity, double price, String idDonHang, int idProduct);
        void nextActivity(Context context, Class<?> activity);
        void getCustomerId(int price);
        void getEphemeralKey(String id);
        void getPaymentIntent(String id);
    }
}
