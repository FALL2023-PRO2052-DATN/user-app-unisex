package com.example.shopclothes.view.fragment.settingsFragment.changePassword;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.utils.ValidateUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordPresenter implements ChangePasswordContract.Presenter{
    private final ChangePasswordContract.View view;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void doChange(String password, String passwordNew, String passwordNewAgain) {
        if (!ValidateUtils.validateChangePasswordIsEmpty(password, passwordNew, passwordNewAgain)){
            view.onMessage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }
        if (!ValidateUtils.validateChangePasswordEqual(passwordNew, passwordNewAgain)){
            view.onMessage(AppConstants.PASS_NOT_DUPLICATES);
            return;
        }
        updateUserFirebase(passwordNew);
    }

    @Override
    public void updateUserFirebase(String passwordNew) {
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       if (user != null){
           user.updatePassword(passwordNew).addOnCompleteListener(task -> {
               if (task.isSuccessful()){
                   view.onMessage(AppConstants.ON_SUCCESS);
               }else {
                   Exception e = task.getException();
                   if (e != null) {
                       e.printStackTrace();
                   }
                   view.onMessage(AppConstants.ON_FAILURE);
               }
           });
       }
    }


}
