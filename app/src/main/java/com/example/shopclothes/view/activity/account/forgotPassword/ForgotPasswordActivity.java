package com.example.shopclothes.view.activity.account.forgotPassword;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityForgotPasswordBinding;
import com.example.shopclothes.utils.UIUtils;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.View {
    private ActivityForgotPasswordBinding mBinding;
    private ForgotPasswordContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new ForgotPasswordPresenter(this);
        onClick();
    }

    private void onClick() {
        mBinding.btnSenForgotPass.setOnClickListener(view -> senEmail());
        mBinding.btnBackForgot.setOnClickListener(view -> {
            mPresenter.nextActivity(this);
            finish();
        });
    }

    private void senEmail() {
        String email = mBinding.etEmailForgotPass.getText().toString();
        if (isValidate(email)) return;
        String SEN_CODE = "Gửi mã xác nhận";
        mProgressDialog = ProgressDialog.show(this, SEN_CODE, AppConstants.LOADING);
        mPresenter.senEmailResetPass(email);
    }

    private boolean isValidate(String email) {
        if (!UIUtils.isEmailValid(email)){
            UIUtils.showMessage(mBinding.getRoot(), "Email sai định dạng");
            return true;
        }
        return false;
    }

    @Override
    public void onMessage(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailForgotPass);
        UIUtils.showMessage(mBinding.getRoot(),message);
    }
}