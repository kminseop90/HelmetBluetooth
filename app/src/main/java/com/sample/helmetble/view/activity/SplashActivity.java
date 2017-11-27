package com.sample.helmetble.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseActivity;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestPermission();

    }

    private void requestPermission() {
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }


    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, UserInfoActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            finish();
        }


    };
}
