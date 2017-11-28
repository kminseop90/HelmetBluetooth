package com.sample.helmetble.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sample.helmetble.adapter.holder.BaseViewHolder;
import com.sample.helmetble.adapter.holder.PhoneViewHolder;
import com.sample.helmetble.model.vo.Phone;

import java.util.ArrayList;


public class PhoneAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<Phone> items = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PhoneViewHolder.newInstance(parent);
    }

    public void addAll(ArrayList<Phone> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
