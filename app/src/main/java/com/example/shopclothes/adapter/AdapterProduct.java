package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemProductGridBinding;
import com.example.shopclothes.databinding.ItemProductLinearBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductActivity;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LINEAR = 1;
    private static final int TYPE_GRID = 2;
    private final List<Product> mList;
    private final int type;
    private final Context mContext;
    private ItemProductLinearBinding mBindingLinear;
    private ItemProductGridBinding mBindingGrid;
    public AdapterProduct(List<Product> list, int type, Context context) {
        this.mList = list;
        this.type = type;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_LINEAR == viewType){
            mBindingLinear = ItemProductLinearBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LinearViewHolder(mBindingLinear);
        } else  if (TYPE_GRID == viewType){
            mBindingGrid = ItemProductGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new GridViewHolder(mBindingGrid);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Product product = mList.get(position);
        if (product == null){
            return;
        }

        if (TYPE_LINEAR == holder.getItemViewType()){
            LinearViewHolder linearViewHolder = (LinearViewHolder) holder;
            linearViewHolder.bind(product);
            ItemProductLinearBinding binding = linearViewHolder.binding;
            binding.layoutProductLinear.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("id", product.getId());
                mContext.startActivity(intent);
            });

        } else if (TYPE_GRID == holder.getItemViewType()){
            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            gridViewHolder.bind(product);
            ItemProductGridBinding binding = gridViewHolder.binding;
            binding.layoutProductGrid.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("id", product.getId());
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1){
            return TYPE_LINEAR;
        }else {
            return TYPE_GRID;
        }
    }

    public static class LinearViewHolder extends RecyclerView.ViewHolder{
        ItemProductLinearBinding binding;
        public LinearViewHolder(ItemProductLinearBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        @SuppressLint("SetTextI18n")
        public void bind(Product product){
            Glide.with(binding.getRoot()).load(product.getImageProduct()).into(binding.ivProductLinear);
            binding.tvNameProductLinear.setText(product.getNameProduct());
            binding.tvPriceProductLinear.setPaintFlags(binding.tvPriceProductLinear.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvPriceProductLinear.setText(FormatUtils.formatCurrency(product.getPrice()));
            binding.tvTypeProductLinear.setText(AppConstants.UNISEX + product.getNameTypeProduct());
            binding.tvPriceSalesProductLinear.setText(FormatUtils.formatCurrency(product.getPrice() - (product.getPrice() * product.getSale() / 100)));
            if (product.getSale() == 0){
                binding.tvNumberSalesLinear.setVisibility(View.GONE);
            }else {
                binding.tvNumberSalesLinear.setText(product.getSale() + AppConstants.PERCENT);
            }
        }
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder{
        ItemProductGridBinding binding;
        public GridViewHolder(ItemProductGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        @SuppressLint("SetTextI18n")
        public void bind(Product product){
            Glide.with(binding.getRoot()).load(product.getImageProduct()).into(binding.ivProductGrid);
            binding.tvNameProductGrid.setText(product.getNameProduct());
            binding.tvPriceProductGrid.setPaintFlags(binding.tvPriceProductGrid.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvPriceProductGrid.setText(FormatUtils.formatCurrency(product.getPrice()));
            binding.tvTypeProductGrid.setText(AppConstants.UNISEX + product.getNameTypeProduct());
            binding.tvPriceSalesProductGrid.setText(FormatUtils.formatCurrency(product.getPrice() - (product.getPrice() * product.getSale() / 100)));
            if (product.getSale() == 0){
                binding.tvNumberSalesGrid.setVisibility(View.GONE);
            }else {
                binding.tvNumberSalesGrid.setText(product.getSale() + AppConstants.PERCENT);
            }
        }
    }
}
