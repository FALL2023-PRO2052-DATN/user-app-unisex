package com.example.shopclothes.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemAddCartBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductContract;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBottomSheetCart extends BottomSheetDialogFragment {
    private ItemAddCartBinding mBinding;
    private final Product mProduct;
    private final List<Size> mListSize;
    private String mSize = "";
    private final FirebaseUser mUser;
    private  ProgressDialog mProgressDialog;
    private final DetailProductContract.Presenter mPresenter;
    private  FirebaseUser user;
    public MyBottomSheetCart(Product mProduct, List<Size> listSize, DetailProductContract.Presenter presenter) {
        this.mProduct = mProduct;
        this.mListSize = listSize;
        this.mPresenter = presenter;
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBinding = ItemAddCartBinding.inflate(LayoutInflater.from(getContext()));
        mBottomSheetDialog.setContentView(mBinding.getRoot());
        setData();
        setAdapter(mListSize);
        onClickItem(-1, 1);
        onClick();
        return mBottomSheetDialog;
    }

    private void onClick() {
        // mới vào sẽ sét
        mBinding.btnAddCartProduct.setEnabled(false);
        mBinding.btnAddCartProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.linear));
       if (mBinding.btnAddQuantity.isEnabled()){
           mBinding.btnAddCartProduct.setEnabled(true);
           mBinding.btnAddCartProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary));
           mBinding.btnAddCartProduct.setOnClickListener(view -> addCart());
       }
    }


    public void setData() {
        if (mProduct == null && mListSize == null){
            Toast.makeText(getContext(), "Rỗng", Toast.LENGTH_SHORT).show();
            return;
        }
        assert mProduct != null;
        Glide.with(mBinding.getRoot()).load(mProduct.getImageProduct()).into(mBinding.ivProductAddCart);
        mBinding.tvNameProductAddCart.setText(mProduct.getNameProduct());
        mBinding.tvPriceProductAddCart.setPaintFlags(mBinding.tvPriceProductAddCart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.tvPriceProductAddCart.setText(FormatUtils.formatCurrency(mProduct.getPrice()));
        mBinding.tvPriceSalesProductAddCart.setText(FormatUtils.formatCurrency(mProduct.getPrice() - (mProduct.getPrice() * mProduct.getSale() / 100)));
    }
    public void setAdapter(List<Size> mListSize) {
        AdapterSize adapterSize = new AdapterSize(mListSize, (quantitySize, position, size) -> {
            mBinding.tvIntenvoryNumbersAddCart.setText(String.valueOf(quantitySize)); // số lượng size còn
            onClickItem(position, quantitySize); // vị trí chọn size, số lượng size còn
            mSize = size;
            onClick();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.recyclerSize.setLayoutManager(layoutManager);
        mBinding.recyclerSize.setAdapter(adapterSize);
    }

    public void onClickItem(int position, int sizeNumber) {

        if (position != -1){ // size đã dc chọn
            mBinding.tvQuantity.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
            mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity);
            mBinding.btnMinusQuantity.setEnabled(true);
            mBinding.btnAddQuantity.setEnabled(true);
            // nếu số lượng hiên tại > tồn kho
            if(Integer.parseInt(mBinding.tvQuantity.getText().toString()) > sizeNumber){
                mBinding.tvQuantity.setText(String.valueOf(sizeNumber)); // xét lại số lượng tồn kho lên tv
                mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity_1);
            }

            // thêm số lượng
            mBinding.btnAddQuantity.setOnClickListener(view -> {
                int num = Integer.parseInt(mBinding.tvQuantity.getText().toString());

                if (num >= sizeNumber){
                    mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity_1);
                }else {
                    if(num == sizeNumber -1){
                        // khi click tăng số lượng lên = tồn khô -> số lượng lúc chưa tăng sẽ == tồn kho - 1 ,tăng lên = tồn kho sẽ xét bgr thành xám
                        mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity_1);
                    }
                    mBinding.ivMinusQuantity.setImageResource(R.drawable.ic_minus_quatity);
                    mBinding.tvQuantity.setText(String.valueOf(num + 1));
                }

            });

            // giảm số lượng
            mBinding.btnMinusQuantity.setOnClickListener(view -> {
                int num = Integer.parseInt(mBinding.tvQuantity.getText().toString());

                if (num <= 1){
                    mBinding.ivMinusQuantity.setImageResource(R.drawable.ic_minus_quatity_1);
                }else {
                    if (num == 2){
                        // khi click giảm số lượng = 1 -> số lượng lúc chưa giảm = 2 ,giảm = 1 sẽ xét bgr thành xám
                        mBinding.ivMinusQuantity.setImageResource(R.drawable.ic_minus_quatity_1);
                    }
                    mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity);
                    mBinding.tvQuantity.setText(String.valueOf(num - 1));
                }
            });
        }else {
            mBinding.btnMinusQuantity.setEnabled(false);
            mBinding.btnAddQuantity.setEnabled(false);
            mBinding.tvQuantity.setTextColor(ContextCompat.getColor(requireContext(), R.color.linear));
            mBinding.ivMinusQuantity.setImageResource(R.drawable.ic_minus_quatity_1);
            mBinding.ivAddQuantity.setImageResource(R.drawable.ic_add_quantity_1);
        }
    }

    public void addCart(){
        mProgressDialog = ProgressDialog.show(getContext(), "", AppConstants.LOADING);
        int quantity = Integer.parseInt(mBinding.tvQuantity.getText().toString());

        ApiService.API_SERVICE.insertCart(quantity , mProduct.getPrice(), mSize,  mUser.getUid(), mProduct.getId()).enqueue(new Callback<ResponseCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCart> call, @NonNull Response<ResponseCart> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    Toast.makeText(getContext(), AppConstants.ON_SUCCESS, Toast.LENGTH_SHORT).show();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    mPresenter.getListCartByIdUser(user.getUid()); // lúc thêm vào giỏ hàng load lại giỏ hàng
                    mProgressDialog.dismiss();
                    dismiss();
                }else {
                    Toast.makeText(getContext(), AppConstants.ON_FAILURE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCart> call, @NonNull Throwable t) {

            }
        });
    }

}
