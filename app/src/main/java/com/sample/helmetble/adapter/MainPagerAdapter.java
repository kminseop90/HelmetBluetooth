package com.sample.helmetble.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sample.helmetble.view.fragment.ControllerFragment;
import com.sample.helmetble.view.fragment.GraphFragment;
import com.sample.helmetble.view.fragment.SettingFragment;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MainPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ControllerFragment controllerFragment = new ControllerFragment();
                return controllerFragment;
            case 1:
                GraphFragment graphFragment = new GraphFragment();
                return graphFragment;
            case 2:
            default:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
