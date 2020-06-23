package main.smart.bus.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.smart.bus.activity.adapter.AutoAdapter;
import main.smart.common.http.DataBase;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

//import com.baidu.mapapi.search.MKGeocoderAddressComponent;
//百度换乘页面
public class BaiduRouteFragment extends Fragment implements OnGetRoutePlanResultListener {
    public String qidian;
    public String zongdian;
    private int MID;
    private Button btn;
    private String city;
    private ImageButton ks;
    private ImageButton js;
    private String isdata = "1";
    private View localView;
    private Activity activity;
    private String[] hisstore = new String[3];
    ;        // 历史数据的数据源
    private DataBase database;
    private boolean iden = false;
    private boolean flag = false;
    private String Address = "";    //公司地址
    private String Homeadd = "";    //家庭地址
    private String Curadd = "";    //当前地址
    private ProgressDialog pd;
    private RoutePlanSearch mSearch = null;    //搜索模块
    //	private BMapManager mBMapMan=null;	//地图引擎
    static public EditText textqd;
    static public EditText textzd;

    static public EditText textqd1;
    static public EditText textzd1;
    private ListView mListViewZd;
    private TransitRouteResult re;
    private PlanNode stNode = null;
    private PlanNode enNode = null;
    private boolean typeFlag = false;
    private List<String> sugAdapter = new ArrayList();
    private LocationClient mLocClient;//百度定位

    private List<PoiInfo> stPois = null;
    private List<PoiInfo> enPois = null;
    private View mHistoryClearView;
    TransitRoutePlanOption mOption = new TransitRoutePlanOption();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        localView = inflater.inflate(R.layout.baidu_route_fragment, container, false);
        Context context = localView.getContext();
        activity = (Activity) context;
        //city = ConstData.SELECT_CITY;getString(R.string.city)
        city = getString(R.string.city);
        // 定位初始化
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(new MyLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开手机GPS
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(500);// 设置发起定位请求的间隔时间为5000ms ；间隔设置小于1000ms，则只定位一次
//	 	option.disableCache(true);// 禁止启用缓存定位
//	 	option.setPoiNumber(5); // 最多返回POI个数
//	 	option.setPoiDistance(1000); // poi查询距离
//	 	option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
        mLocClient.setLocOption(option);
        mLocClient.start();
        this.mHistoryClearView = inflater.inflate(R.layout.search_record_clear, null);
        btn = (Button) localView
                .findViewById(R.id.baidu_route_fragment_search);
        textqd = (EditText) localView.findViewById(R.id.baidu_route_fragment_qd);
        textzd = (EditText) localView.findViewById(R.id.baidu_route_fragment_zd);
        textqd1 = (EditText) localView.findViewById(R.id.et1);
        textzd1 = (EditText) localView.findViewById(R.id.et2);
        ks = (ImageButton) localView.findViewById(R.id.baidu_start_btn);
        ks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("zdType", "qd");
                it.putExtras(bundle);
                it.setClass(activity, BaiduZDMapActivity.class);
                startActivity(it);


            }
        });
        js = (ImageButton) localView.findViewById(R.id.baidu_end_btn);
        js.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("zdType", "zd");
                it.putExtras(bundle);
                it.setClass(activity, BaiduZDMapActivity.class);
                startActivity(it);

            }
        });
//		mBMapMan=new BMapManager(SmartBusApp.getInstance());
//		mBMapMan.init(null);
        btn.setOnClickListener(new SearchListener());

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        database = new DataBase(activity, "AppData");
        textqd.setFocusable(false);
        textqd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 0);
                it.putExtras(bundle);
                it.setClass(activity, BaiduZdSearchActivity.class);
                startActivity(it);
                //startActivityForResult(new Intent(getActivity(),BaiduZdSearchActivity.class),0);

            }
        });

        textzd.setFocusable(false);
        textzd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                it.putExtras(bundle);
                it.setClass(activity, BaiduZdSearchActivity.class);
                startActivity(it);
                //startActivityForResult(new Intent(getActivity(),BaiduZdSearchActivity.class),0);

            }
        });

        database = new DataBase(activity, "AppData");
        Thread thhis = new Thread(LoadHistory);
        thhis.start();
        return localView;
    }

    class SearchListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String qd = textqd1.getText().toString();
            String zd = textzd1.getText().toString();
//            String qd = qidian;
//            String zd = zongdian;
            Builder log = new AlertDialog.Builder(activity);
            log.setTitle(getResources().getString(R.string.title_tips));
            log.setPositiveButton(getResources().getString(R.string.title_tips_confim), null);
            if (qd.equals("")) {
                log.setMessage(getResources().getString(R.string.nostart));
                log.show();
                return;
            }
            if (zd.equals("")) {
                log.setMessage(getResources().getString(R.string.noend));
                log.show();
                return;
            }
            if (qd.equals(zd)) {// 开始结束站点相同报错
                log.setMessage(getResources().getString(R.string.startandend));
                log.show();
                return;
            }
            Log.e("soso","qqqqqqqqqqqqq"+qd);
//            Log.e("soso","22222qqqqqqqqqqqqq"+qd.substring(qd.indexOf("(")+1,qd.lastIndexOf(")")));

            //handler.sendEmptyMessage(0);

//            stNode = PlanNode.withCityNameAndPlaceName(city, qd.substring(zd.indexOf("(")+1,qd.lastIndexOf(")")));
//            enNode = PlanNode.withCityNameAndPlaceName(city, zd.substring(zd.indexOf("(")+1,zd.lastIndexOf(")")));
//            LatLng latLng = new LatLng(34.78235306122535,34.78235306122535);
//            stNode.withLocation(latLng);
//            stNode = PlanNode.withLocation(latLng);
//            LatLng latLng1 = new LatLng(34.542598,110.892885);
//            enNode.withLocation(latLng1);
//            enNode = PlanNode.withLocation(latLng1);
          //
           // mOption.from(stNode).city(getActivity().getResources().getString(R.string.city)).to(enNode).policy(TransitPolicy.EBUS_TRANSFER_FIRST);
//            SQLiteDatabase db = database.getReadableDatabase();
            Double latitude = Double.parseDouble(qd.substring(qd.indexOf("=")+1,qd.indexOf("/")));
            Double longitude = Double.parseDouble(qd.substring(qd.indexOf("/")+1,qd.length()));
            LatLng qidian=new LatLng(latitude,longitude);
            stNode=PlanNode.withLocation(qidian);
//            if(stNode==null){
//                Cursor cursor = db.rawQuery("select * from JWD where address=?",new String[]{qd.substring(qd.indexOf("(")+1,qd.lastIndexOf(")"))});
//                if(cursor.moveToFirst()) {
//                    Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
//                    Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
//                    LatLng qidian=new LatLng(latitude,longitude);
//
//                    stNode=PlanNode.withLocation(qidian);
//                }
//            }

            Double latitude1 = Double.parseDouble(zd.substring(zd.indexOf("=")+1,zd.indexOf("/")));
            Double longitude1 = Double.parseDouble(zd.substring(zd.indexOf("/")+1,zd.length()));
            LatLng zongdian=new LatLng(latitude1,longitude1);
            enNode=PlanNode.withLocation(zongdian);
//            stNode=PlanNode.withLocation(qidian);
//            if(enNode==null){
//                Cursor cursor1 = db.rawQuery("select * from JWD where address=?",new String[]{zd.substring(zd.indexOf("(")+1,zd.lastIndexOf(")"))});
//                if(cursor1.moveToFirst()) {
//                    Double latitude = cursor1.getDouble(cursor1.getColumnIndex("latitude"));
//                    Double longitude = cursor1.getDouble(cursor1.getColumnIndex("longitude"));
//                    LatLng zhongdian=new LatLng(latitude,longitude);
//
//                    enNode=PlanNode.withLocation(zhongdian);
//                }
//            }
           // mOption.from(stNode).city(city).to(enNode).policy(TransitPolicy.EBUS_TRANSFER_FIRST);



            mOption.from(stNode).city(city).to(enNode);



            handler.sendEmptyMessage(0);
            try {
                mSearch.transitSearch(mOption);
            } catch (Exception e) {
                // TODO: handle exception
                handler.sendEmptyMessage(2);
            }

//            new AlertDialog.Builder(activity).setTitle(getResources().getString(R.string.huancheng_type))
//                    .setIcon(android.R.drawable.ic_dialog_info)
//                    .setCancelable(true)
//                    .setSingleChoiceItems(new String[]{getResources().getString(R.string.bus_transfer_first)
//                            , getResources().getString(R.string.bus_walk_first)
//                            , getResources().getString(R.string.bus_time_first)}, 0, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//                            typeFlag = true;
//                            //= new TransitRoutePlanOption().city(city);
//                            if (which == 0) {
//
//                                mOption.from(stNode).city(getActivity().getResources().getString(R.string.city)).to(enNode).policy(TransitPolicy.EBUS_TRANSFER_FIRST);
//                                //mSearch.setTransitPolicy(Search.EBUS_TRANSFER_FIRST);
//                            }
//                            if (which == 1) {
//                                mOption.from(stNode).city(city).to(enNode).policy(TransitPolicy.EBUS_WALK_FIRST);
//                                //mSearch.setTransitPolicy(MKSearch.EBUS_WALK_FIRST);
//                            }
//
//                            if (which == 2) {
//                                mOption.from(stNode).city(city).to(enNode).policy(TransitPolicy.EBUS_TIME_FIRST);
//                                //mSearch.setTransitPolicy(MKSearch.EBUS_TIME_FIRST);
//                            }
//                            dialog.dismiss();
//                            mOption.from(stNode).city(city).to(enNode).policy(TransitPolicy.EBUS_TIME_FIRST);
//                            handler.sendEmptyMessage(0);
//                            try {
//                                mSearch.transitSearch(mOption);
//                            } catch (Exception e) {
//                                // TODO: handle exception
//                                handler.sendEmptyMessage(2);
//                            }
//                        }
//                    }).show();
//            } else {
//                handler.sendEmptyMessage(0);
//                try {
//                    mOption.from(stNode).city(city).to(enNode);
//                    mSearch.transitSearch(mOption);
//                } catch (Exception e) {
//                    // TODO: handle exception
//                    handler.sendEmptyMessage(2);
//                }
//            }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0://显示查询等待
                    pd = ProgressDialog.show(activity, getResources().getString(R.string.getfound), getResources().getString(R.string.getdata));
                    break;
                case 1://跳转到换乘详细信息
                    pd.cancel();
                    typeFlag = false;
                    Intent ient = new Intent();
                    ient.setClass(activity, BaiduRouteResultActivity.class);
                    startActivity(ient);
                    break;
                case 2://关闭查询进度条
                    pd.cancel();
                    break;
                case 3://显示历史数据
                    break;
                case 4://增加历史记录
                    Thread th = new Thread(addHistory);
                    th.start();
                    break;
                case 5:
                    pd.cancel();
                    //List<PoiInfo> stPois = re.getAddrResult().mStartPoiList;
                    String[] data = new String[stPois.size()];
                    for (int i = 0; i < stPois.size(); i++) {
                        PoiInfo mk = stPois.get(i);
                        data[i] = mk.name;
                    }
                    selectQd(data, stPois);
                    break;
                case 6:
                    pd.cancel();
                    //List<PoiInfo> enPois = re.getAddrResult().mEndPoiList;
                    String[] str = new String[enPois.size()];
                    for (int i = 0; i < enPois.size(); i++) {
                        PoiInfo mk = enPois.get(i);
                        str[i] = mk.name;
                    }
                    selectZd(str, enPois);
                    break;
                case 7:
                    Toast.makeText(activity, getResources().getString(R.string.umeng_socialize_network), Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    //加载历史记录信息
    Runnable LoadHistory = new Runnable() {
        @Override
        public void run() {
            if (isdata.equals("1")) {// 加载历史数据
                int i = 3;
                int j = 3;
                Set<String> set = new HashSet();
                SQLiteDatabase db = database.getReadableDatabase();
                //db.beginTransaction();
                Cursor curor = db.query(true, "LSJL", new String[]{"zdname,zdtype"}, null, null, null, null, null, null);
                while (curor.moveToNext()) {
                    String zd = curor.getString(curor.getColumnIndex("zdname"));
                    String lx = curor.getString(curor.getColumnIndex("zdtype"));
                    if (lx.equals("10")) {
                        i++;
                        set.add(zd);
                    }
                }
                //db.setTransactionSuccessful();
                //db.endTransaction();
                if (curor != null) {
                    curor.close();
                }
                db.close();
                Homeadd = ConstData.homeAddr;
                Address = ConstData.workAddr;
                Curadd = ConstData.curAddr;
                hisstore = new String[set.size() + j];
                hisstore[0] = "我要回家 " + Homeadd;
                hisstore[1] = "我要上班 " + Address;
                hisstore[2] = "我的位置 " + Curadd;
                //hisstore[3] = "我的目的";
                for (String str : set) {
                    hisstore[j] = str;
                    j++;
                }


            }
            if (isdata.equals("2")) {// 清空历史数据
                synchronized (this) {
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.delete("LSJL", "zdtype=?", new String[]{"10"});
                    hisstore = new String[3];
                    hisstore[0] = "我要回家";
                    hisstore[1] = "我要上班";
                    hisstore[2] = "我的位置 " + Curadd;
                    //hisstore[3] = "我的目的";
                    db.close();
                    Homeadd = "";
                    ConstData.homeAddr = "";
                    Address = "";
                    ConstData.workAddr = "";
                }
            }
            handler.sendEmptyMessage(3);
        }
    };

    //增加到历史记录
    Runnable addHistory = new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.beginTransaction();
                ContentValues value1 = new ContentValues();

                value1.put("zdname", textqd.getText().toString());
                value1.put("zdtype", "10");
                ContentValues value2 = new ContentValues();
                value2.put("zdname", textzd.getText().toString());
                value2.put("zdtype", "10");
                db.insert("LSJL", null, value1);
                db.insert("LSJL", null, value2);
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            }
        }
    };

    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        MID = (int) info.id;
        switch (item.getItemId()) {
            case 9:
                // 清空历史
                isdata = "2";
                Thread th = new Thread(LoadHistory);
                th.start();
                Toast.makeText(activity, "清空历史", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    //	//明确起点信息
    public void selectQd(String[] data, final List<PoiInfo> stPs) {
        LinearLayout linearLayoutMain = new LinearLayout(activity);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ListView listView = new ListView(activity);
        listView.setBackgroundColor(Color.WHITE);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, data);
        final AutoAdapter<String> adapter = new AutoAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, data);
        listView.setAdapter(adapter);
        linearLayoutMain.addView(listView);//往这个布局中加入listview
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("选择起点").setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
        dialog.show();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long arg3) {
                String str = adapter.getItem(i);
                textqd.setText(str);

                stNode.withLocation(stPs.get(i).location);

                dialog.cancel();
                handler.sendEmptyMessage(6);
            }
        });
    }

    //	//明确终点点信息
    public void selectZd(String[] data, final List<PoiInfo> enPs) {
        LinearLayout linearLayoutMain = new LinearLayout(activity);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ListView listView = new ListView(activity);
        listView.setBackgroundColor(Color.WHITE);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, data);
        final AutoAdapter<String> adapter = new AutoAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, data);
        listView.setAdapter(adapter);
        linearLayoutMain.addView(listView);//往这个布局中加入listview
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("选择终点").setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
        dialog.show();
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long arg3) {
                String str = adapter.getItem(i);
                textzd.setText(str);

                enNode.withLocation(enPs.get(i).location);

                dialog.cancel();
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        if (stNode != null && enNode != null) {
            isdata = "1";
            Thread th1 = new Thread(LoadHistory);
            th1.start();

        }
        super.onResume();
//		stNode = new PlanNode();
//		enNode = new PlanNode();

    }

    /**
     * 自动定位当前位置
     */
    public class MyLocationListenner implements BDLocationListener {
        public void onReceiveLocation(BDLocation result) {

            // map view 销毁后不在处理新接收的位置
            if (result == null)
                return;

            Curadd = result.getAddrStr();
            ConstData.curAddr = Curadd;
            hisstore[2] = "我的位置 " + Curadd;

            Toast.makeText(activity, getResources().getString(R.string.addresssuccess), Toast.LENGTH_LONG).show();
            mLocClient.stop();
        }

        public void onReceivePoi(BDLocation poiLocation) {
//			Curadd = poiLocation.getAddrStr();
//			hisstore[2] = "我的位置 "+Curadd;
//			initHistory(hisstore);
//			Toast.makeText(activity, "我的位置定位成功:"+Curadd, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult arg0) {
        // TODO Auto-generated method stub
        //起点或终点有歧义，需要选择具体的城市列表或地址列表
        pd.cancel();
        if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR) {
            handler.sendEmptyMessage(2);
            Toast.makeText(activity, getResources().getString(R.string.nodata), Toast.LENGTH_SHORT).show();
            return;
        }
        if (arg0.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            SuggestAddrInfo res = arg0.getSuggestAddrInfo();
            re = arg0;
            stPois = arg0.getSuggestAddrInfo().getSuggestStartNode();
            enPois = arg0.getSuggestAddrInfo().getSuggestStartNode();
            Toast.makeText(activity, getResources().getString(R.string.mqzdxx), Toast.LENGTH_LONG).show();
            if (stPois != null) {
                if (stPois.size() > 1) {
                    handler.sendEmptyMessage(5);
                    return;
                }
            }
            if (enPois != null) {
                if (enPois.size() > 1) {
                    handler.sendEmptyMessage(6);
                    return;
                }
            }
            //ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
            //ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;

            return;
        }

//		if (error == MKEvent.ERROR_ROUTE_ADDR){
//			// 遍历所有地址
//			re=res;
//            ArrayList<PoiInfo> stPois = res.getAddrResult().mStartPoiList;
//            ArrayList<PoiInfo> enPois = res.getAddrResult().mEndPoiList;
//            Toast.makeText(activity, "请明确站点信息", Toast.LENGTH_LONG).show();
//            if(stPois!=null){
//            	if (stPois.size()>1){
//            		handler.sendEmptyMessage(5);
//                 	return;
//                }
//            }
//            if(enPois!=null){
//            	if (enPois.size()>1){
//                	handler.sendEmptyMessage(6);
//                	return;
//                }
//            }
//            //ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//            //ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
//			return;
//		}
//		if (error != 0 || res == null) {
//			Toast.makeText(activity, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
//			handler.sendEmptyMessage(2);
//			return;
//		}
        if (arg0==null){
            Toast.makeText(activity, getResources().getString(R.string.nodata), Toast.LENGTH_SHORT).show();
        }else {
            re = arg0;
            flag = true;
            ConstData.res = arg0;
            if (arg0.getRouteLines().size() > 0) {
                handler.sendEmptyMessage(4);
            }
            handler.sendEmptyMessage(1);

        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult arg0) {
        // TODO Auto-generated method stub

    }


}
