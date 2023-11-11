package com.example.shopclothes.view.activity.address.address;

import com.example.shopclothes.model.Address;

import java.util.List;

public interface AddressContract {
    interface View{
        void onListAddressAll(List<Address> list);
        void launchAddressUpdateActivity(Address address);
        void onMessage(String message);
    }
    interface Presenter{
        void readAddress(String userId);
        void getListAddressAll(String userId);
        void deleteAddress(int id);
    }
}
