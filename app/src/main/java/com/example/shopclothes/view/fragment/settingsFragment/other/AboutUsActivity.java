package com.example.shopclothes.view.fragment.settingsFragment.other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutUsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.ivBack.setOnClickListener(view -> onBackPressed());
    }
}