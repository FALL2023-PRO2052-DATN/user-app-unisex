package com.example.shopclothes.view.activity.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityRegisterBinding;
import com.example.shopclothes.utils.UIView;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    ActivityRegisterBinding mActivityRegisterBinding;
    RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mActivityRegisterBinding.getRoot());
        mPresenter = new RegisterPresenter(this);
        onClick();
    }

    public void onClick() {
        mActivityRegisterBinding.btnRegister.setOnClickListener(view -> register());
    }

    public void register() {
        String email = mActivityRegisterBinding.etEmailRegister.getText().toString();
        String password = mActivityRegisterBinding.etPasswordRegister.getText().toString();
        String confirmPassword = mActivityRegisterBinding.etPasswordAgainRegister.getText().toString();
        mPresenter.doRegister(email, password, confirmPassword);
    }

    @Override
    public void onMessage(String message) {
        UIView.showMessage(mActivityRegisterBinding.getRoot(), message);
    }
}