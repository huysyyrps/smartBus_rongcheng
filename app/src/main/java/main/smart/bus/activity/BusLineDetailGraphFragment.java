package main.smart.bus.activity;


import java.util.List;

import main.smart.activity.SameSationLineActivity;
import main.smart.activity.TimeTableActivity;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.LineBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;
import main.smart.bus.util.BusMonitor;
import main.smart.bus.view.BusLineGraphView;
import main.smart.common.http.DBHandler;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BusLineDetailGraphFragment extends Fragment implements BusMonitor.BusMonitorInfoListener,BusLineGraphView.OnBusStationClickListener, BusLineRefresh {
	/**
	 * 线路图
	 * **/
	//private static final String BICYCLE_ACTIVITY_NAME =
	//"insigma.waybook.bicycle.activity.BicycleActivity";
	private TextView mFirstStation,mEndStation,mFirstTime,mEndTime;//首发站，终点站，首班时间,末班时间
	//private TextView mFirstStation,mEndStation;//首发站，终点站，首班时间,末班时间
	private LinearLayout same_station,timetable;//同站路线,时刻表
	private BusManager mBusMan = BusManager.getInstance();
	private BusLineGraphView mBusLineGraph;
	private int mGetOffStation = -1;
	private int mGetOnStation = -1;
	String TAG="BUS_fragment";
	private BusLineDetailActivity mActivity;
	private Handler mHandler;
	private List<BusBean> mBusList;
	private LineBean mBusLine;		//线路对象
	private String firstStation;//起始站
	private String endStation;//终点站
	private LineBean line;;
	private Context mContext;
	private LineBean lineInfo;
	private String runText;
	//构造函数
	public BusLineDetailGraphFragment(double z){
		this.mBusMan.addBusMonitorInfoListener(this);
	}
	public BusLineDetailGraphFragment(){
		this.mBusMan.addBusMonitorInfoListener(this);
	}
	public BusLineGraphView getLineDetailGraphView(){
		return this.mBusLineGraph;
	}
	public void setLineDetailGraphView(BusLineGraphView v){
		this.mBusLineGraph=v;
	}
	///初始化渲染
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle paramBundle){
		View lineGraphView =null;
		try {
			this.mContext = getActivity();
			lineGraphView= layoutInflater.inflate(R.layout.bus_line_detail_graph, paramViewGroup, false);
			this.mBusLineGraph = ((BusLineGraphView) lineGraphView.findViewById(R.id.bus_line_graph));
			this.mBusLineGraph.setBusLineAndOnOff(this.mBusMan.getSelectedLine(),this.mGetOnStation, this.mGetOffStation);
			this.mBusLineGraph.setOnBusStationClickListener(this);
			this.mFirstStation=(TextView) lineGraphView.findViewById(R.id.FirstStation);
			this.mEndStation=(TextView) lineGraphView.findViewById(R.id.EndStation);
			this.mFirstTime=(TextView) lineGraphView.findViewById(R.id.FirstTime);
			this.mEndTime=(TextView) lineGraphView.findViewById(R.id.EndTime);
			this.same_station=(LinearLayout) lineGraphView.findViewById(R.id.same_station);
			this.timetable=(LinearLayout) lineGraphView.findViewById(R.id.timetable);
			lineInfo=mBusMan.getSelectedLine();
			line=mBusMan.getSelectedLine();
			new Thread(run).start();

			Message msg =new Message();
			msg.what=1;
			msg.obj = mBusLineGraph;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		this.same_station.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				if(ConstData.onBus==0){
					Toast.makeText(getActivity(), R.string.xuezhe, Toast.LENGTH_SHORT).show();
				}else{
					Intent intent=new Intent(getActivity(), SameSationLineActivity.class);
					startActivity(intent);
				}
			}
		});
		this.timetable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent=new Intent(getActivity(), TimeTableActivity.class);
				startActivity(intent);
			}
		});
		this.mBusMan.addBusMonitorInfoListener(this);
		ConstData.onBus=0;
		ConstData.upBus=0;
		ConstData.BusCode="";

		return lineGraphView;
	}

	//线路车辆实时数据刷新控制
	public void onBusMonitorInfoUpdated(List<BusBean> paramList){

		if(mBusLineGraph!=null){
			mBusList = paramList;
			mBusLineGraph.updateBuses(mBusList);
			mBusLineGraph.setBusLine(this.mBusMan.getSelectedLine());
		}

	}
	//点击站点图标
	public void onBusStationClick(View paramView, int paramInt1, int paramInt2,int paramInt3) {}

	//刷新数据
	@Override
	public void refreshData() {
		//this.mBusMan.addBusMonitorInfoListener(this);
		this.mGetOnStation = -1;
		this.mGetOffStation = -1;

		new Thread(run).start();
		if(mBusLineGraph!=null){
			mBusLineGraph.setBusLine(mBusMan.getSelectedLine());
			this.mBusLineGraph.requestLayout();
		}
		//mFirstTime.setText(runText);
	}

//	//通知已经上车
//	@Override
//	public void setOnBus(boolean iden) {
//		// TODO Auto-generated method stub
//		mBusLineGraph.setOnBus(iden);
//	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (BusLineDetailActivity) activity;
		mHandler = mActivity.getHandler();
	}
	public void onDestroy(){
		this.mBusMan.removeBusMonitorInfoListener(this);
		super.onDestroy();
	}
	@Override
	public void onResume() {
		super.onResume();
		if(mBusLineGraph!=null){

			mBusLineGraph.updateBuses(mBusList);
			mBusLineGraph.setBusLine(this.mBusMan.getSelectedLine());

		}


		//this.mBusMan.addBusMonitorInfoListener(this);
	}
	Handler han=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					String firstStation=msg.getData().getString("firstStation");
					String endStation=msg.getData().getString("endStation");
					String firstTime=msg.getData().getString("firstTime");
					String endTime=msg.getData().getString("endTime");
					mFirstStation.setText(firstStation);
					mEndStation.setText(endStation);
					mFirstTime.setText(runText);
					//	mEndTime.setText(endTime);
					break;
				case 1:
					String firstStation_=msg.getData().getString("firstStation");
					String endStation_=msg.getData().getString("endStation");
					String firstTime_=msg.getData().getString("firstTime");
					String endTime_=msg.getData().getString("endTime");
					mFirstStation.setText(firstStation_);
					mEndStation.setText(endStation_);
					//mFirstTime.setText(firstTime_);
					mFirstTime.setText(runText);
					//	mEndTime.setText(endTime_);
					break;
				case 2:
					Toast.makeText(mContext, R.string.wuche, Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		};
	};
	Thread run=new Thread() {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
//			do {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			LineBean line=mBusMan.getSelectedLine();
			int sxx=line.getLineId();
			BusTime bt=new BusTime();
			DBHandler db=new DBHandler();
			//查询票价、首末班及下班次时间
			if(sxx==0){
				runText=db.getTicketPrice(ConstData.goURL+"/GetXL/RunInfo",lineInfo.getLineCode(), 0);

				String firstStation=line.getBeginStation();
				String endStation=line.getEndStation();
				bt.setLineCode(line.getLineCode());
				bt.setSxx("0");
				List<BusTime> list=mBusMan.getBusTime(bt);
				Log.e("list", "---------xianlu-----"+line.getLineCode());

				for (int i=0;i<list.size();i++){
					Log.e("list", "---------1-----"+list.get(i).getBeginTime());
					Log.e("list", "---------2-----"+list.get(i).getEndTime());
				}

				if(list.size()!=0){
					String time=list.get(0).getBeginTime();
					String time_=list.get(0).getEndTime();
					Log.e("list", "---------28-----"+time_);
					String firstTimela=time.substring(0,2)+":"+time.substring(2,4);
					String endTimela=time_.substring(0,2)+":"+time_.substring(2,4);
					Log.e("list", "---------28-----"+endTimela);
					Message msg=new Message();
					msg.what=0;
					Bundle bun=new Bundle();
					bun.putString("firstStation", firstStation);
					bun.putString("endStation", endStation);
					bun.putString("firstTime", runText);
					//bun.putString("endTime", endTimela);
					msg.setData(bun);
					han.sendMessage(msg);
				}	else{
					han.sendEmptyMessage(2);
				}
			}else if(sxx==1){
				runText=db.getTicketPrice(ConstData.goURL+"/GetXL/RunInfo",lineInfo.getLineCode(), 1);
				firstStation=line.getBeginStation();
				endStation=line.getEndStation();
				bt.setLineCode(line.getLineCode());
				bt.setSxx("1");
				List<BusTime> list=mBusMan.getBusTime(bt);
				if(list.size()!=0){
					String time=list.get(0).getBeginTime();
					String time_=list.get(0).getEndTime();
					String firstTime=time.substring(0,2)+":"+time.substring(2,4);
					String endTime=time_.substring(0,2)+":"+time.substring(2,4);
					Message msg=new Message();
					msg.what=1;
					Bundle bun=new Bundle();
					bun.putString("firstStation", firstStation);
					bun.putString("endStation", endStation);
					bun.putString("firstTime", firstTime);
					bun.putString("endTime", endTime);
					msg.setData(bun);
					han.sendMessage(msg);
				}else{
					han.sendEmptyMessage(2);
				}
			}
//			} while (true);
		}
	};

}
