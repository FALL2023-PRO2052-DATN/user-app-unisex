package com.example.shopclothes.view.activity.cart;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartContract.Presenter{

    private final CartContract.View view;

    public CartPresenter(CartContract.View view) {
        this.view = view;
    }

    @Override
    public void readListCart() {
        ApiService.API_SERVICE.readCart().enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCart> call, @NonNull Response<ResponseCart> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onListCart(response.body().getCartList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }
}
