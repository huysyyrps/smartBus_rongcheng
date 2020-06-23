package main.smart.bus.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import main.smart.common.util.ConstData;

/**
 *
 * 车列表 即将到站车辆
 * */
public class BusLineDetailBusFragment extends Fragment implements BusMonitor.BusMonitorInfoListener, BusLineRefresh {

	// 在线车辆
	private changedTab listener;
	private Activity activity;
	private List<BusBean> mBusInfoList = new ArrayList();
	private BusManager mBusMan = BusManager.getInstance();
	private List<StationBean> mStations;// 所有站点列表
	String TAG = "BUS_fragment";
	private int sxx;
	// -------构造函数-----------
	public BusLineDetailBusFragment() {
		this.mBusMan.addBusMonitorInfoListener(this);
		this.mStations = this.mBusMan.getSelectedLine().getStations();
	}
	public View onCreateView(LayoutInflater layoutinflater,ViewGroup viewgroup, Bundle bundle) {
		View view=null;
		try
		{
			view = layoutinflater.inflate(R.layout.list_view, viewgroup, false);
			activity=(Activity) view.getContext();
			listener=(changedTab)activity;
			ListView listview = (ListView) view.findViewById(R.id.list);
			LineBean lineInfo = mBusMan.getSelectedLine();
			sxx = lineInfo.getLineId();
			listview.setAdapter(mAdapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		this.mBusMan.addBusMonitorInfoListener(this);
		return view;
	}


	/****
	 * 自定义适配器
	 *
	 * ***/
	private BaseAdapter mAdapter = new BaseAdapter(){
		public int getCount() {// 在线车辆
			return mBusInfoList.size();
		}

		public Object getItem(int paramInt) {
			return mBusInfoList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView,ViewGroup paramViewGroup) {
			mStations = mBusMan.getSelectedLine().getStations();
			HandleView localHandleView;
			final BusBean localBusInfo;
			int passOrder;
			int leftNum = 0;
			int onBus = ConstData.onBus;
			localBusInfo = (BusBean) mBusInfoList.get(paramInt);
			if (paramView != null) {// 即将到达站点，站列表
				localHandleView = (HandleView) paramView.getTag();
			} else {
				paramView = LayoutInflater.from(BusLineDetailBusFragment.this.getActivity()).inflate(R.layout.bus_line_detail_bus_item, null);
				localHandleView = new HandleView();
				localHandleView.tvBusId = ((TextView) paramView.findViewById(R.id.bus_id));
				localHandleView.tvArrvStation = ((TextView) paramView.findViewById(R.id.bus_arrive_station));
				localHandleView.tvTargetStation = ((TextView) paramView.findViewById(R.id.bus_target_station));
				localHandleView.tvStationToDest = ((TextView) paramView.findViewById(R.id.station_to_dest));
				paramView.setTag(localHandleView);
				localHandleView.tvBusId.setVisibility(View.VISIBLE);
			}
			//车号
			String showCPH=localBusInfo.getShowCPH();
			String s=null;
			if(showCPH.equals("0")){
				s = localBusInfo.getBusCode();
			}else{
				s = localBusInfo.getBusNum();
			}
			/*if (localBusInfo.getBusNum() != null && !localBusInfo.getBusNum().equals(""))
			{
				s = localBusInfo.getBusNum();
			}
			else
			{
				s = localBusInfo.getBusCode();
			}*/
			localHandleView.tvBusId.setText(s);
			//刚经过的站点编号
			passOrder = localBusInfo.getLeftStation();
			//刚经过的站点名称
			localHandleView.tvArrvStation.setText((mStations.get(passOrder - 1).getStationName()));
			//目的站点下标
			int destStation=1;
			if(onBus==0){
				//没有设置上车点默认距离终点即可
				if (sxx == 0) {// 下行
					destStation=mStations.size();
					leftNum = -passOrder + mStations.size();
				} else {
					destStation=1;
					leftNum = passOrder - 1;
				}
				localHandleView.tvTargetStation.setText(mBusMan.getSelectedLine().getEndStation());
			}else{
				//设置了上车点,只有没经过的显示，经过了的依然显示终点
				if (sxx==0){
					if (onBus>passOrder){
						destStation=onBus;
						leftNum = onBus-passOrder;
						localHandleView.tvTargetStation.setText(mStations.get(onBus-1).getStationName());
					}else{
						destStation=mStations.size();
						leftNum = -passOrder + mStations.size();
						localHandleView.tvTargetStation.setText(mBusMan.getSelectedLine().getEndStation());
					}
				}else{
					if(onBus<passOrder){
						destStation=onBus;
						leftNum = passOrder - onBus;
						localHandleView.tvTargetStation.setText(mStations.get(onBus-1).getStationName());
					}else{
						destStation=1;
						leftNum = passOrder - 1;
						localHandleView.tvTargetStation.setText(mBusMan.getSelectedLine().getEndStation());
					}
				}
			}
			//距离站点数
			localHandleView.tvStationToDest.setText(leftNum + getResources().getString(R.string.station));
			//计算距离目的站点距离
			float distance = 0.0f;
			if (sxx == 0) {// 下行
				for(int i=passOrder-1;i<destStation;i++){
					distance += Float.parseFloat(mStations.get(i).getDis());
				}
			} else {// 上行
				for (int i = destStation-1; i < passOrder-1; i++) {
					distance += Float.parseFloat(mStations.get(i).getDis());
				}
			}
			return paramView;
		}

		class HandleView {
			TextView tvArrvStation;// 当前站点
			TextView tvBusId;// 车号
			TextView tvStationToDest;// 距离多少站
			TextView tvTargetStation;// 目标站
		}
	};

	public void refreshData() {
		this.mBusMan.addBusMonitorInfoListener(this);
		LineBean lineInfo = mBusMan.getSelectedLine();
		sxx = lineInfo.getLineId();
	}

	public void onBusStationClick(View paramView, int paramInt1, int paramInt2,
								  int paramInt3) {

	}

	/**
	 * 收到新数据包更新车辆位置
	 * */
	@Override
	public void onBusMonitorInfoUpdated(List<BusBean> busDataList) {

		this.mBusInfoList.clear();
		for (int i = 0; i < busDataList.size(); i++) {
			int k = i;
			for (int j = busDataList.size() - 1; j > i; j--) {
				if (busDataList.get(j).getLeftStation() < busDataList.get(k).getLeftStation()) {
					k = j;
				}
			}
			BusBean temp = busDataList.get(i);
			busDataList.set(i, busDataList.get(k));
			busDataList.set(k, temp);
		}

		this.mBusInfoList.addAll(busDataList);
		this.mAdapter.notifyDataSetChanged();

	}

	public void onDestroy() {
		mBusMan.removeBusMonitorInfoListener(this);
		super.onDestroy();
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//this.mBusMan.addBusMonitorInfoListener(this);
	}

	//给主Fragment通知切换tab
	public interface changedTab{
		public void changedTab(int i,String ch);
	}


}
