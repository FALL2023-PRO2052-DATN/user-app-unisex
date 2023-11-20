package com.example.shopclothes.view.activity.order.finishOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityFinishOrderBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.MainActivity;
import com.example.shopclothes.view.activity.cart.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FinishOrderActivity extends AppCompatActivity implements FinishOrderContract.View {
    private ActivityFinishOrderBinding mBinding;
    private FinishOrderContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFinishOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new FinishOrderPresenter(this);
        UIUtils.openLayout(mBinding.ivLoadingFinishOrder, mBinding.layoutFinishOrder, false, this);
        mPresenter.getListProductOutstanding();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.getListCartByIdUser(user.getUid());
        onClick();
    }

    @Override
    public void onClick() {
        mBinding.btnHomeFinishOrder.setOnClickListener(view -> mPresenter.nextActivity(this, MainActivity.class));
        mBinding.btnBackFinishOrder.setOnClickListener(view -> mPresenter.nextActivity(this, MainActivity.class));
        mBinding.btnBillFinishOrder.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("BILL", 2);
            startActivity(intent);

        });
        mBinding.btnCartFinishOrder.setOnClickListener(view -> mPresenter.nextActivity(this, CartActivity.class));
    }

    @Override
    public void onListCartByIdUser(List<Cart> list) {
        mBinding.tvCartFinishOrder.setText(String.valueOf(list.size()));
    }

    @Override
    public void onListProductOutstanding(List<Product> list) {
        mProgressDialog.dismiss();
        AdapterProduct adapter= new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.rcvFinishOrder.setLayoutManager(layoutManager);
        mBinding.rcvFinishOrder.setAdapter(adapter);
        UIUtils.openLayout(mBinding.ivLoadingFinishOrder, mBinding.layoutFinishOrder, true, this);
    }
}