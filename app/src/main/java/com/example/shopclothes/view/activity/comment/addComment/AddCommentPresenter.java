package com.example.shopclothes.view.activity.comment.addComment;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.product.response.ResponseComment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentPresenter implements AddCommentContract.Presenter{
    private final AddCommentContract.View mView;

    public AddCommentPresenter(AddCommentContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getListProduct(String id) {
        ApiService.API_SERVICE.readProductByIdForComment(id).enqueue(new Callback<ResponseProductComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProductComment> call, @NonNull Response<ResponseProductComment> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onListProduct(response.body().getProductCommentList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProductComment> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void insertComment(double pointRating, String content, String idUser, int idProduct) {
        ApiService.API_SERVICE.insertComment(pointRating, content, idUser, idProduct).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mView.onMessage(AppConstants.ON_SUCCESS, true);
                }else {
                    mView.onMessage(AppConstants.ON_FAILURE, false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {

            }
        });
    }

}
