package com.sample.helmetble.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sample.helmetble.adapter.holder.BaseViewHolder;
import com.sample.helmetble.adapter.holder.ScanDeviceViewHolder;

import java.util.ArrayList;

public class ScanDeviceAdapter extends RecyclerView.Adapter<BaseViewHolder> implements ScanDeviceViewHolder.OnItemClickListener {

    ArrayList<BluetoothDevice> items = new ArrayList<>();
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeviceClick(BluetoothDevice device);
    }


    public ScanDeviceAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void add(BluetoothDevice item) {
        if(!items.contains(item)){
            this.items.add(item);
            notifyItemChanged(items.size() - 1);
        }
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ScanDeviceViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ((ScanDeviceViewHolder)holder).setOnItemClickListener(this);
        holder.onBindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onDeviceClick(BluetoothDevice device) {
        if(onItemClickListener != null) {
            onItemClickListener.onDeviceClick(device);
        }
    }
}
