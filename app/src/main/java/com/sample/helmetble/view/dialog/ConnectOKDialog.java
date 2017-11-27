package com.sample.helmetble.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.sample.helmetble.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectOKDialog extends Dialog {

    public ConnectOKDialog(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_connect_ok, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.connect_confirm)
    public void onConfirmClick(View v) {
        dismiss();
    }
}
