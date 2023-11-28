package com.example.shopclothes.utils;
public interface ItemClickUtils {
    void onItemClickListener(int quantitySize, int position, String size);
    interface MyBottomSheetBill {
        void onBottomSheetDismissed();
    }
    interface onLogoutListener {
        void onLogout();
    }
}
