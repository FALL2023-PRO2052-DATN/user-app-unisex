package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemBillBinding;
import com.example.shopclothes.model.Bill;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.view.fragment.billFragment.BillContract;

import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolder>{
    private final List<Bill> mList;
    private final int selectTab;
    private final MyBottomSheetBill myBottomSheetBill;
    private final FragmentManager fragmentManager;
    private final BillContract.View mView;
    @SuppressLint("NotifyDataSetChanged")
    public AdapterBill(List<Bill> mList, int selectTab, MyBottomSheetBill bottomSheet, FragmentManager fragmentManager, BillContract.View view) {
        this.mList = mList;
        this.selectTab = selectTab;
        this.myBottomSheetBill = bottomSheet;
        this.fragmentManager = fragmentManager;
        this.mView = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBillBinding mBinding = ItemBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = mList.get(position);
        holder.bind(bill);
        // ẩn hiện đánh giá, hủy
        switch (selectTab){
            case 0:
                holder.mBinding.btnEvaluateBill.setVisibility(View.GONE);
                break;
            case 1:
            case 2:
            case 4:
                holder.mBinding.btnEvaluateBill.setVisibility(View.GONE);
                holder.mBinding.btnCancelBill.setVisibility(View.GONE);
                break;
            case 3:
                holder.mBinding.btnCancelBill.setVisibility(View.GONE);
                break;
        }

        // mở buttom sheet
        if(fragmentManager != null) {
            holder.mBinding.btnCancelBill.setOnClickListener(view -> {
                myBottomSheetBill.show(fragmentManager, myBottomSheetBill.getTag());
                myBottomSheetBill.setId(bill.getId());
            });
        }

        // xem theo sản phẩm
        holder.mBinding.tvSeeAllDetailBill.setOnClickListener(view -> mView.nextScreenDetailBill(bill.getId()));

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemBillBinding mBinding;
        public ViewHolder(ItemBillBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind (Bill bill){
            mBinding.tvIdBill.setText(bill.getId());
            mBinding.tvStatusProductBill.setText(bill.getDeliveryStatus());
            Glide.with(mBinding.getRoot()).load(bill.getImageProduct()).into(mBinding.ivProductBill);
            mBinding.tvNameProductBill.setText(bill.getNameProduct());
            mBinding.tvSizeProductBill.setText(AppConstants.SIZE + bill.getSize());
            mBinding.tvPriceProductBill.setText(FormatUtils.formatCurrency(bill.getPrice() / bill.getQuantity()));
            mBinding.tvQuantityBill.setText(AppConstants.X + bill.getQuantity());
            mBinding.tvTotalPriceProductBill.setText(FormatUtils.formatCurrency(bill.getPriceBill()));
            mBinding.tvQuantityCartBill.setText(bill.getQuantityBill() + AppConstants.PRODUCT);
        }
    }
}
