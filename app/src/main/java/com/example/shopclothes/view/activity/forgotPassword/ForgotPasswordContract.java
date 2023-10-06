package com.example.shopclothes.view.activity.forgotPassword;

import android.content.Context;

public interface ForgotPasswordContract {
    interface View {
        void onMessage(String message);
    }
    interface Presenter {
        void senEmailResetPass(String email);
        void nextActivity(Context context);
    }
}
