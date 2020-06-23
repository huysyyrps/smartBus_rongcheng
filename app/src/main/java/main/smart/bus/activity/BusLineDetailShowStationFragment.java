package main.smart.bus.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;
import main.smart.bus.util.BusMonitor;
import main.smart.bus.view.RunBusView;
import main.smart.common.http.DBHandler;
import main.smart.common.util.ConstData;

public class BusLineDetailShowStationFragment extends Fragment implements BusMonitor.BusMonitorInfoListener, BusLineRefresh {

    private TextView timeText;
    private TextView nexttime;
    private TextView rangeText;
    private RunBusView runview;
    private LineBean lineInfo;
    private String BusText;
    private String NextText;
    private List<StationBean> mStations;//站点数据
    private BusManager mBusMan = BusManager.getInstance();
    private List<BusBean> mBusInfoList = new ArrayList<BusBean>();

    /**
     * 构造函数
     */
    public BusLineDetailShowStationFragment() {
        this.mBusMan.addBusMonitorInfoListener(this);
        this.mStations = this.mBusMan.getSelectedLine().getStations();
    }

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        // TODO Auto-generated method stub
        View view = null;
        try {
            lineInfo = mBusMan.getSelectedLine();
            view = layoutinflater.inflate(R.layout.buslineshowstation, viewgroup, false);
            runview = (RunBusView) view.findViewById(R.id.bus_view_zxt);
            rangeText = (TextView) view.findViewById(R.id.bus_operation_range);
            timeText = (TextView) view.findViewById(R.id.bus_operation_time);
            nexttime = (TextView) view.findViewById(R.id.bus_operation_nexttime);
            rangeText.setText(getResources().getString(R.string.operation_interval) + lineInfo.getBeginStation() + "--" + lineInfo.getEndStation());
            runview.setData(mStations, lineInfo.getLineId(), lineInfo.getLineCode());
            Thread th = new Thread(runnable);
            th.start();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.mBusMan.addBusMonitorInfoListener(this);
        return view;
    }

    //查询首末班及下班次时间
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lineInfo = mBusMan.getSelectedLine();
            // TODO Auto-generated method stub
            DBHandler db = new DBHandler();
            BusText = db.getBusTime(ConstData.goURL + "/GetRunBusTime", lineInfo.getLineCode(), lineInfo.getLineId() + "");
            NextText = db.getBusTime(ConstData.goURL + "/GetNextBusTime", lineInfo.getLineCode(), lineInfo.getLineId() + "");
            handler.sendEmptyMessage(1);
        }
    };

    //handler处理
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    timeText.setText(getResources().getString(R.string.operation_time) + BusText);
                    nexttime.setText(getResources().getString(R.string.next_time) + NextText);
                    break;
                default:
                    break;
            }

        }

        ;
    };


    @Override
    public void onDestroy() {
        mBusMan.removeBusMonitorInfoListener(this);
        super.onDestroy();
    }

    @Override
    public void refreshData() {
        this.mBusMan.addBusMonitorInfoListener(this);
        LineBean lineInfo = mBusMan.getSelectedLine();
        if (lineInfo != null && rangeText != null && runview != null) {
            this.mStations = this.mBusMan.getSelectedLine().getStations();
            rangeText.setText(getResources().getString(R.string.operation_interval) + lineInfo.getBeginStation() + "--" + lineInfo.getEndStation());
            Thread th = new Thread(runnable);
            th.start();
            runview.setData(mStations, lineInfo.getLineId(), lineInfo.getLineCode());
        }
    }

    @Override
    public void onBusMonitorInfoUpdated(List<BusBean> list) {

        if (list.size() > 0) {
            this.mBusInfoList.clear();
            this.mBusInfoList.addAll(list);
            if (runview != null) {
                runview.updateBus(list);
            }
        }

    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //this.mBusMan.addBusMonitorInfoListener(this);
    }

}
