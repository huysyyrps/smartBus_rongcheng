package main.smart.bus.activity.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class BusLineDetailPagerAdapter extends FragmentPagerAdapter {
    private static final boolean DEBUG = true;
    private static final String TAG = "BusLineDetailPagerAdapter";
    private ArrayList<Fragment> fragments;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem;
    private final FragmentManager mFragmentManager;
    static final int NUM_ITEMS = 3;
    //定义三个Fragment的索引
    public static final int Fragment_Index_0 = 0;
    public static final int Fragment_Index_1 = 1;
    public static final int Fragment_Index_2 = 2;
    public static final int Fragment_Index_3 = 3;

    public BusLineDetailPagerAdapter(FragmentManager paramFragmentManager) {
        super(paramFragmentManager);
        this.mCurTransaction = null;
        this.mCurrentPrimaryItem = null;
        this.mFragmentManager = paramFragmentManager;
    }

    public BusLineDetailPagerAdapter(FragmentManager paramFragmentManager, ArrayList<Fragment> paramArrayList) {
        this(paramFragmentManager);
        this.fragments = paramArrayList;
    }

    public int getCount() {

        return NUM_ITEMS;
    }

    public Fragment getItem(int paramInt) {
        Fragment mFragemnt = fragments.get(paramInt);
        return mFragemnt;
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public int getItemPosition(Object paramObject) {
        return super.getItemPosition(paramObject);
    }

}
