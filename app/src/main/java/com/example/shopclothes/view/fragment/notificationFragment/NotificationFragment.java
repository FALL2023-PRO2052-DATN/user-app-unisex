package com.example.shopclothes.view.fragment.notificationFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shopclothes.adapter.AdapterDiscount;
import com.example.shopclothes.adapter.AdapterNotification;
import com.example.shopclothes.databinding.FragmentNotificationBinding;
import com.example.shopclothes.model.Discount;
import com.example.shopclothes.model.Notification;
import com.example.shopclothes.utils.SwipeToDeleteCallback;
import com.example.shopclothes.utils.UIUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class NotificationFragment extends Fragment implements NotificationContract.View {
    private FragmentNotificationBinding mBinding;
    private NotificationContract.Presenter mPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        mPresenter = new NotificationPresenter(this);
        UIUtils.openLayout(mBinding.ivLoadingNotificationFragment, mBinding.layoutNotificationFragment, false, getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.readListDiscount();
        mPresenter.readListNotification(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    @Override
    public void onListNotification(List<Notification> notificationList) {
        Collections.reverse(notificationList);
        if (notificationList.size() != 0){
            mBinding.ivEmptyBillNotificationBill.setVisibility(View.GONE);
            mBinding.tvBillNotificationBill.setVisibility(View.GONE);
        }
        AdapterNotification adapterNotification = new AdapterNotification(notificationList, mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rcvNotificationBill.setLayoutManager(layoutManager);
        mBinding.rcvNotificationBill.setAdapter(adapterNotification);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapterNotification));
        itemTouchHelper.attachToRecyclerView(mBinding.rcvNotificationBill);
        UIUtils.openLayout(mBinding.ivLoadingNotificationFragment, mBinding.layoutNotificationFragment, true, getContext());
    }

    @Override
    public void onListDiscount(List<Discount> discountList) {
        if (discountList.size() != 0){
            mBinding.ivEmptyNotificationDiscount.setVisibility(View.GONE);
            mBinding.tvNotificationDiscount.setVisibility(View.GONE);
        }
        AdapterDiscount adapterDiscount = new AdapterDiscount(discountList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rcvNotificationDiscount.setLayoutManager(layoutManager);
        mBinding.rcvNotificationDiscount.setAdapter(adapterDiscount);
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mBinding.getRoot(), message);
    }
}