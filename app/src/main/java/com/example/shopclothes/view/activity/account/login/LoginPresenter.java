package com.example.shopclothes.view.activity.account.login;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.MainActivity;
import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void insertUserInDatabase(String id) {
        ApiService.API_SERVICE.insertUser(id).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals(AppConstants.SUCCESS)){
                    view.onMessageGoogle(AppConstants.ON_SUCCESS);
                } else {
                    view.onMessageGoogle(AppConstants.ON_SUCCESS);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {
                Log.e(AppConstants.TAG, t.toString());
            }
        });
    }

    @Override
    public void nextActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

}
