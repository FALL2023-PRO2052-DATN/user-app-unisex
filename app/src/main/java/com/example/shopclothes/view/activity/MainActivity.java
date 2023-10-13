package com.example.shopclothes.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityMainBinding;
import com.example.shopclothes.view.fragment.BillFragment.BillFragment;
import com.example.shopclothes.view.fragment.homeFragment.HomeFragment;
import com.example.shopclothes.view.fragment.settingsFragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private Fragment home, bill, bell, settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initFragment();
        switchFragment(home);
        onClick();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick() {
        mBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.btn_home:
                    switchFragment(home);
                    switchIcon(R.id.btn_home);
                    break;
                case R.id.btn_bill:
                    switchFragment(bill);
                    switchIcon(R.id.btn_bill);
                    break;
                case R.id.btn_bell:
                    switchFragment(bell);
                    switchIcon(R.id.btn_bell);
                    break;
                case R.id.btn_settings:
                    switchFragment(settings);
                    switchIcon(R.id.btn_settings);
                    break;
            }
            return false;
        });
    }

    public void initFragment() {
        home = new HomeFragment();
        bill = new BillFragment();
        bell = new BillFragment();
        settings = new SettingsFragment();
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void switchIcon(int id){
        mBinding.bottomNavigationView.getMenu().findItem(R.id.btn_home).setIcon(
                id == R.id.btn_home ? R.drawable.ic_home_black : R.drawable.ic_home
        );
        mBinding.bottomNavigationView.getMenu().findItem(R.id.btn_bill).setIcon(
                id == R.id.btn_bill ? R.drawable.ic_black_bill : R.drawable.ic_bill
        );
        mBinding.bottomNavigationView.getMenu().findItem(R.id.btn_bell).setIcon(
                id == R.id.btn_bell ? R.drawable.ic_black_bell_ringing : R.drawable.ic_bell_ringing
        );
        mBinding.bottomNavigationView.getMenu().findItem(R.id.btn_settings).setIcon(
                id == R.id.btn_settings ? R.drawable.ic_black_setting : R.drawable.ic_settings
        );
    }

}