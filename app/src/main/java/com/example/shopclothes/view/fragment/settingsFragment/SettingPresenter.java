package com.example.shopclothes.view.fragment.settingsFragment;

import androidx.annotation.NonNull;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.example.shopclothes.view.activity.cart.ResponseCart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingPresenter implements SettingContract.Presenter{
    private final SettingContract.View view;

    public SettingPresenter(SettingContract.View view) {
        this.view = view;
    }

    @Override
    public void getUser(String id) {
        ApiService.API_SERVICE.readUser(id).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.updateUI(response.body().getUser());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {

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
                    view.onListCartByIdUser(response.body().getCartList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }

}
