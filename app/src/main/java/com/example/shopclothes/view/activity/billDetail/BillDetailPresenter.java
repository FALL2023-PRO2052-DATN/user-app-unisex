package com.example.shopclothes.view.activity.billDetail;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailPresenter implements BillDetailContract.Presenter{
    private final BillDetailContract.View mView;
    public BillDetailPresenter(BillDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void readListBillDetail(String id) {
        ApiService.API_SERVICE.readBillDetail(id).enqueue(new Callback<ResponseBillDetail>() {
            @Override
            public void onResponse(Call<ResponseBillDetail> call, Response<ResponseBillDetail> response) {
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onListBillDetail(response.body().getBillDetailList());
                }
            }

            @Override
            public void onFailure(Call<ResponseBillDetail> call, Throwable t) {
                Log.d("ER", t.toString());
            }
        });
    }
}
