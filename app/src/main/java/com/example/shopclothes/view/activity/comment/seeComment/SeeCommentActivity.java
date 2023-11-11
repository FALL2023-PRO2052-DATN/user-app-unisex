package com.example.shopclothes.view.activity.comment.seeComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterComment;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivitySeeCommentBinding;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class SeeCommentActivity extends AppCompatActivity implements SeeCommentContract.View {
    private ActivitySeeCommentBinding mBinding;
    private SeeCommentContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySeeCommentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new SeeAllPresenter(this);
        onClick();
        initPresenter();
    }

    @Override
    public void onClick() {
        mBinding.ivBackSeeAllComment.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void initPresenter() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mPresenter.getListCommentById(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListComment(List<Comment> list) {
        AdapterComment adapter = new AdapterComment(list, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvSeeAllComment.setLayoutManager(layoutManager);
        mBinding.rcvSeeAllComment.setAdapter(adapter);

        mBinding.ratingBarSeeComment.setRating(averageRating(list));
        mBinding.tvRatingSeeAllComment.setText(FormatUtils.formatRating(averageRating(list)));
        mBinding.tvNumberSeeAllComment.setText("("+list.size()+ AppConstants.COMMENT +")");
    }

    @Override
    public float averageRating(List<Comment> list) {
        return  (float) list.stream()
                .mapToDouble(Comment::getPointComment) // Lấy giá trị điểm từ từng Comment
                .average() // Tính trung bình
                .orElse(0);  // trả về 0 nếu list trống
    }
}