package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shopclothes.databinding.ItemSearchBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.view.activity.search.SearchContract;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.SearchViewHolder> {
    private List<Product> productList;
    private SearchContract.View mView;
    @SuppressLint("NotifyDataSetChanged")
    public void setProductSearchList(List<Product> productList, SearchContract.View mView){
        this.productList = productList;
        this.mView = mView;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSearchBinding itemSearchBinding = ItemSearchBinding.inflate(layoutInflater, parent, false);
        return new SearchViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
        holder.searchBinding.detailProductSearch.setOnClickListener(view -> mView.nextDetailActivity(product.getId()));
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        ItemSearchBinding searchBinding;
        public SearchViewHolder(@NonNull ItemSearchBinding searchBinding) {
            super(searchBinding.getRoot());
            this.searchBinding = searchBinding;
        }
        public void bind(Product product){
            Glide.with(itemView.getContext())
                    .load(product.getImageProduct())
                    .into(searchBinding.ivProduct);
            searchBinding.nameProductSearch.setText(product.getNameProduct());

        }
    }
}
