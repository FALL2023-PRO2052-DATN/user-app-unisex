package com.example.shopclothes.view.fragment.homeFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shopclothes.adapter.AdapterBanner;
import com.example.shopclothes.adapter.AdapterProduct;
import com.example.shopclothes.adapter.AdapterTypeProduct;
import com.example.shopclothes.databinding.FragmentHomeBinding;
import com.example.shopclothes.model.Banner;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.TypeProduct;

import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View {
    FragmentHomeBinding mBinding;
    HomeContract.Presenter mPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mPresenter = new HomePresenter(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getListBanner();
        mPresenter.getListProductNew();
        mPresenter.getListProductOutstanding();
        mPresenter.getListProductAll();
        mPresenter.getListTypeProduct();
    }

    @Override
    public void onListBanner(List<Banner> list) {
        AdapterBanner adapterBanner = new AdapterBanner(list);
        mBinding.vpBanner.setAdapter(adapterBanner);
        mPresenter.autoNextBanner(mBinding.vpBanner, list);
    }

    @Override
    public void onListProductNew(List<Product> list) {
        AdapterProduct adapter= new AdapterProduct(list, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rcvProductNew.setLayoutManager(layoutManager);
        mBinding.rcvProductNew.setAdapter(adapter);
    }

    @Override
    public void onListProductOutstanding(List<Product> list) {
        AdapterProduct adapter = new AdapterProduct(list, 1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rcvProductOutstanding.setLayoutManager(layoutManager);
        mBinding.rcvProductOutstanding.setAdapter(adapter);
    }

    @Override
    public void onListProductAll(List<Product> list) {
        AdapterProduct adapter = new AdapterProduct(list, 2);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mBinding.rcvProductAll.setLayoutManager(layoutManager);
        mBinding.rcvProductAll.setAdapter(adapter);
    }

    @Override
    public void onListTypeProduct(List<TypeProduct> list) {
        AdapterTypeProduct adapter = new AdapterTypeProduct(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rcvProductCategory.setLayoutManager(layoutManager);
        mBinding.rcvProductCategory.setAdapter(adapter);
    }
}