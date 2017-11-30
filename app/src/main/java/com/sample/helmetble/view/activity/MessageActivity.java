package com.sample.helmetble.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.adapter.ItemDecorator;
import com.sample.helmetble.adapter.PhoneAdapter;
import com.sample.helmetble.base.BaseActivity;
import com.sample.helmetble.model.vo.Phone;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_list)
    RecyclerView messageList;
    private PhoneAdapter phoneAdapter;
    public final String KEY_PHONE_PREFERENCE = "user_phone";
    public final String KEY_PHONE_PREFERENCE_SUB_1 = "user_phone_sub_1";
    public final String KEY_PHONE_PREFERENCE_SUB_2 = "user_phone_sub_2";

    private int MAX_NUM_PHONE = 3;

    private String[] user_arr_phone = new String[MAX_NUM_PHONE];
    private ArrayList<Phone> phones = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initUserPhoneNum();
        init();

        messageList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // RecyclerView로 전달된 TouchEvent를 받는다.
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    View reV = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildAdapterPosition(reV);

                    switch (position){
                        case 0:
                            break;
                        case 1:
                            break;
                    }

                }
                return false; //TouchEvent를 가로채지 않는다.
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

    }

    private void init() {
        phoneAdapter = new PhoneAdapter();
        messageList.setAdapter(phoneAdapter);
        messageList.addItemDecoration(new ItemDecorator());
        messageList.setLayoutManager(new LinearLayoutManager(this));
        phones = new ArrayList<>();

        for(int i = 0 ; i < MAX_NUM_PHONE ; i++ ) {
            Phone phone = new Phone();
            phone.setName("User " + i);
            phone.setPhoneNumber(" " + user_arr_phone[i]);
            phones.add(phone);
        }

        phoneAdapter.addAll(phones);
    }

    public void initUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        user_arr_phone[0] = prefs.getString(KEY_PHONE_PREFERENCE, "");
        user_arr_phone[1] = prefs.getString(KEY_PHONE_PREFERENCE_SUB_1, "");
        user_arr_phone[2] = prefs.getString(KEY_PHONE_PREFERENCE_SUB_2, "");
    }

    public void setUserPhoneNum(){
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PHONE_PREFERENCE, user_arr_phone[0]);
        editor.putString(KEY_PHONE_PREFERENCE_SUB_1, user_arr_phone[1]);
        editor.putString(KEY_PHONE_PREFERENCE_SUB_2, user_arr_phone[2]);
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
