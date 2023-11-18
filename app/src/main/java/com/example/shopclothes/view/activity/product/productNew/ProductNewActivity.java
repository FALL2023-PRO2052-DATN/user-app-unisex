package com.example.shopclothes.view.activity.product.productNew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityProductNewBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.utils.UIUtils;

import java.util.List;

public class ProductNewActivity extends AppCompatActivity implements ProductNewContract.View {
    private ActivityProductNewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductNewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        UIUtils.openLayout(mBinding.ivLoadingProductNewActivity3, mBinding.layoutProductNewActivity, false, this);
        ProductNewContract.Presenter mPresenter = new ProductNewPresenter(this);
        mPresenter.getListProductNew();
        onClick();
    }

    private void onClick() {
        mBinding.btnBackProductNew.setOnClickListener(view -> onBackPressed());
    }


    @Override
    public void onList(List<Product> list) {
        AdapterProduct adapter= new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.rcvProductNewActivity.setLayoutManager(layoutManager);
        mBinding.rcvProductNewActivity.setAdapter(adapter);

        UIUtils.openLayout(mBinding.ivLoadingProductNewActivity3, mBinding.layoutProductNewActivity, true, this);

    }
}