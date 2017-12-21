package com.sample.helmetble.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseActivity;
import com.sample.helmetble.view.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_default_phone)
    TextView text_default_phone;
    @BindView(R.id.phone_default_header_02)
    LinearLayout phoneLayout02;
    @BindView(R.id.phone_default_header_03)
    LinearLayout phoneLayout03;
    @BindView(R.id.message_delete_img_02)
    ImageView phoneNumberDeleteImageView02;
    @BindView(R.id.message_delete_img_03)
    ImageView phoneNumberDeleteImageView03;
    @BindView(R.id.message_setting_img)
    ImageView settingImageView;
    @BindView(R.id.message_default_phone_02)
    TextView phoneNumberTextView02;
    @BindView(R.id.message_default_phone_03)
    TextView phoneNumberTextView03;
    @BindView(R.id.message_default_phone_input02)
    TextView phoneNumberEditText02;
    @BindView(R.id.message_default_phone_input03)
    TextView phoneNumberEditText03;

    private int type = TYPE_NORMAL;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_SETTING = 2;

    public final String KEY_PHONE_PREFERENCE = "user_phone";
    public final String KEY_PHONE_PREFERENCE_SUB_1 = "user_phone_sub_1";
    public final String KEY_PHONE_PREFERENCE_SUB_2 = "user_phone_sub_2";

    private String phone02;
    private String phone03;
    private String user_default_number = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        phoneNumberEditText02.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        phoneNumberEditText03.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        user_default_number = prefs.getString(KEY_PHONE_PREFERENCE, "");
        phone02 = prefs.getString(KEY_PHONE_PREFERENCE_SUB_1, "");
        phone03 = prefs.getString(KEY_PHONE_PREFERENCE_SUB_2, "");

        text_default_phone.setText(user_default_number);
        if(TextUtils.isEmpty(phone02)) {
            phoneLayout02.setVisibility(View.GONE);
        } else {
            phoneLayout02.setVisibility(View.VISIBLE);
            phoneNumberTextView02.setText(phone02);
            phoneNumberDeleteImageView02.setVisibility(View.INVISIBLE);
        }

        if(TextUtils.isEmpty(phone03)) {
            phoneLayout03.setVisibility(View.GONE);
        } else {
            phoneLayout03.setVisibility(View.VISIBLE);
            phoneNumberTextView03.setText(phone03);
            phoneNumberDeleteImageView03.setVisibility(View.INVISIBLE);
        }
    }

    public void initUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        user_default_number = prefs.getString(KEY_PHONE_PREFERENCE, "");
        phone02 = prefs.getString(KEY_PHONE_PREFERENCE_SUB_1, "");
        phone03 = prefs.getString(KEY_PHONE_PREFERENCE_SUB_2, "");

        if(TextUtils.isEmpty(phone02)) {
        } else {
            phoneNumberTextView02.setText(phone02);
        }

        if(TextUtils.isEmpty(phone03)) {
        } else {
            phoneNumberTextView03.setText(phone03);
        }
    }

    public void setUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String phone02 = phoneNumberEditText02.getText().toString();
        String phone03 = phoneNumberEditText03.getText().toString();

        if(!TextUtils.isEmpty(phone02)) {
            editor.putString(KEY_PHONE_PREFERENCE_SUB_1, phone02);
        } else {
            editor.putString(KEY_PHONE_PREFERENCE_SUB_1, "");
        }
        if(!TextUtils.isEmpty(phone03)) {
            editor.putString(KEY_PHONE_PREFERENCE_SUB_2, phone03);
        } else {
            editor.putString(KEY_PHONE_PREFERENCE_SUB_2, "");
        }
        editor.commit();
    }



    @OnClick(R.id.message_back_img)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.message_setting_img)
    public void onSettingClick(View v) {
        toggleMode();
    }

    private void toggleMode() {
        initUserPhoneNum();

        if(type == TYPE_NORMAL) {
            this.type = TYPE_SETTING;
            phoneLayout02.setVisibility(View.VISIBLE);
            phoneLayout03.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(phone02)) {
                phoneNumberDeleteImageView02.setVisibility(View.INVISIBLE);
                phoneNumberTextView02.setVisibility(View.INVISIBLE);
                phoneNumberDeleteImageView02.setVisibility(View.INVISIBLE);
                phoneNumberEditText02.setVisibility(View.VISIBLE);
            } else {
                phoneNumberDeleteImageView02.setVisibility(View.VISIBLE);
                phoneNumberTextView02.setVisibility(View.VISIBLE);
                phoneNumberDeleteImageView02.setVisibility(View.VISIBLE);
                phoneNumberEditText02.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(phone03)) {
                phoneNumberDeleteImageView03.setVisibility(View.INVISIBLE);
                phoneNumberTextView03.setVisibility(View.INVISIBLE);
                phoneNumberDeleteImageView03.setVisibility(View.INVISIBLE);
                phoneNumberEditText03.setVisibility(View.VISIBLE);
            } else {
                phoneNumberDeleteImageView03.setVisibility(View.VISIBLE);
                phoneNumberTextView03.setVisibility(View.VISIBLE);
                phoneNumberDeleteImageView03.setVisibility(View.VISIBLE);
                phoneNumberEditText03.setVisibility(View.INVISIBLE);
            }
        } else {
            this.type = TYPE_NORMAL;
            if(TextUtils.isEmpty(phone02)) {
                phoneLayout02.setVisibility(View.GONE);
            } else {
                phoneLayout02.setVisibility(View.VISIBLE);
                phoneNumberDeleteImageView02.setVisibility(View.INVISIBLE);
            }

            if(TextUtils.isEmpty(phone03)) {
                phoneLayout03.setVisibility(View.GONE);
            } else {
                phoneLayout03.setVisibility(View.VISIBLE);
                phoneNumberDeleteImageView02.setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.message_delete_img_02)
    public void onPhoneNumberDeleteClick02() {
        showDeletePhoneNumberDialog(KEY_PHONE_PREFERENCE_SUB_1);
    }

    @OnClick(R.id.message_delete_img_03)
    public void onPhoneNumberDeleteClick03() {
        showDeletePhoneNumberDialog(KEY_PHONE_PREFERENCE_SUB_2);
    }

    private void showDeletePhoneNumberDialog(final String phoneKey) {
        CommonDialog.Builder dialogBuilder = new CommonDialog.Builder(this, CommonDialog.ButtonType.TWO_BTN);
        dialogBuilder.message("삭제하시겠습니까?")
                .positiveButton("예", new CommonDialog.DialogListener() {
                    @Override
                    public void onClickListener(CommonDialog commonDialog) {
                        resetPreferencePhoneNumber(phoneKey);
                        toggleMode();
                        commonDialog.dismiss();
                    }
                }).negativeButton("아니오", new CommonDialog.DialogListener() {
            @Override
            public void onClickListener(CommonDialog commonDialog) {
                commonDialog.dismiss();
            }
        }).build().show();
    }

    private void resetPreferencePhoneNumber(String phoneKey) {
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(phoneKey, "");
        editor.commit();
    }


    @OnClick(R.id.message_save_btn)
    public void onClickMessage(){
        setUserPhoneNum();
        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
