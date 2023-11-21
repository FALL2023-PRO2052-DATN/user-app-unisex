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
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.User;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.example.shopclothes.view.activity.address.address.AddressActivity;
import com.example.shopclothes.view.activity.cart.CartActivity;
import com.example.shopclothes.view.fragment.settingsFragment.changePassword.ChangePasswordActivity;
import com.example.shopclothes.view.fragment.settingsFragment.other.AboutUsActivity;
import com.example.shopclothes.view.fragment.settingsFragment.other.ContactActivity;
import com.example.shopclothes.view.fragment.settingsFragment.other.PolicyActivity;
import com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount.UpdateAccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


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
        mPresenter.readListCartByIdUser(user.getUid());
        onClick();
    }

    private void onClick() {
        mBinding.btnOut.setOnClickListener(view1 -> logOut());
        mBinding.layoutChangePass.setOnClickListener(view1 -> nextChange());
        mBinding.layoutUpdateInfor.setOnClickListener(view1 -> nextUpdateUser());
        mBinding.btnBagSettings.setOnClickListener(view -> startActivity(new Intent(getContext(), CartActivity.class)));
        mBinding.layoutAddressReceive.setOnClickListener(view -> startActivity(new Intent(getContext(), AddressActivity.class)));
        mBinding.layoutAboutUs.setOnClickListener(view -> startActivity(new Intent(getContext(), AboutUsActivity.class)));
        mBinding.layoutReturnPolicy.setOnClickListener(view -> startActivity(new Intent(getContext(), PolicyActivity.class)));
        mBinding.layoutContact.setOnClickListener(view -> startActivity(new Intent(getContext(), ContactActivity.class)));
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
    public void onListCartByIdUser(List<Cart> cartList) {
        mBinding.tvBagSettings.setText(String.valueOf(cartList.size()));
    }

    @Override
    public void onStart() {
        super.onStart();
        assert user != null;
        mPresenter.getUser(user.getUid());
    }
}