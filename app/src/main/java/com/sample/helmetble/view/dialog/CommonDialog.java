package com.sample.helmetble.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sample.helmetble.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonDialog extends Dialog {

    @BindView(R.id.common_title)
    TextView titleView;
    @BindView(R.id.common_message)
    TextView messageView;
    @BindView(R.id.common_negative)
    Button negativeView;
    @BindView(R.id.common_positive)
    Button positiveView;

    private final Context context;
    private final ButtonType btnType;
    private final String title;
    private final String message;
    private final String positive;
    private final String negative;
    private final DialogListener positiveClickListener;
    private final DialogListener negativeClickListener;
    public enum ButtonType {
        ONE_BTN, TWO_BTN,
    }

    public CommonDialog(Builder builder) {
        super(builder.context);
        this.context = builder.context;
        this.btnType = builder.type;
        this.title = builder.title;
        this.message = builder.message;
        this.positive = builder.positive;
        this.negative = builder.negative;
        this.positiveClickListener = builder.positiveClickListener;
        this.negativeClickListener = builder.negativeClickListener;
        setView();
    }

    private void setView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.common_positive)
    public void onPositiveClick(View v) {
        if(this.positiveClickListener != null) {
            this.positiveClickListener.onClickListener(this);
        }
        this.dismiss();
    }

    @OnClick(R.id.common_negative)
    public void onNegativeClick(View v) {
        if(this.negativeClickListener != null) {
            this.negativeClickListener.onClickListener(this);
        }
        this.dismiss();
    }

    @Override
    public void show() {
        if(isNullOrEmpty(title)) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(message)) {
            messageView.setText(message);
            messageView.setVisibility(View.VISIBLE);
        } else {
            messageView.setVisibility(View.GONE);
        }
        positiveView.setText(positive);
        negativeView.setText(negative);
        switch (btnType) {
            case ONE_BTN:
                this.positiveView.setVisibility(View.VISIBLE);
                this.negativeView.setVisibility(View.GONE);
                break;
            case TWO_BTN:
                this.positiveView.setVisibility(View.VISIBLE);
                this.negativeView.setVisibility(View.VISIBLE);
                break;
            default:
                new Exception();
                break;
        }
        super.show();
    }

    private boolean isNullOrEmpty(String msg) {
        if (msg != null) {
            return !"".equals(msg);
        } else {
            return false;
        }
    }

    public static class Builder {

        private Context context;
        private ButtonType type;
        private String title;
        private String message;
        private String positive;
        private String negative;
        private DialogListener positiveClickListener;
        private DialogListener negativeClickListener;

        public Builder(Context context, ButtonType type) {
            this.context = context;
            this.type = type;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder positiveButton(String positive, DialogListener clickListener) {
            this.positive = positive;
            this.positiveClickListener = clickListener;
            return this;
        }

        public Builder negativeButton(String negative, DialogListener clickListener) {
            this.negative = negative;
            this.negativeClickListener = clickListener;
            return this;
        }

        public CommonDialog build() {
            return new CommonDialog(this);
        }

    }

    public interface DialogListener {
        void onClickListener(CommonDialog commonDialog);
    }
}