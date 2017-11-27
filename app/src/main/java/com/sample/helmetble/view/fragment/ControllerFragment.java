package com.sample.helmetble.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControllerFragment extends BaseFragment implements MainActivity.FragmentDataPath {
    private final String KEY_ID_PREFERENCE = "user_id";
    @BindView(R.id.text_user_id)
    TextView textUserID;
    @BindView(R.id.text_battery_remain)
    TextView batteryView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("PrefName", getActivity().MODE_PRIVATE);
        String userID = prefs.getString(KEY_ID_PREFERENCE, "");

        View v = inflater.inflate(R.layout.fragment_controller, container, false);
        ButterKnife.bind(this, v);


//        FragmentControllerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_controller, container, false);


        textUserID.setText(userID + "의 디바이스");

        return v;
    }

    @OnClick(R.id.btn_start)
    public void onStartClick(View v) {
        if(!((MainActivity)getContext()).isDataConnection()) {
            ((MainActivity) getContext()).setDataConnection(true);
            ((MainActivity) getContext()).send();
        }
    }

    @OnClick(R.id.btn_end)
    public void onEndClick(View v) {
        if(((MainActivity)getContext()).isDataConnection()) {
            ((MainActivity) getContext()).setDataConnection(false);
            ((MainActivity) getContext()).notiEnd();
        }
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
