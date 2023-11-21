package com.example.shopclothes.view.fragment.settingsFragment.other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityPolicyBinding;

public class PolicyActivity extends AppCompatActivity {
    private ActivityPolicyBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.ivBack.setOnClickListener(view -> onBackPressed());
    }
}