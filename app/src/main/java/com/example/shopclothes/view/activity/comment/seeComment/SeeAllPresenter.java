package com.example.shopclothes.view.activity.comment.seeComment;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductContract;
import com.example.shopclothes.view.activity.product.response.ResponseComment;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import com.example.shopclothes.view.activity.product.response.ResponseSize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllPresenter implements SeeCommentContract.Presenter {
    private final SeeCommentContract.View view;

    public SeeAllPresenter(SeeCommentContract.View view) {
        this.view = view;
    }


    @Override
    public void getListCommentById(int id) {
        ApiService.API_SERVICE.readCommentById(id).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onListComment(response.body().getCommentList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseComment> call,@NonNull Throwable t) {

            }
        });
    }

}
