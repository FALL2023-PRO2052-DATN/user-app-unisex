package com.example.shopclothes.view.activity.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.shopclothes.databinding.ActivityLoginBinding;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.forgotPassword.ForgotPasswordActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    ActivityLoginBinding mActivityLoginBinding;
    LoginContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginBinding.getRoot());
        mPresenter = new LoginPresenter(this);
        onClick();
    }

    public void onClick() {
        mActivityLoginBinding.btnLogin.setOnClickListener(view -> login());
        mActivityLoginBinding.tvForgotPassword.setOnClickListener(view -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }
    public void login() {
        String email = mActivityLoginBinding.etEmailLogin.getText().toString();
        String password = mActivityLoginBinding.etPasswordLogin.getText().toString();
        mPresenter.doLogin(email,password);
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mActivityLoginBinding.getRoot(), message);
    }
}