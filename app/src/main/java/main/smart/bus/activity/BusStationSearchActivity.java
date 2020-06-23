package main.smart.bus.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.smart.bus.activity.adapter.BusStationAdapter;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusManager;
import main.smart.common.util.CityManager;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

/**
 * 换乘 详细信息页面
 */
public class BusStationSearchActivity extends BaseActivity
        implements AdapterView.OnItemClickListener {
    private static final int BUS_STATION_PAGE_SIZE = 200;
    private static final int MSG_SEARCH_BUS_STATION = 1;
    @BindView(R.id.header)
    Header header;
    private BusStationAdapter mAdapter;
    private BusManager mBusMan;
    private ListView mBusStationListView;
    private List<StationBean> mBusStations = new ArrayList();
    private ProgressBar mProgress;
    private EditText mSearchEdit;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            String str;
            switch (paramMessage.what) {
                default:
                    return;
                case 1:
                    str = BusStationSearchActivity.this.mSearchEdit.getText().toString();
                    break;
            }

            if (TextUtils.isEmpty(str)) {
                mBusStations.clear();
                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.VISIBLE);
                queryBusStation(str);
                return;
            } else {
                mProgress.setVisibility(View.VISIBLE);
                queryBusStation(str);
                return;

            }
        }
    };

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        ButterKnife.bind(this);
        header.setTvTitle(getResources().getString(R.string.bus_search_station));
        this.mBusMan = BusManager.getInstance();
        this.mProgress = ((ProgressBar) findViewById(R.id.progress));
        this.mSearchEdit = ((EditText) findViewById(R.id.search_line));

        BusStationSearchActivity.this.mHandler.removeMessages(1);
        BusStationSearchActivity.this.mHandler.sendEmptyMessageDelayed(1, 600L);
Log.e("momo","这里开始查询站点");
        this.mSearchEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramEditable) {
                BusStationSearchActivity.this.mHandler.removeMessages(1);
                BusStationSearchActivity.this.mHandler.sendEmptyMessageDelayed(1, 600L);
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

            }
        });
        this.mAdapter = new BusStationAdapter(this, this.mBusStations);
        this.mBusStationListView = ((ListView) findViewById(R.id.result_list));
        this.mBusStationListView.setAdapter(this.mAdapter);
        this.mBusStationListView.setOnItemClickListener(this);
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

    /**
     * 实际应用时，采用webservice服务读取静态数据（线路，站点）
     * 同时考虑异步处理不阻塞主线程
     */
    private void queryBusStation(String str) {
        mBusStations.clear();

        List<String> list = mBusMan.FuzzyQueryStationName(str);

        Log.e("momo", "我要到这里站点查询" + list);
        List<StationBean> stationList = new ArrayList<StationBean>();
        for (int i = 0; i < list.size(); i++) {
            StationBean bean = new StationBean();
            bean.setStationName(list.get(i));
            bean.setArea(CityManager.getInstance().getSelectCityCode());
            stationList.add(bean);
        }
        mBusStations.addAll(stationList);
        mAdapter.notifyDataSetChanged();
        mProgress.setVisibility(View.GONE);//进度条
        return;
    }

    public void back(View paramView) {
        onBackPressed();
    }


    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        StationBean localBusStation = (StationBean) this.mBusStations.get(paramInt);
        this.mBusMan.saveBusStationToHistory(localBusStation);
        //HashMap<String,String> lineList = this.mBusMan.getStationAllLineName2(localBusStation.getStationName());
        Intent ient = new Intent();
        Bundle bundle = new Bundle();
        //ConstData.searchStation = localBusStation.getStationName();
        //bundle.putString("zdxx", localBusStation.getStationName());
        List<String> nameList = new ArrayList<String>();
        List<String> codeList = new ArrayList<String>();
        HashMap<String, String> lineList = this.mBusMan.getStationAllLineName2(localBusStation.getStationName());

        if (lineList == null) {
            nameList = this.mBusMan.getStationAllLineName(localBusStation.getStationName());
            bundle.putStringArrayList("lineCodeList", (ArrayList<String>) codeList);
        } else {
            Iterator iter = lineList.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                String lineCode = entry.getKey();
                String lineName = entry.getValue();
                codeList.add(lineCode);
                nameList.add(lineName);
            }
            bundle.putStringArrayList("lineCodeList", (ArrayList<String>) codeList);
            bundle.putStringArrayList("lineNameList", (ArrayList<String>) nameList);

        }
        ient.putExtras(bundle);
        ient.setClass(this, BusLineSearchActivity1.class);
        startActivity(ient);
        setResult(-1);
        finish();
    }
}
