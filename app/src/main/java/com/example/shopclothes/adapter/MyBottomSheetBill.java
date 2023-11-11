package com.example.shopclothes.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemAddCartBinding;
import com.example.shopclothes.databinding.ItemBillBinding;
import com.example.shopclothes.databinding.ItemCancelBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductContract;
import com.example.shopclothes.view.fragment.billFragment.ResponseBill;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBottomSheetBill extends BottomSheetDialogFragment {

    private ItemCancelBinding mBinding;
    private String id;
    private  String selectedText = "";
    private ProgressDialog mProgressDialog;

    private ItemClickUtils.MyBottomSheetBill myBottomSheetBill;

    public void setId(String id) {
        this.id = id;
    }

    public void dismissBottomSheet(ItemClickUtils.MyBottomSheetBill myBottomSheetBill) {
        this.myBottomSheetBill = myBottomSheetBill;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBinding = ItemCancelBinding.inflate(LayoutInflater.from(getContext()));
        mBottomSheetDialog.setContentView(mBinding.getRoot());
        isCheckRadio();
        onClick();
        return mBottomSheetDialog;
    }
    private void onClick() {
        mBinding.btnApplyReasonCancel.setOnClickListener(view -> {
            mProgressDialog = ProgressDialog.show(getContext(), "", AppConstants.LOADING);
            callApiCancelBill(AppConstants.USER + selectedText);
            myBottomSheetBill.onBottomSheetDismissed();
        });
    }
    private void isCheckRadio() {

        mBinding.radioGroupBill.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != View.NO_ID ){

                RadioButton selectedRadioButton = mBinding.getRoot().findViewById(checkedId);
                selectedText = selectedRadioButton.getText().toString();

                mBinding.btnApplyReasonCancel.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary));
                mBinding.tvApplyReasonCancel.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            }
        });

    }
    private void callApiCancelBill(String reasonCancel) {
        ApiService.API_SERVICE.cancelBill(id, reasonCancel).enqueue(new Callback<ResponseBill>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBill> call, @NonNull Response<ResponseBill> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mProgressDialog.dismiss();
                    dismiss();

                }else {
                    dismiss();
                    UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_FAILURE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBill> call, @NonNull Throwable t) {

            }
        });
    }


}
