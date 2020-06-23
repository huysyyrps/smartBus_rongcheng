package main.smart.bus.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import main.smart.bus.activity.BaiduRouteFragment;
import main.smart.common.http.DataBase_JWD;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;
import main.smart.rcgj.R;

public class GaodeZDMapActivity extends Activity implements OnGeocodeSearchListener {
	private String zdType = "";
	private String zd = "";
	// private LocationClient mLocClient;
	private MapView mMapView = null; // ��ʾ��ͼ��View
	private AMap aMap = null;
	private LatLng currentPt = null;// ��ǰ�ص����
	private RegeocodeQuery query = null;
	private Button btn,gaodebaiduzdhelp;
	private boolean isFirstLoc = true;
	private GeocodeSearch geocoderSearch;
	private MarkerOptions markerOption;
	private Marker marker;;
	private DataBase_JWD database;
	//����AMapLocationClient�����
	public AMapLocationClient mLocationClient = null;
		//����AMapLocationClientOption����
	public AMapLocationClientOption mLocationOption = null;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gaode_zdmap);
		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		zdType = bunde.getString("zdType");
		mMapView = (MapView) this.findViewById(R.id.gaode_route_zd);
		
		//�ߵ� 
	 	//��ʼ����λ
	 	mLocationClient = new AMapLocationClient(this);
	 	//���ö�λ�ص�����
	 	mLocationClient.setLocationListener(new MyAMapLocationListenner());
	 	//��ʼ��AMapLocationClientOption����
	 	mLocationOption = new AMapLocationClientOption();
	 	//���ö�λģʽΪAMapLocationMode.Hight_Accuracy���߾���ģʽ��
	 	mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
	 	//��ȡһ�ζ�λ�����
	 	//�÷���Ĭ��Ϊfalse��
	 	mLocationOption.setOnceLocation(true);
	 	mLocationOption.setInterval(500);
	 	//����λ�ͻ��˶������ö�λ����
	 	mLocationClient.setLocationOption(mLocationOption);
	 	//������λ
	 	mLocationClient.startLocation();
		mMapView.onCreate(savedInstanceState);
		
		if (aMap == null) {
			aMap = mMapView.getMap();
		}
		// aMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
		// ������λͼ��
		aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.valueOf(17)));
//		aMap.setMyLocationEnabled(true);
		btn=(Button) findViewById(R.id.gaodezdhelp);
		btn.setOnClickListener(new HelpListener());
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		database = new DataBase_JWD(this, "JWD1");
		aMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng arg0) {
				// TODO �Զ����ɵķ������
				currentPt = arg0;
				LatLonPoint latLonPoint = new LatLonPoint(arg0.latitude, arg0.longitude);
				query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
				geocoderSearch.getFromLocationAsyn(query);// �����첽������������
			}
		});
		PreferencesHelper mPreference = PreferencesHelper.getInstance();
		int ver = mPreference.getTitleVersion();
		if (ver == 0) {
			Helper();
			mPreference.updateTitleVersion();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()�����ٵ�ͼ
		aMap.setMyLocationEnabled(false);
		mLocationClient.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView.onResume ()�����»��Ƽ��ص�ͼ
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView.onPause ()����ͣ��ͼ�Ļ���
		mMapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// ��activityִ��onSaveInstanceStateʱִ��mMapView.onSaveInstanceState
		// (outState)�������ͼ��ǰ��״̬
		mMapView.onSaveInstanceState(outState);
	}

	// ���ذ�ťʱ��
	public void back(View paramView) {
		onBackPressed();
	}

	public void Helper() {
		new AlertDialog.Builder(this).setTitle("����").setIcon(android.R.drawable.ic_dialog_info).setCancelable(false)
				.setMessage("������ͼ����ѡ�еص�").setPositiveButton("ȷ��", null).show();
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		// TODO �Զ����ɵķ������
		if (rCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				aMap.clear();
				markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pins))
						.position(currentPt)
						.draggable(true);
				marker = aMap.addMarker(markerOption);
				zd=result.getRegeocodeAddress().getFormatAddress().toString();
				db = database.getWritableDatabase();
				db.beginTransaction();
				new AlertDialog.Builder(this).setTitle("λ��")
		        .setCancelable(false)
		        .setMessage(zd)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//System.exit(0);
						finish();
						if (zdType.equals("qd"))
						{
							ConstData.startne=currentPt;

							BaiduRouteFragment.textqd.setText(zd);							 
			    			ContentValues value = new ContentValues();		
			    			value.put("address", zd);
			    			value.put("latitude", currentPt.latitude);
			    			value.put("longitude", currentPt.longitude);
			    			db.insert("JWD", null, value);
							
						}
						else if (zdType.equals("zd"))
						{
							ConstData.endne=currentPt;
							BaiduRouteFragment.textzd.setText(zd);
							ContentValues value = new ContentValues();		
			    			value.put("address", zd);
			    			value.put("latitude", currentPt.latitude);
			    			value.put("longitude", currentPt.longitude);
			    			db.insert("JWD", null, value);
						}
						db.setTransactionSuccessful();
						db.endTransaction();
						db.close();
//						database.close();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
			} else {
				Toast.makeText(GaodeZDMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
				.show();
			}
		} else {
			Toast.makeText(GaodeZDMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
			.show();
		}
	}
	class HelpListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Helper();
		}
			
	}
	public class MyAMapLocationListenner implements AMapLocationListener{
		public void onLocationChanged(AMapLocation result) {
			
			// map view ���ٺ��ڴ����½��յ�λ��
			if (result.getErrorCode() !=0 ){
				Toast.makeText(getApplicationContext(), "�ҵ�λ�ö�λʧ��", Toast.LENGTH_LONG).show();
			}else{								    	 			
				
				aMap.moveCamera(
						CameraUpdateFactory.newCameraPosition(new CameraPosition(
								new LatLng(result.getLatitude(), result.getLongitude()), 18, 30, 30)));
				aMap.clear();
				aMap.addMarker(new MarkerOptions().position(new LatLng(result.getLatitude(), result.getLongitude()))
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
				mLocationClient.stopLocation();
			}
					
			
		}
		
	}
}
