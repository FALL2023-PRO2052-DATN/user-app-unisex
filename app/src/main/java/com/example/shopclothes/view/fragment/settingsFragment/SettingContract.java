package com.example.shopclothes.view.fragment.settingsFragment;

import com.example.shopclothes.model.User;

public interface SettingContract {
    interface View{
        void updateUI(User user);
        void onMessage(String message);
    }

    interface Presenter{
        void getUser(String id);
    }
}
