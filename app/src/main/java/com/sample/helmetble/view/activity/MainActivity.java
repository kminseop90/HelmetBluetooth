package com.sample.helmetble.view.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseActivity;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.model.vo.VODataFilter;
import com.sample.helmetble.service.BluetoothLeService;
import com.sample.helmetble.service.SampleGattAttributes;
import com.sample.helmetble.view.dialog.ConnectOKDialog;
import com.sample.helmetble.view.fragment.ControllerFragment;
import com.sample.helmetble.view.fragment.GraphFragment;
import com.sample.helmetble.view.fragment.SettingFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tab_layout)
    public TabLayout mainTabLayout;

    public static final String TAG = MainActivity.class.getSimpleName();
    boolean isConnected = false;
    boolean isDataConnection = false;
    private StringBuffer gattDataBuffer = new StringBuffer();
    private FragmentDataPath fragmentDataPath;
    private FragmentManager fm = getSupportFragmentManager();

    ControllerFragment controllerFragment = new ControllerFragment();
    GraphFragment graphFragment = new GraphFragment();
    SettingFragment settingFragment = new SettingFragment();


    public interface FragmentDataPath {
        void onGattDataUpdate(String gattData);
    }

    public void setFragmentDataPath(FragmentDataPath fragmentDataPath) {
        this.fragmentDataPath = fragmentDataPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        findViewById(R.id.temp_send).setEnabled(false);
        init();
    }

    private void init() {
        mainTabLayout.addTab(mainTabLayout.newTab().setText("제 어"));
        mainTabLayout.addTab(mainTabLayout.newTab().setText("그래프"));
        mainTabLayout.addTab(mainTabLayout.newTab().setText("세 팅"));
//        binding.mainTabLayout.setTabGravity(binding.mainTabLayout.GRAVITY_FILL);

        fm.beginTransaction().add(R.id.main_view_layout, new ControllerFragment()).commit();
        mainTabLayout.addOnTabSelectedListener(tabSelectedListener);


        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }


    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            BaseFragment currentFragment;
            switch (position) {
                case 0:
                    currentFragment = controllerFragment;
                    break;
                case 1:
                    currentFragment = graphFragment;
                    break;
                case 2:
                default:
                    currentFragment = settingFragment;
                    break;
            }
            Bundle b = new Bundle();
            b.putString(EXTRAS_DEVICE_NAME, mDeviceName);
            b.putString(EXTRAS_DEVICE_ADDRESS, mDeviceAddress);
            currentFragment.setArguments(b);
            fm.beginTransaction().replace(R.id.main_view_layout, currentFragment).commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    /*  연결 시도 후 연결성공 되었을 때, 수시로 통신을 주고받을 때 사용 */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: " + action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                isConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                isConnected = false;
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                ConnectOKDialog dialog = new ConnectOKDialog(MainActivity.this);
                dialog.show();
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                if (isDataConnection) {
                    displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                }
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void displayData(String data) {
        if (data != null) {
            Log.d(TAG, "displayData: " + data);
            String[] code = data.split("\n");
            SimpleDateFormat format = new SimpleDateFormat();
//            gattDataBuffer.append(format.format(System.currentTimeMillis()) + "_");
            gattDataBuffer.append(code[1]);
            gattDataBuffer.append("\n");
            fragmentDataPath.onGattDataUpdate(code[code.length - 1]);
        }
    }

    public void fileSave() {
        String dirPath = "sdcard";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fos = null;
        File saveFile = new File(dirPath + "/GattData.txt");
        try {
            fos = new FileOutputStream(saveFile);
            fos.write((gattDataBuffer.toString()).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private String mDeviceName;
    private String mDeviceAddress;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    public void send(VODataFilter filterData) {
        mBluetoothLeService.startFileSave();
        mBluetoothLeService.setFilterData(filterData);
        if (mGattCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic =
                    mGattCharacteristics.get(3).get(1);
            //TODO 하드코딩한거 해결해야함
            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mBluetoothLeService.readCharacteristic(characteristic);
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
            }
        }
    }

    public void notiEnd() {
        if (mGattCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic =
                    mGattCharacteristics.get(3).get(1);
            mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
            mNotifyCharacteristic = null;
            mBluetoothLeService.readCharacteristic(characteristic);
        }
        mBluetoothLeService.finishFileSave();
//        fileSave();
    }

    public void writeCharac(BluetoothGattCharacteristic charac, int value) {
        if (charac == null) {
            Log.e(TAG, "char not found!");
        }

        int unixTime = value;
        String unixTimeString = Integer.toHexString(unixTime);
        byte[] byteArray = new BigInteger(unixTimeString, 16).toByteArray();
        charac.setValue(byteArray);
        boolean status = mBluetoothLeService.writeCharacteristic(charac);
        if (status) {
            Toast.makeText(this, "Written Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error writing characteristic", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, String.format("RequestCode = %d, ResultCode = %d", requestCode, resultCode));
        if (requestCode == 0) { // bluetoothSetting
            if (resultCode == RESULT_OK && data != null) {
                mDeviceName = data.getStringExtra(EXTRAS_DEVICE_NAME);
                mDeviceAddress = data.getStringExtra(EXTRAS_DEVICE_ADDRESS);

                mBluetoothLeService.disconnect();
                Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
                bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

    public boolean isDataConnection() {
        return isDataConnection;
    }

    public void setDataConnection(boolean dataConnection) {
        isDataConnection = dataConnection;
    }
}
