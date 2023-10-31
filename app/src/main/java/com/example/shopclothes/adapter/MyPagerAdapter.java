package com.example.shopclothes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopclothes.view.fragment.billFragment.chlidBill.CanceledFragment;
import com.example.shopclothes.view.fragment.billFragment.chlidBill.DeliveredFragment;
import com.example.shopclothes.view.fragment.billFragment.chlidBill.DeliveringFragment;
import com.example.shopclothes.view.fragment.billFragment.chlidBill.WaitConfirmFragment;
import com.example.shopclothes.view.fragment.billFragment.chlidBill.WaitPickupFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){

            case 1 : return  new WaitPickupFragment();
            case 2 : return  new DeliveringFragment();
            case 3 : return  new DeliveredFragment();
            case 4 : return  new CanceledFragment();
            default:  return new WaitConfirmFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
