package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ItemTypeProductBinding;
import com.example.shopclothes.model.TypeProduct;
import com.example.shopclothes.view.fragment.homeFragment.HomeContract;

import java.util.List;

public class AdapterTypeProduct extends RecyclerView.Adapter<AdapterTypeProduct.ViewHolder> {

    List<TypeProduct> mList ;
    HomeContract.View view;
    Context context;
    private int selectedItem = RecyclerView.NO_POSITION;
    public AdapterTypeProduct(List<TypeProduct> list, HomeContract.View view, Context context ) {
        this.mList = list;
        this.view = view;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTypeProductBinding binding = ItemTypeProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TypeProduct typeProduct = mList.get(position);
            holder.bind(typeProduct, view);
            holder.binding.btnTypeProduct.setOnClickListener(view1 -> {
                view.onItemClickListener(typeProduct.getId());
                selectedItem = holder.getAdapterPosition();
                notifyDataSetChanged();
            });
            holder.binding.btnTypeProduct.setCardBackgroundColor(ContextCompat.getColor(context,
                    position == selectedItem ? R.color.primary : R.color.white
            ));
            holder.binding.tvTypeProduct.setTextColor(ContextCompat.getColor(context,
                    position == selectedItem ? R.color.white : R.color.linear));
        }
    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemTypeProductBinding binding;

        public ViewHolder(ItemTypeProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        @SuppressLint("ResourceAsColor")
        public void bind(TypeProduct typeProduct, HomeContract.View view){
            binding.tvTypeProduct.setText(typeProduct.getNameTypeProduct());
//            binding.btnTypeProduct.setOnClickListener(view1 -> view.onItemClickListener(typeProduct.getId()));
        }
    }
}
