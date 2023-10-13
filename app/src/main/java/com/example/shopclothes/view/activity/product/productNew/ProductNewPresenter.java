package com.example.shopclothes.view.activity.product.productNew;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.product.ResponseProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductNewPresenter implements ProductNewContract.Presenter {

    private ProductNewContract.View view;

    @Override
    public void getListProductNew() {
        ApiService.API_SERVICE.readProductNew().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onList(response.body().getProductList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }
}
