package com.example.shopclothes.view.fragment.settingsFragment.changePassword;

public interface ChangePasswordContract {
    interface View{
        void onMessage(String message);
    }
    interface Presenter{
        void doChange(String password, String passwordNew, String passwordNewAgain);
        void updateUserFirebase(String passwordNew);
    }

}
