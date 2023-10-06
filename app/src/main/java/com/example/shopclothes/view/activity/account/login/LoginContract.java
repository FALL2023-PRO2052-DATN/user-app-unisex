package com.example.shopclothes.view.activity.account.login;

public interface LoginContract {
    interface View{
        void onMessage(String message);
    }

    interface Presenter{
        void doLogin(String email, String password);
    }
}
