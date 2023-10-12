package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemBannerBinding;
import com.example.shopclothes.model.Banner;

import java.util.List;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.viewHolder> {
    private final List<Banner> mList;
    @SuppressLint("NotifyDataSetChanged")
    public AdapterBanner(List<Banner> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding mBinding = ItemBannerBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new viewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Banner banner = mList.get(position);
        holder.bind(banner);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ItemBannerBinding binding;

        public viewHolder(ItemBannerBinding  binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Banner banner) {
          Glide.with(binding.getRoot()).load(banner.getAnh()).into(binding.ivBanner);
        }
    }
}
