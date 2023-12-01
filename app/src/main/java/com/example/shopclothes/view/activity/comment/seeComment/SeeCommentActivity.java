package com.example.shopclothes.view.activity.comment.seeComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupMenu;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterComment;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivitySeeCommentBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.cart.CartActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SeeCommentActivity extends AppCompatActivity implements SeeCommentContract.View {
    private ActivitySeeCommentBinding mBinding;
    private SeeCommentContract.Presenter mPresenter;
    private List<Comment> mCommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySeeCommentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new SeeAllPresenter(this);
        UIUtils.openLayout(mBinding.ivLoadingSeeCommentActivity, mBinding.layoutSeeCommentActivity, false, this);
        onClick();
        initPresenter();
    }

    @Override
    public void onClick() {
        mBinding.ivBackSeeAllComment.setOnClickListener(view -> onBackPressed());
        mBinding.tvFilterComment.setOnClickListener(view -> {
            if (mCommentList != null){
                showPopupMenu();
            }else {
                UIUtils.showMessage(mBinding.getRoot(), AppConstants.FILTER_MESSAGE);
            }
        });
        mBinding.btnSeeCommentProduct.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
    }

    @Override
    public void initPresenter() {
        mPresenter.getListCommentById(getIntent().getIntExtra("id", 0));
        mPresenter.readListCartByIdUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListComment(List<Comment> list) {
        Collections.reverse(list);
        AdapterComment adapter = new AdapterComment(list, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvSeeAllComment.setLayoutManager(layoutManager);
        mBinding.rcvSeeAllComment.setAdapter(adapter);

        mBinding.ratingBarSeeComment.setRating(averageRating(list));
        mBinding.tvRatingSeeAllComment.setText(FormatUtils.formatRating(averageRating(list)));
        mBinding.tvNumberSeeAllComment.setText("("+list.size()+ AppConstants.COMMENT +")");

        mCommentList = list;

        UIUtils.openLayout(mBinding.ivLoadingSeeCommentActivity, mBinding.layoutSeeCommentActivity, true, this);
    }

    @Override
    public float averageRating(List<Comment> list) {
        return  (float) list.stream()
                .mapToDouble(Comment::getPointComment) // Lấy giá trị điểm từ từng Comment
                .average() // Tính trung bình
                .orElse(0);  // trả về 0 nếu list trống
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mBinding.tvFilterComment);
        popupMenu.getMenuInflater().inflate(R.menu.menu_filter_comment, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            UIUtils.openLayout(mBinding.ivLoadingSeeCommentActivity, mBinding.layoutSeeCommentActivity, false, this);
            switch (item.getItemId()) {
                case R.id.oneStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentByStart(getIntent().getIntExtra("id", 0),1);
                    break;
                case R.id.towStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentByStart(getIntent().getIntExtra("id",0),2);
                    break;
                case R.id.threeStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentByStart(getIntent().getIntExtra("id",0),3);
                    break;
                case R.id.fourStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentByStart(getIntent().getIntExtra("id",0),4);
                    break;
                case R.id.fifStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentByStart(getIntent().getIntExtra("id",0),5);
                    break;
                case R.id.allStar:
                    mBinding.tvFilterComment.setText(item.getTitle());
                    mPresenter.getListCommentById(getIntent().getIntExtra("id", 0));
                    break;
            }
            return true;
        });
        popupMenu.show();

    }

    @Override
    public void onListCartByIdUser(List<Cart> cartList) {
        mBinding.tvQuantityCartSeeComment.setText(String.valueOf(cartList.size()));
    }
}