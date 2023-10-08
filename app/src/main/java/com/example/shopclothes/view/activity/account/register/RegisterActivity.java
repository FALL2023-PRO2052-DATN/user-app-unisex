package com.example.shopclothes.view.activity.account.register;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityRegisterBinding;
import com.example.shopclothes.utils.UIUtils;


public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private ActivityRegisterBinding mBinding;
    private RegisterContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new RegisterPresenter(this);
        onClick();
    }

    public void onClick() {
        mBinding.btnRegister.setOnClickListener(view -> register());
        mBinding.btnBackRegister.setOnClickListener(view -> mPresenter.nextActivity(this));
        mBinding.tvLogin.setOnClickListener(view -> mPresenter.nextActivity(this));
    }

    public void register() {
        String email = mBinding.etEmailRegister.getText().toString();
        String password = mBinding.etPasswordRegister.getText().toString();
        String confirmPassword = mBinding.etPasswordAgainRegister.getText().toString();
        String REGISTER = "Đăng ký tài khoản";
        mProgressDialog = ProgressDialog.show(this, REGISTER, AppConstants.LOADING);
        mPresenter.doRegister(email, password, confirmPassword);
    }

    @Override
    public void onMessage(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailRegister, mBinding.etPasswordRegister, mBinding.etPasswordAgainRegister);
        UIUtils.showMessage(mBinding.getRoot(), message);
    }
}