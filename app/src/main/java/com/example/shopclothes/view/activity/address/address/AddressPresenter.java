package com.example.shopclothes.view.activity.address.address;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPresenter implements AddressContract.Presenter {
    private final AddressContract.View view;

    public AddressPresenter(AddressContract.View view) {
        this.view = view;
    }

    @Override
    public void getListAddressAll(String userId) {
        ApiService.API_SERVICE.getListAddress(userId).enqueue(new Callback<ResponseAddress>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddress> call, @NonNull Response<ResponseAddress> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onListAddressAll(response.body().getAddressList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddress> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void deleteAddress(int id) {
        ApiService.API_SERVICE.deleteAddress(id).enqueue(new Callback<ResponseAddress>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddress> call, @NonNull Response<ResponseAddress> response) {
                assert  response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onMessage(AppConstants.ON_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddress> call, @NonNull Throwable t) {

            }
        });
    }


}
