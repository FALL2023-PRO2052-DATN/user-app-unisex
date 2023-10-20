package com.example.shopclothes.view.activity.product.detailProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shopclothes.adapter.AdapterComment;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.adapter.MyBottomSheet;
import com.example.shopclothes.databinding.ActivityDetailProductBinding;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DetailProductActivity extends AppCompatActivity implements DetailProductContract.View {
    private ActivityDetailProductBinding mBinding;
    private DetailProductContract.Presenter mPresenter;
    List<Size> mListSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new DetailProductPresenter(this);
        initPresenter();
        seeMore();
    }

    @Override
    public void initPresenter() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mPresenter.getProduct(id);
        mPresenter.getListCommentById(id);
        mPresenter.getListSizeByIdProduct(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProduct(Product product) {
        String phanTram = "%";
        String TYPE = "Unisex-";
        String UNI = "-UNI_";
        Glide.with(this).load(product.getImageProduct()).into(mBinding.ivDetailProduct);
        mBinding.tvNameProductDetail.setText(product.getNameProduct());
        mBinding.tvTypeProductDetail.setText(TYPE + product.getNameTypeProduct() + UNI + product.getId());
        mBinding.tvPriceSalesProductDetail.setText(FormatUtils.formatCurrency(product.getPrice() * product.getSale() / 100));
        mBinding.tvPriceProductDetail.setText(FormatUtils.formatCurrency(product.getPrice()));
        mBinding.tvPriceProductDetail.setPaintFlags(mBinding.tvPriceProductDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.tvNumberSalesDetail.setText(product.getSale() + phanTram);
        mBinding.tvDescriptionProduct.setText(product.getNote());
        mPresenter.getListProductByIdCategory(product.getIdCategory());
        mBinding.btnAddCart.setOnClickListener(view -> openBottomSheetDialogFragment(product, mListSize));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListComment(List<Comment> list) {
        AdapterComment adapter = new AdapterComment(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rcvCommentDetail.setLayoutManager(layoutManager);
        mBinding.rcvCommentDetail.setAdapter(adapter);
        mBinding.ratringBar.setRating(averageRating(list));
        mBinding.tvRatingComment.setText(averageRating(list) + "/5");
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
        MyBottomSheet myBottomSheet = new MyBottomSheet(product, list);
        myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());

    }

}