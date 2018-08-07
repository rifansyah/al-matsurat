package com.stud.reef.almatsurat_dhikr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DhikrPagerAdapter extends FragmentStatePagerAdapter {
    private List<Dhikr> dhikrList;

    public DhikrPagerAdapter(FragmentManager fm, List<Dhikr> dhikrList) {
        super(fm);
        this.dhikrList = dhikrList;
    }

    @Override
    public Fragment getItem(int position) {
        if (position != dhikrList.size() - 1) {
            return DhikrFragment.newInstance(dhikrList.get(position));
        } else {
            return new EndOfPageFragment();
        }
    }

    @Override
    public int getCount() {
        return dhikrList.size();
    }
}
