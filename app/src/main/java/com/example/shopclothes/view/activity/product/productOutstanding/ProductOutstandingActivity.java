package com.example.shopclothes.view.activity.product.productOutstanding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityProductOutstandingBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.utils.UIUtils;

import java.util.List;

public class ProductOutstandingActivity extends AppCompatActivity implements ProductOutstandingContract.View {
    private ActivityProductOutstandingBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductOutstandingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        UIUtils.openLayout(mBinding.ivLoadingProductOutActivity3, mBinding.layoutProductOutActivity, false, this);
        ProductOutstandingContract.Presenter mPresenter = new ProductOutstandingPresenter(this);
        mPresenter.getListProductOutstanding();
        onClick();
    }
    private void onClick() {
        mBinding.btnBackProductOutstanding.setOnClickListener(view -> onBackPressed());
    }
    @Override
    public void onList(List<Product> list) {
        AdapterProduct adapter= new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.rcvProductOutstandingActivity.setLayoutManager(layoutManager);
        mBinding.rcvProductOutstandingActivity.setAdapter(adapter);

        UIUtils.openLayout(mBinding.ivLoadingProductOutActivity3, mBinding.layoutProductOutActivity, true, this);
    }
}