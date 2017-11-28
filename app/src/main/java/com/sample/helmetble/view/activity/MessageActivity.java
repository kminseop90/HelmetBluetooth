package com.sample.helmetble.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    private PhoneAdapter phoneAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        phoneAdapter = new PhoneAdapter();
        messageList.setAdapter(phoneAdapter);
        messageList.addItemDecoration(new ItemDecorator());
        messageList.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Phone> phones = new ArrayList<>();
        for(int i = 0 ; i < 3 ; i++ ) {
            Phone phone = new Phone();
            phone.setName("User " + i);
            phone.setPhoneNumber("PhoneNumber " + i);
            phones.add(phone);
        }
        phoneAdapter.addAll(phones);
    }

    @OnClick(R.id.message_back_img)
    public void onBackClick() {
        finish();
    }


}
