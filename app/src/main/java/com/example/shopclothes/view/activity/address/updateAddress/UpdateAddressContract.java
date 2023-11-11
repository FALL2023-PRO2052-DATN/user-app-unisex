package com.example.shopclothes.view.activity.address.updateAddress;

public interface UpdateAddressContract {
    interface View{
        void onMessege(String message);
    }
    interface Presenter{
        void updateAddress(int id, String name, String email, String phone, String address, int defaultStatus, int idDefault);
    }
}
