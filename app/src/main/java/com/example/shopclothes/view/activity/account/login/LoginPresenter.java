package com.example.shopclothes.view.activity.account.login;
import android.content.Context;
import android.content.Intent;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.MainActivity;
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
        if (!ValidateUtils.validateLoginIsEmpty(email,password)){
            view.onMessage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }
        doLoginFirebase(email, password);

    }

    @Override
    public void doLoginFirebase(String email, String password) {
        Executor executor = Executors.newSingleThreadExecutor();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        view.onMessageSuccess(AppConstants.ON_SUCCESS);
                    } else {
                        view.onMessageFailure(AppConstants.ON_FAILURE);
                    }
                });
    }
    @Override
    public void nextActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
