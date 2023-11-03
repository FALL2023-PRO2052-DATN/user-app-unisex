package com.example.shopclothes.view.activity.address.updateAddress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityUpdateAddressBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.utils.ValidateUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class UpdateAddressActivity extends AppCompatActivity implements UpdateAddressContract.View {
    private ActivityUpdateAddressBinding mbinding;
    private UpdateAddressContract.Presenter mPresenter;
    private ProgressDialog progressDialog;

    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivityUpdateAddressBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());
        mPresenter = new UpdateAddressPresenter(this);
        updateUI();
        setListener();
    }
    public void setListener(){
        mbinding.btnBack.setOnClickListener(view -> onBackPressed());
        mbinding.btnUpdateAddress.setOnClickListener(view -> startUpdateAddress());
    }
    public void startUpdateAddress(){
        String name = mbinding.etNameUpdateAddress.getText().toString();
        String email = mbinding.etEmailUpdateAddress.getText().toString();
        String phone = mbinding.etPhoneUpdateAddress.getText().toString();
        String textaddress = mbinding.etInforUpdateAddress.getText().toString();
        int statusDefault = mbinding.switchDefault.isChecked() ? 1 : 0 ;

        if (ValidateUtils.isDataInputEmpty(name, email, phone, textaddress)){
            showSanckbar(AppConstants.ENTER_COMPLETE_INFORMATION);
        }else {
            progressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            int idDefault = getIntent().getIntExtra("idDefault", 0);
            mPresenter.updateAddress(address.getId(), name, email, phone, textaddress, statusDefault, idDefault);
        }
    }

    private void showSanckbar(String message) {
        UIUtils.showMessage(mbinding.getRoot(), message);
    }

    @Override
    public void onMessege(String message) {
        progressDialog.dismiss();
        UIUtils.showMessage(mbinding.getRoot(), message);
    }


    public void updateUI(){
        address = (Address) getIntent().getSerializableExtra("address");
        if (address != null){
            mbinding.etNameUpdateAddress.setText(address.getName());
            mbinding.etEmailUpdateAddress.setText(address.getEmail());
            mbinding.etPhoneUpdateAddress.setText(address.getPhone());
            mbinding.etInforUpdateAddress.setText(address.getAddress());
            mbinding.switchDefault.setChecked(address.getDefaultStatus() == 1);
        }
    }
}