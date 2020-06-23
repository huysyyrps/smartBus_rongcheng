package main.smart.bus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.smart.bus.activity.adapter.BusLineAdapter;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusManager;
import main.smart.common.InstantAutoComplete;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.CharUtil;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

/**
 * 线路查询输入框
 **/
public class BusLineSearchActivity1 extends BaseActivity implements OnItemClickListener {
    private static String SEARCH_LINE = "";
    private static String SEARCH_LINENAME = "";
    @BindView(R.id.header)
    Header header;
    private BusLineAdapter mBusLineAdapter;
    private ListView mBusLineListView;
    private List<LineBean> mBusLines = new ArrayList();
    private List<LineBean> lineList = null;
    private BusManager mBusMan;
    private ProgressBar mProgress;
    private EditText mSearchEdit;
    private CityManager mCityMan = CityManager.getInstance();
    private InstantAutoComplete lineAuto;
    int cityCode = 0;
    private String mLineCode = "";
    private HashMap<String, String> mLineName = new HashMap<String, String>();
    private List<String> mLineNameList = new ArrayList<String>();
    private List<String> mLineCodeList = null;
    private int requstCount = 0;
    private int backCount = 0;
    private int linepostion;
    List<String> listweb;
    List<String> listbsport;
    List<String> listsocketport;
    String webip = "";
    String bsPort = "";
    String socketport = "";
    // private String lineName = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            String line = "";
            String lineName = "";

            switch (paramMessage.what) {

                case 1:

                    lineName = lineAuto.getText().toString();

                    Log.e(null, "##55555####################" + lineName);

                    LineBean lv = new LineBean();

                    for (int i = 0; i < mCityMan.getLine().size(); i++) {
                        lv = mCityMan.getLine().get(i);
                        // Log.e(null,
                        // "##77777777777####################"+lv.getLineName());

                        if (lv.getLineName().equals(lineName)) {
                            line = lv.getLineCode();
                            Log.e(null, "##6666####################" + lv.getLineCode());
                        }
                    }

                    break;
                case 2:
                    line = mLineCode;
                    lineName = mLineName.get(line);

                    // String webip="";
                    // String bsPort = "";
                    // String socketport="";
                    break;
                default:
                    return;
            }
            // 线路框填写空值
            if (TextUtils.isEmpty(line)) {
                mBusLines.clear();
                mBusLineAdapter.notifyDataSetChanged();
                return;
            } else {
                mProgress.setVisibility(View.VISIBLE);// 显示进度条
                Log.e(null, "##2532525####################" + webip);

                queryBusLine(line, lineName, webip, bsPort, socketport);
                return;

            }
        }
    };

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bunde = intent.getExtras();

        if (bunde != null) {
            mLineNameList = bunde.getStringArrayList("lineNameList");
            mLineCodeList = bunde.getStringArrayList("lineCodeList");
        }
        ButterKnife.bind(this);
        header.setTvTitle(getResources().getString(R.string.bus_line_search));
        try {
            this.mBusMan = BusManager.getInstance();
            this.mProgress = ((ProgressBar) findViewById(R.id.progress));
            // 查询按钮
            // 若是取用的gps，则城市代码另取
            cityCode = mCityMan.getSelectedCityCode() == 0 ? mCityMan.getCurrentCityCode()
                    : mCityMan.getSelectCityCode();
            lineAuto = (InstantAutoComplete) findViewById(R.id.search_line);
            if (bunde != null) {
                // lineAuto.setFocusable(false);

            } else {
                lineAuto.setThreshold(0);
                setLineAdapter();
            }
            lineAuto.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    if (mLineCodeList == null) {
                        Log.e(null, "#2222####################" + mLineCodeList);
                        mHandler.removeMessages(1);
                        mHandler.sendEmptyMessageDelayed(1, 300L);
                        webip = listweb.get(position);
                        bsPort = listbsport.get(position);
                        socketport = listsocketport.get(position);

                    } else {
                        try {
                            Log.e(null, "######################" + mLineCodeList);
                            mLineCode = mLineCodeList.get(position);

                            mLineName.put(mLineCode, mLineNameList.get(position));

                        } catch (Exception e) {
                            // TODO: handle exception
                            mLineCode = "";
                        }
                        mHandler.removeMessages(2);
                        mHandler.sendEmptyMessageDelayed(2, 300L);

                    }
                }

            });

            lineAuto.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    if (lineAuto.getText().length() == 0) {
                        lineAuto.showDropDown();

                    }
                    return false;
                }
            });

            lineAuto.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    if (hasFocus) {
                        // lineAuto.showDropDown();
                    }
                }

            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.mBusLineListView = ((ListView) findViewById(R.id.result_list));
        this.mBusLineAdapter = new BusLineAdapter(this, this.mBusLines);
        this.mBusLineListView.setAdapter(this.mBusLineAdapter);
        this.mBusLineListView.setOnItemClickListener(this);

        if (bunde != null) {
            Log.e(null, "这里能打印出来值吗" + mLineNameList);
            ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(BusLineSearchActivity1.this, R.layout.down,
                    R.id.contentTextView, mLineNameList);
            lineAuto.setAdapter(adapterLine);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.search_input_layout;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO 自动生成的方法存根
        super.onWindowFocusChanged(hasFocus);
        lineAuto.showDropDown();
    }

    // 设置所有线路数据源
    public void setLineAdapter() {
        List<LineBean> listLine = mCityMan.getLine();
        List<String> list2 = new ArrayList<String>();
        List<String> list = new ArrayList<String>();
        listweb = new ArrayList<String>();
        listbsport = new ArrayList<String>();
        listsocketport = new ArrayList<String>();
        if (listLine.size() < 1) {
            mCityMan.getLocalAllLine();
            listLine = mCityMan.getLine();
        }
        Log.e(null, "%%%%%%%%%%%%%%%" + ConstData.danwei);
        String city = ConstData.SELECT_CITY;
        list.clear();
        for (int n = 0; n < listLine.size(); n++) {
            list.add(listLine.get(n).getLineName());
            listweb.add(listLine.get(n).getWebIp());
            listbsport.add(listLine.get(n).getBsPort());
            listsocketport.add(listLine.get(n).getSocketPort());

            Log.e(null, "==========================" + listLine.get(n).getDwbhs());
        }

        Log.e(null, "==========================" + listweb);
        Log.e(null, "==333333========================" + list);
        ArrayAdapter<String> adapterLine = new ArrayAdapter<String>(BusLineSearchActivity1.this, R.layout.down,
                R.id.contentTextView, list);

        // ArrayAdapter<String> adapterLine = new
        // ArrayAdapter<String>(BusLineFragment.this,android.R.layout.simple_dropdown_item_1line,listLine);
        lineAuto.setAdapter(adapterLine);

    }

    private void queryBusLine(String lineCode, String lineName, String webip, String bsPort, String socketport) {

        SEARCH_LINE = lineCode;
        SEARCH_LINENAME = lineName;

        mBusLines.clear();
        /**
         * 实际应用时，采用webservice服务读取静态数据（线路，站点） 同时考虑异步处理不阻塞主线程 查询本地数据库
         */
        lineList = mBusMan.getLineByLineCode(lineCode);

        // 1.城市编码currentRegion。getCityCode
        // 测试数据静态填写 11路为例

        if (lineList.size() == 0) {
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

            Log.e(null, "@@@@@@@@(9999999999999@@@@@@@@@" + url);
            RequestParams param = new RequestParams();
            param.put("lineCode", lineCode);
            RequstClient.post(url,

                    param, new LoadCacheResponseLoginouthandler(BusLineSearchActivity1.this, new LoadDatahandler() {
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
                                // Log.d("BusLineSearch","json="+json);
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
                                    BusLineSearchActivity1.this.mBusMan.deleteBusTime(bt);
                                    for (int k = 0; k < busTimeArr.length(); k++) {
                                        JSONObject timeJson = busTimeArr.getJSONObject(k);
                                        // 首末班发车信息存入本地数据库
                                        bt.setSxx(timeJson.getString("sxx"));
                                        bt.setBeginTime(timeJson.getString("beginTime"));
                                        bt.setEndTime(timeJson.getString("endTime"));
                                        BusLineSearchActivity1.this.mBusMan.saveBusTime(bt);
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
                                        // line0.setStationDistances(distance);
                                        BusLineSearchActivity1.this.lineList.add(line0);
                                        if (BusLineSearchActivity1.this.mBusMan.getLocalLine(line0).size() == 0) {
                                            // 线路信息存入本地数据库
                                            BusLineSearchActivity1.this.mBusMan.saveBusLine(line0);
                                            Log.d("-----线路站点存入本地--------", "存储");
                                            System.out.println("返回的线路站点" + data);
                                        }

                                    }
                                    mProgress.setVisibility(View.GONE);// 进度条8
                                    mBusLines.addAll(lineList);
                                    mBusLineAdapter.notifyDataSetChanged();
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
            mProgress.setVisibility(View.GONE);// 进度条8
            mBusLines.addAll(lineList);
            mBusLineAdapter.notifyDataSetChanged();
        }
    }

    public void back(View paramView) {
        onBackPressed();
    }

    /**
     * 选中线路
     */
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        LineBean localBusLine = (LineBean) this.mBusLines.get(paramInt);
        mBusMan.saveBusLineToHistory(localBusLine);
        mBusMan.setSelectedLine(localBusLine);
        // mBusMan.clearSoiList();
        // 跳转到线路图
        startActivity(new Intent(BusLineSearchActivity1.this, BusLineDetailActivity.class));
        setResult(-1);
        finish();
    }
}