package main.smart.bus.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.net.londatiga.android.ActionItem;
import main.net.londatiga.android.QuickAction;
import main.smart.bus.activity.adapter.BusLineDetailPagerAdapter;
import main.smart.rcgj.R;
import main.smart.bus.activity.BusLineDetailBusFragment.changedTab;
import main.smart.bus.bean.LineBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;
import main.smart.bus.view.BusLineGraphView;
import main.smart.bus.view.ScrollViewPager;
import main.smart.common.http.DBHandler;
import main.smart.common.util.ConstData;
import main.smart.common.util.ScreenObserver;
import main.smart.bus.view.RunBusView;

/**
 * 线路图+百度地图+车辆表（底部标签）
 * */
public class BusLineDetailActivity extends FragmentActivity implements changedTab,RunBusView.GetComeTime, View.OnClickListener, QuickAction.OnActionItemClickListener,ScreenObserver.ScreenStateListener{
    private boolean iden=true;
    private String Url;
    private String lineCode;
    private int Sxx;
    private int station;
    private String str;
    private List<LineBean> mBusLinks;
    private BusManager mBusMan;
    private boolean mFlag;
    public static  ArrayList<Fragment> mFragmentList;
    private ScrollViewPager.OnPageChangeListener mPageChangeListener;
    private ProgressDialog mProgress;
    private QuickAction mQuickAction;
    private int[] mTabIds;
    private ArrayList<View> mTabs;
    private TextView mTitleView;
    private ScrollViewPager mViewPager;
    private String TAG = "BusLineDetailActivity";
    //private ScreenObserver.ScreenStateListener mScreenObserver = null;
    /**
     * 初始化构造函数整体布局
     * */
    public BusLineDetailActivity(){
        int[] tabArr = new int[3];
        tabArr[0] = R.id.bus_line_detail_graph_btn;//线路图
        //tabArr[1] =R.id.bus_line_detail_station_btn;//站列表
        tabArr[1] =R.id.bus_line_detail_bus_btn;//车列表
        tabArr[2] =R.id.bus_line_showstation_btn;//即将进站
//        tabArr[3] =R.id.bus_favorite_btn;//收藏
        this.mTabIds = tabArr;
        this.mBusLinks = new ArrayList();
        this.mFlag = false;
        this.mPageChangeListener = new ViewPager.OnPageChangeListener(){
            public void onPageScrollStateChanged(int paramInt){
            }
            public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2){
            }
            //标签 切换监听
            public void onPageSelected(int paramInt){
                mViewPager.setScanScroll(iden);
                setCurrentTab(paramInt);
            }
        };
    }
    //初始化
    protected void onCreate(Bundle paramBundle){
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_bus_line_detail);
        this.mBusMan = BusManager.getInstance();
        this.mBusLinks.add(this.mBusMan.getSelectedLine());
        this.mTitleView = ((TextView)findViewById(R.id.bus_line_title));
        String lineName =((LineBean)this.mBusLinks.get(0)).getLineName();
        //this.mTitleView.setText(lineName + "▲");
        this.mTitleView.setText(lineName + getResources().getString(R.string.lu)+"("+getResources().getString(R.string.check_line)+")");
        if (getIntent().getBooleanExtra("favorite", false))
            //findViewById(R.id.bus_favorite_btn).setVisibility(8);//隐藏

            mBusMan.startMonitor();
        DisplayMetrics dis=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dis);
        int a=dis.heightPixels;
        //float density= dis.density;//3.0
        int den=   dis.densityDpi;//480
        //float xdpi =dis.xdpi;
        int wid =dis.widthPixels;//1080
        int height =dis.heightPixels;//1776
        double cc=1.00*wid/den;
        double dd=1.00*height/den;
        double z=Math.sqrt(Math.pow(cc, 2)+Math.pow(dd, 2));   //4.95->4.33,
        Log.e("+++++++++++++++++++++++", Double.toString(z));
        //double f=(z/(xdpi*density));
        int widPix =2*a/15;
        this.mTabs = new ArrayList();
        //添加标签
        for (int i = 0;i<mTabIds.length ; ++i){
            View localView = findViewById(this.mTabIds[i]);
            localView.setOnClickListener(this);
            this.mTabs.add(localView);
        }
        //布局然后完毕后加载数据
        this.mFragmentList = new ArrayList();
        //加载线路图BusLineDetailGraphFragment
        this.mFragmentList.add(new BusLineDetailGraphFragment(z));
        //加载站点图
        //this.mFragmentList.add(new BusLineDetailStationFragment());
        //加载车辆图
        this.mFragmentList.add(new BusLineDetailBusFragment());
        //加载进站图
        this.mFragmentList.add(new BusLineDetailShowStationFragment());
//        //加载收藏界面
//        this.mFragmentList.add(new BusLineFavorFragment());

        //左右滑动的 控件，适配器是pageAdapter,数据源是ArrayList
        this.mViewPager = ((ScrollViewPager)findViewById(R.id.viewpager));
        BusLineDetailPagerAdapter pagerAdapter = new BusLineDetailPagerAdapter(getSupportFragmentManager(), this.mFragmentList);
        this.mViewPager.setAdapter(pagerAdapter);
        this.mViewPager.setOnPageChangeListener(this.mPageChangeListener);
        mViewPager.setScanScroll(true);
        //设置默认标签（线路图）
        setCurrentTab(0);
        ///////上下行选择
        try {
            ActionItem item1 = new ActionItem(this, 1, 0, getString(R.string.bus_line_up_down));
            item1.setTitleColor(getResources().getColor(R.color.dark_blue_text));
            item1.setTitleSize(2, 18);
            item1.setEnabled(false);
            LineBean localBusLine = (LineBean)this.mBusLinks.get(0);
            ActionItem item2 = new ActionItem(this, 1, 0, localBusLine.getBeginStation() + "-" + localBusLine.getEndStation());
            item2.setTitleSize(2, 18);
            item2.setSelected(true);
            this.mQuickAction = new QuickAction(this, 1);
            this.mQuickAction.addActionItem(item1);
            this.mQuickAction.addActionItem(item2);
            this.mQuickAction.setOnActionItemClickListener(this);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ScreenObserver.requestScreenStateUpdate(this);

        return;
    }

    /***
     *
     * 点击选项卡
     * 切换
     * */
    public void onClick(View paramView){
        try {
            int i = paramView.getId();
            switch(i){
                case R.id.bus_line_detail_graph_btn:
                    this.mViewPager.setCurrentItem(0);
                    break;
                //case R.id.bus_line_detail_station_btn:
                //this.mViewPager.setCurrentItem(1);
                //break;
                case R.id.bus_line_detail_bus_btn:
                    this.mViewPager.setCurrentItem(1);
                    break;
                case R.id.bus_line_showstation_btn:
                    this.mViewPager.setCurrentItem(2);
                    break;
//                case R.id.bus_favorite_btn:
//                    this.mViewPager.setCurrentItem(3);
//                    break;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("detail on click :", paramView.getId()+"");
        }
    }

    //设置当前标签页
    private void setCurrentTab(int paramInt){
        for(int i=0;i<mTabs.size();i++){
            View localView = (View)this.mTabs.get(i);
            if (paramInt == i){
                localView.setClickable(false);
                localView.setSelected(true);
            }else{
                localView.setClickable(true);
                localView.setSelected(false);
            }
        }
    }
    /**
     * 顶部返回按钮
     * */
    public void back(View paramView){
        ConstData.BusCode="";
        ConstData.onBus=0;
        ConstData.upBus=0;
        onBackPressed();
        this.mBusMan.stopMonitor();
        ScreenObserver.removeScreenStateUpdate(this);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        this.mBusMan.startMonitor();
    }


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //this.mBusMan.stopMonitor();
        super.onStop();
    }
    protected void onPause(){
        //this.mBusMan.stopMonitor();
        super.onPause();
    }
    protected void onResume(){
        super.onResume();
//		this.mBusMan.stopMonitor();
//		this.mBusMan.startMonitor();
    }
    public void switchBusReverseLine(View paramView){
        if (!(this.mFlag)){
            getBusReverseLine();
            return;
        }
        this.mQuickAction.show(paramView);
    }
    //收藏
    public void toBusFavoriteSetting(View paramView){
        //startActivity(new Intent(this, BusFavoriteSettingActivity.class));
    }
    //最靠右的按钮：跳转到带百度地图
    public void toBusLineMap(View paramView){
        startActivity(new Intent(this, BusLineStationMap.class));
        //this.finish();
    }

    public void onItemClick(QuickAction quickaction, int i, int j){
        if (i != 0 && !mQuickAction.getActionItem(i).isSelected()){{
            mBusMan.stopMonitor();
            mBusMan.setSelectedLine((LineBean)mBusLinks.get(i - 1));
            mBusMan.clearSoiList();
            //mTitleView.setText((new StringBuilder(String.valueOf(mBusMan.getSelectedLine().getLineName()))).append("▲").toString());
//            mTitleView.setText((new StringBuilder(String.valueOf(mBusMan.getSelectedLine().getLineName()))).append(getResources().getString(R.string.check_line)).toString());

            this.mTitleView.setText(mBusMan.getSelectedLine().getLineName() + getResources().getString(R.string.lu)+"("+getResources().getString(R.string.check_line)+")");
            int k;
            Iterator it = mFragmentList.iterator();
            mBusMan.startMonitor();
            while(it.hasNext()){
                ((BusLineRefresh)(Fragment)it.next()).refreshData();
            }
            k = 1;
            //0:上下行选择  1：  2：
            while (k < mQuickAction.getActionItemCount()) {
                ActionItem actionitem = mQuickAction.getActionItem(k);
                if (i == k)
                    actionitem.setSelected(true);
                else
                    actionitem.setSelected(false);
                k++;
            }
        }}
    }

    private void getBusReverseLine(){
        //	this.mProgress = ProgressDialog.show(this, null, getString(R.string.bus_progress_loading), false, false);
        LineBean localBusLine = (LineBean)this.mBusLinks.get(0);
        List<LineBean> lineList= mBusMan.getLineByLineCode(localBusLine.getLineCode());
        if(lineList.size()==0){
            //mProgress.dismiss();
            return;
        }
        for(int i=0;i<lineList.size();i++){
            LineBean dirLine =lineList.get(i);
            if( dirLine.getLineId()==localBusLine.getLineId())
                continue;
            this.mBusLinks.add(dirLine);
            String sta =dirLine.getBeginStation()+"-"+dirLine.getEndStation();
            ActionItem localActionItem = new ActionItem(BusLineDetailActivity.this, 1, 0, sta);
            localActionItem.setTitleSize(2, 18);
            this.mQuickAction.addActionItem(localActionItem);
            //mProgress.dismiss();
            this.mQuickAction.show(this.mTitleView);
            this.mFlag = true;
        }
    }
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    BusLineDetailGraphFragment frag=(BusLineDetailGraphFragment)(BusLineDetailActivity.mFragmentList.get(0));
                    frag.setLineDetailGraphView((BusLineGraphView)msg.obj);


                    break;
                case 2:
                    Toast.makeText(BusLineDetailActivity.this, str, Toast.LENGTH_LONG).show();
                    break;
            }
        };
    };
    public Handler getHandler(){
        return mHandler;
    }
    public void setHandler(Handler handler) {
        mHandler = handler;
    }
    @Override
    public void getTime(String url,String line, int sxx, int zd) {
        Url=url;
        Sxx=sxx;
        station=zd;
        lineCode=line;
        new Thread(runnable).start();
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // TODO Auto-generated method stub
            DBHandler db=new DBHandler();
            str=db.getCometime(Url, lineCode, Sxx, station);
            mHandler.sendEmptyMessage(2);
        }
    };
    /**
     * 切换标签页
     */
    @Override
    public void changedTab(int i, String ch) {
        // TODO Auto-generated method stub
        iden=false;
        ConstData.BusCode=ch;
        this.mViewPager.setCurrentItem(i);
        Fragment fr=mFragmentList.get(i);
        mTitleView.setVisibility(View.GONE);
        ((selectOnBus)fr).setOnBus(true);
        for(int j=1;j<mTabs.size();j++){
            View localView = (View)this.mTabs.get(j);
            localView.setClickable(false);
            localView.setSelected(false);
        }
    }

    //通知上车事件接口
    public static abstract interface selectOnBus{
        public abstract void setOnBus(boolean iden);
    }

    @Override
    public void onScreenOff() {
        // TODO Auto-generated method stub
        mBusMan.stopMonitor();
        Log.e(TAG,"onScreeOff");

    }
    @Override
    public void onScreenOn() {
        // TODO Auto-generated method stub
        mBusMan.startMonitor();
        Log.e(TAG,"onScreeOn");

    }

}