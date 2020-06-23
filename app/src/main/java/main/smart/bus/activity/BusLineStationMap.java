package main.smart.bus.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.smart.bus.bean.StationBeans;
import main.smart.bus.view.BusLineGraphView;
import main.smart.common.SmartBusApp;
import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;
import main.smart.bus.util.BusMonitor;
import main.smart.common.http.DBHandler;
import main.smart.common.util.ConstData;
import main.utils.base.Constant;
import main.utils.base.OkHttpUtil;
import main.utils.base.ProgressDialogUtil;
import okhttp3.Request;
import okhttp3.Response;

public class BusLineStationMap extends Activity implements BusMonitor.BusMonitorInfoListener, BusLineRefresh {
    private static final String TAG = "BusLineStationMap";
    private BusManager mBusMan = BusManager.getInstance();
    private List<StationBean> mStations;
    private List<StationBeans.DataBean> mStationsData = new ArrayList<>();
    private int sxx = 0;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private List<BusBean> mBusData;//排序后的车辆列表
    private Toast mToast = null;
    private List<Marker> mMarkers = new ArrayList<Marker>();
    private List<Marker> mBuses = new ArrayList<Marker>();
    private List<Overlay> mBuseTexts = new ArrayList<Overlay>();
    private Bitmap busIcon;
    private Thread mThread;
    private OkHttpUtil httpUtil;
    private List<StationBean> mLinePotList = null;
    String path_url = "http://222.135.92.18:7006/GetXL/LineJDWD";

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Constant.TAG_ONE:
                    Bundle b = msg.getData();
                    String error = b.getString("error");
                    ProgressDialogUtil.stopLoad();
                    Toast.makeText(BusLineStationMap.this, error, Toast.LENGTH_SHORT).show();
                    break;
                case Constant.TAG_TWO:
                    Bundle b1 = msg.getData();
                    String data = b1.getString("data");
                    Gson gsonF = new Gson();
                    final StationBeans bean = gsonF.fromJson(data, StationBeans.class);
                    for (int i = 0;i<bean.getData().size();i++){
                        mStationsData.add(bean.getData().get(i));
                    }
                    ProgressDialogUtil.stopLoad();
                    addStationPoint();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public BusLineStationMap() {
        this.mStations = this.mBusMan.getSelectedLine().getStations();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_mapview);
        httpUtil = OkHttpUtil.getInstance(this);
        busIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bustransfer_busway);
        mMapView = (MapView) findViewById(R.id.bmapsView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setTrafficEnabled(true);
        sxx = mBusMan.getSelectedLine().getLineId();
        this.mBusMan.addBusMonitorInfoListener(this);
        StationBean sta0 = this.mStations.get(0);
        if (sxx == 1) {
            sta0 = this.mStations.get(this.mStations.size() - 1);
        }
        double lat = sta0.getLat();
        double lng = sta0.getLng();
        LatLng point = new LatLng(lat, lng);
        LatLng baiduPt = new CoordinateConverter().from(CoordType.GPS).coord(point).convert();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                for (int i = 0; i < mMarkers.size(); i++) {
                    if (arg0 == mMarkers.get(i)) {
                        showToast(mStations.get(i).getStationName());
                    }
                }
                return true;
            }
        });


        HashMap<String, String> map = new HashMap();
        map.put("sxx", sxx+"");
        map.put("xl", mBusMan.getSelectedLine().getLineCode());
        http://222.135.92.18:7006/GetXL/LineJDWD?xl=101&sxx=0
        ProgressDialogUtil.startLoad(this, getResources().getString(R.string.getfound));
        httpUtil.postForm(path_url, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
//                Log.i("main", "response:" + e.toString());
                Message message = new Message();
                Bundle b = new Bundle();
                b.putString("error", e.toString());
                message.setData(b);
                message.what = Constant.TAG_ONE;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Response response) throws IOException {
//                      Log.i("main", "response:" + response.body().string());
                String data = response.body().string();
                Message message = new Message();
                Bundle b = new Bundle();
                b.putString("data", data);
                message.setData(b);
                message.what = Constant.TAG_TWO;
                mHandler.sendMessage(message);
            }
        });

//        addStationPoint();

    }

    //* 显示Toast消息
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }




    public void addStationPoint() {
        BitmapDescriptor s1 = BitmapDescriptorFactory.fromResource(R.drawable.pins);
        BitmapDescriptor sqd = BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
        BitmapDescriptor szd = BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
        LatLng staPoint = null;
        StationBean sta = null;
        StationBeans.DataBean sta1 = null;
        mMarkers.clear();
        List<LatLng> points = new ArrayList<LatLng>();
        if (mStationsData.size()>2){
            for (int i = 0; i < mStationsData.size(); i++) {
                sta1 = mStationsData.get(i);
                staPoint = new LatLng(sta1.getWD(), sta1.getJD());
                // 将GPS设备采集的原始GPS坐标转换成百度坐标
//            LatLng baiduPt = new CoordinateConverter().from(CoordType.GPS).coord(staPoint).convert();
                points.add(staPoint);
            }

            OverlayOptions ooPolyline = new PolylineOptions().width(7).color(0xAA0000FF).points(points);
            mBaiduMap.addOverlay(ooPolyline);
        }

        for (int i = 0; i < mStations.size(); i++) {
            sta = mStations.get(i);
            staPoint = new LatLng(sta.getLat(), sta.getLng());
            // 将GPS设备采集的原始GPS坐标转换成百度坐标
            LatLng baiduPt = new CoordinateConverter().from(CoordType.GPS).coord(staPoint).convert();
            OverlayOptions oo = null;
            if (i == 0) {
                if (sxx == 0) {
                    oo = new MarkerOptions().icon(sqd).position(baiduPt);
                } else if (sxx == 1) {
                    oo = new MarkerOptions().icon(szd).position(baiduPt);
                }
            } else if (i == mStations.size() - 1) {
                if (sxx == 0) {
                    oo = new MarkerOptions().icon(szd).position(baiduPt);
                } else if (sxx == 1) {
                    oo = new MarkerOptions().icon(sqd).position(baiduPt);
                }
            } else {
                oo = new MarkerOptions().icon(s1).position(baiduPt);
            }
            mMarkers.add((Marker) mBaiduMap.addOverlay(oo));
        }
    }

    public void drawLine(int type) {
    }

    /**
     * 增加车辆图标覆盖物
     */
    public void addBusPoint(String busCode, double lat, double lon) {


        LatLng point = new LatLng(lat, lon);
        LatLng baiduPt = new CoordinateConverter().from(CoordType.GPS).coord(point).convert();
        //Bitmap bmp = getMyBitmap(busCode);
        MarkerOptions mo = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(busIcon)).position(baiduPt).title(busCode);
        //Overlay aa = mBaiduMap.addOverlay(oo);
        mBuses.add((Marker) mBaiduMap.addOverlay(mo));

        OverlayOptions ooText = new TextOptions()
                .fontSize(20).fontColor(getResources().getColor(R.color.black_text)).text(busCode).position(baiduPt);

        mBuseTexts.add(mBaiduMap.addOverlay(ooText));

//		 GeoPoint gpsPoint = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
//		 GeoPoint baiduP =CoordinateConvert.fromWgs84ToBaidu(gpsPoint);
//		 OverlayItem busItem = new OverlayItem(baiduP,busCode,"item1");
//		 TextItem textItem = new TextItem();
//		 textItem.fontColor = textColor;
//		 textItem.fontSize = 20;
//		 textItem.text = busCode;
//		 textItem.pt =baiduP;
//		 busTextOverLay.addText(textItem);
//		 busItem.setMarker(busIcon);
//		 busOverLay.addItem(busItem);
//		 mMapView.refresh();
    }

    public void clearBusOverLay() {
        for (int i = 0; i < mBuses.size(); i++) {
            Marker mark = mBuses.get(i);
            mark.remove();
        }
        mBuses.clear();
        for (int i = 0; i < mBuseTexts.size(); i++) {
            Overlay mark = mBuseTexts.get(i);
            mark.remove();
        }

        mBuseTexts.clear();
    }

    /**
     * 百度地图
     * 收到新数据包更新车辆位置
     */
    @Override
    public void onBusMonitorInfoUpdated(List<BusBean> paramList) {
        System.out.println("数据更新----->" + mMapView);
        this.mBusData = paramList;
        Log.d("BusLineStationMap", "车辆数据更新开始");
        if (mMapView == null)
            return;
        refreshData();
        //清除车辆覆盖物
        clearBusOverLay();
        LineBean line = this.mBusMan.getSelectedLine();
        int sxx = line.getLineId();
        for (int i = 0; i < this.mBusData.size(); i++) {
            BusBean bus = this.mBusData.get(i);
            String s = "";
            if (bus.getBusNum() != null && !bus.getBusNum().equals("")) {
                s = bus.getBusNum();
            } else {
                s = bus.getBusCode();
            }
            addBusPoint(s, Double.parseDouble(bus.getLat()), Double.parseDouble(bus.getLng()));
        }
        Log.d("BusLineStationMap", "车辆数据更新结束");
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
//		this.mGetOnStation = -1;
//		this.mGetOffStation = -1;
//		int newSxx =mBusMan.getSelectedLine().getLineId();
//		if(sxx!=newSxx){
//			clearStationOverLay();
//			sxx =newSxx;
//			//上下行改变，重新加载站点
//			this.addStationPoint();
//		}
    }

    public void onDestroy() {

        this.mBusMan.removeBusMonitorInfoListener(this);
        mMapView.onDestroy();
        super.onDestroy();
    }

    public void back(View paramView) {
        //mBusMan.stopMonitor();
        onBackPressed();
    }

}

