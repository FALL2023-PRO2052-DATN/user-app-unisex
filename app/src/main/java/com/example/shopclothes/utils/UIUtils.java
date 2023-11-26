package com.example.shopclothes.utils;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shopclothes.R;
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

    /**
     * Hàm thay đổi hình ảnh của nút hiển thị password và thay đổi thuộc tính InputType
     */
    public static void togglePasswordVisibleWithImage(EditText passwordEditText, ImageView passwordToggleImage) {
        TransformationMethod transformationMethod;
        int imgResource;

        boolean isPasswordVisible = passwordEditText.getTransformationMethod() instanceof HideReturnsTransformationMethod;
        if (isPasswordVisible) {
            transformationMethod = PasswordTransformationMethod.getInstance();
            imgResource = R.drawable.ic_eye;
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance();
            imgResource = R.drawable.ic_eye_true;
        }

        passwordEditText.setTransformationMethod(transformationMethod);
        passwordToggleImage.setImageResource(imgResource);
    }
}
