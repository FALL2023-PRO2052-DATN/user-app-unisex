package com.example.shopclothes.view.activity.product.productNew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityProductNewBinding;
import com.example.shopclothes.model.Product;

import java.util.List;

public class ProductNewActivity extends AppCompatActivity implements ProductNewContract.View {
    ActivityProductNewBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductNewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    @Override
    public void onList(List<Product> list) {

    }
}