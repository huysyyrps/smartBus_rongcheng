package main.smart.bus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import butterknife.OnClick;
import main.Adapter.BaseRecyclerAdapternew;
import main.sheet.bean.ComplaintsList;
import main.sheet.complaints.ComplaintsDetailActivity;
import main.sheet.complaints.ComplaintsListActivity;
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

import main.utils.utils.BaseViewHolder;
import main.utils.views.Header;

/**
 * 线路查询输入框
 **/
public class BusLineSearchActivity extends BaseActivity implements OnItemClickListener {
    private static String SEARCH_LINE = "";
    private static String SEARCH_LINENAME = "";
    @BindView(R.id.header)
    Header header;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvCleam)
    TextView tvCleam;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BusLineAdapter mBusLineAdapter;
    private ListView mBusLineListView;
    private List<LineBean> mBusLines = new ArrayList();
    private List<LineBean> lineList = null;
    private BusManager mBusMan;
    private ProgressBar mProgress;
    private CityManager mCityMan = CityManager.getInstance();
    int cityCode = 0;
    private String mLineCode = "";
    private HashMap<String, String> mLineName = new HashMap<String, String>();
    private List<String> mLineNameList = new ArrayList<String>();
    private List<String> mLineCodeList = null;
    List<String> listweb = new ArrayList<String>();
    List<String> listbsport = new ArrayList<String>();
    List<String> listsocketport = new ArrayList<String>();
   // List<LineBean> listAll = new ArrayList<LineBean>();
    List<LineBean> listCity = new ArrayList<LineBean>();
    List<LineBean> listVillage = new ArrayList<LineBean>();
    List<LineBean> listAll1 = new ArrayList<LineBean>();
    List<LineBean> listCity1 = new ArrayList<LineBean>();
    List<LineBean> listVillage1 = new ArrayList<LineBean>();
    String webip = "";
    String bsPort = "";
    String socketport = "";
    BaseRecyclerAdapternew mAdapter;

    String line = "";
    String lineName = "";
    List<LineBean> list = new ArrayList<LineBean>();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            String line = "";
            String lineName = "";
            switch (paramMessage.what) {
                case 1:
                    LineBean lv = new LineBean();
                    for (int i = 0; i < mCityMan.getLine().size(); i++) {
                        lv = mCityMan.getLine().get(i);
                        if (lv.getLineName().equals(lineName)) {
                            line = lv.getLineCode();
                            Log.e(null, "##6666####################" + lv.getLineCode());
                        }
                    }
                    break;
                case 2:
                    line = mLineCode;
                    lineName = mLineName.get(line);
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Intent intent = getIntent();
        Bundle bunde = intent.getExtras();

        ButterKnife.bind(this);
        header.setTvTitle(getResources().getString(R.string.bus_line_search));
        try {
            this.mBusMan = BusManager.getInstance();
            this.mProgress = ((ProgressBar) findViewById(R.id.progress));
            // 查询按钮
            // 若是取用的gps，则城市代码另取
            cityCode = mCityMan.getSelectedCityCode() == 0 ? mCityMan.getCurrentCityCode() : mCityMan.getSelectCityCode();
            if (bunde != null) {
                // lineAuto.setFocusable(false);
            } else {
                setLineAdapter();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.mBusLineListView = ((ListView) findViewById(R.id.result_list));
        this.mBusLineAdapter = new BusLineAdapter(this, this.mBusLines);
        this.mBusLineListView.setAdapter(this.mBusLineAdapter);
        this.mBusLineListView.setOnItemClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();

                listAll1.clear();
                if(text.equals("")){
                    listAll1.addAll(list);
                }else{
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getLineName().contains(text)) {
                            listAll1.add(list.get(i));
                        }
                    }
                }

                    mAdapter = new BaseRecyclerAdapternew<LineBean>(BusLineSearchActivity.this, R.layout.down, listAll1) {
                        @Override
                        public void convert(BaseViewHolder holder, LineBean noticeBean) {
                            holder.setText(R.id.contentTextView, noticeBean.getLineName());;
                            holder.setOnClickListener(R.id.contentTextView, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mProgress.setVisibility(View.VISIBLE);// 显示进度条
                                    recyclerView.setVisibility(View.GONE);

                                    queryBusLine(noticeBean.getLineCode(), noticeBean.getLineName(), webip, bsPort, socketport);
                                }
                            });
                        }


                    };

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();


            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.search_input_layout1;
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
    }

    // 设置所有线路数据源
    public void setLineAdapter() {
        List<LineBean> listLine = mCityMan.getLine();

        if (listLine.size() < 1) {
            mCityMan.getLocalAllLine();
            listLine = mCityMan.getLine();
        }
        list.clear();
        list.addAll(listLine);
        for (int n = 0; n < listLine.size(); n++) {
            //listAll.add(listLine.get(n));
           // list.add(listLine.get(n));
            Log.e("momo",listLine.get(n).getLineName());
        }


        mAdapter = new BaseRecyclerAdapternew<LineBean>(this, R.layout.down, list) {
            @Override
            public void convert(BaseViewHolder holder, LineBean noticeBean) {
                holder.setText(R.id.contentTextView, noticeBean.getLineName());
                holder.setOnClickListener(R.id.contentTextView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgress.setVisibility(View.VISIBLE);// 显示进度条
                        recyclerView.setVisibility(View.GONE);

                        queryBusLine(noticeBean.getLineCode(), noticeBean.getLineName(), webip, bsPort, socketport);
                    }
                });
            }

//            @Override
//            public void convert(BaseViewHolder holder, final LineBean noticeBean) {
//                holder.setText(R.id.contentTextView, noticeBean.getLineName()+"\t\t\t"+noticeBean.getBeginStation()+"--"+noticeBean.getEndStation());;
//                holder.setOnClickListener(R.id.contentTextView, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mProgress.setVisibility(View.VISIBLE);// 显示进度条
//                        recyclerView.setVisibility(View.GONE);
//                        queryBusLine(noticeBean.getLineCode(), noticeBean.getLineName(), webip, bsPort, socketport);
//                    }
//                });
//            }
        };
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

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
          //  http://222.135.92.18:4001/sdhyschedule/PhoneQueryAction!getLineStation.shtml?lineCode=101
            Log.e("soso", "@@@@@@@@(9999999999999@@@@@@@@@" + url+lineCode);
            RequestParams param = new RequestParams();
            param.put("lineCode", lineCode);
            RequstClient.post(url,

                    param, new LoadCacheResponseLoginouthandler(BusLineSearchActivity.this, new LoadDatahandler() {
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
                                 Log.e("gogo","json===="+json);
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
                                    BusLineSearchActivity.this.mBusMan.deleteBusTime(bt);
                                    for (int k = 0; k < busTimeArr.length(); k++) {
                                        JSONObject timeJson = busTimeArr.getJSONObject(k);
                                        // 首末班发车信息存入本地数据库
                                        bt.setSxx(timeJson.getString("sxx"));
                                        bt.setBeginTime(timeJson.getString("beginTime"));
                                        bt.setEndTime(timeJson.getString("endTime"));
                                        BusLineSearchActivity.this.mBusMan.saveBusTime(bt);
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
                                        // line0.setStationDistances(distance);
                                        BusLineSearchActivity.this.lineList.add(line0);
                                        if (BusLineSearchActivity.this.mBusMan.getLocalLine(line0).size() == 0) {
                                            // 线路信息存入本地数据库
                                            BusLineSearchActivity.this.mBusMan.saveBusLine(line0);
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
        startActivity(new Intent(BusLineSearchActivity.this, BusLineDetailActivity.class));
        setResult(-1);
        finish();
    }

    @OnClick({R.id.tvCleam})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCleam:
                etSearch.setText("");
                break;

        }
    }
    public List<String> getLineBusTimeList(String date,String lineCode,final int sxx)
    {
        System.out.println("sxx="+sxx);
        final List<String> list=new ArrayList<String>();
        String url=ConstData.URL+"!getLineBusTimeList.shtml";
        RequestParams param = new RequestParams();
        param.put("queryDate", date);
        param.put("lineCode",lineCode);
        final List<String> sxx0=new ArrayList<String>();
        final List<String> sxx1=new ArrayList<String>();
        Log.e("gogo",url+"timeList==============="+date+"******"+lineCode);
        http://222.135.92.18:4001/sdhyschedule/PhoneQueryAction!getLineBusTimeList.shtml?queryDate=2020-01-06&lineCode=103
        RequstClient.post(url ,
                param, new LoadCacheResponseLoginouthandler(BusLineSearchActivity.this,
                        new LoadDatahandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
//	                              Log.d("getLineInfo:","get line and station data");
                            }
                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                try {
                                    //解析list
                                    System.out.println("连接成功了");
                                    JSONObject  json =new JSONObject(data.toString());
//	                            	   Log.d("BusLineSearch","json="+json);
                                    if(json!=null){
                                        JSONArray timeListArr = json.getJSONArray("lineTime");
//	                            		   System.out.println("timeListArr="+timeListArr);
                                        for(int k=0;k<timeListArr.length();k++){
                                            JSONObject timeList =timeListArr.getJSONObject(k);

//	                                		 //发车时间存入数组
                                            if(!timeList.get("cmdType").equals("3")&&!timeList.get("cmdType").equals("6")){
                                                sxx0.add(timeList.getString("planBegin"));

                                            }else{
                                                sxx1.add(timeList.getString("planBegin"));

                                            }
                                        }
                                    }
                                    if(sxx==0){
                                        list.addAll(sxx0);
                                    }else{
                                        list.addAll(sxx1);
                                    }
                                    ConstData.timeTable=list;
                                    System.out.println("list="+list.toString());

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
        System.out.println("list="+list.toString());
        return list;
    }
}