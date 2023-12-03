package com.example.shopclothes.view.activity.order.order;

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
import java.util.Objects;

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

        UIUtils.openLayout(mBinding.ivLoadingOrderActivity, mBinding.layoutOrderActivity, false, this);
        mPresenter = new OrderPresenter(this);

        PaymentConfiguration.init(this, AppConstants.PUBLISHABLE_KEY);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        onClick();
        initPresenter();
        onListProduct();
    }

    @Override
    public void onClick() {
        mBinding.layoutInformation.setOnClickListener(view -> {
           Intent intent1 = new Intent(this, AddressActivity.class);
           mLauncher.launch(intent1);
        });
        mBinding.ivBackOrder.setOnClickListener(view -> onBackPressed());
        mBinding.btnApply.setOnClickListener(view ->{
            mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
           if (!mBinding.etApply.getText().toString().trim().equals("")){
                mPresenter.readDiscountById(mBinding.etApply.getText().toString().trim());
            }else {
               UIUtils.showMessage(mBinding.getRoot(), AppConstants.DISCOUNT_EMPTY);
               mProgressDialog.dismiss();
           }
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
        String jsonCartList = intent.getStringExtra("listCart");
        Log.i("TAG", "onListProduct: " + jsonCartList);
        mPresenter.readListProductByListId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), jsonCartList);
    }
    @Override
    public void setAdapter(List<Cart> cartList){
        AdapterOrder adapterOrder = new AdapterOrder(cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerViewProductOther.setLayoutManager(layoutManager);
        mBinding.recyclerViewProductOther.setAdapter(adapterOrder);
        setValue(intent, cartList);
        mCartList = cartList;
        UIUtils.openLayout(mBinding.ivLoadingOrderActivity, mBinding.layoutOrderActivity, true, this);
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
        if (discount != null){
            double price = FormatUtils.parseCurrency(intent.getStringExtra("sumPrice"));
            double discountPrice = price * discount.getPercent() / 100;
            mBinding.tvDiscountOrder.setText(FormatUtils.formatCurrency(discountPrice));
            mBinding.tvSumPriceAllOrder.setText(FormatUtils.formatCurrency(price - discountPrice));
            mBinding.tvPriceOrder.setText(mBinding.tvSumPriceAllOrder.getText().toString());
        }else {
            UIUtils.showMessage(mBinding.getRoot(), AppConstants.DISCOUNT_NOT_FOUND);
            UIUtils.clearText(mBinding.etApply);
        }
        mDiscount = discount;
    }

    @Override
    public void onInsertOrder(String idOrder) { // dc gọi khi hàm insertOrder gọi thành công
        // thêm đơn hàng chi tiết
        for (int i = 0 ; i < mCartList.size(); i++){
            mPresenter.insertOrderDetail(mCartList.get(i).getSize(), mCartList.get(i).getQuantity(),
                    (mCartList.get(i).getPrice() - (mCartList.get(i).getPrice() * mCartList.get(i).getDiscount() / 100)) * mCartList.get(i).getQuantity() ,  idOrder, mCartList.get(i).getIdProduct());
        }
        mProgressDialog.dismiss();
        mPresenter.nextActivity(this, FinishOrderActivity.class);
        finish();
    }

    @Override
    public void insertOrderActivity() { // bắt sự kiện khi click nút thanh toán
        mProgressDialog = ProgressDialog.show(this, "", AppConstants.LOADING);
        double price = FormatUtils.parseCurrency(mBinding.tvPriceOrder.getText().toString());
        // nếu radio check là thanh toán khi giao hàng
        if (mBinding.radioOf.isChecked()){
            String note = mBinding.etNoteOther.getText().toString();
            String idDiscount = null;
            String deliveryStatus = AppConstants.DELIVERY_STATUS_WAIT_CONFIRM;
            if (mDiscount != null){
                idDiscount = String.valueOf(mDiscount.getId());
            }
            int idAddress = mAddress.getId();
             String payments = mBinding.tvOf.getText().toString();
             String peacefulState = AppConstants.PEACEFUL_STATE_NOT;
             mPresenter.insertOrder(FormatUtils.formatID(), note, payments, deliveryStatus, "" ,price, idDiscount, idAddress  ,peacefulState, mCartList.size());
        }else {
            mPresenter.getCustomerId(FormatUtils.formatCurrencyForInt(price)); // lấy id người dùng thanh toán để mở giao diện thanh toán
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
        mProgressDialog.dismiss();
    }

    // nhận địa chỉ trả về
    private final ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    assert intent != null;
                    Address address = (Address) intent.getSerializableExtra("data_address");
                    if (address != null){
                        updateUIWithAddress(address);
                    }
                }

    });
    @SuppressLint("SetTextI18n")
    private void updateUIWithAddress(Address address) {
        mBinding.tvNameOther.setText(address.getName());
        mBinding.tvPhoneOther.setText(AppConstants.PHONE + address.getPhone());
        mBinding.tvEmailOther.setText(address.getEmail());
        mBinding.tvAddressOther.setText(address.getAddress());
        mBinding.btnOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
        mBinding.btnOrder.setEnabled(true);
        mAddress = address;
    }

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