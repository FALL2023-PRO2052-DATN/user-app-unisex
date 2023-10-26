package com.example.shopclothes.view.activity.order;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.address.address.AddressActivity;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;
import com.example.shopclothes.view.activity.order.response.ResponseDiscount;
import com.example.shopclothes.view.activity.order.response.ResponseOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPresenter implements OrderContract.Presenter {
    private final OrderContract.View mView;

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
    public void insertOrder(String id, String note, String payments, String deliveryStatus, String reasonCancel, double price, String discount, int idAddress, String peacefulState) {
        ApiService.API_SERVICE.insertOrder(id, note, payments, deliveryStatus, reasonCancel, price, discount, idAddress, peacefulState).enqueue(new Callback<ResponseOrder>() {
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
    public void nextActivity(Context context) {
        context.startActivity(new Intent(context, AddressActivity.class));
    }
}
