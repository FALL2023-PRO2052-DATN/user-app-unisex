package com.example.shopclothes.view.activity.account.login;

import android.content.Context;

public interface LoginContract {
    interface View{
        void onMessage(String message);
    }

    interface Presenter{
        void doLogin(String email, String password);
        void nextActivity(Context context);
        void doLoginFirebase(String email, String password);
    }
}
