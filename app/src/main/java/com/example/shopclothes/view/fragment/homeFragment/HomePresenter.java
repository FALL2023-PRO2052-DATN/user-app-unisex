package com.example.shopclothes.view.fragment.homeFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.model.Banner;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseBanner;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseTypeProduct;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter, Handler.Callback {

    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getListBanner() {
        ApiService.API_SERVICE.readBanner().enqueue(new Callback<ResponseBanner>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBanner> call, @NonNull Response<ResponseBanner> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals(AppConstants.SUCCESS)){
                    view.onListBanner(response.body().getBanner());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBanner> call, @NonNull Throwable t) {
                Log.d("ER", t.toString());
            }
        });
    }
    @Override
    public void getListProductNew() {
        ApiService.API_SERVICE.readProductNew().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onListProductNew(response.body().getProductList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }
    @Override
    public void getListProductOutstanding() {
        ApiService.API_SERVICE.readProductOutstanding().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onListProductOutstanding(response.body().getProductList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getListProductAll() {
        ApiService.API_SERVICE.readProductAll().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onListProductAll(response.body().getProductList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    public void getListTypeProduct() {
        ApiService.API_SERVICE.readTypeProduct().enqueue(new Callback<ResponseTypeProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTypeProduct> call, @NonNull Response<ResponseTypeProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onListTypeProduct(response.body().getTypeProductList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTypeProduct> call, @NonNull Throwable t) {

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
                    view.onListProductById(response.body().getProductList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void autoNextBanner(ViewPager2 pager2, List<Banner> list) {
        Handler.Callback callback = new HomePresenter(view);
        Handler mHandler = new Handler(callback);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (pager2.getCurrentItem() == list.size() - 1) {
                    pager2.setCurrentItem(0);
                } else {
                    pager2.setCurrentItem(pager2.getCurrentItem() + 1);
                }
                mHandler.postDelayed(this, 2000); // Lặp lại sau 2 giây
            }
        };

        mHandler.postDelayed(runnable, 2000); // Chạy lần đầu sau 2 giây

    }
    @Override
    public void nextActivity(Context context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }
    @Override
    public boolean handleMessage(@NonNull Message message) {
        return true;
    }
}
