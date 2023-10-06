package com.example.shopclothes.view.activity.register;

import com.example.shopclothes.constant.Constant;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final FirebaseAuth mAuth;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doRegister(String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            view.onMessage("Passwords Incorrect");
            return;
        }

        Executor executor = Executors.newSingleThreadExecutor();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        view.onMessage(Constant.onSuccess);
                    } else {
                        view.onMessage(Constant.onError);
                    }
                });
    }
}
