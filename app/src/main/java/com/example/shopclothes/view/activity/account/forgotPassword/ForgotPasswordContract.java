package com.example.shopclothes.view.activity.account.forgotPassword;

import android.content.Context;

public interface ForgotPasswordContract {
    interface View {
        void onMessage(String message);
    }
    interface Presenter {
        void senEmailResetPass(String email);
        void senEmailResetPassFirebase(String email);
        void nextActivity(Context context);
    }
}
