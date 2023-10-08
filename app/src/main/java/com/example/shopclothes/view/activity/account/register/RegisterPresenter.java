package com.example.shopclothes.view.activity.account.register;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final FirebaseAuth mAuth;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doRegister(String email, String password, String confirmPassword) {
        if (!ValidateUtils.validateRegisterIsEmpty(email, password, confirmPassword)){
            view.onMessage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }
        if (!ValidateUtils.validateRegisterEqual(password,confirmPassword)){
            view.onMessage(AppConstants.PASS_NOT_DUPLICATES);
            return;
        }
        createUserFirebase(email, password);
    }


    @Override
    public void createUserFirebase(String email, String password) {
        Executor executor = Executors.newSingleThreadExecutor();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        insertUserInDatabase(user.getUid());
                    } else {
                        view.onMessage(AppConstants.ON_FAILURE);
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
                    view.onMessage(AppConstants.ON_SUCCESS);
                } else {
                    view.onMessage(AppConstants.ON_FAILURE);
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
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}
