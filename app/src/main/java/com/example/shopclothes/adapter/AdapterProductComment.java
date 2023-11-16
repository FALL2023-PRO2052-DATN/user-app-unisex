package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemProductCommentBinding;
import com.example.shopclothes.model.ProductComment;
import java.util.List;

public class AdapterProductComment extends RecyclerView.Adapter<AdapterProductComment.ViewHolder> {
    private final List<ProductComment> mList;
    public AdapterProductComment(List<ProductComment> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductCommentBinding mBinding = ItemProductCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductComment productComment = mList.get(position);
        holder.bind(productComment);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductCommentBinding mBinding;
        public ViewHolder(ItemProductCommentBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(ProductComment productComment){
            Glide.with(mBinding.getRoot()).load(productComment.getImage()).into(mBinding.ivProductComment);
            mBinding.tvNameProductComment.setText(productComment.getName());
            mBinding.tvSizeComment.setText(AppConstants.SIZE + productComment.getSize());
        }
    }
}
