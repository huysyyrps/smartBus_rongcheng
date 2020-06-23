package main.smart.bus.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

/**
 * 公交
 **/
public class BusActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.header)
    Header header;

    private BusLineFragment fragment01;
    private BaiduRouteFragment fragment02;
    private BusStationSerachFragment fragment03;
    private BusStationFragment fragment04;
    private FragmentManager manager;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type.equals("line")){
            header.setTvTitle(getResources().getString(R.string.bus_line_search));
            //默认选中第一个
            RadioButton btn = (RadioButton) radioGroup.getChildAt(0);
            btn.setChecked(true);
            initFragment(type);
            radioGroup.setOnCheckedChangeListener(BusActivity.this);
        }else if (type.equals("station")){
            header.setTvTitle(getResources().getString(R.string.bus_search_station));
            //默认选中第一个
            RadioButton btn = (RadioButton) radioGroup.getChildAt(2);
            btn.setChecked(true);
            initFragment(type);
            radioGroup.setOnCheckedChangeListener(BusActivity.this);
        } else if (type.equals("search")){
            header.setTvTitle(getResources().getString(R.string.bus_transfer));
            //默认选中第一个
            RadioButton btn = (RadioButton) radioGroup.getChildAt(1);
            btn.setChecked(true);
            initFragment(type);
            radioGroup.setOnCheckedChangeListener(BusActivity.this);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bus;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    /**
     * 初始化第一个页面
     */
    private void initFragment(String type) {
        if (type.equals("line")){
            //获取管理器
            manager = getFragmentManager();
            //通过管理器获取一个事件
            FragmentTransaction transaction = manager.beginTransaction();
            //添加第一个fragment到帧布局中
            fragment01 = new BusLineFragment();
            transaction.add(R.id.frame_layout, fragment01);
            transaction.commit();
        }else if (type.equals("station")){
            //获取管理器
            manager = getFragmentManager();
            //通过管理器获取一个事件
            FragmentTransaction transaction = manager.beginTransaction();
            //添加第一个fragment到帧布局中
            fragment03 = new BusStationSerachFragment();
            transaction.add(R.id.frame_layout, fragment03);
            transaction.commit();
        }else if (type.equals("search")){
            //获取管理器
            manager = getFragmentManager();
            //通过管理器获取一个事件
            FragmentTransaction transaction = manager.beginTransaction();
            //添加第一个fragment到帧布局中
            fragment02 = new BaiduRouteFragment();
            transaction.add(R.id.frame_layout, fragment02);
            transaction.commit();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                FragmentTransaction ft1 = manager.beginTransaction();
                hideAll(ft1);
                if (fragment01 == null) {
                    fragment01 = new BusLineFragment();
                    ft1.add(R.id.frame_layout, fragment01);
                } else {
                    ft1.show(fragment01);
                }
                header.setTvTitle(getResources().getString(R.string.bus_line_search));
                ft1.commit();
                break;
            case R.id.rb2:
                FragmentTransaction ft2 = manager.beginTransaction();
                hideAll(ft2);
                if (fragment02 == null) {
                    fragment02 = new BaiduRouteFragment();
                    ft2.add(R.id.frame_layout, fragment02);
                } else {
                    ft2.show(fragment02);
                }
                header.setTvTitle(getResources().getString(R.string.bus_transfer));
                ft2.commit();
                break;
            case R.id.rb3:
                FragmentTransaction ft3 = manager.beginTransaction();
                hideAll(ft3);
                if (fragment03 == null) {
                    fragment03 = new BusStationSerachFragment();
                    ft3.add(R.id.frame_layout, fragment03);
                } else {
                    ft3.show(fragment03);
                }
                header.setTvTitle(getResources().getString(R.string.bus_search_station));

                ft3.commit();
                break;
            case R.id.rb4:
                FragmentTransaction ft4 = manager.beginTransaction();
                hideAll(ft4);
                if (fragment04 == null) {
                    fragment04 = new BusStationFragment();
                    ft4.add(R.id.frame_layout, fragment04);
                } else {
                    ft4.show(fragment04);
                }
                header.setTvTitle(getResources().getString(R.string.bus_baidu_route));
                ft4.commit();
                break;
        }
    }

    /**
     * 隐藏所有fragment
     *
     * @param ft
     */
    private void hideAll(FragmentTransaction ft) {
        if (ft == null) {
            return;
        }
        if (fragment01 != null) {
            ft.hide(fragment01);
        }
        if (fragment02 != null) {
            ft.hide(fragment02);
        }
        if (fragment03 != null) {
            ft.hide(fragment03);
        }
        if (fragment04 != null) {
            ft.hide(fragment04);
        }
    }
}
