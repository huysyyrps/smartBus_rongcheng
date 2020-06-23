package main.smart.bus.util;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import main.smart.rcgj.R;

public class GaodeMapLinesActivity extends Activity implements  OnMapLoadedListener, 
OnMapClickListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener{
	private AMap aMap;
	private MapView mapView;
	private GaodeBusRouteOverlay mBusrouteOverlay;
	private BusPath mBuspath;
	private BusRouteResult mBusRouteResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gaode_route_lines);
		Intent intent=getIntent();
		mBuspath = intent.getParcelableExtra("mBuspath");
		mBusRouteResult = intent.getParcelableExtra("mBusRouteResult");
		mapView = (MapView) this.findViewById(R.id.route_map);
		mapView.onCreate(savedInstanceState);// �˷���������д
		init();
	}
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();	
		}
		registerListener();		
		aMap.clear();// �����ͼ�ϵ����и�����
		mBusrouteOverlay = new GaodeBusRouteOverlay(this, aMap,
				mBuspath, mBusRouteResult.getStartPos(),
				mBusRouteResult.getTargetPos());
		mBusrouteOverlay.removeFromMap();
	}
	private void registerListener() {
		aMap.setOnMapLoadedListener(this);
		aMap.setOnMapClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setOnInfoWindowClickListener(this);
		aMap.setInfoWindowAdapter(this);
	}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO �Զ����ɵķ������
		return false;
	}
	public void back(View paramView){
		onBackPressed();
	}
	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public View getInfoContents(Marker arg0) {
		// TODO �Զ����ɵķ������
		return null;
	}
	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO �Զ����ɵķ������
		return null;
	}
	@Override
	public void onMapClick(LatLng arg0) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void onMapLoaded() {
		// TODO �Զ����ɵķ������
		if (mBusrouteOverlay != null) {
			mBusrouteOverlay.addToMap();
			mBusrouteOverlay.zoomToSpan();
		}
	}

	
}
