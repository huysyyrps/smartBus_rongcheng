package main.smart.bus.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapView;

import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.util.BusLineRefresh;
import main.smart.bus.util.BusManager;
import main.smart.bus.util.BusMonitor;
import main.smart.bus.view.BusLineGraphView;

public class BusLineDetailStationFragment extends Fragment implements
		                                                    BusLineGraphView.OnBusStationClickListener,
		BusMonitor.BusMonitorInfoListener,BusLineRefresh {
	/**
	 * ���Ӱٶȵ�ͼ
	 * */
	private BusLineGraphView mBusLineGraph;
	private BusManager mBusMan = BusManager.getInstance();
	private List<StationBean> mStations;
	private int mGetOffStation = -1;
	private int mGetOnStation = -1;
	private int sxx=0;
	//private BMapManager mBMapMan;
	private MapView mMapView;
	//�Զ�λ����λ��Ϊ��ͼ���ĵ�
//	private ItemizedOverlay<OverlayItem> centerOverLay;
//	//����ͼ��
//	private ItemizedOverlay<OverlayItem> busOverLay;
//	private TextOverlay busTextOverLay; 
//	//վ��ͼ��
//	private ItemizedOverlay<OverlayItem> stationOverLay;
//	private TextOverlay stationTextOverLay; 
	private Drawable mark;
	private Drawable busIcon;
	// Drawable s1= getResources().getDrawable(R.drawable.arrow_down);
	 //Drawable s2 =getResources().getDrawable(R.drawable.arrow_right);
	
	 //--------����������
//	 Symbol textSymbol = new Symbol();    
//	 Symbol.Color textColor = textSymbol.new Color();    
	public BusLineDetailStationFragment() {
		Log.d("graphFrag", "graphFrag");
		 this.mStations = this.mBusMan.getSelectedLine().getStations();

     	this.mBusMan.addBusMonitorInfoListener(this);
//		 textColor.alpha = 255;    
//		 textColor.red = 30;    
//		 textColor.blue = 255;    
//		 textColor.green = 50; 
	}
	 @Override  
	public void onCreate(Bundle bl){
		super.onCreate(bl);
//		mBMapMan=new BMapManager(SmartBusApp.getInstance());
//		mBMapMan.init(null); 
	}
	///��ʼ��ҳ��
	 @Override  
	public View onCreateView(LayoutInflater paramLayoutInflater,
			                 ViewGroup paramViewGroup, Bundle paramBundle) {
		View lineGraphView = paramLayoutInflater.inflate(
				R.layout.bus_mapview, paramViewGroup, false);
		 mark= getResources().getDrawable(R.drawable.arrow_down);
		 busIcon = getResources().getDrawable(R.drawable.sketch_busicon);
				 
				 
		mMapView=(MapView)lineGraphView.findViewById(R.id.bmapsView);
//		mMapView.setBuiltInZoomControls(true);
		//---------��ͼ��������---------------------------
		//�����������õ����ſؼ�
//		MapController mMapController=mMapView.getController();
//		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
//	     //��������	
//		GeoPoint point =new GeoPoint((int)(ConstData.CENTER_Y* 1E6),(int)(ConstData.CENTER_X* 1E6));
//		//��λ����
	   //	GeoPoint point =new GeoPoint((int)(Double.parseDouble(ConstData.CENTER_X)* 1E6),(int)(Double.parseDouble(ConstData.CENTER_Y)* 1E6));
		//�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
	
		addLocationPoint();
	
	  //-----�ٶȵ�ͼ�ϻ�վ��--------------------------------------------	
		sxx =mBusMan.getSelectedLine().getLineId();//��¼�����г�ʼֵ
		addStationPoint();
   
	///	addBusPoint("1203",36.692788,117.097902);
		
		
		return lineGraphView;
	}
	 public void addLocationPoint(){
		 Drawable locDraw=getResources().getDrawable(R.drawable.point);
		 //����վ��ͼ��
//		 if(centerOverLay==null){
//			 
//			 centerOverLay =new ItemizedOverlay<OverlayItem>( locDraw, mMapView);
//			 mMapView.getOverlays().add(centerOverLay);
//		 } 
//		
//		//����Maker�����
//		 GeoPoint point =new GeoPoint((int)(ConstData.CENTER_Y* 1E6),(int)(ConstData.CENTER_X* 1E6));
//		 //����MarkerOption�������ڵ�ͼ�����Marker
//		 OverlayItem locPoint = new OverlayItem(point,"��ǰλ��","");
//		 locPoint.setMarker(locDraw);
//		 centerOverLay.addItem(locPoint);

	 }
	 /**
	  * 
	  * ����վ�㸲�����
	  * **/
	 public void addStationPoint(){
		// int sxx =this.mBusMan.getSelectedLine().getLineId();
//		 Drawable s1=sxx==0? getResources().getDrawable(R.drawable.arrow_right):getResources().getDrawable(R.drawable.arrow_left);
//		 //�Ƿ��������ת���ٶ�����
//		 GeoPoint spoint =null;
//			StationBean sta =null;
//			 //--------����������---------
//			 Symbol textSymbol = new Symbol();    
//			 Symbol.Color textColor = textSymbol.new Color();    
//			 textColor.alpha = 255;    
//			 textColor.red = 0;    
//			 textColor.blue = 128;    
//			 textColor.green = 0; 
//			 //����վ��ͼ��
//			 if(stationOverLay==null){
//				 
//				 stationOverLay =new ItemizedOverlay<OverlayItem>( mark, mMapView);
//			 } 
//			 if(!mMapView.getOverlays().contains( stationOverLay)){
//				 mMapView.getOverlays().add(stationOverLay);
//			 }
//			 //����ͼ��
//			 if( stationTextOverLay==null  ){
//				 stationTextOverLay = new TextOverlay(mMapView); 
//			
//			 } 
//			 if(!mMapView.getOverlays().contains( stationTextOverLay)){
//				 mMapView.getOverlays().add(stationTextOverLay);
//			 }
//			for(int i=0;i<mStations.size();i++){
//				sta =mStations.get(i);
//				spoint=new GeoPoint((int)(sta.getLat()* 1E6),(int)(sta.getLng()* 1E6));
//				
//				GeoPoint baiduP =CoordinateConvert.fromWgs84ToBaidu(spoint);
//			//	Log.d("���꣺", baiduP.getLatitudeE6()+","+baiduP.getLongitudeE6());
//				 OverlayItem point = new OverlayItem(baiduP,sta.getStationName(),"");
//				 point.setMarker(s1);
//				 stationOverLay.addItem(point);
//				 TextItem textItem = new TextItem();
//				 textItem.fontColor = textColor;
//				 textItem.fontSize = 20;
//				 textItem.text = sta.getStationName();
//				 textItem.pt =baiduP;
//				 stationTextOverLay.addText(textItem);
//			}
//			mMapView.refresh();
	 }
	/**
	 * ���ӳ���ͼ�긲����
	 * */
	 public void addBusPoint(String busCode,double lat,double lon){
		
	
//		    GeoPoint gpsPoint = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
//		 //   Log.d("before:", lat+":"+lon);
//			GeoPoint baiduP =CoordinateConvert.fromWgs84ToBaidu(gpsPoint);
//		//	Log.d("after:", baiduP.getLatitudeE6()+":"+baiduP.getLongitudeE6());
//			OverlayItem busItem = new OverlayItem(baiduP,busCode,"item1");
//			
//	
//		//����
//			 TextItem textItem = new TextItem();
//			 textItem.fontColor = textColor;
//			 textItem.fontSize = 20;
//			 textItem.text = busCode;
//			 textItem.pt =baiduP;
//			   
//			 busTextOverLay.addText(textItem); 
// 
//			//��OverlayItem׼��Overlay����   ---------���ԣ�û������
//			 busItem.setMarker(busIcon);
//			
//			 busOverLay.addItem(busItem);
//		
//			 mMapView.refresh();
//			
	 }
	 /**
	  * ɾ��������
	  * **/
	 public void removeBusPoint(){
		 
	 }
	/**\
	 * 
	 * ����������и�����
	 * */
	 public void clearOverLay(){
		// mMapView.getOverlays().clear();
//		 if(busOverLay!=null){
//			 busTextOverLay.removeAll();
//			 busOverLay.removeAll();
//			 mMapView.getOverlays().remove(busTextOverLay);
//			 mMapView.getOverlays().remove(busOverLay);
//			 mMapView.refresh();  //2.0.0�汾�������������ˢ�½�֧��refresh����
//		 }
	 }
	/**
	 * ��� վ���
	 * */ 
	 public void clearStationOverLay(){
//		 if(stationOverLay!=null){
//			 stationTextOverLay.removeAll();
//			 stationOverLay.removeAll();
//			 mMapView.getOverlays().remove(stationOverLay);
//			 mMapView.getOverlays().remove(stationTextOverLay);
//			 mMapView.refresh();  //2.0.0�汾�������������ˢ�½�֧��refresh����
//		 }
	 }

/**�ٶȵ�ͼ
 * �յ������ݰ����³���λ��
 * */
	public void onBusMonitorInfoUpdated(List<BusBean> paramList) {
		Log.d("BusLineDetailStationFragment", "�������ݸ��¿�ʼ");
		if(mMapView==null)
			return;
	   refreshData();
		//�������������
		clearOverLay();
		 //����ͼ��
//		 if(busOverLay==null){
//			 
//			 busOverLay =new ItemizedOverlay<OverlayItem>( busIcon, mMapView);
//		 } 
//		 if(!mMapView.getOverlays().contains( busOverLay)){
//			 mMapView.getOverlays().add(busOverLay);
//		 }
//		 if( busTextOverLay==null  ){
//			 busTextOverLay = new TextOverlay(mMapView); 
//		
//		 } 
//		 if(!mMapView.getOverlays().contains( busTextOverLay)){
//			 mMapView.getOverlays().add(busTextOverLay);
//		 }
		//�ػ泵��������
		for(int i=0;i<paramList.size();i++){
			BusBean bus =paramList.get(i);
	//		System.out.println(bus.getBusCode()+"Number:"+i);
			addBusPoint(bus.getBusCode(),Double.parseDouble(bus.getLat()),Double.parseDouble(bus.getLng()));
		}
		Log.d("BusLineDetailStationFragment", "�������ݸ��½���");
		
	}
	public void onSoiChanged(List<Integer> paramList) {
		this.mGetOnStation = -1;
		this.mGetOffStation = -1;
		if (paramList.size() > 0)
			this.mGetOnStation = ((Integer) paramList.get(0)).intValue();
		if (paramList.size() > 1)
			this.mGetOffStation = ((Integer) paramList.get(1)).intValue();
		this.mBusLineGraph.setGetOnOffStations(this.mGetOnStation,
				this.mGetOffStation);
	}
 
/**
 * ˢ������
 * */
	public void refreshData() {
		this.mGetOnStation = -1;
		this.mGetOffStation = -1;
		int newSxx =mBusMan.getSelectedLine().getLineId();
		if(sxx!=newSxx){
			clearStationOverLay();
			sxx =newSxx;
			//�����иı䣬���¼���վ��
			this.addStationPoint();
		}
	}
	/**
	 * ���վ�����
	 * */
		public void onBusStationClick(View paramView, int paramInt1, int paramInt2,
				int paramInt3) {
			/*
			List localList = this.mBusMan.getSoiList();
			if (this.mGetOnStation < 0)
				localList.add(Integer.valueOf(paramInt1));
			while (true) {
				this.mBusMan.notifySoiChanged();
				return;
				if (this.mGetOffStation < 0) {
					if (paramInt1 < this.mGetOnStation)
						localList.set(0, Integer.valueOf(paramInt1));
					if (paramInt1 > this.mGetOnStation)
						localList.add(Integer.valueOf(paramInt1));
					localList.clear();
				}
				if (paramInt1 == this.mGetOffStation)
					localList.remove(1);
				if (paramInt1 == this.mGetOnStation)
					localList.clear();
				localList.clear();
				localList.add(Integer.valueOf(paramInt1));
			}*/
		}
	public void onDestroy() {
		//	this.mBusMan.removeOnSoiChangedListener(this);
	//		this.mBusMan.removeBusMonitorInfoListener(this);
			super.onDestroy();
		}
}
