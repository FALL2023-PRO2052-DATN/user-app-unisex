package com.example.shopclothes.view.activity.forgotPassword;

import android.content.Context;
import android.content.Intent;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.view.activity.login.LoginActivity;
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
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        view.onMessage(AppConstants.onSuccess);
                    }else {
                        view.onMessage(AppConstants.onError);
                    }
                });
    }

    @Override
    public void nextActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
