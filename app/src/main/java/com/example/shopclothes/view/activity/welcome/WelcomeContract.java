package com.example.shopclothes.view.activity.welcome;

import android.content.Context;
import android.os.Handler;

public interface WelcomeContract {
    interface View{
    }

    interface Presenter{
        void nextActivity(Context context, Handler handler);
    }

}
