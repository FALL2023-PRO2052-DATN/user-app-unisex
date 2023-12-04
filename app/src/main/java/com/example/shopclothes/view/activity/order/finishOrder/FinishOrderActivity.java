package com.example.shopclothes.view.activity.order.finishOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFinishOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new FinishOrderPresenter(this);

        UIUtils.openLayout(mBinding.ivLoadingFinishOrder, mBinding.layoutFinishOrder, false, this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.getListProductOutstanding();
        mPresenter.getListCartByIdUser(user.getUid());

        senNotification();
        onClick();
    }

    private void senNotification() {
        // dùng PendingIntent để chuyển vào main khi click vào thông báo
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mPresenter.senNotification(AppConstants.NOTIFICATION, AppConstants.NOTIFICATION_CONTENT, pendingIntent, this, getSystemService(Context.NOTIFICATION_SERVICE));
    }
    @Override
    public void onClick() {
        mBinding.btnHomeFinishOrder.setOnClickListener(view -> mPresenter.nextActivity(this, MainActivity.class));
        mBinding.btnBackFinishOrder.setOnClickListener(view -> mPresenter.nextActivity(this, MainActivity.class));
        mBinding.btnBillFinishOrder.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("bill", 2);
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
        AdapterProduct adapter= new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.rcvFinishOrder.setLayoutManager(layoutManager);
        mBinding.rcvFinishOrder.setAdapter(adapter);
        UIUtils.openLayout(mBinding.ivLoadingFinishOrder, mBinding.layoutFinishOrder, true, this);
    }
}