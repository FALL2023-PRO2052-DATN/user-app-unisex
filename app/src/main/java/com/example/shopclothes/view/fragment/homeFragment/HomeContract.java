package com.example.shopclothes.view.fragment.homeFragment;

import android.content.Context;

import androidx.viewpager2.widget.ViewPager2;

import com.example.shopclothes.model.Banner;

import java.util.List;

public class HomeContract {
    interface View {
        void onListBanner(List<Banner> list);
    }
    interface Presenter {
        void getListBanner();
        void autoNextBanner(ViewPager2 pager2,  List<Banner> list);
        void nextActivity(Context context);
    }
}
