package com.sample.helmetble.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<ITEM> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindView(ITEM item);
}
