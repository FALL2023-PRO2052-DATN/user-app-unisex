package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ItemSizeProductBinding;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductContract;

import java.util.List;

public class AdapterSize extends RecyclerView.Adapter<AdapterSize.ViewHolder> {

    private final List<Size> mList;
    private final Context mContext;
    private int selectedItem = -1;
    private final ItemClickUtils itemClickUtils;
    @SuppressLint("NotifyDataSetChanged")
    public AdapterSize(List<Size> mList, Context context, ItemClickUtils itemClickUtils) {
        this.mList = mList;
        this.mContext = context;
        this.itemClickUtils = itemClickUtils;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSizeProductBinding mBinding = ItemSizeProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ViewHolder(mBinding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Size size = mList.get(position);
        holder.bind(size);
        holder.mBinding.btnSizeProduct.setOnClickListener(view1 -> {

            selectedItem = holder.getAdapterPosition();
            notifyDataSetChanged();

            itemClickUtils.onItemClickListener(size.getQuantity(), selectedItem, holder.mBinding.tvSizeProduct.getText().toString());
        });
        holder.mBinding.btnSizeProduct.setBackgroundResource(
                position == selectedItem ? R.drawable.bg_size : R.color.gray1
                        );
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSizeProductBinding mBinding;
        public ViewHolder(ItemSizeProductBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
        public void bind(Size size){
            mBinding.tvSizeProduct.setText(size.getName());
        }
    }
}
