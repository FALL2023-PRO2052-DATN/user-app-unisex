package com.example.shopclothes.view.activity.product.productOutstanding;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.MainActivity;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOutstandingPresenter implements ProductOutstandingContract.Presenter {

    ProductOutstandingContract.View view;

    public ProductOutstandingPresenter(ProductOutstandingContract.View view) {
        this.view = view;
    }

    @Override
    public void getListProductOutstanding() {
        ApiService.API_SERVICE.readProductOutstanding().enqueue(new Callback<ResponseProduct>() {
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

    @Override
    public void nextActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
