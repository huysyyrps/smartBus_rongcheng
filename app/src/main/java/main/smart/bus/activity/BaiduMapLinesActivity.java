package main.smart.bus.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import main.smart.bus.util.TransitRouteOverlay;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

/**
 * 根据选择的换乘信息画线路图
 * @author wang
 *
 */
public class BaiduMapLinesActivity extends Activity{
	private MapView mMapView = null;		//显示地图的View
	//private BMapManager mBMapManager = null;//地图引擎管理类
	private BaiduMap mBaiduMap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baidu_route_lines);
		Intent intent = getIntent();
		int index=intent.getExtras().getInt("index");
		mMapView = (MapView) this.findViewById(R.id.baidumapline);
		mBaiduMap = mMapView.getMap();

		TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
		mBaiduMap.setOnMarkerClickListener(overlay);

		overlay.removeFromMap();
		overlay.setData(ConstData.res.getRouteLines().get(index));
		overlay.addToMap();
		overlay.zoomToSpan();
//		mMapView.setBuiltInZoomControls(true);
//	    mMapView.getController().setZoom(15);
//	    mMapView.getController().enableClick(true);
//	    TransitOverlay transitOverlay = new TransitOverlay (BaiduMapLinesActivity.this, mMapView);
//	    // 此处仅展示一个方案作为示例
//	    transitOverlay.setData(ConstData.res.getPlan(index));
//	    //清除其他图层
//	    mMapView.getOverlays().clear();
//	    //添加路线图层
//	    mMapView.getOverlays().add(transitOverlay);
//	    //执行刷新使生效
//	    mMapView.refresh();
//	    //使用zoomToSpan()绽放地图，使路线能完全显示在地图上
//	    //mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
//	    //移动地图到起点
//	    mMapView.getController().animateTo(ConstData.res.getStart().pt);
//        mMapView.setVisibility(View.VISIBLE);
	}

	public void back(View paramView){
		onBackPressed();
	}
	//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		mMapView.onSaveInstanceState(outState);
//	}
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//		mMapView.onRestoreInstanceState(savedInstanceState);
//	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}
}
