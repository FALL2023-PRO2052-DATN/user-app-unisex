package com.example.shopclothes.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemNotificationBinding;
import com.example.shopclothes.model.Notification;
import com.example.shopclothes.utils.FormatUtils;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {

    private final List<Notification> mList;

    public AdapterNotification(List<Notification> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding mBinding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = mList.get(position);
        if (notification == null){
          return;
        }
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding mBinding;
        public ViewHolder( ItemNotificationBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(Notification notification){
            Glide.with(mBinding.getRoot()).load(notification.getImage()).into(mBinding.ivNotification);
            mBinding.tvTitleNotification.setText(notification.getTitle());
            mBinding.tvContentNotification.setText(notification.getContent());
            mBinding.tvDataNotification.setText(FormatUtils.formatDate(notification.getDate()));
        }
    }
}
