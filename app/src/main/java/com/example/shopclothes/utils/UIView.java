package com.example.shopclothes.utils;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class UIView {
    public static void showMessage(View view, String message){
        new Handler(Looper.getMainLooper()).post(() -> Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show());
    }

}
