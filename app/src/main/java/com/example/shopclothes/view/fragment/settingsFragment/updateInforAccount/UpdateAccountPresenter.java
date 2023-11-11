package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.model.User;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccountPresenter implements UpdateAccountContract.Presenter{
    private final UpdateAccountContract.View view;

    public UpdateAccountPresenter(UpdateAccountContract.View view) {
        this.view = view;
    }


    @Override
    public void updateUserInformation(String id, String fullName, String image) {
        ApiService.API_SERVICE.updateUser(id, fullName, image).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                        view.onMessage(AppConstants.ON_SUCCESS);
                    }else {
                        view.onMessage(AppConstants.NOT_FOUND);
                    }
                }else {
                    view.onMessage(AppConstants.ON_FAILURE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {
                Log.e("ERR", t.toString());
            }
        });
    }

    public void uploadImageToFirebaseStorage(Uri mUri, String id, String name){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images/" + mUri.getLastPathSegment());
        storageReference.putFile(mUri)
                .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    updateUserInformation(id, name, uri.toString());
                }))
                .addOnFailureListener(e -> view.onMessage(AppConstants.ON_FAILURE));
    }



}
