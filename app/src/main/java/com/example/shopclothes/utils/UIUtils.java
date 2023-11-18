package com.example.shopclothes.utils;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
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

    public static void openLayout(ImageView imageView, FrameLayout frameLayout, boolean check, Context context){
        if (check){
            imageView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
        }else {
            Glide.with(context).load("https://i.gifer.com/ZKZg.gif").into(imageView);
            frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.main));
        }
    }

}
