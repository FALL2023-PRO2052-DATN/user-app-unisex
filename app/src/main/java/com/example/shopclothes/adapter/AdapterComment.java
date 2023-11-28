package com.example.shopclothes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemCommentBinding;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.utils.FormatUtils;
import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    private final List<Comment> mList;
    /*
       selectScreen = 1 -> màn comment của xem chi tiết sản phẩm
       selectScreen = 0 -> màn comment của xem tất cả đánh giá
     */
    private final int selectedScreen;

    public AdapterComment(List<Comment> mList, int selectedScreen) {
        this.mList = mList;
        this.selectedScreen = selectedScreen;
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
        /*
            nếu 1 - sét màn xem chi tiết sản phẩm , nếu list.size <= 3 thì là list , nếu lơn hơn 3 thí sét = 3
            nếu 0 -> sét cho mà đánh giá
         */
        return (selectedScreen == 1 && mList != null) ? Math.min(mList.size(), 3) : (mList != null ? mList.size() : 0);
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
            mBinding.ratingComment.setIsIndicator(true);
            mBinding.tvContentComment.setText(comment.getContentComment());
            mBinding.tvDatetimeComment.setText(FormatUtils.formatDate(comment.getDateComment()));
        }
    }
}
