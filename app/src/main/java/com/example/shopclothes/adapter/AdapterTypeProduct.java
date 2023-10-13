package com.example.shopclothes.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopclothes.databinding.ItemTypeProductBinding;
import com.example.shopclothes.model.TypeProduct;
import java.util.List;

public class AdapterTypeProduct extends RecyclerView.Adapter<AdapterTypeProduct.ViewHolder> {

    List<TypeProduct> mList ;

    public AdapterTypeProduct(List<TypeProduct> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTypeProductBinding binding = ItemTypeProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypeProduct typeProduct = mList.get(position);
        holder.bind(typeProduct);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemTypeProductBinding binding;
        public ViewHolder(ItemTypeProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(TypeProduct typeProduct){
            binding.tvTypeProduct.setText(typeProduct.getNameTypeProduct());
        }
    }
}
