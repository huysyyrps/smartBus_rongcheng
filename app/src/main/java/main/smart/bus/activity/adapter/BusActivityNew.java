package main.smart.bus.activity.adapter;

import java.util.ArrayList;



import main.smart.bus.bean.BusBean;
import main.smart.bus.util.BusMonitor;
import main.smart.common.SmartBusApp;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
/**
 * 线路输入/站点输入 页面
 * SERVLET_URL
 * 通知连接转发服务器
 * **/

public class BusActivityNew extends FragmentActivity
{
    private int[] mFragmentIds;
    private int[] mIconIds;
    private int[] mLabelIds;
    private TabHost mTabHost;
    private int tabLen =5;
    public ArrayList<Fragment> mFragmentList=new ArrayList<Fragment>();
    private View[] mTabs = new View[tabLen];
    /**
     * 初始化构造函数
     * **/
    public BusActivityNew()
    {
        int[] labelIdArr = new int[tabLen];
        //线路查询
        labelIdArr[0] = R.string.bus_line_search;
        //公交换乘
        labelIdArr[1] = R.string.bus_transfer;
        //站点查询
        labelIdArr[2] = R.string.bus_search_station;
        //直达换乘
        labelIdArr[3] = R.string.bus_baidu_route;
        //收藏夹标签
        labelIdArr[4] = R.string.bus_favorite_store;
        //tab标签 图片
        this.mLabelIds = labelIdArr;
        int[] iconIdArr = new int[tabLen];
        iconIdArr[0] = R.drawable.tab_busline;
        iconIdArr[1] = R.drawable.tab_station;
        iconIdArr[2] = R.drawable.tab_bustransfer;
        iconIdArr[3] = R.drawable.tab_baidu_route;
        iconIdArr[4] = R.drawable.tab_help;
        this.mIconIds = iconIdArr;
        //tab标签对象
        int[] fragIdArr = new int[tabLen];
        fragIdArr[0] = R.id.bus_line_fragment;
        fragIdArr[1] = R.id.bus_baidu_route;
        fragIdArr[2] = R.id.bus_searchstation_fragment;
        fragIdArr[3] = R.id.bus_station_fragment;
        fragIdArr[4] = R.id.bus_favorite_fragment;
        this.mFragmentIds = fragIdArr;
    }

    public void back(View paramView)
    {
        onBackPressed();
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_bus_new);
       // StatusBarUtil.setStatusBarMode(this, true, R.color.color_bg_selected);

        this.mTabHost = findViewById(R.id.tabhostnew);
        this.mTabHost.setup();
        getResources();
        for (int i = 0; ; ++i)
        {
            if (i >= this.mLabelIds.length)
                break;
            this.mTabs[i] = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
            ((ImageView)this.mTabs[i].findViewById(R.id.tab_icon)).setImageResource(this.mIconIds[i]);
            ((TextView)this.mTabs[i].findViewById(R.id.tab_label)).setText(getString(this.mLabelIds[i]));
            this.mTabHost.addTab(this.mTabHost.newTabSpec(getString(this.mLabelIds[i])).setIndicator(this.mTabs[i]).setContent(this.mFragmentIds[i]));
        }

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                ConstData.tab=tabId;
                for(int i=0;i<mFragmentList.size();i++){
                    Fragment it=mFragmentList.get(i);
                    if (i==1||i==4){
                        ((TabRefresh)it).refreshData();
                    }
                }
            }
        });

        Intent intent = getIntent();
        Bundle bunde = intent.getExtras();
        if (bunde!=null){
            int tab=bunde.getInt("tab");
            if (tab!=0){
                this.mTabHost.setCurrentTab(tab);
                String qs = bunde.getString("qs");
                String js = bunde.getString("js");
                EditText qszd=(EditText) findViewById(R.id.baidu_route_fragment_qd);
                EditText jszd=(EditText) findViewById(R.id.baidu_route_fragment_zd);
                qszd.setText(qs);
                jszd.setText(js);
            }else{
//    		this.mTabHost.setCurrentTab(3);
//    		String qs = bunde.getString("qszd");
//        	String js = bunde.getString("jszd");
//        	AutoCompleteTextView qszd=(AutoCompleteTextView) findViewById(R.id.qszd);
//        	AutoCompleteTextView jszd=(AutoCompleteTextView) findViewById(R.id.jszd);
//        	qszd.setText(qs);
//        	jszd.setText(js);
            }
        }
    }

    public static abstract interface TabRefresh{
        public abstract void refreshData();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);
        mFragmentList.add(fragment);
    }


    public void onPause()
    {
        MobclickAgent.onPause(this);
        SmartBusApp.getInstance().getLocManager().stop();
        super.onPause();
    }

    public void onResume()
    {
        MobclickAgent.onResume(this);
        SmartBusApp.getInstance().getLocManager().start();
        super.onResume();
    }
}
