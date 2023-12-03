package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityUpdateInforAccountBinding;
import com.example.shopclothes.model.User;
import com.example.shopclothes.utils.UIUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UpdateAccountActivity extends AppCompatActivity implements UpdateAccountContract.View {
    private ActivityUpdateInforAccountBinding mBinding;
    private UpdateAccountContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Uri mUri;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUpdateInforAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        onClick();
        mPresenter = new UpdateAccountPresenter(this);
        mUser = (User) getIntent().getSerializableExtra("user");
        updateUI(mUser);
    }


    @Override
    public void onClick() {
        mBinding.btnCiv.setOnClickListener(view -> choseImgFromGallery());
        mBinding.btnSaveUpInfor.setOnClickListener(view -> {

          if (mBinding.etFullnameUpdate.getText().toString().isEmpty()){
              UIUtils.showMessage(mBinding.getRoot(), AppConstants.NAME_IS_EMPTY);
          }else {
              mProgressDialog = ProgressDialog.show(this,"",AppConstants.LOADING);
              if (mUri != null){
                  // nếu đường dẫn ko rỗng thì update theo đường dẫn ảnh
                  mPresenter.uploadImageToFirebaseStorage(mUri, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), mBinding.etFullnameUpdate.getText().toString());
              }else {
                  // nêu rỗng update ảnh có sẵn
                  mPresenter.updateUserInformation(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), mBinding.etFullnameUpdate.getText().toString(), mUser.getAnh());
              }
          }
        });
        mBinding.ivBackInfor.setOnClickListener(view -> onBackPressed());
    }



    @Override
    public void updateUI(User mUser) {
        Glide.with(this)
                .load(mUser.getAnh())
                .centerCrop()
                .placeholder(R.drawable.pick_image)
                .into(mBinding.civUser);
        mBinding.etFullnameUpdate.setText(mUser.getName());
    }

    @Override
    public void onMessage(String message) {
        onBackPressed();
    }

    @Override
    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, AppConstants.PICK_IMAGE));
    }

    // nhận uri khi chọn ảnh từ thư viện
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data == null){
                        return;
                    }
                    mUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                        mBinding.civUser.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
}