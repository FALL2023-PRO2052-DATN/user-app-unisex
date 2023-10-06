package com.example.shopclothes.view.activity.account.register;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.utils.ValidateUtils;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final FirebaseAuth mAuth;
    private final String PASS_NOT_DUPLICATES = "Mật khẩu không trùng lặp";
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doRegister(String email, String password, String confirmPassword) {
        if (!ValidateUtils.validateRegisterIsEmpty(email, password, confirmPassword)){
            view.onMessage(AppConstants.ENTER_COMPLETE_INFORMATION);
            return;
        }
        if (!ValidateUtils.validateRegisterEqual(password,confirmPassword)){
            view.onMessage(PASS_NOT_DUPLICATES);
            return;
        }
        Executor executor = Executors.newSingleThreadExecutor();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        view.onMessage(AppConstants.ON_SUCCESS);
                    } else {
                        view.onMessage(AppConstants.ON_FAILURE);
                    }
                });
    }
}
