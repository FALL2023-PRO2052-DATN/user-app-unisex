package com.example.shopclothes.view.fragment.notificationFragment;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.order.response.ResponseDiscount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements NotificationContract.Presenter {
    private final NotificationContract.View mView;

    public NotificationPresenter(NotificationContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void readListNotification(String userId) {
        ApiService.API_SERVICE.readNotification(userId).enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNotification> call, @NonNull Response<ResponseNotification> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                        mView.onListNotification(response.body().getNotificationList());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNotification> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void readListDiscount() {
        ApiService.API_SERVICE.readDiscount().enqueue(new Callback<ResponseDiscount>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDiscount> call, @NonNull Response<ResponseDiscount> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                        mView.onListDiscount(response.body().getDiscountList());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDiscount> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void deleteNotification(int id) {
        ApiService.API_SERVICE.deleteNotification(id).enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNotification> call, @NonNull Response<ResponseNotification> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                        mView.onMessage(AppConstants.ON_SUCCESS);
                    }else {
                        mView.onMessage(AppConstants.ON_FAILURE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNotification> call, @NonNull Throwable t) {

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
                    mView.onListCartByIdUser(response.body().getCartList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }
}
