package com.example.shopclothes.view.fragment.homeFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shopclothes.adapter.AdapterBanner;
import com.example.shopclothes.databinding.FragmentHomeBinding;
import com.example.shopclothes.model.Banner;
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
    }

    @Override
    public void onListBanner(List<Banner> list) {
        AdapterBanner adapterBanner = new AdapterBanner(list);
        mBinding.vpBanner.setAdapter(adapterBanner);
        mPresenter.autoNextBanner(mBinding.vpBanner, list);
    }
}