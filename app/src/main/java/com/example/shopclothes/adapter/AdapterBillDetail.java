package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemOtherBinding;
import com.example.shopclothes.model.BillDetail;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class AdapterBillDetail extends RecyclerView.Adapter<AdapterBillDetail.ViewHolder> {
    private final List<BillDetail> mList;

    public AdapterBillDetail(List<BillDetail> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOtherBinding mBinding = ItemOtherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillDetail billDetail = mList.get(position);
        holder.bind(billDetail);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOtherBinding mBinding;
        public ViewHolder(ItemOtherBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(BillDetail billDetail){
            Glide.with(mBinding.getRoot()).load(billDetail.getImageProduct()).into(mBinding.ivProductCartOrder);
            mBinding.tvNameProductCartOrder.setText(billDetail.getNameProduct());
            mBinding.tvSizeCartOrder.setText(AppConstants.SIZE + billDetail.getSizeProduct());
            mBinding.tvPriceCartOrder.setText(FormatUtils.formatCurrency((billDetail.getPriceProduct() - (billDetail.getPriceProduct() * billDetail.getDiscount() / 100)) / billDetail.getQuantityProduct()));
            mBinding.tvQuantityOrder.setText(AppConstants.X + billDetail.getQuantityProduct());
        }
    }
}
