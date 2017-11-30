package com.sample.helmetble.adapter.holder;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sample.helmetble.R;
import com.sample.helmetble.model.vo.Phone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneViewHolder extends BaseViewHolder<Phone> {
    String KEY_PHONE_PREFERENCE_SUB_1 = "user_phone_sub_1";
    String KEY_PHONE_PREFERENCE_SUB_2 = "user_phone_sub_2";

    @BindView(R.id.message_phone)
    TextView phoneNumber;
    @BindView(R.id.edit_message_phone)
    EditText editPhoneNumber;

    public PhoneViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public static PhoneViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message, parent, false);
        return new PhoneViewHolder(itemView);
    }


    @Override
    public void onBindView(Phone phone) {
        if(phone != null) {



            phoneNumber.setText(phone.getPhoneNumber());
        }
    }
}
