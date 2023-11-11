package com.example.shopclothes.view.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterCart;
import com.example.shopclothes.databinding.ActivityCartBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.order.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {
    private ActivityCartBinding mBinding;
    private CartContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        onClick();
        mPresenter = new CartPresenter(this);
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
        Collections.reverse(cartList);
        AdapterCart adapterCart = new AdapterCart(cartList, this, new CartPresenter(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvCart.setLayoutManager(layoutManager);
        mBinding.rcvCart.setAdapter(adapterCart);

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void itemCartClick(double sumPrice, boolean check, boolean checkItem) {
        double price = FormatUtils.parseCurrency(mBinding.tvPriceOderCart.getText().toString());
        Log.e("TAG", "itemCartClick: " + price );
        // nếu item đc chọn
        if (checkItem){
            // lấy tổng giá
            int number = Integer.parseInt(mBinding.tvNumberOderCart.getText().toString().substring(1, mBinding.tvNumberOderCart.getText().toString().length()-1));
            if (check){
                // cộng
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price + sumPrice));
                mBinding.tvNumberOderCart.setText("(" + (number + 1) + ")");
            }else {
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price - sumPrice));
                mBinding.tvNumberOderCart.setText("(" + (number - 1) + ")");
            }
        }else {
            if (check){
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price + sumPrice));
            }else {
                mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price - sumPrice));
            }
        }
    }
    @Override
    public void listCartClick(List<Cart> cartList) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putParcelableArrayListExtra("listCart", (ArrayList<? extends Parcelable>) cartList);
        intent.putExtra("sumPrice", mBinding.tvPriceOderCart.getText().toString());
        mBinding.btnOtherCart.setOnClickListener(view -> {
            startActivity(intent);
        });
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readListCartByIdUser(user.getUid());
        mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(0));
        mBinding.tvNumberOderCart.setText("(" + 0 + ")");
        selectedItemsCount(0);
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