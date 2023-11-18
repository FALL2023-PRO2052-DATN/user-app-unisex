package com.example.shopclothes.view.fragment.settingsFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.FragmentProfileBinding;
import com.example.shopclothes.model.User;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.example.shopclothes.view.fragment.settingsFragment.changePassword.ChangePasswordActivity;
import com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount.UpdateAccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment implements SettingContract.View {
    private FragmentProfileBinding mBinding;
    private SettingContract.Presenter mPresenter;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private User mUserUpdate;
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
        UIUtils.openLayout(mBinding.ivLoadingSettingsFragment, mBinding.layoutSettingsFragment, false, getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert user != null;
        mPresenter.getUser(user.getUid());
        onClick();
    }

    private void onClick() {
        mBinding.btnOut.setOnClickListener(view1 -> logOut());
        mBinding.layoutChangePass.setOnClickListener(view1 -> nextChange());
        mBinding.layoutUpdateInfor.setOnClickListener(view1 -> nextUpdateUser());
    }

    public void nextChange(){
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void nextUpdateUser(){
        Intent intent = new Intent(getContext(), UpdateAccountActivity.class);
        intent.putExtra("user", mUserUpdate);
        startActivity(intent);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void updateUI(User mUser) {
        mUserUpdate = mUser;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(mBinding.getRoot().getContext())
                .load(mUser.getAnh())
                .centerCrop()
                .placeholder(R.drawable.pick_image)
                .into(mBinding.ivAvtSetting);
        mBinding.tvNameSetting.setText(mUser.getName());
        assert firebaseUser != null;
        mBinding.tvEmailSetting.setText(firebaseUser.getEmail());

        UIUtils.openLayout(mBinding.ivLoadingSettingsFragment, mBinding.layoutSettingsFragment, true, getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        assert user != null;
        mPresenter.getUser(user.getUid());
    }
}