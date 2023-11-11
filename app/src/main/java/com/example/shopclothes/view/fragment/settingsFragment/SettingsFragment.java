package com.example.shopclothes.view.fragment.settingsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.FragmentProfileBinding;
import com.example.shopclothes.model.User;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.example.shopclothes.view.fragment.settingsFragment.changePassword.ChangePasswordActivity;
import com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount.UpdateInforAccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment implements SettingContract.View {
    private FragmentProfileBinding mBinding;
    private SettingContract.Presenter mPresenter;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mBinding = FragmentProfileBinding.inflate(inflater, container, false);
        mPresenter = new SettingPresenter(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getUser(user.getUid());
        mBinding.btnOut.setOnClickListener(view1 -> onClick());
        mBinding.layoutChangePass.setOnClickListener(view1 -> nextChange());
        mBinding.layoutUpdateInfor.setOnClickListener(view1 -> nextUpdateUser());
    }
    public void nextChange(){
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void nextUpdateUser(){
        Intent intent = new Intent(getContext(), UpdateInforAccountActivity.class);
        startActivity(intent);
    }

    private void onClick() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void updateUI(User mUser) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(mBinding.getRoot().getContext())
                .load(mUser.getAnh())
                .centerCrop()
                .placeholder(R.drawable.pick_image)
                .into(mBinding.ivAvtSetting);
        mBinding.tvNameSetting.setText(mUser.getName());
        mBinding.tvEmailSetting.setText(firebaseUser.getEmail());
    }

    @Override
    public void onMessage(String message) {

    }
}