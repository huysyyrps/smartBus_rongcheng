package main.smart.bus.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;

import main.smart.rcgj.R;
import main.smart.bus.activity.adapter.FavorBusLineAdapter;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.FavorLineBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusManager;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.CharUtil;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class BusFavoritesFragment extends Fragment implements View.OnClickListener,  AdapterView.OnItemClickListener {

	private static String SEARCH_LINE = "";
	private BusManager mBusMan;
	private CityManager mCityMan;
	private ListView mBusLineFavorView;
	private FavorBusLineAdapter mAdapter;
	private List<FavorLineBean> mFavorRecords = new ArrayList();
	private ProgressBar mProgress;
	private ProgressDialog mProgressDialog;
	List<LineBean> lineList = null;
	private FavorLineBean favorBusLine;
	private Button editButton;
	private Button delButton;
	private LinearLayout layout;
	private Button allselBtn;
	private Button allnoBtn;

	//private List<LineBean> mBusLines = new ArrayList();
	int cityCode = 0;

	Handler mHandler = new Handler() {
		public void handleMessage(Message paramMessage) {
			super.handleMessage(paramMessage);
			String line="";
			switch (paramMessage.what) {

				case 1:

					for (int i=0;i<lineList.size();i++)
					{
						LineBean localBusLine = lineList.get(i);
						if (localBusLine.getLineCode().equals(favorBusLine.getLineCode()) &&
								localBusLine.getLineId() == favorBusLine.getLineSxx())
						{

							mBusMan.setSelectedLine(localBusLine);
							break;
						}

					}
					//   this.mBusMan.clearSoiList();
					startActivityForResult(new Intent(getActivity(), BusLineDetailActivity.class), 1);
					break;
				case 2:
					mAdapter.setEditCheck(false);
					editButton.setText("编辑");
					mAdapter.notifyDataSetChanged();
					layout.setVisibility(View.GONE);
					break;
				case 3:
					mAdapter.setEditCheck(true);
					editButton.setText("取消");
					mAdapter.notifyDataSetChanged();
					layout.setVisibility(View.VISIBLE);
					break;
				default:
					return;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View localView = inflater.inflate(R.layout.bus_favorites_fragment, container, false);

		this.mBusMan = BusManager.getInstance();
		this.mProgress = ((ProgressBar) localView.findViewById(R.id.favoritesProgress));
		this.mCityMan = CityManager.getInstance();
		try {
			this.mFavorRecords.addAll(this.mBusMan.getFavorLineInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
		this.layout = (LinearLayout) localView.findViewById(R.id.bus_favorite_set_layout);
		this.mBusLineFavorView = ((ListView)localView.findViewById(R.id.bus_favorite_list));
		this.editButton = (Button) localView.findViewById(R.id.favorite_edit_btn);
		this.editButton.setOnClickListener(new EditListener());
		this.delButton = (Button)localView.findViewById(R.id.bus_favorite_delete_btn);
		this.delButton.setOnClickListener(new DelListener());
		this.allselBtn = (Button)localView.findViewById(R.id.bus_favorite_allsel_btn);
		this.allselBtn.setOnClickListener(new AllSelListener());
		this.allnoBtn = (Button)localView.findViewById(R.id.bus_favorite_allno_btn);
		this.allnoBtn.setOnClickListener(new AllNoListener());
		//this.mBusLineFavorView.addFooterView(this.mFavorClearView);
		this.mAdapter = new FavorBusLineAdapter(getActivity(), this.mFavorRecords);
		this.mBusLineFavorView.setAdapter(this.mAdapter);
		this.mBusLineFavorView.setOnItemClickListener(this);
//
		return localView;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}

	class AllNoListener implements OnClickListener {
		public void onClick(View v) {
			mAdapter.resetCheckList();
			mAdapter.notifyDataSetChanged();
		}
	}
	class AllSelListener implements OnClickListener {
		public void onClick(View v) {

			mAdapter.allYesCheckList();
			mAdapter.notifyDataSetChanged();
		}
	}
	class EditListener implements OnClickListener {
		public void onClick(View v) {
			if (mAdapter.isEditCheck())
			{
				mHandler.sendEmptyMessage(2);

			}
			else
			{
				mHandler.sendEmptyMessage(3);

			}

		}
	}

	class DelListener implements OnClickListener {
		public void onClick(View v) {
			List<Boolean> checkList = mAdapter.getCheckList();
			for(int i=0;i<checkList.size();i++)
			{
				if(checkList.get(i))
				{
					try {
						mBusMan.deleteFavorLineInfo(mFavorRecords.get(i).getFavorName());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mFavorRecords.remove(i);
					checkList.remove(i);
					i--;
				}
			}

			mAdapter.setBusLines(mFavorRecords);
			mAdapter.resetCheckList();
			mHandler.sendEmptyMessage(2);

		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub

		favorBusLine = (FavorLineBean)this.mFavorRecords.get(position);
		this.cityCode = favorBusLine.getCityCode();
		mProgress.setVisibility(View.VISIBLE);// 显示进度条
		queryBusLine(favorBusLine.getLineCode());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mFavorRecords = this.mBusMan.getFavorLineInfo();
		this.mAdapter = new FavorBusLineAdapter(getActivity(), this.mFavorRecords);
		this.mBusLineFavorView.setAdapter(this.mAdapter);


	}

	private void queryBusLine(String lineCode)
	{

		//SEARCH_LINE =lineCode;
		//  mBusLines.clear();
		/**
		 * 实际应用时，采用webservice服务读取静态数据（线路，站点）
		 * 同时考虑异步处理不阻塞主线程
		 * 查询本地数据库
		 * */
		lineList=mBusMan.getLineByLineCode(lineCode);
		//1.城市编码currentRegion。getCityCode
		//测试数据静态填写 11路为例
		if(lineList.size()==0){
			//3.查询sqlserver数据库结果集存入本地
			String url= ConstData.URL+"!getLineStation.shtml";

			RequestParams param = new RequestParams();
			param.put("lineCode",lineCode );

			RequstClient.post(url, param,
					new LoadCacheResponseLoginouthandler(null,
							new LoadDatahandler()
							{
								@Override
								public void onStart() {
									super.onStart();

									Log.d("getLineInfo:","get line and station data");
								}

								@Override
								public void onSuccess(String data) {
									super.onSuccess(data);
									try
									{
										//解析list
										JSONObject  json =new JSONObject(data.toString());
										if(json!=null)
										{
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
											bt.setLineCode(SEARCH_LINE);
											BusFavoritesFragment.this.mBusMan.deleteBusTime(bt);
											for(int k=0;k<busTimeArr.length();k++)
											{
												JSONObject timeJson =busTimeArr.getJSONObject(k);
												//首末班发车信息存入本地数据库
												bt.setSxx(timeJson.getString("sxx"));
												bt.setBeginTime(timeJson.getString("beginTime"));
												bt.setEndTime(timeJson.getString("endTime"));
												BusFavoritesFragment.this.mBusMan.saveBusTime(bt);
											}
											//设定线路:确定上下行起止站点
											for(int j=0;j<staArr.length();j++)
											{
												JSONObject staJson =staArr.getJSONObject(j);
												StationBean sta =new StationBean();
												sta.setDis(staJson.getString("staDis"));//站点间距
												sta.setStationName(staJson.get("stationName").toString());
												sta.setArea(cityCode);
												sta.setLng(Double.parseDouble(staJson.get("lon").toString()));
												sta.setLat(Double.parseDouble(staJson.get("lat").toString()));
												sta.setState(SEARCH_LINE);//线路
												sta.setId(staJson.get("sxx").toString());//上下行
												if(staJson.get("sxx").toString().equals("0"))
												{
													lineList0.add(sta);
													downDisList.add(Float.parseFloat(sta.getDis()));
												}
												else
												{
													upDisList.add(Float.parseFloat(sta.getDis()));
													lineList1.add(sta);
												}

												//站点信息存入本地数据库
												//  BusLineSearchActivity.this.mBusMan.saveBusStation(sta);
											}
											for(int i=0;i<lineArr.length();i++)
											{
												JSONObject lineJson =lineArr.getJSONObject(i);
												LineBean line0 =new LineBean();
												line0.setLineId(lineJson.getInt("sxx"));
												line0.setLineCode(SEARCH_LINE);
												line0.setLineName(lineJson.getString("lineName"));
												line0.setGid(Long.toString(new Date().getTime()));
												line0.setBeginStation(lineJson.get("beginStation").toString());
												line0.setEndStation(lineJson.get("endStation").toString());
												//	   line0.setBeginTime(lineJson.get("beginTime").toString());
												//	   line0.setEndTime(lineJson.get("endTime").toString());
												line0.setCityCode(cityCode);
												if(lineJson.get("sxx").toString().equals("0"))
												{
													line0.setStationSerial(CharUtil.objectToByte(lineList0.toArray()));
													Float[] a=new Float[downDisList.size()];
													line0.setStationDistances(downDisList.toArray(a));
												}
												else
												{
													line0.setStationSerial(CharUtil.objectToByte(lineList1.toArray()));
													Float[] b=new Float[upDisList.size()];
													line0.setStationDistances(upDisList.toArray(b));

												}
												// line0.setStationDistances(distance);
												BusFavoritesFragment.this.lineList.add(line0);
												if(BusFavoritesFragment.this.mBusMan.getLocalLine(line0).size()==0)
												{
													//线路信息存入本地数据库
													BusFavoritesFragment.this.mBusMan.saveBusLine(line0);
													Log.d("-----线路站点存入本地--------", "存储");
													System.out.println("返回的线路站点" + data);
												}

											}

											mProgress.setVisibility(View.GONE);//进度条8
											mHandler.sendEmptyMessage(1);
											//mBusLines.addAll(lineList);

										}

									}
									catch (JSONException e) {
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

		}
		else
		{
			mProgress.setVisibility(View.GONE);//进度条8
			mHandler.sendEmptyMessageDelayed(1, 300L);
			//mBusLines.addAll(lineList);
		}

	}
}
