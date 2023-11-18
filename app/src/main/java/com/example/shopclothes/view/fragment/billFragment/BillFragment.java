package com.example.shopclothes.view.fragment.billFragment;

import android.content.Intent;
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
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.cart.CartActivity;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BillFragment extends Fragment implements BillContract.View.ViewParents {

    private FragmentBillBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BillContract.Presenter mPresenter = new BillPresenter(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readListCartByIdUser(user.getUid());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentBillBinding.inflate(inflater, container, false);
        UIUtils.openLayout(mBinding.ivLoadingBillFragment, mBinding.layoutBillFragment, false, getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // gán tab
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

        onClick();
    }

    private void onClick() {
        mBinding.btnBagBill.setOnClickListener(view -> startActivity(new Intent(getContext(), CartActivity.class)));
    }

    @Override
    public void onListCartByIdUser(List<Cart> cartList) {
        mBinding.tvBagBill.setText(String.valueOf(cartList.size()));
        UIUtils.openLayout(mBinding.ivLoadingBillFragment, mBinding.layoutBillFragment, true, getContext());
    }
}