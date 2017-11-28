package com.sample.helmetble.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorator extends RecyclerView.ItemDecoration{
    private final int bottomDiv;

    public ItemDecorator() {
        this.bottomDiv = 10;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = bottomDiv;
    }


}
