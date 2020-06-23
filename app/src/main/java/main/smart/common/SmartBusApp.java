package main.smart.common;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.mob.MobApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.smart.common.http.SSLSocketClient;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.common.util.ScreenObserver;
import okhttp3.OkHttpClient;

public class SmartBusApp extends MobApplication {
    private static SmartBusApp mInstance;
    // public BMapManager mBMapMan = null;
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    private static LocationClient mLocMan;
    public LocationListener locationListener = new LocationListener();

    public static SmartBusApp getInstance() {
        if (null == mInstance) {
            mInstance = new SmartBusApp();
        }
        return mInstance;
    }

    public static LocationClient getLocManager() {
        return mLocMan;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        initOkGo();
        oList = new ArrayList<Activity>();
        mInstance = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        this.mLocMan = new LocationClient(mInstance);
        mLocMan.registerLocationListener(locationListener);// 注册定位监听
        setLocationOption();// 设定定位参数
        mLocMan.start();// 开始定位
        ScreenObserver.startScreenBroadcastReceiver(this);// 启动屏幕监听广播
    }

    private void initOkGo() {
        OkHttpClient  build = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        //初始化okgo并配置给httpclient
        OkGo.getInstance().init(this).setOkHttpClient(build);
    }
    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
        this.mLocMan = null;
    }

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class LocationListener implements BDLocationListener {
        // 接收位置信息
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            // 例如济南：city :济南市 cityCode:288
            // location.getCityCode();
            CityManager.getInstance().setCurrentCity(location.getProvince(), location.getCity());
            StringBuffer sb = new StringBuffer(256);
            // sb.append("time : ");
            // sb.append(location.getTime());
            // sb.append("\nerror code : ");
            // sb.append(location.getLocType());
            // sb.append("\nlatitude : ");
            // sb.append(location.getLatitude());//36.692788
            // 按照定位给地图中心点赋值
            ConstData.GPS_X = location.getLatitude();
            ConstData.GPS_Y = location.getLongitude();
            // sb.append("\nlontitude : ");
            // sb.append(location.getLongitude());//117.097902
            // sb.append("\ncity:");
            // sb.append(location.getCity());
            ConstData.GPS_CITY = location.getCity();
            // sb.append("\ncityCode:");
            // sb.append(location.getCityCode());
            // sb.append("\nradius : ");
            // sb.append(location.getRadius());//56.75
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");// 山东省济南市历下区华阳路67-1
                sb.append(location.getAddrStr());
            }

            // System.out.println("onReceiveLocation:"+sb.toString());
        }

        public void onReceivePoi(BDLocation poiLocation) {
            // 将在下个版本中去除poi功能
            if (poiLocation == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("Poi time : ");
            sb.append(poiLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(poiLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(poiLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(poiLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(poiLocation.getRadius());
            if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(poiLocation.getAddrStr());
            }

            // if(poiLocation.hasPoi()){
            // sb.append("\nPoi:");
            // sb.append(poiLocation.getPoi());
            // }else{
            // sb.append("noPoi information");
            // }
            System.out.println("onReceivePoi:" + sb.toString());
        }
    }

    /**
     * 停止，减少资源消耗
     */
    public void stopListener() {
        if (mLocMan != null && mLocMan.isStarted()) {
            mLocMan.stop();
            mLocMan = null;
        }
    }

    // 设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开手机GPS
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms ；间隔设置小于1000ms，则只定位一次
        // option.disableCache(true);//禁止启用缓存定位
        // option.setPoiNumber(5); //最多返回POI个数
        // option.setPoiDistance(1000); //poi查询距离
        // option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
        mLocMan.setLocOption(option);

    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            System.out.println("activity=" + activity);
            activity.finish();
        }
    }

    //获取软件版本号
    private int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode

            versionCode = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionCode;
            Log.e("updateversionmanager", "versioncode=" + String.valueOf(versionCode));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }



}
