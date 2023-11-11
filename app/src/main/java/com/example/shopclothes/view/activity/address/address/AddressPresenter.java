package com.example.shopclothes.view.activity.address.address;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;
import com.google.android.gms.common.api.Api;

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
            public void onResponse(Call<ResponseAddress> call, Response<ResponseAddress> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onListAddressAll(response.body().getAddressList());
                }
            }

            @Override
            public void onFailure(Call<ResponseAddress> call, Throwable t) {

            }
        });
    }

    @Override
    public void readAddress(String userId) {
    }

    @Override
    public void deleteAddress(int id) {
        ApiService.API_SERVICE.deleteAddress(id).enqueue(new Callback<ResponseAddress>() {
            @Override
            public void onResponse(Call<ResponseAddress> call, Response<ResponseAddress> response) {
                assert  response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onMessage(AppConstants.ON_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<ResponseAddress> call, Throwable t) {

            }
        });
    }


}
