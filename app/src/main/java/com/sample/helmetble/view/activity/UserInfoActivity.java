package com.sample.helmetble.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.name_input)
    EditText nameInput;
    @BindView(R.id.phone_input)
    EditText phoneInput;

    private String str_id = "";
    private String str_phone_number = "";

    public final String KEY_ID_PREFERENCE = "user_id";
    public final String KEY_PHONE_PREFERENCE = "user_phone";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        String userID = prefs.getString(KEY_ID_PREFERENCE, "");
        String userPhone = prefs.getString(KEY_PHONE_PREFERENCE, "");
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        if(userID == null || userID.isEmpty()){
            return;
        }else if(userPhone == null || userPhone.isEmpty()){
            return;
        }

        Intent i = new Intent(this, BLEScanActivity.class);
        startActivity(i);
        finish();
    }


    private void startDeviceScanActivity(String id, String phone) {
        int duration = Toast.LENGTH_SHORT;

        String USER_ID_NONE = "이름을 정확히 입력해 주세요.";
        String USER_PHONE_NONE = "핸드폰 번호를 입력해 주세요.";

        str_id = id;
        str_phone_number = phone;

        if(str_id == null || str_id.isEmpty()){
            Toast toast = Toast.makeText(this, USER_ID_NONE, duration);
            toast.show();
            return;
        }

        if(str_phone_number == null || str_phone_number.isEmpty()){
            Toast toast = Toast.makeText(this, USER_ID_NONE, duration);
            toast.show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_ID_PREFERENCE, str_id);
        editor.putString(KEY_PHONE_PREFERENCE, str_phone_number);
        editor.commit();


        Intent i = new Intent(this, BLEScanActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.user_info_next)
    public void onNextClick(View v) {
        String id = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();

        startDeviceScanActivity(id, phone);
    }
}
