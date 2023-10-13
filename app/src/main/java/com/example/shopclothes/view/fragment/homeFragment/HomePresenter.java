package com.example.shopclothes.view.fragment.homeFragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.model.Banner;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseBanner;
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
                    Log.d("L", response.body().getBanner().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBanner> call, @NonNull Throwable t) {
                Log.d("ER", t.toString());
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
    public void nextActivity(Context context) {

    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        return true;
    }
}
