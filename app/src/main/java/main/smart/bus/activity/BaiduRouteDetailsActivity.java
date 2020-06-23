//package main.smart.bus.activity;
//
//import main.smart.rcgj.R;
//import main.smart.bus.view.BaiduRouteView;
//import main.smart.bus.view.RunBusView;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//public class BaiduRouteDetailsActivity extends Activity{
//	private int index=0;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		Intent intent = getIntent();
//		index=intent.getExtras().getInt("index");
//		String qszd = intent.getStringExtra("qd");
//		String jszd = intent.getStringExtra("zd");
//		String memo = intent.getStringExtra("memo");
//		setContentView(R.layout.baidu_route_details);
////		BaiduRouteView baiduview = (BaiduRouteView) findViewById(R.id.baidurouteview);
////		baiduview.setData(qszd, jszd, memo);
//	}
//
//	public void sendbaidumap(View paramView){
//		Intent inte=new Intent();
//		Bundle bundle=new Bundle();
//		bundle.putInt("index",index);
//		inte.putExtras(bundle);
//		inte.setClass(BaiduRouteDetailsActivity.this, BaiduMapLinesActivity.class);
//		startActivity(inte);// 跳转主页
//
//	}
//	public void back(View paramView){
//		onBackPressed();
//	}
//}


package main.smart.bus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;

import java.util.ArrayList;
import java.util.List;

import main.smart.bus.view.BaiduRouteView;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

public class BaiduRouteDetailsActivity extends Activity implements View.OnClickListener{
	private int index=0;
	TextView tvDH;
//	com.amap.api.maps.model.LatLng latLng,latLng1,mCurPoint;
//	String startName = "";
//	String endName = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		index=intent.getExtras().getInt("index");
		String qszd = intent.getStringExtra("qd");
		String jszd = intent.getStringExtra("zd");
//		String lat = intent.getStringExtra("lat");
//		String log = intent.getStringExtra("log");
//		latLng = new LatLng(Double.valueOf(lat),Double.valueOf(log));
//		latLng = ConstData.startne;
//		latLng1 = ConstData.endne;
//		startName = ConstData.startStudia;
//		endName = ConstData.endStudia;
		//String memo = intent.getStringExtra("memo");
		setContentView(R.layout.baidu_route_details);
		BaiduRouteView baiduview = (BaiduRouteView) findViewById(R.id.baidurouteview);
		//baiduview.setData(qszd, jszd, memo);
		TransitRouteResult res = ConstData.res;
		TransitRouteLine trLine = res.getRouteLines().get(index);
		baiduview.setData(trLine);

		tvDH = findViewById(R.id.tvDH);
		tvDH.setOnClickListener(this);

	}

	public void sendbaidumap(View paramView){
		Intent inte=new Intent();
		Bundle bundle=new Bundle();
		bundle.putInt("index",index);
		inte.putExtras(bundle);
		inte.setClass(BaiduRouteDetailsActivity.this, BaiduMapLinesActivity.class);
		startActivity(inte);// 跳转主页
	}
	public void back(View paramView){
		onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tvDH:
				if(isAvilible(BaiduRouteDetailsActivity.this,"com.baidu.BaiduMap")) {//传入指定应用包名
					Toast.makeText(BaiduRouteDetailsActivity.this,R.string.openbaidu,Toast.LENGTH_LONG).show();
					Intent i1 = new Intent();
					i1.setData(Uri.parse("baidumap://map/direction?origin=name:"+ConstData.startStudia+"|latlng:"+ConstData.startlat+","+ConstData.startlon+"&destination="+ConstData.endStudia+"&coord_type=bd09ll&mode=transit&sy=3&index=0&target=1&src=andr.baidu.openAPIdemo"));
					startActivity(i1);
				}else {
					Toast.makeText(BaiduRouteDetailsActivity.this,R.string.nobaidu,Toast.LENGTH_LONG).show();

					return;
				}


				break;
		}
	}

	/**
	 * 检查手机上是否安装了指定的软件
	 * @param context
	 * @param packageName：应用包名
	 * @return
	 */
	private boolean isAvilible(Context context, String packageName){
		//获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		//获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		//用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		//从pinfo中将包名字逐一取出，压入pName list中
		if(packageInfos != null){
			for(int i = 0; i < packageInfos.size(); i++){
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		//判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}

}

