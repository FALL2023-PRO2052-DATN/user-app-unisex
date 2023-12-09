package com.example.shopclothes.view.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterCart;
import com.example.shopclothes.databinding.ActivityCartBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.order.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {
    private ActivityCartBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvShowCartActivity.setVisibility(View.GONE);
        UIUtils.openLayout(mBinding.ivLoadingCartActivity, mBinding.layoutCartActivity, false, this);
        onClick();
        CartContract.Presenter mPresenter = new CartPresenter(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readListCartByIdUser(user.getUid());
    }

    @Override
    public void onClick() {
        mBinding.btnOtherCart.setEnabled(false);
        mBinding.btnOtherCart.setBackgroundColor(ContextCompat.getColor(this, R.color.linear));
        mBinding.btnBackCart.setOnClickListener(view -> onBackPressed());
    }
    @Override
    public void onListCartByIdUser(List<Cart> cartList) {
        if (cartList.size() == 0) {
            mBinding.tvShowCartActivity.setVisibility(View.VISIBLE);
        }else {
            mBinding.tvShowCartActivity.setVisibility(View.GONE);
        }
        Collections.reverse(cartList);
        AdapterCart adapterCart = new AdapterCart(cartList, this, new CartPresenter(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvCart.setLayoutManager(layoutManager);
        mBinding.rcvCart.setAdapter(adapterCart);

        UIUtils.openLayout(mBinding.ivLoadingCartActivity, mBinding.layoutCartActivity, true, this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void itemCartClick(double sumPrice, boolean check, boolean checkItem) {
        /*
          check = true -> thêm giá tiền, false -> xóa giá tiền
          check item = true -> bấm vào checkbox , false -> bấm vào button add, minus
         */
        double price = FormatUtils.parseCurrency(mBinding.tvPriceOderCart.getText().toString());
        if (checkItem){
            int number = Integer.parseInt(mBinding.tvNumberOderCart.getText().toString().substring(1, mBinding.tvNumberOderCart.getText().toString().length()-1));
            if (check){
                // cộng vào giá tiền tổng
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price + sumPrice));
                mBinding.tvNumberOderCart.setText("(" + (number + 1) + ")");
            }else {
                // trừ vào giá tiền tổng
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price - sumPrice));
                mBinding.tvNumberOderCart.setText("(" + (number - 1) + ")");
            }
        } else {
            if (check){
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price + sumPrice));
            }else {
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price - sumPrice));
            }
        }
    }
    @Override
    public void listCartClick(List<Integer> cartList) {
        mBinding.btnOtherCart.setOnClickListener(view -> {
            String jsonListIdCart = new Gson().toJson(cartList);
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra("listCart", jsonListIdCart);
            intent.putExtra("sumPrice", mBinding.tvPriceOderCart.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDeleteCartShowText(List<Cart> cartList) {
        if (cartList.size() == 0) {
            mBinding.tvShowCartActivity.setVisibility(View.VISIBLE);
        }else {
            mBinding.tvShowCartActivity.setVisibility(View.GONE);
        }
    }

    @Override
    public void selectedItemsCount(int selectedItemsCount) {
        if (selectedItemsCount == 0){
            mBinding.btnOtherCart.setEnabled(false);
            mBinding.btnOtherCart.setBackgroundColor(ContextCompat.getColor(this, R.color.linear));
        }else {
            mBinding.btnOtherCart.setEnabled(true);
            mBinding.btnOtherCart.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
        }
    }

}