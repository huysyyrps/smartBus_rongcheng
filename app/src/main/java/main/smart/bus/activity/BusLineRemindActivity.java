package main.smart.bus.activity;

import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.util.BusManager;
import main.smart.bus.util.BusMonitor;
import main.smart.bus.view.BusLineRemindView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

@SuppressLint("NewApi")
public class BusLineRemindActivity extends Activity implements BusMonitor.BusMonitorInfoListener {

	private BusManager mBusMan = BusManager.getInstance();
	private BusLineRemindView mBusLineGraph;
	private ScrollView mScrollView;


	public BusLineRemindActivity() {
		// TODO Auto-generated constructor stub
		this.mBusMan.addBusMonitorInfoListener(this);
	}

	public BusLineRemindView getmBusLineGraph() {
		return mBusLineGraph;
	}

	public void setmBusLineGraph(BusLineRemindView mBusLineGraph) {
		this.mBusLineGraph = mBusLineGraph;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_line_remind);
		
		//int index=intent.getExtras().getInt("index");
		
		this.mBusLineGraph = (BusLineRemindView) findViewById(R.id.bus_line_remind);
		//this.mScrollView.addView(mBusLineGraph);
		//this.mScrollView =  (ScrollView) findViewById(R.id.remind_scrollview);
		try {
			
			this.mBusLineGraph.setBusLineAndOnOff(this.mBusMan.getSelectedLine(),-1, -1);

			//mHandler.sendEmptyMessageDelayed(1, 500);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("BusLineRemindActivity", e.getMessage());
			e.printStackTrace();
		}
		
		this.mBusMan.addBusMonitorInfoListener(this);
	}


	@Override
	public void onBusMonitorInfoUpdated(List<BusBean> paramList) {
		// TODO Auto-generated method stub
		if(mBusLineGraph!=null){
			mBusLineGraph.updateBuses(paramList);
			mBusLineGraph.setBusLine(this.mBusMan.getSelectedLine());
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//this.mBusMan.addBusMonitorInfoListener(this);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.mBusMan.removeBusMonitorInfoListener(this);
		super.onDestroy();
	}


	public void back(View paramView)
  	{
	    onBackPressed();
    }
	
	

	
	
}
