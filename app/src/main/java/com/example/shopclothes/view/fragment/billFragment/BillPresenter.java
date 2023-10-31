package com.example.shopclothes.view.fragment.billFragment;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillPresenter implements BillContract.Presenter {
    private BillContract.View mView;

    public BillPresenter(BillContract.View mView) {
        this.mView = mView;
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
}
