package com.example.shopclothes.view.activity.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.shopclothes.adapter.AdapterCart;
import com.example.shopclothes.databinding.ActivityCartBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {
    private ActivityCartBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        CartContract.Presenter mPresenter = new CartPresenter(this);
        mPresenter.readListCart();
    }

    @Override
    public void onListCart(List<Cart> cartList) {
        AdapterCart adapterCart = new AdapterCart(cartList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvCart.setLayoutManager(layoutManager);
        mBinding.rcvCart.setAdapter(adapterCart);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void itemCartClick(double sumPrice, boolean check) {
        double price = FormatUtils.parseCurrency(mBinding.tvPriceOderCart.getText().toString());
        int number = Integer.parseInt(mBinding.tvNumberOderCart.getText().toString().substring(1, mBinding.tvNumberOderCart.getText().toString().length()-1));
        if (check){
            mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price + sumPrice));
            mBinding.tvNumberOderCart.setText("(" + (number + 1) + ")");
        }else {
            mBinding.tvPriceOderCart.setText(FormatUtils.formatCurrency(price - sumPrice));
            mBinding.tvNumberOderCart.setText("(" + (number - 1) + ")");
        }
    }
}