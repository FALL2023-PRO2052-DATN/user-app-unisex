package com.example.shopclothes.view.fragment.billFragment;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.cart.ResponseCart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPresenter implements BillContract.Presenter {
    private BillContract.View mView;

    private BillContract.View.ViewParents mViewParents;

    public BillPresenter(BillContract.View mView) {
        this.mView = mView;
    }

    public BillPresenter(BillContract.View.ViewParents mViewParents) {
        this.mViewParents = mViewParents;
    }

    @Override
    public void readListBill(String iUser, String deliveryStatus) {
        ApiService.API_SERVICE.readListBill(iUser, deliveryStatus).enqueue(new Callback<ResponseBill>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBill> call, @NonNull Response<ResponseBill> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onListBill(response.body().getBillList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBill> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void readListCartByIdUser(String id) {
        ApiService.API_SERVICE.readCartById(id).enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCart> call, @NonNull Response<ResponseCart> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mViewParents.onListCartByIdUser(response.body().getCartList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void nextActivity(Context context, Class<?> activity, String id) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
}
