package main.utils.baidu;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.smart.rcgj.R;

public class BaiDuLineTrackManager {

    /**************************************百度地图画线路轨迹
     * @param stationsDataList**********************************/
    public static   void baiduMapPolyBusTrackline(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> stationsDataList, MapView baiduMapView){
        //添加纹理图片
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        int src = R.drawable.lyf_icon_road_blue_arrow;
        BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromResource(src);//箭头图片
        textureList.add(mRedTexture);
        // 添加纹理图片对应的顺序
        List<Integer> textureIndexs = new ArrayList<Integer>();
        //构建折线点坐标

        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < stationsDataList.size() ; i++) {
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean stationBean = stationsDataList.get(i);
            LatLng p = new LatLng(Double.valueOf(stationBean.getStation().getLat()), Double.valueOf(stationBean.getStation().getLon()));
            textureIndexs.add(0);
            points.add(p);
        }

//设置折线的属性  .dottedLine(true)
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(16)
                .points(points)
                .textureIndex(textureIndexs)
                .customTextureList(textureList);
//在地图上绘制折线
//mPloyline 折线对象
        Overlay mPolyline = baiduMapView.getMap().addOverlay(mOverlayOptions);




    }
    //轨迹上的点
    public static void baiduMapAddBusStationMarkers(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> stationsDataList, MapView baiduMapView){
        //定义Maker坐标点
        List<OverlayOptions> options = new ArrayList<>();
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < stationsDataList.size() ; i++) {
            int imgSrc = R.drawable.lyf_icon_on_station;
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean stationBean = stationsDataList.get(i);
            LatLng p = new LatLng(Double.valueOf(stationBean.getStation().getLat()), Double.valueOf(stationBean.getStation().getLon()));
            points.add(p);
            switch (stationBean.getStation().getFlag()){
                case 0:
                    imgSrc = R.drawable.lyf_icon_on_station;
                    break;
                case 1:
                    imgSrc = R.drawable.lyf_icon_off_station;
                    break;
                case 2:
                    imgSrc = R.drawable.lyf_icon_onoff_station;
                    break;
            }
            LatLng point = new LatLng(Double.valueOf(stationBean.getStation().getLat()), Double.valueOf(stationBean.getStation().getLon()));
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(imgSrc);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap)
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .anchor(0.5f,0.5f).zIndex(10);

            options.add(option);
            if (i==0){//起始站点
                bitmap = BitmapDescriptorFactory
                        .fromResource(imgSrc);
//构建MarkerOption，用于在地图上添加Marker
                option = new MarkerOptions()
                        .position(point)
                        .icon( BitmapDescriptorFactory.fromAssetWithDpi("Icon_station_start.png")).zIndex(10);
                options.add(option);
            }else if(i == stationsDataList.size()-1){//结束站点
                bitmap = BitmapDescriptorFactory
                        .fromResource(imgSrc);
//构建MarkerOption，用于在地图上添加Marker
                option = new MarkerOptions()
                        .position(point)
                        .icon( BitmapDescriptorFactory.fromAssetWithDpi("Icon_station_end.png")).zIndex(10);
                options.add(option);
            }
        }

//在地图上添加Marker，并显示
        baiduMapView.getMap().addOverlays(options);
        //地图显示线范围内
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng p : points) {
            builder = builder.include(p);
        }
        LatLngBounds latlngBounds = builder.build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds,baiduMapView.getWidth()-160,baiduMapView.getHeight()-160);
        baiduMapView.getMap().animateMapStatus(u);
    }
}
