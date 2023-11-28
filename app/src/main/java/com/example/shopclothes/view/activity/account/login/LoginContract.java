package com.example.shopclothes.view.activity.account.login;

import android.content.Context;

public interface LoginContract {
    interface View{
        void onMessage(String message);
        void onMessageGoogle(String massage);
        void onMessageSuccess(String message);
        void onMessageFailure(String message);
        void googleSignIn();
    }

    interface Presenter{
        void insertUserInDatabase(String id);
        void doLogin(String email, String password);
        void nextActivity(Context context);
        void doLoginFirebase(String email, String password);

    }
}
