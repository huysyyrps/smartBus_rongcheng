package main.smart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import main.smart.bus.activity.BusLineDetailActivity;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusManager;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.CharUtil;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;


public class SameSationLineActivity extends Activity implements OnClickListener {
	private BusManager busManager= BusManager.getInstance();
	private List<String> lineCodeList;
	private EditText destination;
	private Button query;
	private Handler han;
	private ListView listview;
	private String stationName;
	private CityManager cityManager= CityManager.getInstance();
	private List<LineBean> lineList = null;
	private int cityCode;
	private List<LineBean> mBusLines = new ArrayList<LineBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_same_sation_line);

		han=new Handler();
		Runnable run=new Runnable() {
			
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				mAdapter.notifyDataSetChanged();
//				listview.setAdapter(mAdapter);
			}
		};
		han.postDelayed(run,500);
		destination=(EditText) findViewById(R.id.destination);
		listview=(ListView) findViewById(R.id.same_station_listview);
//		lineCodeList=busManager.getStationAllLineName(stationName);
		query=(Button) findViewById(R.id.same_station_query);
		query.setOnClickListener(this);	

		if(ConstData.onBus!=0){
			stationName=busManager.getSelectedLine().getStations().get(ConstData.onBus-1).getStationName();
			lineCodeList=busManager.getStationAllLineName(stationName);
			System.out.println("stationName="+stationName);
			listview.setAdapter(mAdapter);
		}else{
			listview.setAdapter(null);
		}
		for(int i=0;i<lineCodeList.size();i++){
			String lineCode=getLineCodeByLineName(lineCodeList.get(i));
			queryBusLine(lineCode, lineCodeList.get(i));
			mAdapter.notifyDataSetChanged();
		}					
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
				// TODO 自动生成的方法存根
				LineBean localBusLine = busManager.getLineByLineCode(divideRoomName(lineCodeList.get(paramInt))[1]).get(busManager.getSelectedLine().getLineId());
//				LineBean line0 =busManager.getLineListByLineCode((divideRoomName(lineCodeList.get(paramInt))[1])).get(0);				
      		   	busManager.setSelectedLine(localBusLine);
      		   	startActivity(new Intent(SameSationLineActivity.this,
      			BusLineDetailActivity.class));
      		   	finish();// 结束当前activity
//				LineBean localBusLine = (LineBean) lineCodeList.get(paramInt);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.same_sation_line, menu);
		return true;
	}
	public void back(View view){
		finish();
	}
	
	
	/****
	 * 自定义适配器
	 * 
	 * ***/
	private BaseAdapter mAdapter = new BaseAdapter(){
		public int getCount() {// 在线车辆
			return lineCodeList.size();
		}

		public Object getItem(int paramInt) {
			return lineCodeList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
//			mStations = mBusMan.getSelectedLine().getStations();
			HandleView localHandleView;
			final BusBean localBusInfo;
			if (paramView != null) {// 即将到达站点，站列表
				localHandleView = (HandleView) paramView.getTag();
			}else{
				paramView = LayoutInflater.from(SameSationLineActivity.this).inflate(R.layout.same_station_listview, null);
				localHandleView = new HandleView();
				localHandleView.lineCode = ((TextView) paramView.findViewById(R.id.same_station_lineCode));
				localHandleView.nextStation = ((TextView) paramView.findViewById(R.id.same_station_nextStation));
				localHandleView.beginStation = ((TextView) paramView.findViewById(R.id.same_station_firstStation));
				localHandleView.endStation = ((TextView) paramView.findViewById(R.id.same_station_endStation));
				paramView.setTag(localHandleView);
			}			
			localHandleView.lineCode.setText(lineCodeList.get(paramInt));
			String lineCode=divideRoomName(lineCodeList.get(paramInt))[1];
			System.out.println("lineCode="+lineCode);
			localHandleView.beginStation.setText(busManager.getBeginAndEndStaion(lineCode, busManager.getSelectedLine().getLineId()).get(0));
			localHandleView.endStation.setText(busManager.getBeginAndEndStaion(lineCode, busManager.getSelectedLine().getLineId()).get(1));			
			List<LineBean> list=new ArrayList<LineBean>();
			list=busManager.getLineByLineCode(lineCode);
			System.out.println("list="+list.size());
			List<StationBean> list_=new ArrayList<StationBean>();
			for(int i=0;i<list.size();i++){
				if(list.get(i).getLineId()==busManager.getSelectedLine().getLineId()){
					list_=list.get(i).getStations();
				}
			}
					
			System.out.println("list_="+list_.toString());
			for(int i=0;i<list_.size()-1;i++){				
				if(list_.get(i).getStationName().equals(stationName)){
					localHandleView.nextStation.setText(list_.get(i+1).getStationName());
				}
			}
			
			return paramView;
		}
		class HandleView {
			TextView lineCode;// 线路号
			TextView nextStation;// 下一站
			TextView beginStation;// 始发站
			TextView endStation;// 终点站
		}
	};

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.same_station_query:
			stationName=destination.getText().toString();
			lineCodeList=busManager.getStationAllLineName(stationName);
			
			for(int i=0;i<lineCodeList.size();i++){
				String lineCode=getLineCodeByLineName(lineCodeList.get(i));
				queryBusLine(lineCode, lineCodeList.get(i));
//				mAdapter.notifyDataSetChanged();
			}		
			listview.setAdapter(mAdapter);			
//			mAdapter.notifyDataSetChanged();
			System.out.println("lineCode="+lineCodeList.toString());
			break;

		default:
			break;
		}
	}	
	// Divide a room name n为字符串，m为数字
	 public String[] divideRoomName(String roomName) {
	  String[] name_Id = new String[2];
	    String n = "";
	  String m = "";
	    if (roomName.length() > 0) {
	    for (int i = 0; i < roomName.length(); i++) {
	     if (roomName.substring(i, i + 1)
	       .matches("[\u4e00-\u9fa5]+")) {
	      n += roomName.substring(i, i + 1);
	     } else {
	      m += roomName.substring(i, i + 1);
	     }
	    }
	   name_Id[0] = n;
	   name_Id[1] = m;
	  }
	  return name_Id;
	 }
	 private void queryBusLine(final String lineCode, final String lineName)
		{		
//			  mBusLines.clear();
			  /**
			   * 实际应用时，采用webservice服务读取静态数据（线路，站点）
			   * 同时考虑异步处理不阻塞主线程
			   * 查询本地数据库
			   * */
			  lineList=busManager.getLineByLineCode(lineCode);
			  //1.城市编码currentRegion。getCityCode
			  //测试数据静态填写 11路为例
		     if(lineList.size()==0){
		      //3.查询sqlserver数据库结果集存入本地
		    	 String url= ConstData.URL+"!getLineStation.shtml";
		   	  RequestParams param = new RequestParams();
				  param.put("lineCode",lineCode );
		       RequstClient.post(url ,
		               
		               param, new LoadCacheResponseLoginouthandler(SameSationLineActivity.this,
		                       new LoadDatahandler() {
		                           @Override
		                           public void onStart() {  
		                               super.onStart();  
		                            
		                              Log.d("getLineInfo:","get line and station data");
		                           }  
		 
		                           @Override
		                           public void onSuccess(String data) {
		                               super.onSuccess(data); 
		                               try {
		                               	//解析list
		                            	   
		                            	   JSONObject json =new JSONObject(data.toString());
		                            	   Log.d("SameStationActivity","json="+json);
		                            	   if(json!=null){
		                            		   JSONArray lineArr = json.getJSONArray("lineList");
		                                	   JSONArray staArr = json.getJSONArray("stationList");
		                                	   //起始发车时间
		                                	   JSONArray busTimeArr =json.getJSONArray("busTimeList");
		                                	   List<StationBean> lineList0=new ArrayList<StationBean>();
		                                	   List<StationBean> lineList1=new ArrayList<StationBean>();
		                                	   Collection<Float> upDisList =new ArrayList<Float>();//上行站点间距
		                                	   Collection<Float> downDisList =new ArrayList<Float>();//下行站点间距
		                                	   //删除原有发车时间
		                                	   BusTime bt =new BusTime();
		                                	   bt.setLineCode(lineCode);
		                                	   SameSationLineActivity.this.busManager.deleteBusTime(bt);
		                                	   for(int k=0;k<busTimeArr.length();k++){
		                                		 JSONObject timeJson =busTimeArr.getJSONObject(k);
		                                		 //首末班发车信息存入本地数据库
		                                		 bt.setSxx(timeJson.getString("sxx"));
		                                		 bt.setBeginTime(timeJson.getString("beginTime"));
		                                		 bt.setEndTime(timeJson.getString("endTime"));
		                              		   SameSationLineActivity.this.busManager.saveBusTime(bt);
	                       						Log.e("list", "---------71-----"+timeJson.getString("beginTime"));

		                              		   //Log.d("-----线路首末班发车时间存入本地--------", "存储");
		                                	 }
		                                	   //设定线路:确定上下行起止站点
		                                	   for(int j=0;j<staArr.length();j++){
		                                		   JSONObject staJson =staArr.getJSONObject(j);
		                                		   StationBean sta =new StationBean();
		                                		   sta.setDis(staJson.getString("staDis"));//站点间距
		                                		   sta.setStationName(staJson.get("stationName").toString());
		                                		   cityCode = cityManager.getSelectedCityCode() == 0 ? cityManager
		                               					.getCurrentCityCode() : cityManager.getSelectCityCode();
		                                		   sta.setArea(cityCode);
		                                		   sta.setLng(Double.parseDouble(staJson.get("lon").toString()));
		                                		   sta.setLat(Double.parseDouble(staJson.get("lat").toString()));
		                                		   sta.setState(lineCode);//线路
		                                		   sta.setId(staJson.get("sxx").toString());//上下行
		                                		   if(staJson.get("sxx").toString().equals("0")){
		                                			   lineList0.add(sta);
		                                			 downDisList.add(Float.parseFloat(sta.getDis()));
		                                		   }else{
		                                			 upDisList.add(Float.parseFloat(sta.getDis()));
		                                			   lineList1.add(sta);
		                                		   }
		                                		   
		                                		   //站点信息存入本地数据库
		                                		 //  BusLineSearchActivity.this.mBusMan.saveBusStation(sta);
		                                	   }
		                                	   for(int i=0;i<lineArr.length();i++){
		                                		   JSONObject lineJson =lineArr.getJSONObject(i);
		                                		   LineBean line0 =new LineBean();
		                                		   line0.setLineId(lineJson.getInt("sxx"));
		                                		   line0.setLineCode(lineCode);
		                                		   line0.setLineName(lineName);
		                                		   //line0.setLineName(lineJson.getString("lineName"));
		                                		   line0.setGid(Long.toString(new Date().getTime()));
		                                		   line0.setBeginStation(lineJson.get("beginStation").toString());
		                                		   line0.setEndStation(lineJson.get("endStation").toString());
		                                	//	   line0.setBeginTime(lineJson.get("beginTime").toString());
		                                	//	   line0.setEndTime(lineJson.get("endTime").toString());
		                                		   line0.setCityCode(cityCode);
		                                		   if(lineJson.get("sxx").toString().equals("0")){
		                                			   line0.setStationSerial(CharUtil.objectToByte(lineList0.toArray()));
		                                			   Float[] a=new Float[downDisList.size()];
		                                			   line0.setStationDistances(downDisList.toArray(a));
		                                		   }else{
		                                			   line0.setStationSerial(CharUtil.objectToByte(lineList1.toArray()));
		                                			 Float[] b=new Float[upDisList.size()];
		                              			   line0.setStationDistances(upDisList.toArray(b));

		                                		   }
		                                		  // line0.setStationDistances(distance);
		                                		   SameSationLineActivity.this.lineList.add(line0);
		                                		   if(SameSationLineActivity.this.busManager.getLocalLine(line0).size()==0){
		                                			 //线路信息存入本地数据库
		                                    		   SameSationLineActivity.this.busManager.saveBusLine(line0);
		                                    		   Log.d("-----线路站点存入本地--------", "存储");
		                                    		 System.out.println("返回的线路站点" + data);
		                                		   }
		                                		  
		                                	   }
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
		   	  
		     }else{
		  	   mBusLines.addAll(lineList);
		     }
		}
	 private String getLineCodeByLineName(String lineName)
		{
			String line=null;
			LineBean lv =new LineBean();
			for(int i=0;i<cityManager.getLine().size();i++){
				lv =cityManager.getLine().get(i);
				if(lv.getLineName().equals(lineName)){
					line =  lv.getLineCode();
				}
			}
			return line;
		}
	 
}
