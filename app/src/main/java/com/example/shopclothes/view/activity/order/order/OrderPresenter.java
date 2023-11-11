package com.example.shopclothes.view.activity.order.order;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.network.ApiServiceStripe;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;
import com.example.shopclothes.view.activity.order.response.ResponseDiscount;
import com.example.shopclothes.view.activity.order.response.ResponseModel;
import com.example.shopclothes.view.activity.order.response.ResponseOrder;
import com.example.shopclothes.view.activity.order.response.ResponsePayment;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPresenter implements OrderContract.Presenter {
    private final OrderContract.View mView;
    private String customerId;
    private String ephemeraKey;
    private String clientSecret;
    private int priceBill;

    public OrderPresenter(OrderContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void readAddress(String id) {
        ApiService.API_SERVICE.readAddress(id).enqueue(new Callback<ResponseAddress>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddress> call, @NonNull Response<ResponseAddress> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onAddress(response.body().getAddress());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddress> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void readDiscountById(String code) {
        ApiService.API_SERVICE.readDiscountById(code).enqueue(new Callback<ResponseDiscount>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDiscount> call, @NonNull Response<ResponseDiscount> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onDiscount(response.body().getDiscount());
                }else {
                    mView.onMessage(AppConstants.NOT_FOUND);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDiscount> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void insertOrder(String id, String note, String payments, String deliveryStatus, String reasonCancel, double price, String discount, int idAddress, String peacefulState, int quantityBill) {
        ApiService.API_SERVICE.insertOrder(id, note, payments, deliveryStatus, reasonCancel, price, discount, idAddress, peacefulState, quantityBill).enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrder> call, @NonNull Response<ResponseOrder> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onInsertOrder(id);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrder> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void insertOrderDetail(String size, int quantity, double price, String idDonHang, int idProduct) {
        ApiService.API_SERVICE.insertOrderDetail(size, quantity, price, idDonHang, idProduct).enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrder> call, @NonNull Response<ResponseOrder> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onInsertDetailOrder(true);
                }else {
                    mView.onInsertDetailOrder(false);
                    Log.d("ERR", response.body().getErr());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrder> call, @NonNull Throwable t) {
                Log.d("ERR", t.toString());
            }
        });
    }

    @Override
    public void nextActivity(Context context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }

    @Override
    public void getCustomerId(int price) {
        ApiServiceStripe.API_SERVICE_STRIPE.getCustomer().enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    customerId = response.body().getId();
                    priceBill = price;
                    getEphemeralKey(customerId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getEphemeralKey(String id) {
        ApiServiceStripe.API_SERVICE_STRIPE.getEphemeralKey(id).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null){
                    ephemeraKey = response.body().getId();
                    getPaymentIntent(id);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getPaymentIntent(String id) {
        ApiServiceStripe.API_SERVICE_STRIPE.getPaymentIntent(id, priceBill, "usd", "card", "alipay").enqueue(new Callback<ResponsePayment>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePayment> call, @NonNull Response<ResponsePayment> response) {
                if (response.isSuccessful() && response.body() != null){
                    clientSecret = response.body().getClient_secret();
                    mView.paymentFlow(customerId, ephemeraKey, clientSecret);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePayment> call, @NonNull Throwable t) {

            }
        });
    }
}
