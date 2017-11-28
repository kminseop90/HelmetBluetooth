package com.sample.helmetble.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.view.activity.BLESettingActivity;
import com.sample.helmetble.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @BindView(R.id.setting_btn_save)
    Button btn_save;

    public String mAddress;
    public String mName;

    private final String KEY_ID_PREFERENCE = "user_id";
    private final String KEY_PHONE_PREFERENCE = "user_phone";

    private String user_phone_number="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", getActivity().MODE_PRIVATE);
        String userID = prefs.getString(KEY_ID_PREFERENCE, "");
        user_phone_number = prefs.getString(KEY_PHONE_PREFERENCE, "");

        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        text_UserDevice_Name.setText(userID + "의 디바이스");
        text_user_ID.setText(userID);
        text_userPhone_Number.setText(user_phone_number);

        if(getArguments() != null) {
            mAddress = getArguments().getString(MainActivity.EXTRAS_DEVICE_ADDRESS);
            mName = getArguments().getString(MainActivity.EXTRAS_DEVICE_NAME);
        }

        btn_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sendSMSMessage();
            }
        });

        return v;

    }


    protected void sendSMSMessage() {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(user_phone_number, null, "Test SMS Sent", null, null);
            Toast.makeText(getContext().getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getContext().getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + user_phone_number));
//        intent.putExtra("sms_body", "Hello Hi");
//        startActivity(intent);
    }

    @OnClick(R.id.setting_bluetooth_layout)
    public void onBluetoothLayoutClick(View v) {
        Intent i = new Intent(getContext(), BLESettingActivity.class);
        i.putExtra(MainActivity.EXTRAS_DEVICE_ADDRESS, mAddress);
        i.putExtra(MainActivity.EXTRAS_DEVICE_NAME, mName);
        ((MainActivity)getContext()).startActivityForResult(i, 0);
    }

    @Override
    public void onGattDataUpdate(String gattData) {
        if(!TextUtils.isEmpty(gattData)) {
            String[] batteryData = gattData.split(" ");
            batteryView.setText(batteryData[6]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getContext()).setFragmentDataPath(this);
    }
}
