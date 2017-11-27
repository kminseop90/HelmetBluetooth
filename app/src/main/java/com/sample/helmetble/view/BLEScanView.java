package com.sample.helmetble.view;

import android.bluetooth.BluetoothDevice;

import com.sample.helmetble.base.BaseView;

public interface BLEScanView extends BaseView {

    void getDevice(BluetoothDevice device);

    void onServiceConnected();

    void onServiceDisconnected();
}
