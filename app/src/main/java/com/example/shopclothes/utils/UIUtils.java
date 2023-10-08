package com.example.shopclothes.utils;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class UIUtils {
    public static void showMessage(View view, String message){
        new Handler(Looper.getMainLooper()).post(() -> Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show());
    }
    public static void clearText(EditText... editText) {
        for (EditText edt : editText) {
            edt.setText(null);
        }
    }
}
