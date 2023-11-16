package com.example.shopclothes.view.activity.comment.addComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterProductComment;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityAddCommentBinding;
import com.example.shopclothes.model.ProductComment;
import com.example.shopclothes.utils.UIUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AddCommentActivity extends AppCompatActivity implements AddCommentContract.View {
    private ActivityAddCommentBinding mBinding;
    private AddCommentContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Intent intent;
    private List<ProductComment> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddCommentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new AddCommentPresenter(this);
        intent = getIntent();
        onClick();
        initPresenter();
    }

    @Override
    public void onClick() {
        mBinding.tvAddComment.setOnClickListener(view -> {
            mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
           for (int i = 0 ; i < mList.size(); i++){
               mPresenter.insertComment(mBinding.ratingBarComment2.getRating(), mBinding.etComment2.getText().toString(), user.getUid(), mList.get(i).getId());
           }
            mProgressDialog.dismiss();
            UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_SUCCESS);
            UIUtils.clearText(mBinding.etComment2);
        });

        mBinding.btnBackAddComment.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void initPresenter() {
        mPresenter.getListProduct(intent.getStringExtra("id"));
    }

    @Override
    public void onListProduct(List<ProductComment> list) {
        AdapterProductComment adapter = new AdapterProductComment(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvComment.setLayoutManager(layoutManager);
        mBinding.rcvComment.setAdapter(adapter);
        mList = list;
    }

    @Override
    public void onMessage(String message, boolean check) {
        if (!check){
            UIUtils.showMessage(mBinding.getRoot(), message);
        }
    }
}