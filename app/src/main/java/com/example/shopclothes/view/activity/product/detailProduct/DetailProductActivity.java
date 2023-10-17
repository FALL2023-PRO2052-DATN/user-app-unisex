package com.example.shopclothes.view.activity.product.detailProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityDetailProductBinding;

public class DetailProductActivity extends AppCompatActivity {
    ActivityDetailProductBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


    }
}