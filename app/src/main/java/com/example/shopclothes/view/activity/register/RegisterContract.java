package com.example.shopclothes.view.activity.register;

public interface RegisterContract {
    interface View {
        void onMessage(String message);
    }

    interface Presenter {
        void doRegister(String email, String password, String confirmPassword);
    }
}
