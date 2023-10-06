package com.example.shopclothes.view.activity.account.forgotPassword;

import android.content.Context;
import android.content.Intent;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    private final ForgotPasswordContract.View view;
    private final FirebaseAuth auth;
    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void senEmailResetPass(String emailAddress) {
        if (!ValidateUtils.validateForgotPass(emailAddress)){
            view.onMessage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        view.onMessage(AppConstants.ON_SUCCESS);
                    }else {
                        view.onMessage(AppConstants.ON_FAILURE);
                    }
                });
    }

    @Override
    public void nextActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
