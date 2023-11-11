package com.example.shopclothes.view.fragment.settingsFragment.updateInforAccount;

import android.net.Uri;

import com.example.shopclothes.model.User;

public interface UpdateInforAccountContract {
    interface View{
        void onMessaage(String message);
        void showLoadingDialog(boolean show);
        void updateUI(User mUser);
    }
    interface Presenter{
        void detachView();
        void getCurrenUser();
        void updateAccount(String fullname, Uri selectedImageUri);

        void updateUserInfor(String id, String fullname, String image);

    }
}
