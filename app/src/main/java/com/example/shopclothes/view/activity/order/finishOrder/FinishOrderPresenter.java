package com.example.shopclothes.view.activity.order.finishOrder;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishOrderPresenter implements FinishOrderContract.Presenter {

    private final FinishOrderContract.View mView;

    public FinishOrderPresenter(FinishOrderContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getListCartByIdUser(String id) {
        ApiService.API_SERVICE.readCartById(id).enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCart> call, @NonNull Response<ResponseCart> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onListCartByIdUser(response.body().getCartList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getListProductOutstanding() {
        ApiService.API_SERVICE.readProductOutstanding().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    mView.onListProductOutstanding(response.body().getProductList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    public void nextActivity(Context context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }
}
