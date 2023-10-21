package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ItemCartBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.view.activity.cart.CartContract;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private final List<Cart> mList;
    private CartContract.View mView;
    @SuppressLint("NotifyDataSetChanged")
    public AdapterCart(List<Cart> mList, CartContract.View view) {
        this.mList = mList;
        this.mView = view;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding mBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = mList.get(position);
        holder.bind(cart);
        onClickItem(holder.mBinding);
        checkBox(holder.mBinding, cart);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public void onClickItem(ItemCartBinding mBinding) {
        mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
        mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);

        mBinding.btnAddQuantityCart.setOnClickListener(view -> {
            int num = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());

            if (num >= 200){
                mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
            }else {
                if(num == 200 -1){
                    mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
                }
                mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity);
                mBinding.tvQuantityCart.setText(String.valueOf(num + 1));
            }

        });

        mBinding.btnMinusQuantityCart.setOnClickListener(view -> {
            int num = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());

            if (num <= 1){
                mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
            }else {
                if (num == 2){
                    mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
                }
                mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
                mBinding.tvQuantityCart.setText(String.valueOf(num - 1));
            }
        });

    }
    public void checkBox(ItemCartBinding mBinding, Cart cart){
    
       mBinding.checkBoxCart.setOnClickListener(view -> {
           double sumPrice = 0;
           if (mBinding.checkBoxCart.isChecked()){
               sumPrice += cart.getPrice() * cart.getQuantity();
               mView.itemCartClick(sumPrice, true);
           }else {
               sumPrice += cart.getPrice() * cart.getQuantity();
               mView.itemCartClick(sumPrice, false);
           }
       });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding mBinding;
        public ViewHolder(ItemCartBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Cart cart){
            Glide.with(mBinding.getRoot()).load(cart.getImage()).into(mBinding.ivProductCart);
            mBinding.tvNameProductCart.setText(cart.getName());
            mBinding.tvSizeCart.setText("Size - " + cart.getSize());
            mBinding.tvPriceCart.setText(FormatUtils.formatCurrency(cart.getPrice()));
            mBinding.tvPriceSalesCart.setPaintFlags(mBinding.tvPriceSalesCart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.tvPriceSalesCart.setText(FormatUtils.formatCurrency(cart.getPrice() * 100 / cart.getDiscount()));
            mBinding.tvQuantityCart.setText(String.valueOf(cart.getQuantity()));
        }
    }
}
