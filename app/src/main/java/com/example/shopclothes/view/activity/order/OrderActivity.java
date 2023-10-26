package com.example.shopclothes.view.activity.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.example.shopclothes.adapter.AdapterOrder;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityOrtherBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Discount;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class OrderActivity extends AppCompatActivity implements OrderContract.View {
    private OrderContract.Presenter mPresenter;
    private ActivityOrtherBinding mBinding;
    private Intent intent;
    private ProgressDialog mProgressDialog;
    private Discount mDiscount;
    private Address mAddress;
    private List<Cart> mCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrtherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new OrderPresenter(this);
        onListProduct();
        initPresenter();
        onClick();
    }

    @Override
    public void onClick() {
        mBinding.ivNextOther.setOnClickListener(view -> mPresenter.nextActivity(this));
        mBinding.ivBackOrder.setOnClickListener(view -> onBackPressed());
        mBinding.btnApply.setOnClickListener(view ->{
            mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            mPresenter.readDiscountById(mBinding.etApply.getText().toString().trim());
        } );
        mBinding.btnOrder.setOnClickListener(view -> insertOrder());
    }

    @Override
    public void initPresenter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readAddress(user.getUid());}

    @SuppressLint("SetTextI18n")
    @Override
    public void onListProduct() {
        intent = getIntent();
        List<Cart> cartList = intent.getParcelableArrayListExtra("listCart");
        AdapterOrder adapterOrder = new AdapterOrder(cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewProductOther.setLayoutManager(layoutManager);
        mBinding.recyclerViewProductOther.setAdapter(adapterOrder);
        setValue(intent, cartList);
        mCartList = cartList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAddress(Address address) {
        mBinding.tvNameOther.setText(address.getName());
        mBinding.tvPhoneOther.setText( "(84+) " +address.getPhone());
        mBinding.tvEmailOther.setText(address.getEmail());
        mBinding.tvAddressOther.setText(address.getAddress());
        mAddress = address;
    }

    @Override
    public void onDiscount(Discount discount) {
        mProgressDialog.dismiss();
        double price = FormatUtils.parseCurrency(intent.getStringExtra("sumPrice"));
        double discountPrice = price * discount.getPercent() / 100;
        mBinding.tvDiscountOrder.setText(FormatUtils.formatCurrency(discountPrice));
        mBinding.tvSumPriceAllOrder.setText(FormatUtils.formatCurrency(price - discountPrice));
        mBinding.tvPriceOrder.setText(mBinding.tvSumPriceAllOrder.getText().toString());
        mDiscount = discount;
    }

    @Override
    public void onInsertOrder(String idOrder) {
        for (int i = 0 ; i < mCartList.size(); i++){
            mPresenter.insertOrderDetail(mCartList.get(i).getSize(), mCartList.get(i).getQuantity(),
                    mCartList.get(i).getPrice() - (mCartList.get(i).getPrice() * mCartList.get(i).getDiscount() / 100) ,  idOrder, mCartList.get(i).getIdProduct());
        }
        UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_SUCCESS);
        mProgressDialog.dismiss();
    }

    @Override
    public void insertOrder() {
        mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
        String note = mBinding.etNoteOther.getText().toString();
        String payments;
        String peacefulState;
        String idDiscount = null;
        if (mBinding.radioOf.isChecked()){
             payments = mBinding.tvOf.getText().toString();
             peacefulState = AppConstants.PEACEFUL_STATE_NOT;
        }else {
             payments = mBinding.tvOn.getText().toString();
             peacefulState = AppConstants.PEACEFUL_STATE_OK;
        }
        String deliveryStatus = AppConstants.DELIVERY_STATUS;
        String reasonCancel = "";
        double price = FormatUtils.parseCurrency(mBinding.tvPriceOrder.getText().toString());
        if (mDiscount != null){
            idDiscount = String.valueOf(mDiscount.getId());
        }
        int idAddress = mAddress.getId();
        mPresenter.insertOrder(FormatUtils.formatID(), note, payments, deliveryStatus, reasonCancel ,price, idDiscount, idAddress  ,peacefulState);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void setValue(Intent intent, List<Cart> cartList) {
        mBinding.tvSumPriceOrder.setText(String.valueOf(intent.getStringExtra("sumPrice")));
        mBinding.tvCountProductOrder.setText("Tổng số tiền ("+cartList.size()+" sản phẩm):");
        mBinding.tvPriceProductOrder.setText(intent.getStringExtra("sumPrice"));
        mBinding.tvSumPriceAllOrder.setText(intent.getStringExtra("sumPrice"));
        mBinding.tvPriceOrder.setText(intent.getStringExtra("sumPrice"));
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    @Override
    public void onInsertDetailOrder(boolean check) {
       if (!check){
           UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_FAILURE);
       }
    }

}