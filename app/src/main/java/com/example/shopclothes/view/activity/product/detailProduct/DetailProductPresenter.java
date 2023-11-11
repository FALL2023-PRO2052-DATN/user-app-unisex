package com.example.shopclothes.view.activity.product.detailProduct;

import androidx.annotation.NonNull;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.response.ResponseComment;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import com.example.shopclothes.view.activity.product.response.ResponseSize;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductPresenter implements DetailProductContract.Presenter {
    private final DetailProductContract.View view;

    public DetailProductPresenter(DetailProductContract.View view) {
        this.view = view;
    }

    @Override
    public void getProduct(int id) {
        ApiService.API_SERVICE.readProductById(id).enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call, @NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onProduct(response.body().getProductList().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call, @NonNull Throwable t) {

            }
        });
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

    @Override
    public void getListSizeByIdProduct(int id) {
        ApiService.API_SERVICE.readSizeByIdProduct(id).enqueue(new Callback<ResponseSize>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSize> call,@NonNull Response<ResponseSize> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    view.onListSizeByIdProduct(response.body().getSizeList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSize> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getListProductByIdCategory(int id) {
        ApiService.API_SERVICE.readProductByIdCategory(id).enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call, @NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onListProductByIdCategory(response.body().getProductList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getListCartByIdUser(String id) {
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
