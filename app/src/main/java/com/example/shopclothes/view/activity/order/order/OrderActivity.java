package com.example.shopclothes.view.activity.order.order;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterOrder;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.ActivityOrtherBinding;
import com.example.shopclothes.model.Address;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Discount;
import com.example.shopclothes.utils.FormatUtils;
import com.example.shopclothes.utils.UIUtils;
import com.example.shopclothes.view.activity.address.address.AddressActivity;
import com.example.shopclothes.view.activity.order.finishOrder.FinishOrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.List;

public class OrderActivity extends AppCompatActivity implements OrderContract.View {
    private OrderContract.Presenter mPresenter;
    private ActivityOrtherBinding mBinding;
    private Intent intent;
    private ProgressDialog mProgressDialog;
    private Discount mDiscount;
    private Address mAddress;
    private List<Cart> mCartList;
    private PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrtherBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mPresenter = new OrderPresenter(this);
        PaymentConfiguration.init(this, AppConstants.PUBLISHABLE_KEY);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        onListProduct();
        initPresenter();
        onClick();
    }

    @Override
    public void onClick() {
        mBinding.ivNextOther.setOnClickListener(view -> {
           Intent intent1 = new Intent(this, AddressActivity.class);
           mLauncher.launch(intent1);
        });
        mBinding.ivBackOrder.setOnClickListener(view -> onBackPressed());
        mBinding.btnApply.setOnClickListener(view ->{
            mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
            mPresenter.readDiscountById(mBinding.etApply.getText().toString().trim());
        } );
        mBinding.btnOrder.setOnClickListener(view -> insertOrderActivity());
    }

    @Override
    public void initPresenter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readAddress(user.getUid());}

    @SuppressLint("SetTextI18n")
    @Override
    public void onListProduct() {
        intent = getIntent();
        List<Cart> cartList = intent.getParcelableArrayListExtra("listCart");
        AdapterOrder adapterOrder = new AdapterOrder(cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewProductOther.setLayoutManager(layoutManager);
        mBinding.recyclerViewProductOther.setAdapter(adapterOrder);
        setValue(intent, cartList);
        mCartList = cartList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAddress(Address address) {
        if (address != null){
            mBinding.tvNameOther.setText(address.getName());
            mBinding.tvPhoneOther.setText( "(84+) " +address.getPhone());
            mBinding.tvEmailOther.setText(address.getEmail());
            mBinding.tvAddressOther.setText(address.getAddress());
            mBinding.btnOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
            mBinding.btnOrder.setEnabled(true);
            mAddress = address;
        }else {
            mBinding.tvNameOther.setText(AppConstants.NO_INFORMATION);
            mBinding.tvPhoneOther.setText("");
            mBinding.tvEmailOther.setText("");
            mBinding.tvAddressOther.setText("");
            mBinding.btnOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.linear));
            mBinding.btnOrder.setEnabled(false);
        }
    }

    @Override
    public void onDiscount(Discount discount) {
        mProgressDialog.dismiss();
        double price = FormatUtils.parseCurrency(intent.getStringExtra("sumPrice"));
        double discountPrice = price * discount.getPercent() / 100;
        mBinding.tvDiscountOrder.setText(FormatUtils.formatCurrency(discountPrice));
        mBinding.tvSumPriceAllOrder.setText(FormatUtils.formatCurrency(price - discountPrice));
        mBinding.tvPriceOrder.setText(mBinding.tvSumPriceAllOrder.getText().toString());
        mDiscount = discount;
    }

    @Override
    public void onInsertOrder(String idOrder) {
        for (int i = 0 ; i < mCartList.size(); i++){
            mPresenter.insertOrderDetail(mCartList.get(i).getSize(), mCartList.get(i).getQuantity(),
                    (mCartList.get(i).getPrice() - (mCartList.get(i).getPrice() * mCartList.get(i).getDiscount() / 100)) * mCartList.get(i).getQuantity() ,  idOrder, mCartList.get(i).getIdProduct());
        }
        UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_SUCCESS);
        mProgressDialog.dismiss();
        mPresenter.nextActivity(this, FinishOrderActivity.class);
        finish();
    }

    @Override
    public void insertOrderActivity() {
        mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
        String note = mBinding.etNoteOther.getText().toString();
        String idDiscount = null;
        String deliveryStatus = AppConstants.DELIVERY_STATUS_WAIT_CONFIRM;

        double price = FormatUtils.parseCurrency(mBinding.tvPriceOrder.getText().toString());

        if (mDiscount != null){
            idDiscount = String.valueOf(mDiscount.getId());
        }
        int idAddress = mAddress.getId();

        if (mBinding.radioOf.isChecked()){
             String payments = mBinding.tvOf.getText().toString();
             String peacefulState = AppConstants.PEACEFUL_STATE_NOT;
             mPresenter.insertOrder(FormatUtils.formatID(), note, payments, deliveryStatus, "" ,price, idDiscount, idAddress  ,peacefulState, mCartList.size());
        }else {
            mPresenter.getCustomerId(FormatUtils.formatCurrencyForInt(price));
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void setValue(Intent intent, List<Cart> cartList) {
        mBinding.tvSumPriceOrder.setText(String.valueOf(intent.getStringExtra("sumPrice")));
        mBinding.tvCountProductOrder.setText("Tổng số tiền ("+cartList.size()+" sản phẩm):");
        mBinding.tvPriceProductOrder.setText(intent.getStringExtra("sumPrice"));
        mBinding.tvSumPriceAllOrder.setText(intent.getStringExtra("sumPrice"));
        mBinding.tvPriceOrder.setText(intent.getStringExtra("sumPrice"));
    }

    @Override
    public void onMessage(String message) {
        UIUtils.showMessage(mBinding.getRoot(), message);
    }

    @Override
    public void onInsertDetailOrder(boolean check) {
       if (!check){
           UIUtils.showMessage(mBinding.getRoot(), AppConstants.ON_FAILURE);
       }
    }

    @Override
    public void paymentFlow(String customerId, String ephemeralKey, String clientSelect) {
        PaymentSheet.CustomerConfiguration customerConfig = new PaymentSheet.CustomerConfiguration(
                customerId,
                ephemeralKey
        );
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Shop Unisex")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                clientSelect,
                configuration
        );
    }

    @SuppressLint("SetTextI18n")
    private final ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    assert intent != null;
                    Address address = (Address) intent.getSerializableExtra("data_address");
                    mBinding.tvNameOther.setText(address.getName());
                    mBinding.tvPhoneOther.setText( "(84+) " +address.getPhone());
                    mBinding.tvEmailOther.setText(address.getEmail());
                    mBinding.tvAddressOther.setText(address.getAddress());
                    mBinding.btnOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
                    mBinding.btnOrder.setEnabled(true);
                }

    });
    @Override
    public void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
        // implemented in the next steps
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            UIUtils.showMessage(mBinding.getRoot(), AppConstants.CANCEL_PAYMENT);
            mProgressDialog.dismiss();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e("TagERR", "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
            mProgressDialog.dismiss();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {

            String note = mBinding.etNoteOther.getText().toString();
            String idDiscount = null;
            String deliveryStatus = AppConstants.DELIVERY_STATUS_WAIT_CONFIRM;
            double price = FormatUtils.parseCurrency(mBinding.tvPriceOrder.getText().toString());
            if (mDiscount != null){
                idDiscount = String.valueOf(mDiscount.getId());
            }
            int idAddress = mAddress.getId();
            String payments = mBinding.tvOn.getText().toString();
            String peacefulState = AppConstants.PEACEFUL_STATE_OK;
            mPresenter.insertOrder(FormatUtils.formatID(), note, payments, deliveryStatus, "" ,price, idDiscount, idAddress  ,peacefulState, mCartList.size());
        }
    }
}