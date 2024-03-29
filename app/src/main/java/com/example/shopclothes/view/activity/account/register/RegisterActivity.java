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
        mBinding.ivHidingPass.setOnClickListener(view -> onPasswordToggleImageClick());
        mBinding.ivHidingPassAgain.setOnClickListener(view -> onPasswordToggleImageClickAgain());
        mBinding.btnBackRegister.setOnClickListener(view -> {
            mPresenter.nextActivity(this);
            finish();
        });
        mBinding.tvLogin.setOnClickListener(view -> {
            mPresenter.nextActivity(this);
            finish();
        });
    }

    public void register() {
        String email = mBinding.etEmailRegister.getText().toString();
        String password = mBinding.etPasswordRegister.getText().toString();
        String confirmPassword = mBinding.etPasswordAgainRegister.getText().toString();
        mProgressDialog = ProgressDialog.show(this, AppConstants.REGISTER, AppConstants.LOADING);
        mPresenter.doRegister(email, password, confirmPassword);
    }



    public void onPasswordToggleImageClick(){
        UIUtils.togglePasswordVisibleWithImage(
                mBinding.etPasswordRegister,
                mBinding.ivHidingPass);
    }

    public void onPasswordToggleImageClickAgain(){
        UIUtils.togglePasswordVisibleWithImage(
                mBinding.etPasswordAgainRegister,
                mBinding.ivHidingPassAgain);
    }

    @Override
    public void onMessage(String message) {
        mProgressDialog.dismiss();
        UIUtils.showMessage(mBinding.getRoot(), message);
    }
}