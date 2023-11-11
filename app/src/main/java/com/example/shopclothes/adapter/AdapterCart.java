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
import com.example.shopclothes.view.activity.cart.CartContract;
import java.util.ArrayList;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private List<Cart> mList;
    private  List<Cart> mListNew;
    private int selectedItemsCount = 0;
    private final CartContract.View mView;
    private final CartContract.Presenter mPresenter;
    @SuppressLint("NotifyDataSetChanged")
    public AdapterCart(List<Cart> mList, CartContract.View view, CartContract.Presenter presenter) {
        this.mList = mList;
        this.mView = view;
        this.mPresenter = presenter;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Cart> mList) {
        this.mList = mList;
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
        onClickItem(holder.mBinding, cart);
        checkBox(holder.mBinding, cart);
        deleteCart(holder.mBinding, cart);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void deleteCart(ItemCartBinding mBinding, Cart cart){
       mBinding.tvClearProductCart.setOnClickListener(view -> {
           mPresenter.deleteCart(cart.getId());
           mList.remove(cart);
           notifyDataSetChanged();
       });
    }
    public void onClickItem(ItemCartBinding mBinding, Cart cart) {
        mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
        if (Integer.parseInt(mBinding.tvQuantityCart.getText().toString()) == 1){
           mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
        }else {
            mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity);
        }
        mBinding.btnAddQuantityCart.setOnClickListener(view -> itemClickAdd(mBinding, cart));
        mBinding.btnMinusQuantityCart.setOnClickListener(view -> itemClickMinus(mBinding, cart));

    }

    public void itemClickAdd(ItemCartBinding mBinding, Cart cart){
        int quantity = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());
        if (quantity >= 200){
            mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
        }else {
            if(quantity == 200 -1){
                mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
            }
            mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity);
            mBinding.tvQuantityCart.setText(String.valueOf(quantity + 1));
            mPresenter.updateCart(cart.getId(), quantity + 1);
            if (mBinding.checkBoxCart.isChecked()){
                mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100)), true, false);
            }
        }
    }

    public void itemClickMinus(ItemCartBinding mBinding, Cart cart){
        int quantity = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());
        if (quantity <= 1){
            mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
        }else {
            if (quantity == 2){
                mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
            }
            mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
            mBinding.tvQuantityCart.setText(String.valueOf(quantity - 1));
            mPresenter.updateCart(cart.getId(), quantity - 1 );
            if (mBinding.checkBoxCart.isChecked()){
                mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100)), false, false);
            }
        }
    }


    public void checkBox(ItemCartBinding mBinding, Cart cart){
        mListNew = new ArrayList<>();
       mBinding.checkBoxCart.setOnClickListener(view -> {
           int quantity = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());
           if (mBinding.checkBoxCart.isChecked()){
               mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))  * quantity, true, true);
               mListNew.add(cart);
               mView.listCartClick(mListNew);
               selectedItemsCount++;
           }else {
               mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))  * quantity, false, true);
               mListNew.remove(cart);
               mView.listCartClick(mListNew);
               selectedItemsCount--;
           }
           mView.selectedItemsCount(selectedItemsCount);
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
            mBinding.tvPriceCart.setText(FormatUtils.formatCurrency((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))));
            mBinding.tvPriceSalesCart.setPaintFlags(mBinding.tvPriceSalesCart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.tvPriceSalesCart.setText(FormatUtils.formatCurrency(cart.getPrice()));
            mBinding.tvQuantityCart.setText(String.valueOf(cart.getQuantity()));
        }
    }
}
