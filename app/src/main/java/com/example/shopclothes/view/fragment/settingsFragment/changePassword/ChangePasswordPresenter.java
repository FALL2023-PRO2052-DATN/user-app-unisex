package com.example.shopclothes.view.fragment.settingsFragment.changePassword;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.utils.ValidateUtils;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


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
        if (!UIUtils.isPasswordValid(password)) {
            view.onMessage(AppConstants.PASS_LENGTH);
            return ;
        }
        if (!UIUtils.isPasswordValid(passwordNew)) {
            view.onMessage(AppConstants.PASS_LENGTH);
            return ;
        }
        if (!ValidateUtils.validateChangePasswordEqual(passwordNew, passwordNewAgain)){
            view.onMessage(AppConstants.PASS_NOT_DUPLICATES);
            return;
        }

        /*
         * xác thực lại người dùng trước khi đổi mật khẩu, mật khẩu cũ có đúng không
         */
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        AuthCredential credential = EmailAuthProvider
                .getCredential(Objects.requireNonNull(currentUser.getEmail()), password);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        updateUserFirebase(passwordNew);
                    } else {
                        view.onMessage(AppConstants.PASS_NOT);
                    }
                });

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
