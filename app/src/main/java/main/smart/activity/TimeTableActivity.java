package main.smart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.smart.bus.activity.BusLineSearchActivity;
import main.smart.bus.bean.BusBean;
import main.smart.bus.util.BusManager;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

public class TimeTableActivity extends Activity {
	private TextView timeTable_lineCode,timeTable_firstStation,timeTable_endStation;
	private BusManager busManager= BusManager.getInstance();
	private String lineCode;
	private ListView timeTable_listView;
	private LinearLayout timeTable__linear_pb;
	private BusLineSearchActivity serch;
	private List<String> timeList=new ArrayList<String>();
	private  Handler han;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_table);

		han=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					timeTable__linear_pb.setVisibility(View.GONE);
					timeTable_listView.setAdapter(mAdapter);
					break;
				default:
					break;
				}
			};
		};
		Runnable run=new Runnable() {
			
			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				mAdapter.notifyDataSetChanged();
			}
		};
		han.postDelayed(run, 6000);
		timeTable__linear_pb=(LinearLayout) findViewById(R.id.timeTable__linear_pb);
		timeTable_lineCode=(TextView) findViewById(R.id.timeTable_lineCode);
		timeTable_firstStation=(TextView) findViewById(R.id.timeTable_firstStation);
		timeTable_endStation=(TextView) findViewById(R.id.timeTable_endStation);
		lineCode=busManager.getSelectedLine().getLineCode();
		timeTable_lineCode.setText(lineCode+"·");
		timeTable_firstStation.setText(busManager.getBeginAndEndStaion(lineCode, busManager.getSelectedLine().getLineId()).get(0));
		timeTable_endStation.setText(busManager.getBeginAndEndStaion(lineCode, busManager.getSelectedLine().getLineId()).get(1));
		timeTable_listView=(ListView) findViewById(R.id.timeTable_listView);
		serch=new BusLineSearchActivity();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		System.out.println("data="+date);
		System.out.println("lineCode"+lineCode);
		//timeList=getLineBusTimeList(date, lineCode,busManager.getSelectedLine().getLineId());
		timeTable__linear_pb.setVisibility(View.VISIBLE);

		new Thread(){
			public void run() {
				try {
					getLineBusTimeList(date, lineCode,busManager.getSelectedLine().getLineId());


				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			};
		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.time_table, menu);
		return true;
	}

	public void getLineBusTimeList(String date,String lineCode,final int sxx)
	{
		System.out.println("sxx="+sxx);
		final List<String> list=new ArrayList<String>();
		String url= ConstData.URL+"!getLineBusTimeList.shtml";
		RequestParams param = new RequestParams();
		param.put("queryDate", date);
		param.put("lineCode",lineCode);
		final List<String> sxx0=new ArrayList<String>();
		final List<String> sxx1=new ArrayList<String>();
		Log.e("gogo",url+"timeList==============="+date+"******"+lineCode);
		http://222.135.92.18:4001/sdhyschedule/PhoneQueryAction!getLineBusTimeList.shtml?queryDate=2020-01-06&lineCode=103
		RequstClient.post(url ,
				param, new LoadCacheResponseLoginouthandler(TimeTableActivity.this,
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
									//����list
									System.out.println("���ӳɹ���");
									JSONObject json =new JSONObject(data.toString());
//	                            	   Log.d("BusLineSearch","json="+json);
									if(json!=null){
										JSONArray timeListArr = json.getJSONArray("lineTime");
//	                            		   System.out.println("timeListArr="+timeListArr);
										for(int k=0;k<timeListArr.length();k++){
											JSONObject timeList =timeListArr.getJSONObject(k);

//	                                		 //����ʱ���������
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
									timeList=list;
									han.sendEmptyMessage(0);

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									Log.e("���ݷ��ش���", "δ�������ص���·վ��");
								}
							}

							@Override
							public void onFailure(String error, String message) {
								super.onFailure(error, message);
								Log.e("�������ݿ����", "�������粻ͨ��Ip��ַ����");
							}

							@Override
							public void onFinish() {
								super.onFinish();
							}
						}));
		System.out.println("list="+list.toString());
		//return list;
	}
	/****
	 * �Զ���������
	 * 
	 * ***/
	private BaseAdapter mAdapter = new BaseAdapter(){
		public int getCount() {// ���߳���
			return timeList.size();
		}

		public Object getItem(int paramInt) {
			return timeList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
//			mStations = mBusMan.getSelectedLine().getStations();
			HandleView localHandleView;
			final BusBean localBusInfo;
			if (paramView != null) {// ��������վ�㣬վ�б�
				localHandleView = (HandleView) paramView.getTag();
			}else{
				paramView = LayoutInflater.from(TimeTableActivity.this).inflate(R.layout.timetable_listview, null);
				localHandleView = new HandleView();
				localHandleView.time = ((TextView) paramView.findViewById(R.id.timeTable_time));
				paramView.setTag(localHandleView);
			}			
			localHandleView.time.setText(timeList.get(paramInt));
			return paramView;
		}
		class HandleView {
			TextView time;// ʱ��
			
		}
	};
	public void back(View view){
		finish();
	}

}
