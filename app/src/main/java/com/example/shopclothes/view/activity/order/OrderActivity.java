package com.example.shopclothes.view.activity.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterOrder;
import com.example.shopclothes.databinding.ActivityOrtherBinding;
import com.example.shopclothes.model.Cart;

import java.util.List;

public class OrderActivity extends AppCompatActivity implements OrderContract.View {
    private OrderContract.Presenter mPresenter;
    ActivityOrtherBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrtherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        onListProduct();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListProduct() {
        Intent intent = getIntent();
        List<Cart> cartList = intent.getParcelableArrayListExtra("listCart");
        AdapterOrder adapterOrder = new AdapterOrder(cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewProductOther.setLayoutManager(layoutManager);
        mBinding.recyclerViewProductOther.setAdapter(adapterOrder);
        mBinding.tvSumPriceOrder.setText(String.valueOf(intent.getStringExtra("sumPrice")));
        mBinding.tvCountProductOrder.setText("Tổng số tiền ("+cartList.size()+")");
    }
}