package com.example.shopclothes.view.fragment.settingsFragment;

import com.example.shopclothes.model.User;

public interface SettingContract {
    interface View{
        void updateUI(User user);
    }

    interface Presenter{
        void getUser(String id);
    }
}
