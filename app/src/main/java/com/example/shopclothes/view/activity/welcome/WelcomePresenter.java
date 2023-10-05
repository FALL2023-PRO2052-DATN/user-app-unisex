package com.example.shopclothes.view.activity.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.shopclothes.view.activity.login.LoginActivity;

public class WelcomePresenter implements WelcomeContract.Presenter {

    @Override
    public void nextActivity(Context context, Handler handler) {
        handler.postDelayed(() -> context.startActivity(new Intent(context, LoginActivity.class)), 3000);
    }
}