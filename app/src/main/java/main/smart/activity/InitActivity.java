package main.smart.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.LocationClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import main.sheet.MainActivity;
import main.smart.bus.bean.AdvertBean;
import main.smart.bus.util.BusManager;
import main.smart.common.SmartBusApp;
import main.smart.common.bean.SwitchCity;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;
import main.smart.rcgj.R;

/**
 * 初始化类
 **/

public class InitActivity extends Activity implements Runnable {

    private String Tag = "InitActivity";
    private BusManager mBusMan;
    private CityManager mCityMan;
    private LocationClient mLocMan;
    private PreferencesHelper mPreferenceMan;
    private static boolean mbFlag = true;//避免重复跳转到主界面
    private int mDelay = 0;
    private AdvertBean adBean = new AdvertBean();
    private Toast mToast = null;
    SharedPreferences preferences;
    private int count;
    private main.smart.bus.util.dialog.AlertDialognew aldialog;


    private Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case 99:
//                    if (mbFlag) {
                        interMenuActivity();
//                    }

                    break;
//                case 100:
//                    if (mbFlag) {
//                        interMenuActivity();
//                    }
//                    break;
                default:
                    break;
            }
        }
    };

    public void interMenuActivity() {
        // 取得活动的Preferences对象
        SharedPreferences uiState = getSharedPreferences("user", MODE_PRIVATE);
        preferences = getSharedPreferences("count", MODE_PRIVATE);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        Log.e("soso","2222过来了，啊");
        preferences = getSharedPreferences("count", MODE_PRIVATE);
        count = preferences.getInt("count", 0);
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        if (count == 0) {
            aldialog=new main.smart.bus.util.dialog.AlertDialognew(InitActivity.this);
            aldialog.builder().setTitle(getResources().getString(R.string.fwtk))
                    .setMsg(getResources().getString(R.string.ysqx))
                    .setPositiveButton(getResources().getString(R.string.queren), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = preferences.edit();
                            // 存入数据
                            editor.putInt("count", ++count);
                            // 提交修改
                            editor.commit();
                            //获取数据
                            String city = uiState.getString("selectCity", null);
                            //赋值
                            ConstData.SELECT_CITY = city;
                            mbFlag = false;


//        InitActivity.this.startActivity(new Intent(InitActivity.this.getApplication(), LoginActivity.class));
                            InitActivity.this.startActivity(new Intent(InitActivity.this.getApplication(), MainActivity.class));
                            InitActivity.this.finish();
                        }
                    }).show();
            aldialog.setCancelable(false);

        }else{

            //获取数据
            String city = uiState.getString("selectCity", null);
            //赋值
            ConstData.SELECT_CITY = city;
            mbFlag = false;


//        InitActivity.this.startActivity(new Intent(InitActivity.this.getApplication(), LoginActivity.class));
            InitActivity.this.startActivity(new Intent(InitActivity.this.getApplication(), MainActivity.class));
            InitActivity.this.finish();
        }



    }

    //初始化
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PreferencesHelper.getInstance().updateNotified();
        setContentView(R.layout.init);
        SharedPreferences uiState = getSharedPreferences("user", MODE_PRIVATE);
        Boolean b = uiState.getBoolean("isLoginAA", false);
        if (!b) {
            DataCleanManager.cleanApplicationData(this);
        }
        ConstData.adFolder = getApplicationContext().getFilesDir().getAbsolutePath() + "/adImages";
        this.mCityMan = CityManager.getInstance();// 城市管理器
        this.mPreferenceMan = PreferencesHelper.getInstance();
        // 地理位置管理：GPS定位。设定选中城市
        mLocMan = SmartBusApp.getInstance().getLocManager();
        this.mBusMan = BusManager.getInstance();// 车辆管理器
        new Thread(this).start();
    }


    //异步处理
    @Override
    public void run() {
        SharedPreferences uiState = getSharedPreferences("user", MODE_PRIVATE);
        Boolean b = uiState.getBoolean("isLoginAA", false);
        System.out.println("b!!!!!!" + b);
        if (!b) {
            SharedPreferences.Editor editor = uiState.edit();
            editor.putBoolean("isLoginAA", true);
            editor.commit();
        }
        this.reSetParams();
        try {
            Log.e(null, "--------------获取城市列表");
            handler.sendEmptyMessage(11);
            mCityMan.loadCityList();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.reSetParams();
        handler.sendEmptyMessage(99);
    }


    //切换城市进行 url重新设置
    public void reSetParams() {
        SwitchCity sc = new SwitchCity();
        int defaultCityCode = mCityMan.getSelectCityCode();
        List<SwitchCity> list = null;
        try {
            if (defaultCityCode == 0) {
                //未选择城市，启用定位到的位置
                sc.setCityName(ConstData.GPS_CITY);
                list = mCityMan.getSwitchCityByName(ConstData.GPS_CITY);
                if (list == null || list.size() == 0) {
                    return;
                }
                sc = list.get(0);
                mCityMan.setCurrentRegion(sc);
                ConstData.URL = sc.getUrl();
                ConstData.goURL = "http://" + sc.getIp() + ":" + sc.getGoServerPort();
                URL url;
                try {
                    url = new URL(ConstData.URL);
                    ConstData.tmURL = "http://" + url.getHost() + ":" + url.getPort();
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ConstData.CENTER_X = ConstData.GPS_X;
                ConstData.CENTER_Y = ConstData.GPS_Y;
            } else {
                sc.setCityCode(defaultCityCode);
                list = mCityMan.getSwitchCityById(defaultCityCode);
                sc = list.get(0);
                mCityMan.setSelectedCity(sc);
                ConstData.URL = sc.getUrl();
                ConstData.goURL = "http://" + sc.getIp() + ":" + sc.getGoServerPort();
                URL url;
                try {
                    url = new URL(ConstData.URL);
                    ConstData.tmURL = "http://" + url.getHost() + ":" + url.getPort();
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ConstData.CENTER_X = Double.parseDouble(sc.getCenterX());
                ConstData.CENTER_Y = Double.parseDouble(sc.getCenterY());
            }

            mCityMan.getAllLine();
            mCityMan.updateCityServer(false, handler);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("初始化", "获取城市列表出错！");
        }
    }


    @Override
    public void onDestroy() {
        mLocMan.stop();// 停止定位
        super.onDestroy();
    }

}