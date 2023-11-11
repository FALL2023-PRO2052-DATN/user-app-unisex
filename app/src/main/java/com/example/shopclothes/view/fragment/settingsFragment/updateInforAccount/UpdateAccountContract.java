package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import android.content.Intent;
import android.net.Uri;

import com.example.shopclothes.model.User;

public interface UpdateAccountContract {
    interface View{
        void onClick();
        void onMessage(String message);
        void updateUI(User mUser);
        void choseImgFromGallery();
    }
    interface Presenter{
        void updateUserInformation(String id, String fullName, String image);
        void uploadImageToFirebaseStorage(Uri mUri, String id, String name);

    }
}
