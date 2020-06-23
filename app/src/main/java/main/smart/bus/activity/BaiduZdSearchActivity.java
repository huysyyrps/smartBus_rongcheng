package main.smart.bus.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.smart.bus.activity.adapter.ListViewAdapterW;
import main.smart.common.http.DataBase;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class BaiduZdSearchActivity extends Activity implements OnItemClickListener, OnGetPoiSearchResultListener {
    private ListViewAdapterW mAdapter;

    private ListView mBusStationListView;
    private List<String> mStationList= new ArrayList<String>();

    private EditText mSearchEdit;
    private int mFlag = -1;
    private String city ;
    private PoiSearch mSearch = null;
    private DataBase database;
//	private MKSearch mSearch = null;	//搜索模块
//	private BMapManager mBMapMan=null;	//地图引擎

//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message paramMessage) {
//			super.handleMessage(paramMessage);
//			String str;
//			switch (paramMessage.what) {
//
//			case 1:
//				break;
//			default:
//				return;
//			}
//
//		}
//	};

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//		mBMapMan=new BMapManager(SmartBusApp.getInstance());
//		mBMapMan.init(null);
        setContentView(R.layout.search_input_layout);
        Intent intent = getIntent();
        Bundle bunde = intent.getExtras();
        mFlag=bunde.getInt("flag");
        city = ConstData.SELECT_CITY;
        database = new DataBase(this, "AppData");
//		this.mBusMan = BusManager.getInstance();
//		this.mProgress = ((ProgressBar) findViewById(R.id.progress));
        // findViewById(R.id.search_map_btn).setVisibility(8);
        this.mSearchEdit = ((EditText) findViewById(R.id.search_line));

        this.mAdapter = new ListViewAdapterW(this, (ArrayList<String>) mStationList);
        this.mBusStationListView = ((ListView) findViewById(R.id.result_list));
        this.mBusStationListView.setAdapter(this.mAdapter);
        this.mBusStationListView.setOnItemClickListener(this);

        mSearch = PoiSearch.newInstance();
        mSearch.setOnGetPoiSearchResultListener(this);
//		mSearch = new MKSearch();
//		//mSearch = new MKPoiResult();
//		mSearch.init(mBMapMan,new MKSearchListener(){
//			@Override
//			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
//				// TODO Auto-generated method stub
//				int error = arg1;
//				if (error != 0) {
//					String str = String.format("错误号：%d", error);
//					Toast.makeText(BaiduZdSearchActivity.this, str, Toast.LENGTH_LONG).show();
//					return;
//				}
//
//			}
//			@Override
//			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
//				// TODO Auto-generated method stub
//
//			}
//			@Override
//			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
//				// TODO Auto-generated method stub
//				MKPoiResult res = arg0;
//				if ( res == null || res.getAllPoi() == null){
//            		return ;
//            	}
//				mStationList.removeAll(mStationList);
//            	for ( MKPoiInfo info : res.getAllPoi()){
//            		if ( info.name != null)
//            			mStationList.add(info.name);
//            	}
//            	mAdapter.notifyDataSetChanged();
//
//			}
//			@Override
//			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
//					int arg2) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
//				// TODO Auto-generated method stub
//
//			}
//			@Override
//			public void onGetTransitRouteResult(MKTransitRouteResult res,
//					int error) {
//				// TODO Auto-generated method stub
//				//起点或终点有歧义，需要选择具体的城市列表或地址列表
//
//			}
//			@Override
//			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
//					int error) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
//					int error) {
//				// TODO Auto-generated method stub
//			}
//
//		});
        mSearchEdit.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                mSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(s.toString()));
//				mSearch.poiSearchInCity(city,s.toString());


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (mFlag == 0) {
            BaiduRouteFragment.textqd1.setText(mStationList.get(position));
            BaiduRouteFragment.textqd.setText(mStationList.get(position).substring(0, mStationList.get(position).indexOf("=")));

            ConstData.startStudia=mStationList.get(position).substring(0, mStationList.get(position).indexOf("="));
            ConstData.startlat=Double.valueOf(mStationList.get(position).substring(mStationList.get(position).indexOf("=")+1, mStationList.get(position).indexOf("/")));
            ConstData.startlon=Double.valueOf(mStationList.get(position).substring(mStationList.get(position).indexOf("/")+1, mStationList.get(position).length()));
            Log.e("lolo",ConstData.startlat+"qqqqqqqqqqqqqqq"+ConstData.startlon);



        }
        else if (mFlag == 1) {
            BaiduRouteFragment.textzd1.setText(mStationList.get(position));
            BaiduRouteFragment.textzd.setText(mStationList.get(position).substring(0, mStationList.get(position).indexOf("=")));


            ConstData.endStudia=mStationList.get(position).substring(0, mStationList.get(position).indexOf("="));
            ConstData.endlat=Double.valueOf(mStationList.get(position).substring(mStationList.get(position).indexOf("=")+1, mStationList.get(position).indexOf("/")));
            ConstData.endlon=Double.valueOf(mStationList.get(position).substring(mStationList.get(position).indexOf("/")+1, mStationList.get(position).length()));
        }
        finish();

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onGetPoiResult(PoiResult arg0) {
        // TODO Auto-generated method stub

        Map<String, com.baidu.mapapi.model.LatLng> map=ConstData.latlngMap;
        if ( arg0 == null  || arg0.error != SearchResult.ERRORNO.NO_ERROR){
            return ;
        }
        mStationList.removeAll(mStationList);
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();

        for ( PoiInfo info : arg0.getAllPoi()){

            if ( info.name != null){
//                ContentValues value = new ContentValues();
//                value.put("address", info.address);
//                value.put("latitude", info.location.latitude);
//                value.put("longitude", info.location.longitude);
//                db.insert("JWD", null, value);
                mStationList.add(info.name+"="+String.valueOf(info.location.latitude)+"/"+String.valueOf(info.location.longitude));
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        ConstData.latlngMap=map;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        mSearch.destroy();
        super.onDestroy();
    }

}
