package com.example.shopclothes.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemNotificationBinding;
import com.example.shopclothes.model.Notification;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.view.fragment.notificationFragment.NotificationContract;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {

    private final List<Notification> mList;
    private final NotificationContract.Presenter mPresenter;

    public AdapterNotification(List<Notification> mList, NotificationContract.Presenter mPresenter) {
        this.mList = mList;
        this.mPresenter = mPresenter;
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

    public void deleteItem(int position) {
        mPresenter.deleteNotification(mList.get(position).getId());
        mList.remove(position);
        notifyItemRemoved(position);
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
