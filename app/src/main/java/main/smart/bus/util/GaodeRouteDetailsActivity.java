package main.smart.bus.util;

import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import main.smart.bus.activity.BaiduMapLinesActivity;
import main.smart.rcgj.R;

public class GaodeRouteDetailsActivity extends Activity implements OnMapLoadedListener, 
OnMapClickListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener{
	private int index=0;
//	private AMap aMap;
//	private MapView mapView;
	private BusPath mBuspath;
	private BusRouteResult mBusRouteResult;
	private TextView mTitle, mTitleBusRoute, mDesBusRoute;
	private ListView mBusSegmentList;
	private BusSegmentListAdapter mBusSegmentListAdapter;
	private LinearLayout mBusMap, mBuspathview;
	private GaodeBusRouteOverlay mBusrouteOverlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baidu_route_details);
//		mapView = (MapView) findViewById(R.id.route_map);
//		mapView.onCreate(savedInstanceState);// �˷���������д
		getIntentData();
		init();
	}
	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();	
//		}
//		registerListener();		
		mTitleBusRoute = (TextView) findViewById(R.id.firstline);
		mDesBusRoute = (TextView) findViewById(R.id.secondline);
		String dur = AMapUtil.getFriendlyTime((int) mBuspath.getDuration());
		String dis = AMapUtil.getFriendlyLength((int) mBuspath.getDistance());
		mTitleBusRoute.setText(dur + "(" + dis + ")");
		int taxiCost = (int) mBusRouteResult.getTaxiCost();
		mDesBusRoute.setText("��Լ"+taxiCost+"Ԫ");
		mDesBusRoute.setVisibility(View.VISIBLE);
		mBuspathview = (LinearLayout)findViewById(R.id.bus_path);
		configureListView();
	}
//	private void registerListener() {
//		aMap.setOnMapLoadedListener(this);
//		aMap.setOnMapClickListener(this);
//		aMap.setOnMarkerClickListener(this);
//		aMap.setOnInfoWindowClickListener(this);
//		aMap.setInfoWindowAdapter(this);
//	}
	private void configureListView() {
		mBusSegmentList = (ListView) findViewById(R.id.bus_segment_list);
		mBusSegmentListAdapter = new BusSegmentListAdapter(
				this.getApplicationContext(), mBuspath.getSteps());
		mBusSegmentList.setAdapter(mBusSegmentListAdapter);
		
	}
	private void getIntentData() {
		Intent intent = getIntent();
		if (intent != null) {
			mBuspath = intent.getParcelableExtra("bus_path");
			mBusRouteResult = intent.getParcelableExtra("bus_result");
		}
	}
	public void sendbaidumap(View paramView){
		Intent inte=new Intent();
		Bundle bundle=new Bundle(); 
		bundle.putInt("index",index);
		inte.putExtras(bundle); 
		inte.setClass(GaodeRouteDetailsActivity.this, BaiduMapLinesActivity.class);
		startActivity(inte);// ��ת��ҳ	
		
	}
	@Override
	public void onMapLoaded() {
		if (mBusrouteOverlay != null) {
			mBusrouteOverlay.addToMap();
			mBusrouteOverlay.zoomToSpan();
		}
	}
	public void back(View paramView){
		onBackPressed();
	}
	public void onMapClick(View view) {
		Intent inte=new Intent();
//		Bundle bundle=new Bundle(); 
//		inte.putExtras(bundle); 
		inte.putExtra("mBuspath", mBuspath);
		inte.putExtra("mBusRouteResult", mBusRouteResult);
		inte.setClass(GaodeRouteDetailsActivity.this, GaodeMapLinesActivity.class);
		startActivity(inte);// ��ת��ҳ
//		mBuspathview.setVisibility(View.GONE);
//		mBusMap.setVisibility(View.GONE);
//		mapView.setVisibility(View.VISIBLE);
//		aMap.clear();// �����ͼ�ϵ����и�����
//		mBusrouteOverlay = new GaodeBusRouteOverlay(this, aMap,
//				mBuspath, mBusRouteResult.getStartPos(),
//				mBusRouteResult.getTargetPos());
//		mBusrouteOverlay.removeFromMap();

	}
	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
