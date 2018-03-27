package com.sample.helmetble.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.util.Utils;
import com.sample.helmetble.view.activity.BLESettingActivity;
import com.sample.helmetble.view.activity.MainActivity;
import com.sample.helmetble.view.activity.MessageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends BaseFragment implements MainActivity.FragmentDataPath{

    @BindView(R.id.setting_text_userDevice)
    TextView text_UserDevice_Name;
    @BindView(R.id.setting_user_id)
    TextView text_user_ID;
    @BindView(R.id.setting_user_number)
    TextView text_userPhone_Number;
    @BindView(R.id.setting_text_Battery)
    TextView text_Battery;
    @BindView(R.id.setting_text_battery_remain)
    TextView batteryView;

    @BindView(R.id.setting_msg_switch)
    Switch switch_message_send;

    @BindView(R.id.setting_btn_save)
    Button btn_save;

    public String mAddress;
    public String mName;

    private final String KEY_ID_PREFERENCE = "user_id";
    private final String KEY_PHONE_PREFERENCE = "user_phone";
    private final String KEY_SEND_MESSAGE = "is_send_msg";

    private String user_phone_number="";
    private boolean is_send_message = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", getActivity().MODE_PRIVATE);
        String userID = prefs.getString(KEY_ID_PREFERENCE, "");
        user_phone_number = prefs.getString(KEY_PHONE_PREFERENCE, "");
        is_send_message = prefs.getBoolean(KEY_SEND_MESSAGE, false);

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        text_UserDevice_Name.setText(userID + "의 디바이스");
        text_user_ID.setText(userID);
        text_userPhone_Number.setText(user_phone_number);

        if(getArguments() != null) {
            mAddress = getArguments().getString(MainActivity.EXTRAS_DEVICE_ADDRESS);
            mName = getArguments().getString(MainActivity.EXTRAS_DEVICE_NAME);
        }

        switch_message_send.setChecked(is_send_message);


        return v;
    }


    @OnCheckedChanged(R.id.setting_msg_switch)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // do something, the isChecked will be
        // true if the switch is in the On position
        if(isChecked){
            is_send_message = isChecked;
//            Toast.makeText(getContext().getApplicationContext(), "메시지가 활성화 되었습니다", Toast.LENGTH_SHORT).show();
        }
        else{
            is_send_message = isChecked;
//            Toast.makeText(getContext().getApplicationContext(), "메시지가 비활성화 되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.setting_btn_save)
    public void onSaveBtnClick(View v) {

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SEND_MESSAGE, is_send_message);

        Toast.makeText(getContext(), "해당 설정이 저장되었습니다!", Toast.LENGTH_LONG).show();
        editor.commit();
    }


    @OnClick(R.id.setting_bluetooth_layout)
    public void onBluetoothLayoutClick(View v) {
        Intent i = new Intent(getContext(), BLESettingActivity.class);
        i.putExtra(MainActivity.EXTRAS_DEVICE_ADDRESS, mAddress);
        i.putExtra(MainActivity.EXTRAS_DEVICE_NAME, mName);
        ((MainActivity)getContext()).startActivityForResult(i, 0);
    }

    @OnClick(R.id.setting_message_layout)
    public void onMessageLayoutClick(View v) {
        startActivity(new Intent(getContext(), MessageActivity.class));
    }

    @Override
    public void onGattDataUpdate(String gattData) {
        if(!TextUtils.isEmpty(gattData)) {
            String[] batteryData = gattData.split(" ");
            batteryView.setText(Utils.parseInt(batteryData[6]) + "%");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", getActivity().MODE_PRIVATE);
        is_send_message = prefs.getBoolean(KEY_SEND_MESSAGE, false);
        switch_message_send.setChecked(is_send_message);
        ((MainActivity)getContext()).setFragmentDataPath(this);
    }
}
