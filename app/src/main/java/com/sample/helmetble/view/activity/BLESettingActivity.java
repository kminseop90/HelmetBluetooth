package com.sample.helmetble.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.adapter.ItemDecorator;
import com.sample.helmetble.adapter.ScanDeviceAdapter;
import com.sample.helmetble.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BLESettingActivity extends BaseActivity {

    public static final String TAG = BLESettingActivity.class.getSimpleName();

    @BindView(R.id.setting_device_name)
    TextView deviceNameView;
    @BindView(R.id.setting_device_list)
    RecyclerView deviceList;
    BluetoothAdapter bluetoothAdapter;
    Handler handler;

    private String mName;
    private String mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }

        handler = new Handler();
        deviceList.setAdapter(new ScanDeviceAdapter(onItemClickListener));
        deviceList.addItemDecoration(new ItemDecorator());
        deviceList.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent() != null) {
            mName = getIntent().getStringExtra(MainActivity.EXTRAS_DEVICE_NAME);
            mAddress = getIntent().getStringExtra(MainActivity.EXTRAS_DEVICE_ADDRESS);
        }
        deviceNameView.setText(mName);
    }


    ScanDeviceAdapter.OnItemClickListener onItemClickListener = new ScanDeviceAdapter.OnItemClickListener() {
        @Override
        public void onDeviceClick(BluetoothDevice device) {
            Toast.makeText(BLESettingActivity.this, device.getAddress(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BLESettingActivity.this, MainActivity.class);
            i.putExtra(MainActivity.EXTRAS_DEVICE_NAME, device.getName());
            i.putExtra(MainActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
            setResult(RESULT_OK, i);
            finish();
        }
    };


    @OnClick(R.id.setting_save_btn)
    public void onSaveClick(View v) {
        ((ScanDeviceAdapter) deviceList.getAdapter()).clear();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothAdapter.stopLeScan(scanCallback);
            }
        }, 5000);
        bluetoothAdapter.startLeScan(scanCallback);
    }

    BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d(TAG, "onLeScan: " + device.getName());
            ((ScanDeviceAdapter) deviceList.getAdapter()).add(device);
        }
    };

    @OnClick(R.id.setting_back_img)
    public void onBackClick(View v) {
        finish();
    }
}
