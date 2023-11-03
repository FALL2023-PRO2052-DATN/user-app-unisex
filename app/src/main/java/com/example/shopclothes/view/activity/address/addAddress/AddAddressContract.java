package com.example.shopclothes.view.activity.address.addAddress;

import com.example.shopclothes.model.Address;

import java.util.List;

public interface AddAddressContract {
    interface View{
        void onMessege(String message);
    }
    interface Presenter{
        void insertAddress(int id, String name, String email, String phone, String address, int defaultStatus, String userId);
    }

}
