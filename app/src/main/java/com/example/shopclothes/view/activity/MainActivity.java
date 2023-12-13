package com.example.shopclothes.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopclothes.R;
import com.example.shopclothes.databinding.ActivityMainBinding;
import com.example.shopclothes.utils.ItemClickUtils;
import com.example.shopclothes.utils.MyApplication;
import com.example.shopclothes.view.fragment.billFragment.BillFragment;
import com.example.shopclothes.view.fragment.homeFragment.HomeFragment;
import com.example.shopclothes.view.fragment.notificationFragment.NotificationFragment;
import com.example.shopclothes.view.fragment.settingsFragment.SettingsFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        switchFragment(new HomeFragment());
        onClick();
        setBadgerNotification();

        try {
            mSocket = IO.socket("http://192.168.0.104:3000");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        onListenSocket();
    }

    private void onListenSocket() {
        // Handle events from the server
        mSocket.on("notification", args -> runOnUiThread(() -> {
            JSONObject data = (JSONObject) args[0];
            // Xử lý dữ liệu nhận được từ máy chủ
            try {
                String message = data.getString("message");
                String userId = data.getString("userId");

                // Xử lý dữ liệu ở đây
                Log.d("Socket", userId);
                if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(userId)){
                    createNotification("Thông báo", message);
                }
                } catch (JSONException e) {
                e.printStackTrace();
            }
        }));
    }

    public void createNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }
        // PendingIntent để chuyển vào main khi click vào thông báo
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bill)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent);
        notificationManager.notify(/* notificationId */ generateRandomId(), builder.build());
    }

    // chuyển từ màn finish order sang home -> xét lại fragment bill
    public void getSelectIntent(){
        Intent intent = getIntent();
        if (intent != null){
            int selectFragment = intent.getIntExtra("bill", 0);
           if (selectFragment == 2){
               switchFragment(new BillFragment());
               mBinding.bottomNavigationView.setSelectedItemId(R.id.btn_bill);
           }
        }
    }

    private void setBadgerNotification() {
        BadgeDrawable badgeDrawable = mBinding.bottomNavigationView.getOrCreateBadge(R.id.btn_bell);
        badgeDrawable.setVisible(true);
        int backgroundColor = ContextCompat.getColor(this, R.color.red);
        badgeDrawable.setBackgroundColor(backgroundColor);
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
                    switchFragment(new SettingsFragment());
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getSelectIntent();
    }
    public int generateRandomId() {
        Random random = new Random();
        return random.nextInt();
    }
}