package com.example.shopclothes.view.activity.account.register;

import android.content.Context;

public interface RegisterContract {
    interface View {
        void onMessage(String message);
    }

    interface Presenter {
        void doRegister(String email, String password, String confirmPassword);
        void insertUserInDatabase(String id);
        void createUserFirebase(String email, String password);
        void nextActivity(Context context);
    }
}
