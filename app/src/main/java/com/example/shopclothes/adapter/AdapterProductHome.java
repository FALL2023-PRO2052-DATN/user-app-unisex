package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemProductBinding;
import com.example.shopclothes.model.Product;

import java.util.List;

public class AdapterProductHome extends RecyclerView.Adapter<AdapterProductHome.viewHolder> {
    private final List<Product> mList;
    public AdapterProductHome(List<Product> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding mBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Product product = mList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ItemProductBinding binding;
        String phanTram = "%";
        String TYPE = "Unisex-";
        public viewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        @SuppressLint("SetTextI18n")
        public void bind(Product product){
            Glide.with(binding.getRoot()).load(product.getImageProduct()).into(binding.ivProduct);
            binding.tvNameProduct.setText(product.getNameProduct());
            binding.tvPriceProduct.setPaintFlags(binding.tvPriceProduct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.tvPriceProduct.setText(String.valueOf(product.getPrice()));
            binding.tvTypeProduct.setText(TYPE+product.getNameTypeProduct());
            binding.tvPriceSalesProduct.setText(String.valueOf(product.getPrice() * product.getSale() / 100));
            binding.tvNumberSales.setText(product.getSale() + phanTram);
        }
    }
}
