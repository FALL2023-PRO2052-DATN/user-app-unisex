package com.example.shopclothes.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemCancelBinding;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.fragment.billFragment.ResponseBill;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBottomSheetBill extends BottomSheetDialogFragment {
    private ItemCancelBinding mBinding;
    private String id;
    private  String selectedText = ""; // text radio được chọn
    private ProgressDialog mProgressDialog;
    private ItemClickUtils.MyBottomSheetBill myBottomSheetBill;
    public void setId(String id) {
        this.id = id;
    }
    public void cancelBottomSheet(ItemClickUtils.MyBottomSheetBill myBottomSheetBill) {
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
        });
    }
    private void isCheckRadio() {
        // bắt sự kiện chọn radio
        mBinding.radioGroupBill.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != View.NO_ID ){ // nếu radio dc chọn
                RadioButton selectedRadioButton = mBinding.getRoot().findViewById(checkedId);
                selectedText = selectedRadioButton.getText().toString();
                mBinding.btnApplyReasonCancel.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary));
                mBinding.tvApplyReasonCancel.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            }
        });

    }
    // gọi api update lí do hủy của đơn hàng
    private void callApiCancelBill(String reasonCancel) {
        ApiService.API_SERVICE.cancelBill(id, reasonCancel).enqueue(new Callback<ResponseBill>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBill> call, @NonNull Response<ResponseBill> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())){
                    mProgressDialog.dismiss();
                    dismiss();
                    nextCancelFragment();
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
    // gọi xong api hủy chuyển qua fragment hủy đơn hàng
    private void nextCancelFragment() {
        myBottomSheetBill.onBottomSheetDismissed();
    }


}
