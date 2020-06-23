package main.smart.bus.activity;

import main.smart.rcgj.R;
import main.smart.common.util.PreferencesHelper;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BaiduZDMapActivity  extends Activity implements OnGetGeoCoderResultListener {
//	private String qd="";
	private String zdType = "";
	private String zd="";
	private LocationClient mLocClient;
	private MapView mMapView = null;		//��ʾ��ͼ��View
	private BaiduMap mBaiduMap = null;
//	private BMapManager mBMapManager = null;//��ͼ���������
//	private MKMapTouchListener mapTouchListener = null; //���ڽػ�������
	private LatLng currentPt = null;//��ǰ�ص����
//	private PopupOverlay pop = null;		//�������
	private GeoCoder mSearch = null;
	private Button btn;
	private boolean isFirstLoc = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		mBMapManager = new BMapManager(this.getApplicationContext());
//		mBMapManager.init(null);
		setContentView(R.layout.baiduzdmap);
		Intent intent = getIntent();
		Bundle bunde = intent.getExtras(); 
//		qd=bunde.getString("qd");
		zdType=bunde.getString("zdType");
		
//		btn=(Button) findViewById(R.id.baiduzdhelp);
//		btn.setOnClickListener(new HelpListener());
		mMapView = (MapView) this.findViewById(R.id.baidu_route_zd);
//		mMapView.setBuiltInZoomControls(true);
//	    mMapView.getController().setZoom(15);
//	    mMapView.getController().enableClick(true);
		mBaiduMap = mMapView.getMap();

		mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
	    // ��λ��ʼ��
	 	mLocClient = new LocationClient(this);
	 	mLocClient.registerLocationListener(new MyLocationListenner());
	 	LocationClientOption option = new LocationClientOption();
	 	option.setOpenGps(true); // ���ֻ�GPS
	 	option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
	 	option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
	 	option.setScanSpan(500);// ���÷���λ����ļ��ʱ��Ϊ5000ms ���������С��1000ms����ֻ��λһ��
//	 	option.disableCache(true);// ��ֹ���û��涨λ
//	 	option.setPoiNumber(5); // ��෵��POI����
//	 	option.setPoiDistance(1000); // poi��ѯ����
//	 	option.setPoiExtraInfo(true); // �Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
	 	mLocClient.setLocOption(option);
	 	mLocClient.start();
	 	
	 	// ��ʼ������ģ�飬ע���¼�����
	 	mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
	 	
	 	mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng arg0) {
				// TODO Auto-generated method stub
				currentPt = arg0;
				
				mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(arg0));
			}
		});
//	    mapTouchListener = new MKMapTouchListener(){
//			@Override
//			public void onMapClick(GeoPoint point) {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void onMapDoubleClick(GeoPoint point) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void onMapLongClick(GeoPoint point) {
//				// TODO Auto-generated method stub
//				currentPt = point;
//				mSearch.reverseGeocode(point);
//			}
//	    };
//	    mMapView.regMapTouchListner(mapTouchListener);
//	    mSearch=new MKSearch();
//	    mSearch.init(mBMapManager,  new MKSearchListener() {
//			@Override
//			public void onGetAddrResult(MKAddrInfo res, int error) {
//				// TODO Auto-generated method stub
//				if (error != 0) {
//					String str = String.format("����ţ�%d", error);
//					Toast.makeText(BaiduZDMapActivity.this, str, Toast.LENGTH_LONG).show();
//					return;
//				}
//				zd=res.strAddr.toString();
//				updateMapState();
//			}
//
//			@Override
//			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0,
//					int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
//					int arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetTransitRouteResult(MKTransitRouteResult arg0,
//					int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,
//					int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//	    });
	    PreferencesHelper  mPreference = PreferencesHelper.getInstance();
		int ver=mPreference.getTitleVersion();
        if (ver==0){
        	Helper();
        	mPreference.updateTitleVersion();
        }
	}
	
	/**
	 * ������Ϣ
	 */
	public void Helper(){
		new AlertDialog.Builder(this).setTitle("����")
        .setIcon(android.R.drawable.ic_dialog_info)
        .setCancelable(false)
        .setMessage("������ͼ����ѡ�еص�")
        .setPositiveButton("ȷ��",null).show();	
	}
	
	/**
	 * ������ť����
	 * @author wang
	 *
	 */
	class HelpListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Helper();
		}
			
	}
	
	
	
	/**
	 * �Զ���λ��ǰλ��
	 * */
	public class MyLocationListenner implements BDLocationListener {
		public void onReceiveLocation(BDLocation result) {
			
			if (result == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(result.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(result.getLatitude())
					.longitude(result.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			
			if (isFirstLoc ) {
				isFirstLoc = false;
				LatLng ll = new LatLng(result.getLatitude(),
						result.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}

		}
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	
	/**
	 * ��ʾѡ����Ϣ
	 */
//	private void updateMapState(){
//		
//		pop = new PopupOverlay(mMapView, null);
//		View view = getLayoutInflater().inflate(R.layout.popview, null);
//		View pop_layout = view.findViewById(R.id.popview_layout);
//		PopupOverlay pop = new PopupOverlay(mMapView,new PopupClickListener() {                 
//			public void onClickedPopup(int index) {
//				if (zdType.equals("qd"))
//				{
//					ConstData.start=currentPt;
//					BaiduRouteFragment.textqd.setText(zd);
//				}
//				else if (zdType.equals("zd"))
//				{
//					ConstData.end=currentPt;
//					BaiduRouteFragment.textzd.setText(zd);
//				}
//				
////				Intent inte=new Intent();
////				Bundle bundle = new Bundle();
////				bundle.putInt("tab", 1);
////				bundle.putString("qs", qd);
////				bundle.putString("js", zd);
////				inte.putExtras(bundle);
////				inte.setClass(BaiduZDMapActivity.this,BusActivity.class);
////				startActivity(inte);
//				finish();
//			}
//		});
//		//��Viewת����������ʾ��bitmap
//		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(pop_layout) };
//		pop.showPopup(bitMaps, currentPt, 32);
//	}
	
	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	//���ذ�ťʱ��
	public void back(View paramView){
		onBackPressed();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null || arg0.error != SearchResult.ERRORNO.NO_ERROR) {
			//Toast.makeText(BaiduZDMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
			//		.show();
			return;
		}

		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(arg0.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.pins)));
//		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(arg0
//				.getLocation()));
//		Toast.makeText(BaiduZDMapActivity.this, arg0.getAddress(),
//				Toast.LENGTH_LONG).show();
		
		zd=arg0.getAddress().toString();//res.strAddr.toString();
		new AlertDialog.Builder(this).setTitle("λ��")
        .setCancelable(false)
        .setMessage(zd)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//System.exit(0);
				finish();
				if (zdType.equals("qd"))
				{
//					ConstData.start=currentPt;
					BaiduRouteFragment.textqd.setText(zd);
				}
				else if (zdType.equals("zd"))
				{
//					ConstData.end=currentPt;
					BaiduRouteFragment.textzd.setText(zd);
				}
			}
		})
		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		}).show();
	}
	
	
}
