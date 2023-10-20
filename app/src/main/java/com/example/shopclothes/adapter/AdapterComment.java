package com.example.shopclothes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemCommentBinding;
import com.example.shopclothes.model.Comment;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    private final List<Comment> mList;

    public AdapterComment(List<Comment> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return  mList != null ? Math.min(mList.size(), 3) : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding mBinding;
        public ViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Comment comment){
            Glide.with(mBinding.getRoot()).load(comment.getAvatarUser()).into(mBinding.ivComment);
            mBinding.tvNameComment.setText(comment.getNameUser());
            mBinding.ratingComment.setRating(comment.getPointComment());
            mBinding.tvContentComment.setText(comment.getContentComment());
            mBinding.tvDatetimeComment.setText(comment.getDateComment());
        }
    }
}
