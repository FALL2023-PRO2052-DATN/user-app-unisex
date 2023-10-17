package com.example.shopclothes.view.activity.product.productOutstanding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityProductOutstandingBinding;
import com.example.shopclothes.model.Product;

import java.util.List;

public class ProductOutstandingActivity extends AppCompatActivity implements ProductOutstandingContract.View {
    private ActivityProductOutstandingBinding mBinding;
    private ProductOutstandingContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductOutstandingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new ProductOutstandingPresenter(this);
        mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
        mPresenter.getListProductOutstanding();
        onClick();
    }
    private void onClick() {
        mBinding.btnBackProductOutstanding.setOnClickListener(view -> {
            mPresenter.nextActivity(getApplicationContext());
            finish();
        });
    }
    @Override
    public void onList(List<Product> list) {
        mProgressDialog.dismiss();
        AdapterProduct adapter= new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.rcvProductOutstandingActivity.setLayoutManager(layoutManager);
        mBinding.rcvProductOutstandingActivity.setAdapter(adapter);
    }
}