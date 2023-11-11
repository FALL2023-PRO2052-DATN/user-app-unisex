package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.model.User;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInforAccountPresenter implements UpdateInforAccountContract.Presenter{
    private UpdateInforAccountContract.View view;
    private User mUser;



    public UpdateInforAccountPresenter(UpdateInforAccountContract.View view) {
        this.view = view;
        mUser = new User();
    }


    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getCurrenUser() {
        view.showLoadingDialog(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ApiService.API_SERVICE.readUser(user.getUid()).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                view.showLoadingDialog(false);
                if (response.isSuccessful()){
                    ResponseUser userList = response.body();
                    if (userList != null){
                        User user1 = userList.getUser();
                        view.updateUI(user1);
                    }else {
                        view.onMessaage(AppConstants.NOT_FOUND);
                    }
                }else {
                    view.onMessaage(AppConstants.ON_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                view.showLoadingDialog(false);
                view.onMessaage(AppConstants.TAG);
            }
        });
    }

    @Override
    public void updateAccount(String fullname, Uri selectedImageUri) {
        if (fullname.isEmpty() && selectedImageUri == null){
            view.onMessaage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }

        view.showLoadingDialog(true);
        uploadImageToFirebaseStorage(selectedImageUri);
        updateUserInfor(mUser.getId(), mUser.getName(), mUser.getAnh());
    }

    @Override
    public void updateUserInfor(String id, String fullname, String image) {
        ApiService.API_SERVICE.updateUser(id, fullname, image).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    ResponseUser responseUser = response.body();
                    if (responseUser != null){
                        User updateUser = responseUser.getUser();
                        view.updateUI(updateUser);
                        view.onMessaage(AppConstants.ON_SUCCESS);
                    }else {
                        view.onMessaage(AppConstants.NOT_FOUND);
                    }
                }else {
                    view.onMessaage(AppConstants.ON_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                view.onMessaage(AppConstants.TAG);
            }
        });
    }

    public void uploadImageToFirebaseStorage(Uri selectedImageUri){
        view.showLoadingDialog(true);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imgReference =storageReference.child("profile_images/" + UUID.randomUUID().toString() + "jpg");
        imgReference.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> imgReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    view.showLoadingDialog(false);
                    String imageUrl = uri.toString();
                    mUser.setAnh(imageUrl);
                }))
                .addOnFailureListener(e -> view.onMessaage(AppConstants.ON_FAILURE));
    }


}
