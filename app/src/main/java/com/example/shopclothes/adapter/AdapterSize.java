package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ItemSizeProductBinding;
import com.example.shopclothes.model.Size;
import com.example.shopclothes.utils.ItemClickUtils;
import java.util.List;

public class AdapterSize extends RecyclerView.Adapter<AdapterSize.ViewHolder> {

    private final List<Size> mList;
    private int selectedItem = -1; // vị trí check item
    private final ItemClickUtils itemClickUtils;
    public AdapterSize(List<Size> mList, ItemClickUtils itemClickUtils) {
        this.mList = mList;
        this.itemClickUtils = itemClickUtils;
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
            selectedItem = holder.getBindingAdapterPosition();
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
