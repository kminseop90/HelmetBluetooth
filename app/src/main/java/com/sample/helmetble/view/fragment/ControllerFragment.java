package com.sample.helmetble.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.model.vo.VODataFilter;
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
    @BindView(R.id.input_gyroX_x)
    EditText minGyroX;
    @BindView(R.id.input_gyroX_y)
    EditText maxGyroX;
    @BindView(R.id.input_gyroY_x)
    EditText minGyroY;
    @BindView(R.id.input_gyroY_y)
    EditText maxGyroY;
    @BindView(R.id.input_gyroZ_x)
    EditText minGyroZ;
    @BindView(R.id.input_gyroZ_y)
    EditText maxGyroZ;
    @BindView(R.id.input_accelX_x)
    EditText minAccelX;
    @BindView(R.id.input_accelX_y)
    EditText maxAccelX;
    @BindView(R.id.input_accelY_x)
    EditText minAccelY;
    @BindView(R.id.input_accelY_y)
    EditText maxAccelY;
    @BindView(R.id.input_accelZ_x)
    EditText minAccelZ;
    @BindView(R.id.input_accelZ_y)
    EditText maxAccelZ;


    VODataFilter filterData = null;

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
        if(!((MainActivity)getContext()).isDataConnection() && ((MainActivity)getContext()).isConnected()) {
            int accelerationMaxX = Integer.parseInt(maxAccelX.getText().toString());
            int accelerationMinX = Integer.parseInt(minAccelX.getText().toString());
            int accelerationMaxY = Integer.parseInt(maxAccelY.getText().toString());
            int accelerationMinY = Integer.parseInt(minAccelY.getText().toString());
            int accelerationMaxZ = Integer.parseInt(maxAccelZ.getText().toString());
            int accelerationMinZ = Integer.parseInt(minAccelZ.getText().toString());
            int gyroMaxX = Integer.parseInt(maxGyroX.getText().toString());
            int gyroMinX = Integer.parseInt(minGyroX.getText().toString());
            int gyroMaxY = Integer.parseInt(maxGyroY.getText().toString());
            int gyroMinY = Integer.parseInt(minGyroY.getText().toString());
            int gyroMaxZ = Integer.parseInt(maxGyroZ.getText().toString());
            int gyroMinZ = Integer.parseInt(minGyroZ.getText().toString());

            filterData = new VODataFilter(accelerationMaxX, accelerationMinX, accelerationMaxY, accelerationMinY, accelerationMaxZ, accelerationMinZ,
                    gyroMaxX, gyroMinX, gyroMaxY, gyroMinY, gyroMaxZ, gyroMinZ);
            ((MainActivity) getContext()).setDataConnection(true);
            ((MainActivity) getContext()).send(filterData);
        }
        else{
            Toast.makeText(getContext(), "블루투스 디바이스에 연결되지 않았습니다.!", Toast.LENGTH_SHORT).show();
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
