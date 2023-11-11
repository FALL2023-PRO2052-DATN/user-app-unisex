package com.example.shopclothes.view.activity.address.addAddress;

import android.util.Log;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressPresenter implements AddAddressContract.Presenter{
    private final AddAddressContract.View view;

    public AddAddressPresenter(AddAddressContract.View view) {
        this.view = view;
    }

    @Override
    public void insertAddress(int id, String name, String email, String phone, String address, int defaultStatus, String userId) {
        ApiService.API_SERVICE.insertAddress(id, name, email, phone, address, defaultStatus, userId).enqueue(new Callback<ResponseAddress>() {
            @Override
            public void onResponse(Call<ResponseAddress> call, Response<ResponseAddress> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onMessege(AppConstants.ON_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResponseAddress> call, Throwable t) {

            }
        });
    }
}
