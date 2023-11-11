package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityUpdateInforAccountBinding;
import com.example.shopclothes.model.User;

public class UpdateInforAccountActivity extends AppCompatActivity implements UpdateInforAccountContract.View {
    private ActivityUpdateInforAccountBinding mBinding;
    private UpdateInforAccountContract.Presenter mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUpdateInforAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new UpdateInforAccountPresenter(this);
    }
    public void setListener(){
        mBinding.ivBackInfor.setOnClickListener(view -> onBackPressed());
//        mBinding.civUser.setOnClickListener(view -> );
//        mBinding.btnSaveUpInfor.setOnClickListener(view -> );
    }



    @Override
    public void onMessaage(String message) {

    }

    @Override
    public void showLoadingDialog(boolean show) {

    }

    @Override
    public void updateUI(User mUser) {
        if (mUser == null){
            return;
        }
        Glide.with(this)
                .load(mUser.getAnh())
                .centerCrop()
                .placeholder(R.drawable.pick_image)
                .into(mBinding.civUser);
        mBinding.etFullnameUpdate.setText(mUser.getName());
    }
}