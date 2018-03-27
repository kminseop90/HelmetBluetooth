package com.sample.helmetble.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.adapter.ItemDecorator;
import com.sample.helmetble.adapter.ScanDeviceAdapter;
import com.sample.helmetble.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BLEScanActivity extends BaseActivity {

    public static final String TAG = BLEScanActivity.class.getSimpleName();

    @BindView(R.id.scan_device_list)
    RecyclerView scanDeviceList;
    BluetoothAdapter bluetoothAdapter;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_scan);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        handler = new Handler();
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (bluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        scanDeviceList.setLayoutManager(new LinearLayoutManager(this));
        scanDeviceList.addItemDecoration(new ItemDecorator());
        scanDeviceList.setAdapter(new ScanDeviceAdapter(new ScanDeviceAdapter.OnItemClickListener() {
            @Override
            public void onDeviceClick(BluetoothDevice device) {
                Toast.makeText(BLEScanActivity.this, device.getAddress(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BLEScanActivity.this, MainActivity.class);
                i.putExtra(MainActivity.EXTRAS_DEVICE_NAME, device.getName());
                i.putExtra(MainActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                startActivity(i);
                finish();
            }
        }));
//
//        findViewById(R.id.temp_btn_moveID).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent(v.getContext(), MainActivity.class));
//                finish();
//            }
//        });

        ((ScanDeviceAdapter) scanDeviceList.getAdapter()).clear();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothAdapter.stopLeScan(scanCallback);
            }
        }, 5000);
        bluetoothAdapter.startLeScan(scanCallback);
    }

    @OnClick(R.id.scan_save_btn)
    public void onScanClick() {
        if(bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.stopLeScan(scanCallback);
        }
        ((ScanDeviceAdapter) scanDeviceList.getAdapter()).clear();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothAdapter.stopLeScan(scanCallback);
            }
        }, 5000);
        bluetoothAdapter.startLeScan(scanCallback);

    }


    /**
     * Scan후 리턴되는 Device Listener
     **/
    BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d(TAG, "onLeScan: " + device.getName());
            ((ScanDeviceAdapter) scanDeviceList.getAdapter()).add(device);
        }
    };

}
