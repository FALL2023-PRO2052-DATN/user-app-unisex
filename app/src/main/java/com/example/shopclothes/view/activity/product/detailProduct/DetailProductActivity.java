package com.example.shopclothes.view.activity.product.detailProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterComment;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.adapter.MyBottomSheetCart;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityDetailProductBinding;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.cart.CartActivity;
import com.example.shopclothes.view.activity.comment.seeComment.SeeCommentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DetailProductActivity extends AppCompatActivity implements DetailProductContract.View {
    private ActivityDetailProductBinding mBinding;
    private DetailProductContract.Presenter mPresenter;
    private List<Size> mListSize;
    private  int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new DetailProductPresenter(this);

        UIUtils.openLayout(mBinding.ivLoadingDetailProductActivity, mBinding.layoutDetailProductActivity, false, this);
        initPresenter();
        seeMore();
        onClick();
    }

    @Override
    public void onClick() {
        mBinding.btnCartDetailProduct.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
        mBinding.ivBackDetail.setOnClickListener(view -> onBackPressed());
        mBinding.tvSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(this, SeeCommentActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    @Override
    public void initPresenter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        mPresenter.getProduct(id);
        mPresenter.getListCommentById(id);
        mPresenter.getListSizeByIdProduct(id);
        assert user != null;
        mPresenter.getListCartByIdUser(user.getUid());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProduct(Product product) {
        Glide.with(this).load(product.getImageProduct()).into(mBinding.ivDetailProduct);
        mBinding.tvNameProductDetail.setText(product.getNameProduct());
        mBinding.tvTypeProductDetail.setText(AppConstants.UNISEX + product.getNameTypeProduct() + AppConstants.UNI + product.getId());
        mBinding.tvPriceSalesProductDetail.setText(FormatUtils.formatCurrency(product.getPrice() - (product.getPrice() * product.getSale() / 100)));
        mBinding.tvPriceProductDetail.setText(FormatUtils.formatCurrency(product.getPrice()));
        mBinding.tvPriceProductDetail.setPaintFlags(mBinding.tvPriceProductDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.tvNumberSalesDetail.setText(product.getSale() + AppConstants.PERCENT);
        mBinding.tvDescriptionProduct.setText(product.getNote());
        mPresenter.getListProductByIdCategory(product.getIdCategory());
        mBinding.btnAddCart.setOnClickListener(view -> openBottomSheetDialogFragment(product, mListSize));


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListComment(List<Comment> list) {
        AdapterComment adapter = new AdapterComment(list, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvCommentDetail.setLayoutManager(layoutManager);
        mBinding.rcvCommentDetail.setAdapter(adapter);
        mBinding.ratringBar.setRating(averageRating(list));
        mBinding.tvRatingComment.setText(FormatUtils.formatRating(averageRating(list)) + "/5");
        mBinding.tvNumberComment.setText("("+list.size()+")");


    }

    @Override
    public void onListSizeByIdProduct(List<Size> list) {
        mListSize = list;
    }

    @Override
    public void onListProductByIdCategory(List<Product> list) {
        AdapterProduct adapter = new AdapterProduct(list, 2, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mBinding.recyclerViewProductRelate.setLayoutManager(layoutManager);
        mBinding.recyclerViewProductRelate.setAdapter(adapter);
    }

    @Override
    public void onListCartByIdUser(List<Cart> list) {
        mBinding.tvQuatityDetail.setText(String.valueOf(list.size()));

        UIUtils.openLayout(mBinding.ivLoadingDetailProductActivity, mBinding.layoutDetailProductActivity, true, this);

    }

    @Override
    public float averageRating(List<Comment> list){
        return  (float) list.stream()
                .mapToDouble(Comment::getPointComment) // Lấy giá trị điểm từ từng Comment
                .average() // Tính trung bình
                .orElse(0);  // trả về 0 nếu list trống
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void seeMore() {
        mBinding.tvDescriptionProduct.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 100)); // Đặt chiều cao mong muốn ở dạng pixel
        // true thu gọn, false mở rộng
        AtomicBoolean isExpanded = new AtomicBoolean(true);

        mBinding.btnSeeMore.setOnClickListener(view -> {
            if (isExpanded.get()) {
                isExpanded.set(false);
                mBinding.tvSeeMore.setText("Thu gọn");
                mBinding.tvDescriptionProduct.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                isExpanded.set(true);
                mBinding.tvSeeMore.setText("Xem thêm");
                mBinding.tvDescriptionProduct.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 100)); // Đặt chiều cao mong muốn ở dạng pixel
            }
        });
    }

    @Override
    public void openBottomSheetDialogFragment(Product product, List<Size> list) {
        MyBottomSheetCart myBottomSheet = new MyBottomSheetCart(product, list, new DetailProductPresenter(this));
        myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.getListCartByIdUser(user.getUid());
    }

}