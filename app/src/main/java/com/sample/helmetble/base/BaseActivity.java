package com.sample.helmetble.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sample.helmetble.view.dialog.CommonDialog;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    CommonDialog networkPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetworkPopup();

    }

    private void initNetworkPopup() {
        networkPopup = new CommonDialog.Builder(this, CommonDialog.ButtonType.ONE_BTN)
                .title("network")
                .message("error")
                .positiveButton("ok", new CommonDialog.DialogListener() {
                    @Override
                    public void onClickListener(CommonDialog commonDialog) {

                    }
                }).build();
    }

    @Override
    public void onNetworkError() {
        if(networkPopup != null && !networkPopup.isShowing()) {
            networkPopup.show();
        }
    }
}
