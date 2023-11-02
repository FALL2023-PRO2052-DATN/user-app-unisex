package com.example.shopclothes.view.activity.address.addAddress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityAddAddressBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.address.address.AddressContract;
import com.example.shopclothes.view.activity.address.address.AddressPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AddAddressActivity extends AppCompatActivity implements AddAddressContract.View {
    private ActivityAddAddressBinding mbinding;
    private AddAddressContract.Presenter mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());
        mPresenter = new AddAddressPresenter(this);
        setListener();
    }

    public void setListener(){
        mbinding.btnBack.setOnClickListener(view -> onBackPressed());
        mbinding.btnSaveAddress.setOnClickListener(view -> startAddAddress());
    }
    public void startAddAddress(){
        String name = mbinding.etFullnameAddress.getText().toString().trim();
        String email = mbinding.etEmailAddress.getText().toString().trim();
        String phone = mbinding.etPhoneAddress.getText().toString().trim();
        String textaddress = mbinding.etInforAddress.getText().toString().trim();
        int isDefault = mbinding.switchDefault.isChecked() ? 1 : 0 ;
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (ValidateUtils.isDataInputEmpty(name, email, phone, textaddress)){
            showSanckbar(AppConstants.ENTER_COMPLETE_INFORMATION);
        }else {
            progressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            mPresenter.insertAddress(id, name, email, phone, textaddress, isDefault, user.getUid());
        }

    }

    private void showSanckbar(String message) {
        UIUtils.showMessage(mbinding.getRoot(), message);
    }

    @Override
    public void onMessege(String message) {
        progressDialog.dismiss();
        UIUtils.showMessage(mbinding.getRoot(), message);
        if (message.equals(AppConstants.ON_SUCCESS)){
            UIUtils.clearText(mbinding.etFullnameAddress, mbinding.etEmailAddress, mbinding.etPhoneAddress, mbinding.etInforAddress);
            mbinding.switchDefault.setChecked((false));
        }
    }

}