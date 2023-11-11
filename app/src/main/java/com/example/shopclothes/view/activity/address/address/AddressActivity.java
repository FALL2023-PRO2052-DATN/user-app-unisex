package com.example.shopclothes.view.activity.address.address;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterAddress;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityAddressBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.address.addAddress.AddAddressActivity;
import com.example.shopclothes.view.activity.address.updateAddress.UpdateAddressActivity;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressContract.View {
    private ActivityAddressBinding addressBinding;
    private AddressContract.Presenter mPresenter;
    private AdapterAddress adapterAddress;
    private int idDefault = 0;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBinding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(addressBinding.getRoot());
        mPresenter = new AddressPresenter(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.getListAddressAll(user.getUid());
        setListener();
    }

    public void setListener(){
        addressBinding.btnBack.setOnClickListener(view -> onBackPressed());
        addressBinding.layoutAddAddress.setOnClickListener(view -> nextScreen());
    }
    public void nextScreen(){
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra("id", idDefault);
        startActivity(intent);
    }

    @Override
    public void onListAddressAll(List<Address> list) {
        adapterAddress = new AdapterAddress(this, mPresenter, this);
        adapterAddress.setAddressList(list);
        List<Address> addressListWithDefaultStatus1 = new ArrayList<>();
        List<Address> addressListWithOtherStatus = new ArrayList<>();

        for (Address address : list) {
            if (address.getDefaultStatus() == 1) {
                addressListWithDefaultStatus1.add(address);
            } else {
                addressListWithOtherStatus.add(address);
            }
        }

        // Đảo vị trí các đối tượng có getDefaultStatus == 1 lên đầu danh sách
        addressListWithDefaultStatus1.addAll(addressListWithOtherStatus);

        adapterAddress.setAddressList(addressListWithDefaultStatus1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressBinding.rcvAddress.setLayoutManager(layoutManager);
        addressBinding.rcvAddress.setAdapter(adapterAddress);
        getDefaultStatus(list);
    }

    public void getDefaultStatus(List<Address> listAddress){
        for (Address address : listAddress) {
            if (address.getDefaultStatus() == 1) {
                idDefault = address.getId();
            }
        }
    }

    @Override
    public void launchAddressUpdateActivity(Address address) {
        Intent intent = new Intent(this, UpdateAddressActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("idDefault", idDefault);
        startActivity(intent);
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(addressBinding.getRoot(), message);
    }

    public void onResume(){
        super.onResume();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        mPresenter.getListAddressAll(firebaseUser.getUid());
    }
}