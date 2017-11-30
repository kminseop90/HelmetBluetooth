package com.sample.helmetble.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.adapter.ItemDecorator;
import com.sample.helmetble.adapter.PhoneAdapter;
import com.sample.helmetble.base.BaseActivity;
import com.sample.helmetble.model.vo.Phone;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_list)
    RecyclerView messageList;
    @BindView(R.id.message_default_phone)
    TextView text_default_phone;

    private PhoneAdapter phoneAdapter;
    public final String KEY_PHONE_PREFERENCE = "user_phone";
    public final String KEY_PHONE_PREFERENCE_SUB_1 = "user_phone_sub_1";
    public final String KEY_PHONE_PREFERENCE_SUB_2 = "user_phone_sub_2";

    private int MAX_NUM_PHONE = 2;

    private String[] user_arr_phone = new String[MAX_NUM_PHONE];
    private String user_default_number = "";
    private ArrayList<Phone> phones = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initUserPhoneNum();
        init();
    }

    private void init() {
        text_default_phone.setText(user_default_number);

        phoneAdapter = new PhoneAdapter();
        messageList.setAdapter(phoneAdapter);
        messageList.addItemDecoration(new ItemDecorator());
        messageList.setLayoutManager(new LinearLayoutManager(this));
        phones = new ArrayList<>();

        for(int i = 0 ; i < MAX_NUM_PHONE ; i++ ) {
            Phone phone = new Phone();
//            phone.setName("User " + i);
            phone.setPhoneNumber("연락처를 입력해주세요.");
            phones.add(phone);
        }

        phoneAdapter.addAll(phones);
    }

    public void initUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        user_default_number = prefs.getString(KEY_PHONE_PREFERENCE, "");
        user_arr_phone[0] = prefs.getString(KEY_PHONE_PREFERENCE_SUB_1, "");
        user_arr_phone[1] = prefs.getString(KEY_PHONE_PREFERENCE_SUB_2, "");
    }

    public void setUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PHONE_PREFERENCE_SUB_1, user_arr_phone[0]);
        editor.putString(KEY_PHONE_PREFERENCE_SUB_2, user_arr_phone[1]);
        editor.commit();

    }



    @OnClick(R.id.message_back_img)
    public void onBackClick() {
        finish();
    }


    @OnClick(R.id.message_save_btn)
    public void onClickMessage(){
        setUserPhoneNum();
        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
