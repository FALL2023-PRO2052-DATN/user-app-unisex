package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemOtherBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {
    private List<Cart> mList;

    public AdapterOrder(List<Cart> mList) {
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
        Cart cart = mList.get(position);
        holder.bind(cart);
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
        public void bind(Cart cart){
            Glide.with(mBinding.getRoot()).load(cart.getImage()).into(mBinding.ivProductCartOrder);
            mBinding.tvNameProductCartOrder.setText(cart.getName());
            mBinding.tvSizeCartOrder.setText("Size - " + cart.getSize());
            mBinding.tvPriceCartOrder.setText(FormatUtils.formatCurrency(cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100)));
            mBinding.tvQuantityOrder.setText("x"+cart.getQuantity());
        }
    }
}
