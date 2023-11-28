package com.example.shopclothes.utils;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
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

    public static void openLayout(ImageView imageView, FrameLayout frameLayout, boolean check, Context context){
        if (check){
            imageView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
        }else {
            Glide.with(context).load("https://i.gifer.com/ZKZg.gif").into(imageView);
            frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.main));
        }
    }

    public static void copyToClipboard(Context context, String textToCopy, View view) {
        // Lấy ClipboardManager từ hệ thống
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // Tạo một đối tượng ClipData để chứa dữ liệu cần sao chép
        ClipData clipData = ClipData.newPlainText("text", textToCopy);

        // Sao chép dữ liệu vào ClipboardManager
        clipboardManager.setPrimaryClip(clipData);
        showMessage(view, AppConstants.MESSAGE_COPY);
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
