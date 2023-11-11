package com.example.shopclothes.view.activity.billDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterBillDetail;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityBillDetailBinding;
import com.example.shopclothes.model.BillDetail;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class BillDetailActivity extends AppCompatActivity implements BillDetailContract.View {
    private ActivityBillDetailBinding mBinding;
    private BillDetailContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBillDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new BillDetailPresenter(this);
        initPresenter();
        onClick();
    }

    @Override
    public void onClick() {
        mBinding.ivBackBillDetail.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void initPresenter() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        mPresenter.readListBillDetail(id);
    }

    @Override
    public void onListBillDetail(List<BillDetail> list) {
        AdapterBillDetail adapter = new AdapterBillDetail(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvBillDetail.setLayoutManager(layoutManager);
        mBinding.rcvBillDetail.setAdapter(adapter);
        setValuesBillDetail(list.get(0));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setValuesBillDetail(BillDetail billDetail) {
        mBinding.tvNoteBillDetail.setText(billDetail.getNote());
        mBinding.tvSumProduct.setText("Tổng số tiền ("+billDetail.getQuantityBill()+" sản phẩm):");
        mBinding.tvTotalPriceBillDetail.setText(FormatUtils.formatCurrency(billDetail.getIntoMoney()));
        // radio
        mBinding.radioOfBillDetail.setEnabled(false);
        mBinding.radioOnBillDetail.setEnabled(false);
        if (AppConstants.PAYMENT.equals(billDetail.getPayments())){
            mBinding.radioOfBillDetail.setChecked(true);
        }else {
            mBinding.radioOnBillDetail.setChecked(true);
        }
        // discount
        if (billDetail.getDiscountCode() != null){
            mBinding.etCodeBillDetail.setText(billDetail.getDiscountCode());
            mBinding.tvPriceSalesProductBillDetail.setText(FormatUtils.formatCurrency(billDetail.getIntoMoney() * billDetail.getDiscount() / 100));
            mBinding.tvTotalPriceBillDetailFinal.setText(FormatUtils.formatCurrency(billDetail.getIntoMoney() - (billDetail.getIntoMoney() * billDetail.getDiscount() / 100)));
        }else {
            mBinding.tvTotalPriceBillDetailFinal.setText(FormatUtils.formatCurrency(billDetail.getIntoMoney()));
            mBinding.etCodeBillDetail.setText(AppConstants.NO_DISCOUNT);
        }
        mBinding.tvStatusBillDetail.setText(billDetail.getDeliveryStatus());
        mBinding.tvTotalBillDetailProduct.setText(FormatUtils.formatCurrency(billDetail.getIntoMoney()));
        //address
        mBinding.tvNameBillDetail.setText(billDetail.getNameUser());
        mBinding.tvPhoneBillDetail.setText(AppConstants.PHONE + billDetail.getPhoneNumber());
        mBinding.tvEmailBillDetail.setText(billDetail.getEmail());
        mBinding.tvAddressBillDetail.setText(billDetail.getAddress());

    }
}