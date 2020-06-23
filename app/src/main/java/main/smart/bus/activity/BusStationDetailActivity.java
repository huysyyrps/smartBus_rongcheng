package main.smart.bus.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import main.Adapter.ExpandableListAdapter;
import main.smart.bus.activity.adapter.FavorBusLineAdapter;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.FavorLineBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.bean.ZDXX;
import main.smart.bus.util.BusManager;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.CharUtil;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

import static main.utils.base.Constant.TAG_ONE;

public class BusStationDetailActivity extends Activity
        implements AdapterView.OnItemClickListener {
    private BusManager mBusMan;
    private ArrayList<FavorLineBean> mStaLineList,mStaLineListnew;
    private String SEARCH_LINE;
    private String SEARCH_LINENAME;
    private int cityCode = 0;
    private CityManager mCityMan = null;
    private FavorBusLineAdapter mAdapter;
    private ProgressBar mProgress;
    private LatLng mCurPoint = null; //当前经纬度
    private LocationClient mLocClient;//百度定位

    private List<LineBean> mLineList = null;
    private FavorLineBean mFavorBusLine;
    private static final double EARTH_RADIUS = 6378137.0;
    private double distance;
    private ListView localListView;
    private Boolean isFirst = true;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private List<LineBean> mBusLines = new ArrayList();
    String tag = "no";
    List<FavorLineBean> aStaList = null;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private LinearLayout zanwu;
    private RelativeLayout wai;
    private List<LineBean> lineList = null;
    public void back(View paramView) {
        onBackPressed();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_bus_station_detail);
        zanwu=findViewById(R.id.zanwu);
        wai=findViewById(R.id.wai);
        mCityMan = CityManager.getInstance();

        this.mBusMan = BusManager.getInstance();
        mProgress = (ProgressBar) findViewById(R.id.staDetailProgress);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, TAG_ONE);
        } else {
            //开始定位
            getLatAndLod();
        }
        mapView=findViewById(R.id.mapView);
        //获取地图控件引用
        mBaiduMap = mapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        this.mProgress = ((ProgressBar) findViewById(R.id.staDetailProgress));
        mStaLineList = new ArrayList<FavorLineBean>();
        mStaLineListnew = new ArrayList<FavorLineBean>();
        localListView = (ListView) findViewById(R.id.bus_station_detail_line_list);

        expandableListView = (ExpandableListView) findViewById(R.id.list);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case TAG_ONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //开始定位
                    getLatAndLod();
                } else {
                    Toast.makeText(BusStationDetailActivity.this, "权限被拒绝，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 开始定位
     */
    private void getLatAndLod() {
        // 定位初始化
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();

        option.setOpenGps(true); // 打开手机GPS
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms ；间隔设置小于1000ms，则只定位一次
        mLocClient.setLocOption(option);
        MyLocationListenner myLocationListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myLocationListener);
        mLocClient.start();
    }
    private void initMap(List<FavorLineBean> listm) {

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17.0f);
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_shengji);
//        LatLng latLngne = new LatLng(mCurPoint.latitude, mCurPoint.longitude);
//        LatLngBounds bounds = new LatLngBounds.Builder().include(latLngne).include(latLngne).build();
//        OverlayOptions ooGround = new GroundOverlayOptions().positionFromBounds(bounds).transparency(0.7f);
//        mBaiduMap.addOverlay(ooGround);
        mBaiduMap.setMapStatus(msu);


        if (listm != null) {

            for(FavorLineBean bean : listm){
                LatLng latLng = new LatLng(Double.parseDouble(bean.getWd()), Double.parseDouble(bean.getJd()));
                Bundle bundle = new Bundle();
                bundle.putSerializable("BEAN", bean);
                View view = View.inflate(getApplicationContext(), R.layout.item_bean, null);
                TextView tView = (TextView)view.findViewById(R.id.item_bean);
               // tView.setText(bean.getId() + "");
                //将View转化为Bitmap
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(view);
                OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor).extraInfo(bundle).zIndex(9).draggable(true);


              //  mBaiduMap.setMaxAndMinZoomLevel(18, 16);
                mBaiduMap.addOverlay(options);
            }


//            mBaiduMap = mapView.getMap();
//            MapStatus.Builder builder = new MapStatus.Builder();
//            for (int i = 0; i < listm.size(); i++) {
//                Log.e("lolo", "这里的几条数据"+listm.get(i).getWd());
//                LatLng ll = new LatLng(Double.valueOf(listm.get(i).getWd()), Double.valueOf(listm.get(i).getJd()));
//
//                        if (tag.equals("no")) {
//            builder.target(ll).zoom(17);
//
//        } else if (tag.equals("yes")) {
//            builder.target(ll);
//        }
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka1n);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .clickable(true)
//                .position(ll)
//
//                .icon(bitmap);
//
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
//
//            }
//
//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        }
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO Auto-generated method stub
                final FavorLineBean beans = (FavorLineBean)marker.getExtraInfo().get("BEAN");
                View markView = View.inflate(getApplicationContext(), R.layout.item_maker, null);
                TextView tv_id = (TextView)markView.findViewById(R.id.mark_id);
                TextView tv_number = (TextView)markView.findViewById(R.id.mark_carnumber);
                TextView tv_speed = (TextView)markView.findViewById(R.id.mark_speed);

                tv_number.setText(beans.getStations().get(0).getStationName());
                tv_speed.setText(getResources().getString(R.string.juli) + beans.getJuli() + getResources().getString(R.string.mi));
                InfoWindow.OnInfoWindowClickListener listener = null;
                listener = new InfoWindow.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick() {
                        // TODO Auto-generated method stub
//                        Intent intent = new Intent(MyMarkActivity.this,TrackActivity.class);
//                        intent.putExtra("bean", beans);
//                        startActivity(intent);
//                        mBaiduMap.hideInfoWindow();
                    }
                };
                LatLng ll = marker.getPosition();
                InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(markView), ll, -47, listener);
                mBaiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });


    }
    /**
     * 自动定位当前位置
     */
    public class MyLocationListenner implements BDLocationListener {
        public void onReceiveLocation(BDLocation result) {
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(result.getRadius())

                    .direction(100).latitude(result.getLatitude())
                    .longitude(result.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirst) {
                isFirst = false;

//                double latitude = 37.180765;    //获取纬度信息
//                double longitude = 122.525497; //获取经度信息
                double latitude = result.getLatitude();    //获取纬度信息
                double longitude = result.getLongitude(); //获取经度信息
                Log.e("latitude", String.valueOf(latitude));
                Log.e("longitude", String.valueOf(longitude));
               // LatLng ll = new LatLng(latitude,longitude);
                LatLng ll = new LatLng(result.getLatitude(),result.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (result == null) {
                    Toast.makeText(BusStationDetailActivity.this, "定位当前位置失败", Toast.LENGTH_LONG).show();
                    return;
                }

                if (result.getLatitude() > 0 && result.getLongitude() > 0) {
                    mCurPoint = new LatLng((int) (result.getLatitude() * 1e6), (int) (result.getLongitude() * 1e6));
                    mCurPoint = new LatLng(latitude, longitude);
//                    mCurPoint = new LatLng(34.559225, 110.890688);
//                    Log.e("mCurPoint", mCurPoint.toString());
                    Thread thread = new Thread(LoadAroundStationLine);
                    thread.start();
                } else {
                    Toast.makeText(BusStationDetailActivity.this, "定位当前位置失败", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }


    }

    //handler处理
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    mProgress.setVisibility(View.INVISIBLE);
                    if (mStaLineListnew.size()==0){
                        zanwu.setVisibility(View.VISIBLE);
//                        mapView.setVisibility(View.GONE);
                        wai.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.GONE);
                        Toast.makeText(BusStationDetailActivity.this, getResources().getString(R.string.no_content), Toast.LENGTH_SHORT).show();
                    }else {
//                        mAdapter = new FavorBusLineAdapter(BusStationDetailActivity.this, mStaLineListnew);
//                        localListView.setAdapter(mAdapter);
//                        localListView.setOnItemClickListener(BusStationDetailActivity.this);
                        initModle();

                    }
                    break;
                default:
                    break;
            }
        }
    };


    private void initModle() {
        List<FavorLineBean> list = new ArrayList();
        List<FavorLineBean> listq ;
        List<FavorLineBean> listnei = null;
        List<List<FavorLineBean>> zuihou = new ArrayList();
        listq=mStaLineList;
        list=removeDuplicate(listq);
        Collections.sort(list, new Comparator<FavorLineBean>() {
            @Override
            public int compare(FavorLineBean favorLineBean, FavorLineBean t1) {
                return favorLineBean.getJuli().compareTo(t1.getJuli());
            }
        });

        List<FavorLineBean> listxian = new ArrayList();
        if(list!=null&&mStaLineListnew!=null&&list.size()>0&&mStaLineListnew.size()>0){
                for (int m = 0; m < list.size(); m++){
                    listnei = new ArrayList();
                    listnei.clear();
                    for (int i = 0; i < mStaLineListnew.size(); i++){
                        if((list.get(m).getStations().get(0).getStationName()).equals(mStaLineListnew.get(i).getStations().get(0).getStationName())){
                            listnei.add(mStaLineListnew.get(i));
                        }
                    }
                    zuihou.add(listnei);
                }
            }
        Log.e("lolo",zuihou.get(0).size()+"=======3333=====");
        adapter = new ExpandableListAdapter(this, list, zuihou);
        expandableListView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(BusStationDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(BusStationDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BusStationDetailActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, TAG_ONE);
        } else {
            //开始定位
            initMap(list);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(getApplicationContext(),
//                        child_text_array[groupPosition][childPosition],
//                        Toast.LENGTH_SHORT).show();

                mFavorBusLine = (FavorLineBean) zuihou.get(groupPosition).get(childPosition);
              //  this.cityCode = mFavorBusLine.getCityCode();
             //   mProgress.setVisibility(View.VISIBLE);// 显示进度条
                ArrayList<StationBean> staList = mFavorBusLine.getStations();
                if (staList != null && staList.size() > 0) {
                    StationBean staBean = staList.get(0);
                    ConstData.searchStation = staBean.getStationName();
                }
                queryBusLine(mFavorBusLine.getLineCode(), mFavorBusLine.getLineName(),"","","");
                return false;
            }
        });
    }

    public   static   List  removeDuplicate(List<FavorLineBean> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  ((list.get(j).getStations().get(0).getStationName()).equals(list.get(i).getStations().get(0).getStationName()))  {
                    list.remove(j);
                }
            }
        }

        return list;
    }
    //加载周边线路信息信息
    Runnable LoadAroundStationLine = new Runnable() {
        @Override
        public void run() {

            try {
                aStaList = loadAroundStation();//站点信息列  里面有附近所有的站点线路信息
                Log.e("gogogo",aStaList.size()+"##############");
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (aStaList != null) {
                Log.e("aStaList", String.valueOf(aStaList.size()));
                for (int i = 0; i < aStaList.size(); i++) {
                    Log.d("i=", String.valueOf(i));

                    FavorLineBean favBean = aStaList.get(i);
                    //展示 只拿上行
                    //List<String> list = mBusMan.getBeginAndEndStaion(favBean.getLineCode(), 0);
                    List<String> list = mBusMan.getBeginAndEndStaion(favBean.getLineCode(), favBean.getLineSxx());
                  //  Log.e("gogogo", "根据站点获取线路"+favBean.getLineSxx());
                    favBean.setBeginStation(list.get(0));
                    favBean.setEndStation(list.get(1));
                    favBean.setStations(aStaList.get(i).getStations());
                //    favBean.setCityCode(mCityMan.getCurrentCityCode());
                    mStaLineList.add(favBean);
                    mStaLineListnew.add(favBean);
                }
            }
            handler.sendEmptyMessage(1);
        }

    };

    //计算所有站点与当前位置距离
    public List<FavorLineBean> loadAroundStation() {
        List<FavorLineBean> staList = new ArrayList<FavorLineBean>();
        LatLng curP = mCurPoint;
        List<ZDXX> stalist = ConstData.list;
        for (int i = 0; i < stalist.size(); i++) {
            ZDXX zdxx = stalist.get(i);
            if (zdxx.getWd() > 0 && zdxx.getJd() > 0) {

                int jds = zdxx.getJd();
                int wds = zdxx.getWd();
                int jdsz = jds / 600000;// 经度整数
                int wdsz = wds / 600000;// 纬度整数
                double jdsd = (double) (jdsz + (jds - jdsz * 600000) / 1000000.0 * 100 / 60);// 经度：整数+小数
                double wdsd = (double) (wdsz + (wds - wdsz * 600000) / 1000000.0 * 100 / 60);// 经度：整数+小数
//                LatLng pt = new LatLng((int) (wdsd * 1E6), (int)(jdsd * 1E6));
                LatLng pt = new LatLng(wdsd, jdsd);
                LatLng baiduP = new CoordinateConverter().from(CoordType.GPS).coord(pt).convert();
//                double distance = DistanceUtil.getDistance(curP, baiduP);
                double distance = gps2m(curP.latitude, curP.longitude,baiduP.latitude,baiduP.longitude);

                if (distance <= 1000) {
                    FavorLineBean favBean = new FavorLineBean();
                    Log.e("gogogo", "loadAroundStation: "+zdxx.getZdname());
                    favBean.setFavorName(zdxx.getXlname() + "-" + zdxx.getZdname());
                    favBean.setLineSxx(zdxx.getSxx());
                    favBean.setLineCode(String.valueOf(zdxx.getXl()));
                    favBean.setLineName(zdxx.getXlname());
                    favBean.setJd(baiduP.longitude+"");
                    favBean.setWd(baiduP.latitude+"");
                    favBean.setJuli(distance+"");
                    StationBean bean = new StationBean();
                    bean.setStationName(zdxx.getZdname());
                    ArrayList<StationBean> list = new ArrayList<StationBean>();
                    list.add(bean);
                    favBean.setStations(list);
                    staList.add(favBean);
                }
            }
        }
        return staList;
    }

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }


    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {

        mFavorBusLine = (FavorLineBean) this.mStaLineList.get(paramInt);
        this.cityCode = mFavorBusLine.getCityCode();
        mProgress.setVisibility(View.VISIBLE);// 显示进度条
        ArrayList<StationBean> staList = mFavorBusLine.getStations();
        if (staList != null && staList.size() > 0) {
            StationBean staBean = staList.get(0);
            ConstData.searchStation = staBean.getStationName();
        }

        queryBusLine(mFavorBusLine.getLineCode(), mFavorBusLine.getLineName());
    }

    private void queryBusLine(String lineCode, String lineName) {
        SEARCH_LINE = lineCode;
        SEARCH_LINENAME = lineName;
        /**
         * 实际应用时，采用webservice服务读取静态数据（线路，站点）
         * 同时考虑异步处理不阻塞主线程
         * 查询本地数据库
         * */
        mLineList = mBusMan.getLineByLineCode(lineCode);
        //1.城市编码currentRegion。getCityCode
        //测试数据静态填写 11路为例
        if (mLineList.size() == 0) {
            //3.查询sqlserver数据库结果集存入本地
            String url = ConstData.URL + "!getLineStation.shtml";
            RequestParams param = new RequestParams();
            param.put("lineCode", lineCode);
            RequstClient.post(url, param,
                    new LoadCacheResponseLoginouthandler(null,
                            new LoadDatahandler() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    Log.d("getLineInfo:", "get line and station data");
                                }
                                @Override
                                public void onSuccess(String data) {
                                    super.onSuccess(data);
                                    try {
                                        //解析list
                                        JSONObject json = new JSONObject(data.toString());
                                        if (json != null) {
                                            JSONArray lineArr = json.getJSONArray("lineList");
                                            JSONArray staArr = json.getJSONArray("stationList");
                                            //起始发车时间
                                            JSONArray busTimeArr = json.getJSONArray("busTimeList");
                                            List<StationBean> lineList0 = new ArrayList<StationBean>();
                                            List<StationBean> lineList1 = new ArrayList<StationBean>();
                                            Collection<Float> upDisList = new ArrayList<Float>();//上行站点间距
                                            Collection<Float> downDisList = new ArrayList<Float>();//下行站点间距
                                            //删除原有发车时间
                                            BusTime bt = new BusTime();
                                            bt.setLineCode(SEARCH_LINE);
                                            BusStationDetailActivity.this.mBusMan.deleteBusTime(bt);
                                            for (int k = 0; k < busTimeArr.length(); k++) {
                                                JSONObject timeJson = busTimeArr.getJSONObject(k);
                                                //首末班发车信息存入本地数据库
                                                bt.setSxx(timeJson.getString("sxx"));
                                                bt.setBeginTime(timeJson.getString("beginTime"));
                                                bt.setEndTime(timeJson.getString("endTime"));
                                                BusStationDetailActivity.this.mBusMan.saveBusTime(bt);
                                            }
                                            //设定线路:确定上下行起止站点
                                            for (int j = 0; j < staArr.length(); j++) {
                                                JSONObject staJson = staArr.getJSONObject(j);
                                                StationBean sta = new StationBean();
                                                sta.setDis(staJson.getString("staDis"));//站点间距
                                                sta.setStationName(staJson.get("stationName").toString());
                                                sta.setArea(cityCode);
                                                sta.setLng(Double.parseDouble(staJson.get("lon").toString()));
                                                sta.setLat(Double.parseDouble(staJson.get("lat").toString()));
                                                sta.setState(SEARCH_LINE);//线路
                                                sta.setId(staJson.get("sxx").toString());//上下行
                                                if (staJson.get("sxx").toString().equals("0")) {
                                                    lineList0.add(sta);
                                                    downDisList.add(Float.parseFloat(sta.getDis()));
                                                } else {
                                                    upDisList.add(Float.parseFloat(sta.getDis()));
                                                    lineList1.add(sta);
                                                }
                                            }
                                            for (int i = 0; i < lineArr.length(); i++) {
                                                JSONObject lineJson = lineArr.getJSONObject(i);
                                                LineBean line0 = new LineBean();
                                                line0.setLineId(lineJson.getInt("sxx"));
                                                line0.setLineCode(SEARCH_LINE);
                                                line0.setLineName(SEARCH_LINENAME);
                                                line0.setGid(Long.toString(new Date().getTime()));
                                                line0.setBeginStation(lineJson.get("beginStation").toString());
                                                line0.setEndStation(lineJson.get("endStation").toString());
                                                //	   line0.setBeginTime(lineJson.get("beginTime").toString());
                                                //	   line0.setEndTime(lineJson.get("endTime").toString());
                                                line0.setCityCode(cityCode);
                                                if (lineJson.get("sxx").toString().equals("0")) {
                                                    line0.setStationSerial(CharUtil.objectToByte(lineList0.toArray()));
                                                    Float[] a = new Float[downDisList.size()];
                                                    line0.setStationDistances(downDisList.toArray(a));
                                                } else {
                                                    line0.setStationSerial(CharUtil.objectToByte(lineList1.toArray()));
                                                    Float[] b = new Float[upDisList.size()];
                                                    line0.setStationDistances(upDisList.toArray(b));

                                                }
                                                // line0.setStationDistances(distance);
                                                BusStationDetailActivity.this.mLineList.add(line0);
                                                if (BusStationDetailActivity.this.mBusMan.getLocalLine(line0).size() == 0) {
                                                    //线路信息存入本地数据库
                                                    BusStationDetailActivity.this.mBusMan.saveBusLine(line0);
                                                    Log.d("-----线路站点存入本地--------", "存储");
                                                    System.out.println("返回的线路站点" + data);
                                                }

                                            }
                                            mProgress.setVisibility(View.GONE);//进度条8
                                            mHandler.sendEmptyMessage(1);
                                        }

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        Log.e("数据返回错误", "未解析返回的线路站点");
                                    }
                                }
                                @Override
                                public void onFailure(String error, String message) {
                                    super.onFailure(error, message);
                                    Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                }
                            }));

        } else {
            mProgress.setVisibility(View.GONE);//进度条8
            mHandler.sendEmptyMessageDelayed(1, 300L);
        }

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            String line = "";
            switch (paramMessage.what) {

                case 1:
                    for (int i = 0; i < mLineList.size(); i++) {
                        LineBean localBusLine = mLineList.get(i);
                        if (localBusLine.getLineCode().equals(mFavorBusLine.getLineCode()) &&
                                localBusLine.getLineId() == mFavorBusLine.getLineSxx()) {

                            mBusMan.setSelectedLine(localBusLine);
                            break;
                        }

                    }
                    //   this.mBusMan.clearSoiList();BusLineDetailActivity
                    startActivityForResult(new Intent(BusStationDetailActivity.this, BusLineStationMap.class), 1);
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "距离为" + distance, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return;
            }

        }
    };

    @Override
    protected void onDestroy() {
        // TODO 自动生成的方法存根
        super.onDestroy();
        mLocClient.stop();
        mStaLineList.clear();
        mStaLineListnew.clear();
    }

    @Override
    public void onBackPressed() {
        // TODO 自动生成的方法存根
        super.onBackPressed();
        mLocClient.stop();
        mStaLineList.clear();
        mStaLineListnew.clear();
        this.finish();
    }

    private void queryBusLine(String lineCode, String lineName, String webip, String bsPort, String socketport) {
        SEARCH_LINE = lineCode;
        SEARCH_LINENAME = lineName;
        mBusLines.clear();
        /**
         * 实际应用时，采用webservice服务读取静态数据（线路，站点） 同时考虑异步处理不阻塞主线程 查询本地数据库
         */
        lineList = mBusMan.getLineByLineCode(lineCode);
//        // 1.城市编码currentRegion。getCityCode
//        // 测试数据静态填写 11路为例
//        if (lineList.size() == 0) {
            String url;
            if (webip == null) {
                url = ConstData.URL + "!getLineStation.shtml";

            } else {
                if (!webip.equals("")) {
                    url = "http://" + webip + ":" + bsPort + "/sdhyschedule/PhoneQueryAction!getLineStation.shtml";
                } else {
                    url = ConstData.URL + "!getLineStation.shtml";
                }
            }
            // 3.查询sqlserver数据库结果集存入本地
            //  http://222.135.92.18:4001/sdhyschedule/PhoneQueryAction!getLineStation.shtml?lineCode=101
            Log.e("soso", "@@@@@@@@(9999999999999@@@@@@@@@" + url+lineCode);
            RequestParams param = new RequestParams();
            param.put("lineCode", lineCode);
            RequstClient.post(url,
                    param, new LoadCacheResponseLoginouthandler(BusStationDetailActivity.this, new LoadDatahandler() {
                        @Override
                        public void onStart() {
                            super.onStart();

                            Log.d("getLineInfo:", "get line and station data");
                        }

                        @Override
                        public void onSuccess(String data) {
                            super.onSuccess(data);
                            try {
                                // 解析list

                                JSONObject json = new JSONObject(data.toString());
                                Log.e("soso","json===="+json);
                                if (json != null) {

                                    JSONArray lineArr = json.getJSONArray("lineList");
                                    JSONArray staArr = json.getJSONArray("stationList");
                                    // 起始发车时间
                                    JSONArray busTimeArr = json.getJSONArray("busTimeList");
                                    List<StationBean> lineList0 = new ArrayList<StationBean>();
                                    List<StationBean> lineList1 = new ArrayList<StationBean>();
                                    Collection<Float> upDisList = new ArrayList<Float>();// 上行站点间距
                                    Collection<Float> downDisList = new ArrayList<Float>();// 下行站点间距
                                    // 删除原有发车时间
                                    BusTime bt = new BusTime();
                                    bt.setLineCode(SEARCH_LINE);
                                    BusStationDetailActivity.this.mBusMan.deleteBusTime(bt);
                                    for (int k = 0; k < busTimeArr.length(); k++) {
                                        JSONObject timeJson = busTimeArr.getJSONObject(k);
                                        // 首末班发车信息存入本地数据库
                                        bt.setSxx(timeJson.getString("sxx"));
                                        bt.setBeginTime(timeJson.getString("beginTime"));
                                        bt.setEndTime(timeJson.getString("endTime"));
                                        BusStationDetailActivity.this.mBusMan.saveBusTime(bt);
                                        //Log.d("-----线路首末班发车时间存入本地--------", "存储");
                                    }


                                    // 设定线路:确定上下行起止站点
                                    for (int j = 0; j < staArr.length(); j++) {
                                        JSONObject staJson = staArr.getJSONObject(j);
                                        StationBean sta = new StationBean();
                                        sta.setDis(staJson.getString("staDis"));// 站点间距
                                        sta.setStationName(staJson.get("stationName").toString());
                                        sta.setArea(cityCode);
                                        sta.setLng(Double.parseDouble(staJson.get("lon").toString()));
                                        sta.setLat(Double.parseDouble(staJson.get("lat").toString()));
                                        sta.setState(SEARCH_LINE);// 线路
                                        sta.setId(staJson.get("sxx").toString());// 上下行
                                        if (staJson.get("sxx").toString().equals("0")) {
                                            lineList0.add(sta);
                                            downDisList.add(Float.parseFloat(sta.getDis()));
                                        } else {
                                            upDisList.add(Float.parseFloat(sta.getDis()));
                                            lineList1.add(sta);
                                        }

                                        // 站点信息存入本地数据库
                                        // BusLineSearchActivity.this.mBusMan.saveBusStation(sta);
                                    }

                                    for (int i = 0; i < lineArr.length(); i++) {
                                        JSONObject lineJson = lineArr.getJSONObject(i);
                                        LineBean line0 = new LineBean();

                                        line0.setLineId(lineJson.getInt("sxx"));
                                        line0.setLineCode(SEARCH_LINE);
                                        line0.setLineName(SEARCH_LINENAME);
                                        // line0.setLineName(lineJson.getString("lineName"));
                                        line0.setGid(Long.toString(new Date().getTime()));
                                        line0.setBeginStation(lineJson.get("beginStation").toString());
                                        line0.setEndStation(lineJson.get("endStation").toString());
                                        // line0.setBeginTime(lineJson.get("beginTime").toString());
                                        // line0.setEndTime(lineJson.get("endTime").toString());
                                        line0.setCityCode(cityCode);


                                        if (lineJson.get("sxx").toString().equals("0")) {
                                            line0.setStationSerial(CharUtil.objectToByte(lineList0.toArray()));
                                            Float[] a = new Float[downDisList.size()];
                                            line0.setStationDistances(downDisList.toArray(a));
                                        } else {
                                            line0.setStationSerial(CharUtil.objectToByte(lineList1.toArray()));
                                            Float[] b = new Float[upDisList.size()];
                                            line0.setStationDistances(upDisList.toArray(b));

                                        }
                                        Log.e("soso","2222json===="+line0);
                                        // line0.setStationDistances(distance);

                                        lineList.add(line0);
//                                        if (BusStationDetailActivity.this.mBusMan.getLocalLine(line0).size() == 0) {
//                                            // 线路信息存入本地数据库
//                                            BusStationDetailActivity.this.mBusMan.saveBusLine(line0);
//                                            Log.d("-----线路站点存入本地--------", "存储");
//                                            System.out.println("返回的线路站点" + data);
//                                        }

                                    }
                                    Log.e("soso","2222json===="+json);
                                   // mProgress.setVisibility(View.GONE);// 进度条8
                                    Log.e("soso","lineList===="+lineList.size());
                                    mBusLines.addAll(lineList);
                                  //  mHandler.sendEmptyMessageDelayed(3, 300L);
                                    LineBean localBusLine = (LineBean) mBusLines.get(0);
                                    mBusMan.saveBusLineToHistory(localBusLine);
                                    mBusMan.setSelectedLine(localBusLine);
                                    // mBusMan.clearSoiList();
                                    // 跳转到线路图
                                    startActivity(new Intent(BusStationDetailActivity.this, BusLineDetailActivity.class));
                                    setResult(-1);

                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.e("soso", "未解析返回的线路站点");
                            }
                        }

                        @Override
                        public void onFailure(String error, String message) {
                            super.onFailure(error, message);
                            Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();


                        }
                    }));

//        } else {
//            mProgress.setVisibility(View.GONE);// 进度条8
//            mBusLines.addAll(lineList);
//
//        }
//        Log.e("soso", "queryBusLine: ==="+this.mBusLines.size());
//         LineBean localBusLine = (LineBean) this.mBusLines.get(0);
//        this.mBusMan.saveBusLineToHistory(localBusLine);
//        this.mBusMan.setSelectedLine(localBusLine);
//        startActivityForResult(new Intent(BusStationDetailActivity.this, BusLineDetailActivity.class), 1);


    }

}