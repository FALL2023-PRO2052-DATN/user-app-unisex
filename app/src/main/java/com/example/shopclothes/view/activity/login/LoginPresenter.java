package com.example.shopclothes.view.activity.login;
import com.example.shopclothes.constant.AppConstants;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginPresenter implements LoginContract.Presenter{
    private final LoginContract.View view;
    private final FirebaseAuth mAuth;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(String email, String password) {
        Executor executor = Executors.newSingleThreadExecutor();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        view.onMessage(AppConstants.onSuccess);
                    } else {
                        view.onMessage(AppConstants.onError);
                    }
                });

    }
}
