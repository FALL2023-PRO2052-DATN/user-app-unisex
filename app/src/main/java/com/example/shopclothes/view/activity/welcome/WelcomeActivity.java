package com.example.shopclothes.view.activity.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.shopclothes.R;

public class WelcomeActivity extends AppCompatActivity {
    WelcomeContract.Presenter mWelcomePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mWelcomePresenter = new WelcomePresenter();
        mWelcomePresenter.nextActivity(this, new Handler());
        finish();
    }


}