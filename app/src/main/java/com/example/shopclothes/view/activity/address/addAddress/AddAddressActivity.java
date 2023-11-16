package com.example.shopclothes.view.activity.address.addAddress;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityAddAddressBinding;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.utils.ValidateUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AddAddressActivity extends AppCompatActivity implements AddAddressContract.View {
    private ActivityAddAddressBinding mBinding;
    private AddAddressContract.Presenter mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new AddAddressPresenter(this);
        setListener();
    }

    public void setListener(){
        mBinding.btnBack.setOnClickListener(view -> onBackPressed());
        mBinding.btnSaveAddress.setOnClickListener(view -> startAddAddress());
    }
    public void startAddAddress(){
        String name = mBinding.etFullnameAddress.getText().toString().trim();
        String email = mBinding.etEmailAddress.getText().toString().trim();
        String phone = mBinding.etPhoneAddress.getText().toString().trim();
        String address = mBinding.etInforAddress.getText().toString().trim();
        int isDefault = mBinding.switchDefault.isChecked() ? 1 : 0 ;
        int id = getIntent().getIntExtra("id", 0);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (ValidateUtils.isDataInputEmpty(name, email, phone, address)){
            UIUtils.showMessage(mBinding.getRoot(), AppConstants.ENTER_COMPLETE_INFORMATION);
        }else {
            progressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            assert user != null;
            mPresenter.insertAddress(id, name, email, phone, address, isDefault, user.getUid());
        }

    }

    @Override
    public void onMessage(String message) {
        progressDialog.dismiss();
        UIUtils.showMessage(mBinding.getRoot(), message);
        UIUtils.clearText(mBinding.etFullnameAddress, mBinding.etEmailAddress, mBinding.etPhoneAddress, mBinding.etInforAddress);
        mBinding.switchDefault.setChecked((false));
    }

}