package com.example.shopclothes.view.activity.account.login;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityLoginBinding;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.account.forgotPassword.ForgotPasswordActivity;
import com.example.shopclothes.view.activity.account.register.RegisterActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private ActivityLoginBinding mBinding;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new LoginPresenter(this);
        onClick();
    }

    public void onClick() {
        mBinding.btnLogin.setOnClickListener(view -> login());
        mBinding.tvForgotPassword.setOnClickListener(view -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
        mBinding.tvRegister.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
    }
    public void login() {
        String email = mBinding.etEmailLogin.getText().toString();
        String password = mBinding.etPasswordLogin.getText().toString();
        String LOGIN = "Đăng nhập";
        mProgressDialog = ProgressDialog.show(this, LOGIN, AppConstants.LOADING);
        mPresenter.doLogin(email,password);
    }

    @Override
    public void onMessage(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailLogin, mBinding.etPasswordLogin);
        UIUtils.showMessage(mBinding.getRoot(), message);
        mPresenter.nextActivity(this);
    }
}