package com.example.shopclothes.view.activity.account.login;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityLoginBinding;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.account.forgotPassword.ForgotPasswordActivity;
import com.example.shopclothes.view.activity.account.register.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private ActivityLoginBinding mBinding;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new LoginPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        configGoogle();
        onClick();
    }

    public void onClick() {
        mBinding.btnLogin.setOnClickListener(view -> login());
        mBinding.tvForgotPassword.setOnClickListener(view -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
        mBinding.tvRegister.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
        mBinding.btnLoginGg.setOnClickListener(view ->{
            logoutGoogle();
            googleSignIn();
        });
    }
    public void login() {
        String email = mBinding.etEmailLogin.getText().toString();
        String password = mBinding.etPasswordLogin.getText().toString();
        String LOGIN = "Đăng nhập";
        mProgressDialog = ProgressDialog.show(this, LOGIN, AppConstants.LOADING);
        mPresenter.doLogin(email,password);
    }

    @Override
    public void onMessage(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailLogin, mBinding.etPasswordLogin);
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    @Override
    public void onMessageGoogle(String massage) {
        mProgressDialog.dismiss();
        if (AppConstants.ON_SUCCESS.equals(massage)){
            mPresenter.nextActivity(this);
        }
    }
    @Override
    public void onMessageSuccess(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailLogin, mBinding.etPasswordLogin);
        UIUtils.showMessage(mBinding.getRoot(), message);
        mPresenter.nextActivity(this);
        finish();
    }

    @Override
    public void onMessageFailure(String message) {
        mProgressDialog.dismiss();
        UIUtils.clearText(mBinding.etEmailLogin, mBinding.etPasswordLogin);
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    public void configGoogle(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    public void logoutGoogle(){
        googleSignInClient.signOut();
    }
    @Override
    public void googleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        mLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                            try {
                                GoogleSignInAccount account = task.getResult(ApiException.class);

                                AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                                mAuth.signInWithCredential(firebaseCredential)
                                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    mPresenter.insertUserInDatabase(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                                } else {
                                                    Exception exception = task.getException();
                                                    if (exception != null) {
                                                        String errorMessage = exception.getMessage();
                                                        UIUtils.showMessage(mBinding.getRoot(), errorMessage);
                                                    }
                                                }
                                            }
                                        });

                            } catch (ApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
}