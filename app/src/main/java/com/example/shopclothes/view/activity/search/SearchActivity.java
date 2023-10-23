package com.example.shopclothes.view.activity.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shopclothes.adapter.AdapterSearch;
import com.example.shopclothes.databinding.ActivitySearchBinding;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.utils.ValidateUtils;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements SearchContract.View {
    private ActivitySearchBinding binding;
    private SearchContract.Presenter mPresenter;
    private List<Product> products;
    private AdapterSearch adapterSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mPresenter = new SearchPresenter(this);
        mPresenter.getListProductAll();
        setListeners();
    }

    private void setListeners(){
        binding.ivBack.setOnClickListener(view -> onBackPressed());
        binding.ivSearch.setOnClickListener(view -> performSearch());
    }

    private void performSearch() {
        String searchText =binding.etSearch.getText().toString().trim();
        if (ValidateUtils.isDataInputEmpty(searchText)){
            showUI(View.GONE, View.VISIBLE);
        }else{
            List<Product> filteredListProduct = new ArrayList<>();
            for (Product product : products){
                if (product.getNameProduct().toLowerCase().contains(searchText.toLowerCase())){
                    filteredListProduct.add(product);

                }
            }
            adapterSearch.setProductSearchList(filteredListProduct, this);

            if (filteredListProduct.size() == 0){
                showUI(View.GONE, View.VISIBLE);
            }else {
                showUI(View.VISIBLE, View.GONE);
            }
        }
    }

    private void showUI(int visibleRecyclerView, int visibleEmptyText){
        binding.recyclerView.setVisibility(visibleRecyclerView);
        binding.tvEmpty.setVisibility(visibleEmptyText);
    }


    @Override
    public void onListProductAll(List<Product> list) {
        adapterSearch = new AdapterSearch();
        RecyclerView recyclerView = binding.recyclerView;
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSearch);
        products = list;
    }

    @Override
    public void nextDetailActivity(int id) {
        // sử lý hàm khi dc gọi
        Intent intent = new Intent(this, DetailProductActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}