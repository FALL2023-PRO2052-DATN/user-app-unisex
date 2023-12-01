package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemDiscountBinding;
import com.example.shopclothes.model.Discount;
import com.example.shopclothes.utils.UIUtils;

import java.util.List;

public class AdapterDiscount extends RecyclerView.Adapter<AdapterDiscount.ViewHolder> {
    private final List<Discount> mList;

    public AdapterDiscount(List<Discount> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDiscountBinding mBinding = ItemDiscountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount discount = mList.get(position);
        if (discount == null){
            return;
        }
        holder.bind(discount);
        holder.mBinding.ivCopy.setOnClickListener(view -> UIUtils.copyToClipboard(holder.mBinding.getRoot().getContext(), discount.getCode(), holder.mBinding.getRoot()));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDiscountBinding mBinding;
        public ViewHolder(ItemDiscountBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Discount discount){
            mBinding.tvDiscount.setText(AppConstants.CODE_SALE + discount.getCode());
            mBinding.tvSalesDiscount.setText(AppConstants.CODE_SALE_CONTENT_1 + discount.getPercent() + AppConstants.PERCENT + AppConstants.CODE_SALE_CONTENT_2);
        }
    }
}
