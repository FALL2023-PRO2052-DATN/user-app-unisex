package com.example.shopclothes.view.activity.address.updateAddress;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressPresenter implements UpdateAddressContract.Presenter{
    private final UpdateAddressContract.View view;

    public UpdateAddressPresenter(UpdateAddressContract.View view) {
        this.view = view;
    }

    @Override
    public void updateAddress(int id, String name, String email, String phone, String address, int defaultStatus, int idDefault) {
        ApiService.API_SERVICE.updateAddress(id, name, email, phone, address, defaultStatus, idDefault).enqueue(new Callback<ResponseAddress>() {
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
