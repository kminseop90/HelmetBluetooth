package com.sample.helmetble.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.service.BluetoothLeService;
import com.sample.helmetble.view.activity.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphFragment extends BaseFragment implements MainActivity.FragmentDataPath{

    @BindView(R.id.temp_gatt_data_tv)
    TextView gattDataView;
    @BindView(R.id.chart_gyro)
    LineChart mGyroChart;
    @BindView(R.id.chart_accel)
    LineChart mAccelChart;

    @BindView(R.id.temp_btn_add)
    Button btnTmp;
    @BindView(R.id.temp_btn_add2)
    Button btnTmp2;

    boolean isConnected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        ButterKnife.bind(this, v);

        mGyroChart.setDrawGridBackground(false);
        mGyroChart.getDescription().setEnabled(false);

        mGyroChart.setData(new LineData());
        mGyroChart.invalidate();

        mAccelChart.setDrawGridBackground(false);
        mAccelChart.getDescription().setEnabled(false);

        mAccelChart.setData(new LineData());
        mAccelChart.invalidate();


        btnTmp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                for(int i=0; i < 200; i++){
                    addEntryGyro();
                }
            }
        });
        btnTmp2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                for(int i=0; i < 200; i++){
                    addEntryAccel();
                }
            }
        });
        return v;
    }

    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;

    private void addEntryGyro() {
        LineData data = mGyroChart.getData();

        LineDataSet set0 = (LineDataSet) data.getDataSetByIndex(0);
        LineDataSet set1 = (LineDataSet) data.getDataSetByIndex(1);
        LineDataSet set2 = (LineDataSet) data.getDataSetByIndex(2);

        if (set0 == null || set1 == null || set2 == null) {
            // creation of null
            set0 = createSet("Gyro X", "Gyro Y", "Gyro Z")[0];
            set1 = createSet("Gyro X", "Gyro Y", "Gyro Z")[1];
            set2 = createSet("Gyro X", "Gyro Y", "Gyro Z")[2];

            data.addDataSet(set0);
            data.addDataSet(set1);
            data.addDataSet(set2);
        }


        int randomDataSetIndex1 = (int) (Math.random() * data.getDataSetCount());
        int randomDataSetIndex2 = (int) (Math.random() * data.getDataSetCount());
        int randomDataSetIndex3 = (int) (Math.random() * data.getDataSetCount());

        float yValue = (float) (Math.random() * 10) + 50f;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex1).getEntryCount(), yValue), 0);
        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex2).getEntryCount(), yValue), 1);
        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex3).getEntryCount(), yValue), 2);

        mGyroChart.notifyDataSetChanged();
        mGyroChart.setVisibleXRangeMaximum(6);
        mGyroChart.moveViewTo(data.getEntryCount() - 7, 50f, AxisDependency.LEFT);
    }

    private void addEntryAccel() {
        LineData data = mAccelChart.getData();

        LineDataSet set0 = (LineDataSet) data.getDataSetByIndex(0);
        LineDataSet set1 = (LineDataSet) data.getDataSetByIndex(1);
        LineDataSet set2 = (LineDataSet) data.getDataSetByIndex(2);

        if (set0 == null || set1 == null || set2 == null) {
            // creation of null
            set0 = createSet("Accel X", "Accel Y", "Accel Z")[0];
            set1 = createSet("Accel X", "Accel Y", "Accel Z")[1];
            set2 = createSet("Accel X", "Accel Y", "Accel Z")[2];

            data.addDataSet(set0);
            data.addDataSet(set1);
            data.addDataSet(set2);
        }


        int randomDataSetIndex1 = (int) (Math.random() * data.getDataSetCount());
        int randomDataSetIndex2 = (int) (Math.random() * data.getDataSetCount());
        int randomDataSetIndex3 = (int) (Math.random() * data.getDataSetCount());

        float yValue = (float) (Math.random() * 10) + 50f;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex1).getEntryCount(), yValue), 0);
        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex2).getEntryCount(), yValue), 1);
        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex3).getEntryCount(), yValue), 2);

        mAccelChart.notifyDataSetChanged();
        mAccelChart.setVisibleXRangeMaximum(6);
        mAccelChart.moveViewTo(data.getEntryCount() - 7, 50f, AxisDependency.LEFT);
    }

    private LineDataSet[] createSet(String strDataSet1, String strDataSet2, String strDataSet3) {
        LineDataSet[] set = new LineDataSet[3];

        set[0] = new LineDataSet(null, strDataSet1);
        set[0].setLineWidth(2.5f);
        set[0].setCircleRadius(4.5f);
        set[0].setColor(Color.rgb(240, 99, 99));
        set[0].setCircleColor(Color.rgb(240, 99, 99));
        set[0].setHighLightColor(Color.rgb(190, 190, 190));
        set[0].setAxisDependency(AxisDependency.LEFT);
        set[0].setValueTextSize(10f);

        set[1] = new LineDataSet(null, strDataSet2);
        set[1].setLineWidth(2.5f);
        set[1].setCircleRadius(4.5f);
        set[1].setColor(Color.rgb(63, 200, 148));
        set[1].setCircleColor(Color.rgb(240, 99, 99));
        set[1].setHighLightColor(Color.rgb(190, 190, 190));
        set[1].setAxisDependency(AxisDependency.LEFT);
        set[1].setValueTextSize(10f);

        set[2] = new LineDataSet(null, strDataSet3);
        set[2].setLineWidth(2.5f);
        set[2].setCircleRadius(4.5f);
        set[2].setColor(Color.rgb(85, 130, 253));
        set[2].setCircleColor(Color.rgb(240, 99, 99));
        set[2].setHighLightColor(Color.rgb(190, 190, 190));
        set[2].setAxisDependency(AxisDependency.LEFT);
        set[2].setValueTextSize(10f);
        return set;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getContext()).setFragmentDataPath(this);
    }


    @Override
    public void onGattDataUpdate(String gattData) {
        gattDataView.setText(gattData);
    }
}
