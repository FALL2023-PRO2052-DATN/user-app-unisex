package com.example.shopclothes.view.fragment.billFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shopclothes.adapter.MyPagerAdapter;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.FragmentBillBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class BillFragment extends Fragment {

    private FragmentBillBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentBillBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(requireActivity());
        mBinding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager, (tab, position) -> {
            switch (position){
                case 0 : tab.setText(AppConstants.DELIVERY_STATUS_WAIT_CONFIRM);
                    break;
                case 1 : tab.setText(AppConstants.DELIVERY_STATUS_WAIT_PICKUP);
                    break;
                case 2 : tab.setText(AppConstants.DELIVERY_STATUS_DELIVERING);
                    break;
                case 3 : tab.setText(AppConstants.DELIVERY_STATUS_DELIVERED);
                    break;
                case 4 : tab.setText(AppConstants.DELIVERY_STATUS_CANCELED);
                    break;
            }
        }).attach();

        // Đăng ký lắng nghe sự kiện truyền dữ liệu từ WaitConfirmFragment
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            if (requestKey.equals("requestKey")) {
                int data = result.getInt("DISMISS"); // Lấy dữ liệu từ Bundle
                mBinding.viewPager.setCurrentItem(data);
            }
        });
    }
}