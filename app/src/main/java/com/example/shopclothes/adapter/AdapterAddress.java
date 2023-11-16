package com.example.shopclothes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopclothes.R;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ItemAddressBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.view.activity.address.address.AddressContract;

import java.util.List;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.AdapterViewHolder> {
    private List<Address> addressList;
    private final AddressContract.View view;
    private final AddressContract.Presenter mPresenter;
    private final Context context;

    public AdapterAddress(AddressContract.View view, AddressContract.Presenter mPresenter, Context context) {
        this.view = view;
        this.mPresenter = mPresenter;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAddressBinding addressBinding = ItemAddressBinding.inflate(layoutInflater,parent, false);
        return new AdapterViewHolder(addressBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Address address = addressList.get(position);
        if (address == null){
            return;
        }
        holder.bind(address);
        holder.itemAddressBinding.ivMenu.setOnClickListener(view -> showPopupEdit(holder, address));
        holder.itemAddressBinding.layoutAddress.setOnClickListener(view1 -> view.launchResultOrderActivity(address));
    }


    @SuppressLint("NonConstantResourceId")
    private void showPopupEdit(@NonNull AdapterViewHolder holder, Address address){
        PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.itemAddressBinding.ivMenu);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_update_delete, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.menu_update:
                    view.launchAddressUpdateActivity(address);
                    return true;
                case R.id.menu_delete:
                    showDeleteAddressConfirm(address);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return addressList == null ? 0 : addressList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showDeleteAddressConfirm(Address address) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(AppConstants.MESS_DELETE_ADDRESS);
        builder.setPositiveButton(AppConstants.OK, ((dialogInterface, i) -> {
            mPresenter.deleteAddress(address.getId());
            addressList.remove(address);
            notifyDataSetChanged();
        }));
        builder.setNegativeButton(AppConstants.CANCEL, ((dialogInterface, i) -> dialogInterface.dismiss()));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {
        ItemAddressBinding itemAddressBinding;
        public AdapterViewHolder(@NonNull ItemAddressBinding itemAddressBinding) {
            super(itemAddressBinding.getRoot());
            this.itemAddressBinding = itemAddressBinding;
        }
        @SuppressLint("SetTextI18n")
        public void bind(Address address){
            itemAddressBinding.tvNameAddress.setText(address.getName());
            itemAddressBinding.tvPhoneAddress.setText(AppConstants.PHONE + address.getPhone());
            itemAddressBinding.tvEmailAddress.setText(address.getEmail());
            itemAddressBinding.tvAddress.setText(address.getAddress());
            if (address.getDefaultStatus() == 1){
                itemAddressBinding.tvDefaultStatus.setText(AppConstants.DEFAULT);
            }else {
                itemAddressBinding.tvDefaultStatus.setVisibility(View.GONE);
            }
        }
    }
}
