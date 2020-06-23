package main.smart.bus.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.smart.bus.activity.adapter.BusStationAdapter;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusManager;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 换乘 详细信息页面
 */

/**
 * 站点查询fragment
 **/
public class BusStationSerachFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private ProgressBar mProgress;
    private BusManager mBusMan;
    private CityManager mCityMan = null;
    private ListView mBusStaHistoryView;
    private BusStationAdapter mAdapter;
    private List<StationBean> mBusStaRecords = new ArrayList();
    private View mHistoryClearView;
    private EditText mSearchEdit;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case 1: {
                }
                case 2: {
                    mProgress.setVisibility(View.VISIBLE);//显示进度条
                    break;
                }
                case 3: {
                }
            }
        }
    };
    private List<LineBean> linebean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.mBusMan = BusManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup paramViewGroup, Bundle savedInstanceState) {
        View localView = inflater.inflate(R.layout.bus_searchstation_fragment, paramViewGroup, false);
        Context context = localView.getContext();
        mCityMan = CityManager.getInstance();
        localView.findViewById(R.id.search_map_btn).setVisibility(View.GONE);
        try {
            this.mBusStaRecords.addAll(this.mBusMan.getBusStationHistory());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.mProgress = ((ProgressBar) localView.findViewById(R.id.stationProgress));
        this.mSearchEdit = ((EditText) localView.findViewById(R.id.search_edit));
        this.mSearchEdit.setHint(R.string.prompt_bus_station);
        this.mSearchEdit.setFocusable(false);
        this.mSearchEdit.setOnClickListener(this);

        this.mHistoryClearView = inflater.inflate(R.layout.search_record_clear, null);
        this.mBusStaHistoryView = ((ListView) localView.findViewById(R.id.bus_station_history_list));
        this.mBusStaHistoryView.addFooterView(this.mHistoryClearView);
        this.mAdapter = new BusStationAdapter(getActivity(), this.mBusStaRecords);
        this.mBusStaHistoryView.setAdapter(this.mAdapter);
        this.mBusStaHistoryView.setOnItemClickListener(this);
        if (this.mBusStaRecords.size() <= 0)
            hideHistoryClearView();
        this.mAdapter.notifyDataSetChanged();
        return localView;
    }

    /**
     * 清空站点查询历史
     */
    private void clearBusStationHistory() {
        this.mBusMan.clearBusStationHistory();
        this.mBusStaRecords.clear();
        this.mAdapter.notifyDataSetChanged();
        hideHistoryClearView();
    }

    /**
     * 隐藏站点查询历史下拉列表面板
     */
    private void hideHistoryClearView() {
        if (this.mBusStaHistoryView.getFooterViewsCount() <= 0)
            return;
        this.mBusStaHistoryView.removeFooterView(this.mHistoryClearView);
    }

    /**
     * 显示 线路查询历史下拉列表面板
     */
    private void showHistoryClearView() {
        if (this.mBusStaHistoryView.getFooterViewsCount() > 0)
            return;
        this.mBusStaHistoryView.addFooterView(this.mHistoryClearView);
    }

    /**
     * 更新 线路查询历史下拉列表
     */
    private void updateBusStationHistroy() {
        this.mBusStaRecords.clear();
        this.mBusStaRecords.addAll(this.mBusMan.getBusStationHistory());
        this.mAdapter.notifyDataSetChanged();
        showHistoryClearView();
    }

    /**
     * 选中查询站点，跳转到线路查询结果界面
     */

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        if (paramInt >= this.mBusStaRecords.size()) {
            clearBusStationHistory();
            return;
        }
        StationBean localBusStation = (StationBean) this.mBusStaRecords.get(paramInt);
        this.mBusMan.saveBusStationToHistory(localBusStation);
        ConstData.searchStation = localBusStation.getStationName();
        List<String> nameList = new ArrayList<String>();
        List<String> codeList = new ArrayList<String>();
        HashMap<String, String> lineList = this.mBusMan.getStationAllLineName2(localBusStation.getStationName());
        Intent ient = new Intent();
        Bundle bundle = new Bundle();
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
        ient.setClass(getActivity(), BusLineSearchActivity1.class);
        startActivity(ient);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        startActivityForResult(new Intent(getActivity(), BusStationSearchActivity.class), 0);
    }

    //选中事件
    class ClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			Thread thread=new Thread(getlinedata);
//			thread.start();
            mHandler.sendEmptyMessage(0);
//			String staName = acTv.getText().toString();
//			Intent ient = new Intent();
//			Bundle bundle = new Bundle();
//			//bundle.getString("zdxx", staName.getZdname());
//			//bundle.putString("jszd", jstxt);
//			ient.putExtras(bundle);
//			ient.setClass(activity, BusStationDetailActivity.class);
//			startActivity(ient);//跳转到查询结果界面
        }

    }

    //站点值过滤
    class zdListener implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }

    //数据补齐
    public String packData(String data) {
        int len = data.length();
        for (int i = len; i < 3; i++) {
            data = data + " ";
        }
        return data;
    }

    /**
     *
     */
    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt2 != -1) && (paramInt1 <= 0))
            return;
        updateBusStationHistroy();
    }

}
