package com.sample.helmetble.adapter.holder;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.helmetble.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ScanDeviceViewHolder extends BaseViewHolder<BluetoothDevice> implements View.OnClickListener {

    BluetoothDevice bluetoothDevice;

    @BindView(R.id.scan_device_address)
    TextView addressView;
    @BindView(R.id.scan_device_name)
    TextView nameVIew;
    OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onDeviceClick(BluetoothDevice device);
    }

    public static ScanDeviceViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scan_device, parent, false);
        return new ScanDeviceViewHolder(itemView);
    }

    public ScanDeviceViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    @Override
    public void onBindView(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        nameVIew.setText(bluetoothDevice.getName());
        addressView.setText(bluetoothDevice.getAddress());
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener != null) {
            itemClickListener.onDeviceClick(bluetoothDevice);
        }
    }
}
