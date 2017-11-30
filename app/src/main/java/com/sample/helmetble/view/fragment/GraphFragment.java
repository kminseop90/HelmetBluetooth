package com.sample.helmetble.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sample.helmetble.R;
import com.sample.helmetble.base.BaseFragment;
import com.sample.helmetble.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphFragment extends BaseFragment implements MainActivity.FragmentDataPath{

    public static LineChart obj_line_gyro = null;
    public static LineChart obj_line_accel = null;

    @BindView(R.id.chart_gyro)
    LineChart mGyroChart;
    @BindView(R.id.chart_accel)
    LineChart mAccelChart;


    boolean isConnected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        ButterKnife.bind(this, v);

        if(obj_line_gyro == null){
            mGyroChart.setDrawGridBackground(false);
            mGyroChart.getDescription().setEnabled(false);

            mGyroChart.setData(new LineData());
            mGyroChart.invalidate();
            obj_line_gyro = mGyroChart;
        }

        if(obj_line_accel == null){
            mAccelChart.setDrawGridBackground(false);
            mAccelChart.getDescription().setEnabled(false);

            mAccelChart.setData(new LineData());
            mAccelChart.invalidate();
            obj_line_accel = mAccelChart;
        }


        mAccelChart.setData(obj_line_accel.getData());
        mGyroChart.setData(obj_line_gyro.getData());

        if(mGyroChart.getData().getEntryCount() > 0){
            mGyroChart.setVisibleXRangeMaximum(6);
        }

        if(mAccelChart.getData().getEntryCount() > 0){
            mAccelChart.setVisibleXRangeMaximum(6);
        }

        return v;
    }

    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;

    private void addEntryGyro(int x, int y, int z) {
        LineData data = mGyroChart.getData();

        LineDataSet set0 = (LineDataSet) data.getDataSetByIndex(0);
        LineDataSet set1 = (LineDataSet) data.getDataSetByIndex(1);
        LineDataSet set2 = (LineDataSet) data.getDataSetByIndex(2);

        if (set0 == null || set1 == null || set2 == null) {
            // creation of null
            set0 = createSet("Gyro X", Color.rgb(240, 99, 99));
            set1 = createSet("Gyro Y", Color.rgb(63, 200, 148));
            set2 = createSet("Gyro Z", Color.rgb(85, 130, 253));

            data.addDataSet(set0);
            data.addDataSet(set1);
            data.addDataSet(set2);
        }


        data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), x), 0);
        data.addEntry(new Entry(data.getDataSetByIndex(1).getEntryCount(), y), 1);
        data.addEntry(new Entry(data.getDataSetByIndex(2).getEntryCount(), z), 2);

        mGyroChart.notifyDataSetChanged();
        mGyroChart.setVisibleXRangeMaximum(6);
        mGyroChart.moveViewTo(data.getEntryCount() - 10, 50f, AxisDependency.LEFT);
    }

    private void addEntryAccel(int x, int y, int z) {
        LineData data = mAccelChart.getData();

        LineDataSet set0 = (LineDataSet) data.getDataSetByIndex(0);
        LineDataSet set1 = (LineDataSet) data.getDataSetByIndex(1);
        LineDataSet set2 = (LineDataSet) data.getDataSetByIndex(2);

        if (set0 == null || set1 == null || set2 == null) {
            // creation of null
            set0 = createSet("Accel X", Color.rgb(240, 99, 99));
            set1 = createSet("Accel Y", Color.rgb(63, 200, 148));
            set2 = createSet("Accel Z", Color.rgb(85, 130, 253));

            data.addDataSet(set0);
            data.addDataSet(set1);
            data.addDataSet(set2);
        }


        data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), x), 0);
        data.addEntry(new Entry(data.getDataSetByIndex(1).getEntryCount(), y), 1);
        data.addEntry(new Entry(data.getDataSetByIndex(2).getEntryCount(), z), 2);

        mAccelChart.notifyDataSetChanged();
        mAccelChart.setVisibleXRangeMaximum(6);
        mAccelChart.moveViewTo(data.getEntryCount() - 10, 50f, AxisDependency.LEFT);
    }

    private LineDataSet createSet(String strDataSet1, int color) {
        LineDataSet set  = new LineDataSet(null, strDataSet1);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(color);
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(AxisDependency.LEFT);
        set.setValueTextSize(10f);

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

        if(!TextUtils.isEmpty(gattData)) {
            String[] bleData = gattData.split(" ");

            // [3], [4], [5], = Accel x data
            addEntryAccel(Integer.parseInt(bleData[0], 16), Integer.parseInt(bleData[1], 16), (int) Integer.parseInt(bleData[2], 16));
            // [0], [1], [2], = Gyro x data
            addEntryGyro(Integer.parseInt(bleData[3], 16), Integer.parseInt(bleData[4], 16), Integer.parseInt(bleData[5], 16));

//            gattDataView.setText(gattData);
        }

    }

}
