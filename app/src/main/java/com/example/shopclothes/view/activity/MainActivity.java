package com.example.shopclothes.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityMainBinding;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.view.fragment.billFragment.BillFragment;
import com.example.shopclothes.view.fragment.homeFragment.HomeFragment;
import com.example.shopclothes.view.fragment.notificationFragment.NotificationFragment;
import com.example.shopclothes.view.fragment.settingsFragment.SettingsFragment;
import com.google.android.material.badge.BadgeDrawable;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.Date;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity implements ItemClickUtils.onLogoutListener {
    private ActivityMainBinding mBinding;
    private Socket mSocket;

    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESC = "My Channel Description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        switchFragment(new HomeFragment());
        onClick();
        getSelectIntent();
        setBadgerNotification();

        try {
            mSocket = IO.socket("http://192.168.1.6:3000");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        onListenSocket();
    }



    private void onListenSocket() {
        // Handle events from the server
        mSocket.on("notification", args -> {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                // Xử lý dữ liệu nhận được từ máy chủ
                try {
                    String orderId = data.getString("orderId");
                    String message = data.getString("message");
                    String userId = data.getString("userId");

                    // Xử lý dữ liệu ở đây

                    createNotification("Thông báo", message);

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void createNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // For Android Oreo and above
//            NotificationChannel channel = new NotificationChannel(
//                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(CHANNEL_DESC);
//            notificationManager.createNotificationChannel(channel);
//        }

        // Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bill)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        notificationManager.notify(/* notificationId */ getIdNotification(), builder.build());
    }

    private int getIdNotification () {
        return (int) new Date().getTime();
    }

    // chuyển từ màn finish order sang home -> xét lại fragment bill
    public void getSelectIntent(){
        Intent intent = getIntent();
        if (intent != null){
            int selectFragment = intent.getIntExtra("BILL", 0);
           if (selectFragment == 2){
               switchFragment(new BillFragment());
               switchIcon(R.id.btn_bill);
           }

        }
    }

    private void setBadgerNotification() {
        BadgeDrawable badgeDrawable = mBinding.bottomNavigationView.getOrCreateBadge(R.id.btn_bell);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(5);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick() {
        mBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.btn_home:
                    switchFragment(new HomeFragment());
                    switchIcon(R.id.btn_home);
                    break;
                case R.id.btn_bill:
                    switchFragment(new BillFragment());
                    switchIcon(R.id.btn_bill);
                    break;
                case R.id.btn_bell:
                    switchFragment(new NotificationFragment());
                    switchIcon(R.id.btn_bell);
                    break;
                case R.id.btn_settings:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    settingsFragment.setLogoutListener(this);
                    switchFragment(settingsFragment);
                    switchIcon(R.id.btn_settings);
                    break;
            }

            return true;
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    @Override
    public void onLogout() {
        finishAffinity();
    }
}