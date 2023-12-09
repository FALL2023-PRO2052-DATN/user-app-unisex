package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemCartBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.view.activity.cart.CartContract;
import java.util.ArrayList;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private List<Cart> mList;
    private final List<Integer> mListNew;
    private int selectedItemsCount = 0; // xem item nào đươc check
    private final CartContract.View mView;
    private final CartContract.Presenter mPresenter;

    public AdapterCart(List<Cart> mList, CartContract.View view, CartContract.Presenter presenter) {
        this.mList = mList;
        this.mView = view;
        this.mPresenter = presenter;
        mListNew = new ArrayList<>();
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

        holder.mBinding.tvClearProductCart.setOnClickListener(view -> deleteCart(cart));
        for (int i = 0 ; i < mListNew.size() ; i ++){
            if (cart.getId() == mListNew.get(i)){
                holder.mBinding.checkBoxCart.setChecked(true);
                holder.mBinding.tvClearProductCart.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void deleteCart(Cart cart){
        mPresenter.deleteCart(cart.getId());
        mList.remove(cart);
        mView.onDeleteCartShowText(mList);
        notifyDataSetChanged();
    }

    public void onClickItem(ItemCartBinding mBinding, Cart cart) {
        mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
        if (Integer.parseInt(mBinding.tvQuantityCart.getText().toString()) == 1){
            // nếu số lượng bằng 1 xét bgt màu xám
           mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
        }else {
            // nếu số lượng > 1 xét bgt màu đen
            mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity);
        }
        // bắt sự thêm và bớt số lượng
        mBinding.btnAddQuantityCart.setOnClickListener(view -> itemClickAdd(mBinding, cart));
        mBinding.btnMinusQuantityCart.setOnClickListener(view -> itemClickMinus(mBinding, cart));

    }

    // thêm soosl lượng
    public void itemClickAdd(ItemCartBinding mBinding, Cart cart){
        int quantity = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());
        if (quantity >= 200){
            mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
        }else {
            if(quantity == 200 -1){
                // nếu số lượng (tốt đa - 1) khi click nó sẽ xét màu xám vì khi click sẽ + 1
                mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity_1);
            }
            mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity); // set bgr khi nhấn vào nút add
            mBinding.tvQuantityCart.setText(String.valueOf(quantity + 1)); // tăng thêm 1
            mPresenter.updateCart(cart.getId(), quantity + 1); // update data lên server

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
                // nếu số lượng bằng 2 khi click sẽ xét màu xám vì khi click sẽ - 1
                mBinding.ivMinusQuantityCart.setImageResource(R.drawable.ic_minus_quatity_1);
            }
            mBinding.ivAddQuantityCart.setImageResource(R.drawable.ic_add_quantity);
            mBinding.tvQuantityCart.setText(String.valueOf(quantity - 1));
            mPresenter.updateCart(cart.getId(), quantity - 1 );// update data lên server

            if (mBinding.checkBoxCart.isChecked()){
                mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100)), false, false);
            }
        }
    }


    public void checkBox(ItemCartBinding mBinding, Cart cart){
       mBinding.checkBoxCart.setOnClickListener(view -> {
           int quantity = Integer.parseInt(mBinding.tvQuantityCart.getText().toString());

           if (mBinding.checkBoxCart.isChecked()){
               mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))  * quantity, true, true);
               mListNew.add(cart.getId()); // add vào list để chuyển qua màn orther
               mView.listCartClick(mListNew); // trả ra list để chuyển
               mBinding.tvClearProductCart.setVisibility(View.GONE);
               selectedItemsCount++;
           }else {
               mView.itemCartClick((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))  * quantity, false, true);
               mListNew.remove(Integer.valueOf(cart.getId())); // xoá khỏi list
               mView.listCartClick(mListNew); // trả ra list để chuyển
               mBinding.tvClearProductCart.setVisibility(View.VISIBLE);
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
            mBinding.tvSizeCart.setText(AppConstants.SIZE + cart.getSize());
            mBinding.tvPriceCart.setText(FormatUtils.formatCurrency((cart.getPrice() - (cart.getPrice() * cart.getDiscount() / 100))));
            mBinding.tvPriceSalesCart.setPaintFlags(mBinding.tvPriceSalesCart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.tvPriceSalesCart.setText(FormatUtils.formatCurrency(cart.getPrice()));
            mBinding.tvQuantityCart.setText(String.valueOf(cart.getQuantity()));
        }
    }
}
