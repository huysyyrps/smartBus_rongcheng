//package main.smart.bus.util;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.AMap.OnMarkerClickListener;
//import com.amap.api.maps.CameraUpdateFactory;
//import com.amap.api.maps.CoordinateConverter;
//import com.amap.api.maps.CoordinateConverter.CoordType;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.model.BitmapDescriptor;
//import com.amap.api.maps.model.BitmapDescriptorFactory;
//import com.amap.api.maps.model.CameraPosition;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.maps.model.MarkerOptions;
//import com.amap.api.maps.model.PolylineOptions;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import main.smart.rcgj.R;
//import main.smart.bus.bean.BusBean;
//import main.smart.bus.bean.LineBean;
//import main.smart.bus.bean.StationBean;
//import main.smart.common.http.DBHandler;
//import main.smart.common.util.ConstData;
//
//public class GaodeBusLineStationMap extends FragmentActivity
//		implements BusMonitor.BusMonitorInfoListener, BusLineRefresh {
//	private BusManager mBusMan = BusManager.getInstance();
//	private List<StationBean> mStations;
//	private int sxx = 0;
//	private MapView mMapView;
//	private AMap aMap;
//	private List<BusBean> mBusData;// �����ĳ����б�
//	private Toast mToast = null;
//	private List<Marker> mMarkers = new ArrayList<Marker>();
//	private List<Marker> mBuses = new ArrayList<Marker>();
//	private BitmapDescriptor busIcon;
//	private Thread mThread;
//	private List<StationBean> mLinePotList = null;
//
//	public GaodeBusLineStationMap() {
//		this.mStations = this.mBusMan.getSelectedLine().getStations();
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_bus_line_station_map);
//		busIcon = BitmapDescriptorFactory.fromResource(R.drawable.bustransfer_busway);
//		mMapView = (MapView) findViewById(R.id.bmapsView);
//		mMapView.onCreate(savedInstanceState);
//		if (aMap == null) {
//			aMap = mMapView.getMap();
//		}
//		aMap.setTrafficEnabled(true);
//		sxx = mBusMan.getSelectedLine().getLineId();
//		this.mBusMan.addBusMonitorInfoListener(this);
//		StationBean sta0 = this.mStations.get(0);
//		if (sxx == 1) {
//			sta0 = this.mStations.get(this.mStations.size() - 1);
//		}
//
//		double lat = sta0.getLat();
//		double lng = sta0.getLng();
//		LatLng point = new LatLng(lat, lng);
//		LatLng gaodePt = new CoordinateConverter(this).from(CoordType.GPS).coord(point).convert();
//		aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.valueOf(15)));
//		aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(gaodePt, 15, 30, 30)));
//		aMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//
//			@Override
//			public boolean onMarkerClick(Marker arg0) {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < mMarkers.size(); i++) {
//					if (arg0 == mMarkers.get(i)) {
//						showToast(mStations.get(i).getStationName());
//
//					}
//				}
//				return true;
//			}
//		});
//		addStationPoint();
//	}
//
//	Runnable getLinePot = new Runnable() {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			DBHandler dbhandler = new DBHandler();
//			String url = ConstData.goURL + "/GetXL/LineJDWD";
//			String line = mBusMan.getSelectedLine().getLineCode();
//			String sxx = String.valueOf(mBusMan.getSelectedLine().getLineId());
//			mLinePotList = dbhandler.getBaiduLineSpot(url, line, sxx);
//		}
//	};
//
//	public void addLocationPoint() {
//		getResources().getDrawable(R.drawable.point);
//	}
//
//	// * ��ʾToast��Ϣ
//	public void showToast(String msg) {
//		if (mToast == null) {
//			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//		} else {
//			mToast.setText(msg);
//			mToast.setDuration(Toast.LENGTH_SHORT);
//		}
//		mToast.show();
//	}
//
//	public void addStationPoint() {
//
//		BitmapDescriptor s1 = BitmapDescriptorFactory.fromResource(R.drawable.pins);
//		BitmapDescriptor sqd = BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
//		BitmapDescriptor szd = BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
//		LatLng staPoint = null;
//		StationBean sta = null;
//
//		mMarkers.clear();
//
//		List<LatLng> points = new ArrayList<LatLng>();
//
//		for (int i = 0; i < mStations.size(); i++) {
//
//			sta = mStations.get(i);
//			staPoint = new LatLng(sta.getLat(), sta.getLng());
//
//			// ��GPS�豸�ɼ���ԭʼGPS����ת���ɰٶ�����
//
//			LatLng baiduPt = new CoordinateConverter(getApplicationContext()).from(CoordType.GPS).coord(staPoint)
//					.convert();
//
//			points.add(baiduPt);
//			MarkerOptions oo = null;
//			if (i == 0) {
//				if (sxx == 0) {
//					oo = new MarkerOptions().icon(sqd).position(baiduPt);
//
//					// point.setMarker(sqd);
//				} else if (sxx == 1) {
//					// point.setMarker(szd);
//					oo = new MarkerOptions().icon(szd).position(baiduPt);
//
//				}
//			} else if (i == mStations.size() - 1) {
//				if (sxx == 0) {
//					oo = new MarkerOptions().icon(szd).position(baiduPt);
//
//				} else if (sxx == 1) {
//					oo = new MarkerOptions().icon(sqd).position(baiduPt);
//
//				}
//
//			} else {
//				oo = new MarkerOptions().icon(s1).position(baiduPt);
//
//			}
//
//			mMarkers.add((Marker) aMap.addMarker(oo));
//
//		}
//
//		PolylineOptions ooPolyline = new PolylineOptions().width(7).color(0xAA0000FF).addAll(points);
//		aMap.addPolyline(ooPolyline);
//
//	}
//
//	@Override
//	public void refreshData() {
//		// TODO �Զ����ɵķ������
//
//	}
//
//	@Override
//	public void onBusMonitorInfoUpdated(List<BusBean> paramList) {
//		// TODO �Զ����ɵķ������
//		System.out.println("���ݸ���----->" + mMapView);
//		this.mBusData = paramList;
//		Log.d("BusLineStationMap", "�������ݸ��¿�ʼ");
//		if (mMapView == null)
//			return;
//		refreshData();
//		// �������������
//		clearBusOverLay();
//		LineBean line = this.mBusMan.getSelectedLine();
//		int sxx = line.getLineId();
//		for (int i = 0; i < this.mBusData.size(); i++) {
//			BusBean bus = this.mBusData.get(i);
//			addBusPoint(bus.getBusCode(), Double.parseDouble(bus.getLat()), Double.parseDouble(bus.getLng()));
//		}
//		Log.d("BusLineStationMap", "�������ݸ��½���");
//	}
//	public void clearBusOverLay()
//	{
//		for (int i=0;i<mBuses.size();i++)
//		{
//			Marker mark = mBuses.get(i);
//			mark.remove();
//		}
//		mBuses.clear();
//	}
//	/**
//	 * ���ӳ���ͼ�긲����
//	 * */
//	public void addBusPoint(String busCode,double lat,double lon){
//
//
//		LatLng point = new LatLng(lat,lon);
//		LatLng baiduPt = new CoordinateConverter(getApplicationContext()).from(CoordType.GPS).coord(point).convert();
//		MarkerOptions mo = new MarkerOptions().icon(busIcon).position(baiduPt);
//		mBuses.add((Marker)aMap.addMarker(mo));
//	 }
//	public void onDestroy() {
//
//		this.mBusMan.removeBusMonitorInfoListener(this);
//		mMapView.onDestroy();
//		super.onDestroy();
//	}
//	public void back(View paramView){
//		//mBusMan.stopMonitor();
//		onBackPressed();
//	}
//}
