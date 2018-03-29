package com.sample.helmetble.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.sample.helmetble.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectFailDialog extends Dialog {

    public ConnectFailDialog(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_connect_fail, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.connect_fail_confirm)
    public void onConfirmClick(View v) {
        dismiss();
    }
}
