package main.utils.baidu;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.List;

import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.smart.rcgj.R;
import main.utils.baidu.overlayutil.BikingRouteOverlay;
import main.utils.baidu.overlayutil.DrivingRouteOverlay;
import main.utils.baidu.overlayutil.MassTransitRouteOverlay;
import main.utils.baidu.overlayutil.TransitRouteOverlay;
import main.utils.baidu.overlayutil.WalkingRouteOverlay;

public class BaiDuRoutePlanSearchMananger implements OnGetRoutePlanResultListener {
    private static   BaiDuRoutePlanSearchMananger planSearchMananger;
    private  Context context;
    private RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private  BaiduMap mBaidumap = null;
    private OnGetRoutePlanResultListener onGetRoutePlanResultListener;
    boolean useDefaultIcon = false;

    public static  BaiDuRoutePlanSearchMananger getInstance(Context context,BaiduMap mBaidumap,boolean useDefaultIcon){
        if (planSearchMananger==null){
            planSearchMananger = new BaiDuRoutePlanSearchMananger();
        }
        planSearchMananger.setContext(context);
        planSearchMananger.setmBaidumap(mBaidumap);
        planSearchMananger.setUseDefaultIcon(useDefaultIcon);
        return planSearchMananger;
    }

    public  BaiDuRoutePlanSearchMananger() {

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }
//驾车线路规划

    public void searchDriving(List<CustomizedLineDetailBean.DataBean.LinePlanDTOListBean> linePlanListBeans){
        PlanNode fromNode = null;
        PlanNode toNode= null;
        List<PlanNode> passNode = new ArrayList<>();
        for (int i = 0; i < linePlanListBeans.size(); i++) {
            CustomizedLineDetailBean.DataBean.LinePlanDTOListBean planDTOListBean = linePlanListBeans.get(i);
            double lat = Double.valueOf(planDTOListBean.getLat());
            double lng = Double.valueOf(planDTOListBean.getLng());
            LatLng latLng = new LatLng(lat,lng);
            PlanNode planNode = PlanNode.withLocation(latLng);
             if (i==0){
              fromNode = planNode;
             }else if(i==linePlanListBeans.size()-1){

              toNode = planNode;
             }else {
                 passNode.add(planNode);

             }
        }
        if (fromNode==null||toNode==null){
            return;
        }
        searchDriving(fromNode,toNode,passNode);
    }
    public void searchDriving(PlanNode fromNode, PlanNode toNode, List<PlanNode> passNode){
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(fromNode).to(toNode).passBy(passNode).policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST)//  设置驾车路线规划策略ECAR_AVOID_JAM:驾车策略： 躲避拥堵 ECAR_DIS_FIRST:驾乘检索策略常量：最短距离 ECAR_FEE_FIRST:驾乘检索策略常量：较少费用ECAR_TIME_FIRST:驾乘检索策略常量：时间优先
                .trafficPolicy(DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH_AND_TRAFFIC));
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
          if (onGetRoutePlanResultListener!=null){
              DrivingRouteOverlay overlay = null;
              if (mBaidumap!=null){
                  overlay = new MyDrivingRouteOverlay(mBaidumap);
              }
              onGetRoutePlanResultListener.onGetDrivingRouteResult(drivingRouteResult,overlay);
          }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
//set

    public void setContext(Context context) {
        this.context = context;
    }

    public void setmBaidumap(BaiduMap mBaidumap) {
        this.mBaidumap = mBaidumap;
    }

    public void setUseDefaultIcon(boolean useDefaultIcon) {
        this.useDefaultIcon = useDefaultIcon;
    }

    public void setOnGetRoutePlanResultListener(OnGetRoutePlanResultListener onGetRoutePlanResultListener) {
        this.onGetRoutePlanResultListener = onGetRoutePlanResultListener;
    }

    //接口
    public interface OnGetRoutePlanResultListener {
        void onGetWalkingRouteResult(WalkingRouteResult var1);

        void onGetTransitRouteResult(TransitRouteResult var1);

        void onGetMassTransitRouteResult(MassTransitRouteResult var1);

        void onGetDrivingRouteResult(DrivingRouteResult var1, DrivingRouteOverlay overlay);

        void onGetIndoorRouteResult(IndoorRouteResult var1);

        void onGetBikingRouteResult(BikingRouteResult var1);
    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
            }
            return null;
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
            }
            return null;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
            }
            return null;
        }
    }

    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
            }
            return null;
        }


    }

    private class MyMassTransitRouteOverlay extends MassTransitRouteOverlay {
        public MyMassTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_start);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.sketch_finish);
            }
            return null;
        }


    }


}
