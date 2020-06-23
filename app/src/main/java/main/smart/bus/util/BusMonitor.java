package main.smart.bus.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.common.SmartBusApp;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.ConstData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

/**
 * 车辆监控类，更新车辆坐标等信息
 * */
public class BusMonitor  implements Runnable{

    private BusManager mBusMan;
    private Handler mHandler;
    private List<BusMonitorInfoListener> mObservers = new ArrayList();
    private Boolean mWatching ;
    int sxx;
    private String webip2 ="";

    private String bsport2="";
    String lineCode="";
    String  urllly="";
    private String socketport2;
    public BusMonitor(BusManager paramBusManager){
        mBusMan = paramBusManager;
        this.mHandler = new Handler();
        //this.mAlarm = BusTrackAlarm.getInstance();
        this.mWatching =  false;
    }
    public void postMonitor(){
        this.mHandler.post(this);
    }
    //定义监听器
    public static abstract interface BusMonitorInfoListener{
        public abstract void onBusMonitorInfoUpdated(List<BusBean> paramList);
    }
    ///添加监听
    public void addBusMonitorInfoListener(BusMonitorInfoListener paramBusMonitorInfoListener){
        this.mObservers.add(paramBusMonitorInfoListener);
    }
    //移除监听器
    public void removeBusMonitorInfoListener(BusMonitorInfoListener paramBusMonitorInfoListener){
        this.mObservers.remove(paramBusMonitorInfoListener);
    }
    //移除全部监听器
    public void removeAllBusBusMonitorInfoListener(){
        mObservers.removeAll(mObservers);
    }

//	public void run(){
//		LineBean line= mBusMan.getSelectedLine();
//	 lineCode = line.getLineCode() ;
//		sxx =line.getLineId();  //线路 ，上下行
//
//
//
//
//		String url = ConstData.URL + "!getLineInfo.shtml";
//
//		RequestParams param = new RequestParams();
//
//		RequstClient.post(url,
//		param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
//				new LoadDatahandler() {
//					@Override
//					public void onStart() {
//						super.onStart();
//
//						Log.d("getLineInfo:", "get line  data");
//					}
//
//					@Override
//					public void onSuccess(String data) {
//						super.onSuccess(data);
//						try {
////							JSONArray lineArr = new JSONArray(data.toString());
//							JSONObject json = new JSONObject(data.toString());
//							JSONArray lineArr=json.getJSONArray("lineList");
//							Log.e(null, "zouni"+lineArr);
//
//								Log.e(null, "******33333333333*******************");
//								for (int t = 0; t < lineArr.length(); t++) {
//								//	Log.e(null, "******4444444444444444*******************"+lineArr);
//									if (lineArr.getJSONObject(t).has("webIp")){
//										if((lineArr.getJSONObject(t).getString("lineCode")).equals(lineCode)){
//
//											Log.e(null, "3333333+++++++*******************"+lineCode);
//											webip2 =lineArr.getJSONObject(t)
//												.getString("webIp").toString();
//											Log.e(null, "******+++++++*******************"+webip2);
//											 bsport2 =lineArr.getJSONObject(t)
//													.getString("bsPort").toString()  ;
//											socketport2 =lineArr.getJSONObject(t)
//													.getString("socketPort").toString() ;
//											break;
//										}
//									}
//								}
//								getBusInfoOnLine(lineCode, Integer.toString(sxx),webip2,bsport2,socketport2);
//
//
//
//
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							Log.e("数据返回错误", "未解析返回的线路站点");
//							Log.e("数据返回错误", e.getMessage());
//						}
//					}
//
//					@Override
//					public void onFailure(String error, String message) {
//						super.onFailure(error, message);
//						Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
//					}
//
//					@Override
//					public void onFinish() {
//						super.onFinish();
//					}
//				}));
//
//
//
//
//
//
//		this.mHandler.postDelayed(this, ConstData.INTERVAL);//3秒刷新一次数据
//	}
//


    public void run(){
        LineBean line= mBusMan.getSelectedLine();
        String lineCode = line.getLineCode() ;
        int sxx =line.getLineId();  //线路 ，上下行
        getBusInfoOnLine(lineCode, Integer.toString(sxx),"","","");
        Log.d("车辆数据监控：", "***上下行*********************************"+sxx);
        this.mHandler.postDelayed(this, ConstData.INTERVAL);//3秒刷新一次数据
    }
    public void startWatch(){
        if (mWatching){
            return;
        }
        this.mWatching =  true;
        this.mHandler.post(this);
    }
    public void stopWatch(){
        if (! mWatching ){
            return;
        }
        this.mHandler.removeCallbacks(this);
        this.mWatching =  false;
    }
    /**
     *
     * 获取车辆实时数据
     * **/
    public void getBusInfoOnLine(String lineCode,String sxx,String webip2,String bsport2,String socketport2){
        RequestParams param = new RequestParams();
        param.put("lineCode",lineCode);
        param.put("sxx", sxx);
        param.put("IMEI", ConstData.imei);

        Log.e(null, "$$$$$$$$$$$$$$$$$$$$$$$$");
        Log.e(null, webip2);

        if(!webip2.equals("")){
            urllly=	"http://"+webip2 +":"+socketport2;
            Log.e(null, "11111111111$$$$$$$$$$$$$$$$$$$$$$$$"+urllly);
        }else{
            urllly=	ConstData.goURL;
            Log.e(null, "2222222222222$$$$$$$$$$$$$$$$$$$$$$$$"+urllly);
        }
        Log.e(null, "333333333333$$$$$$$$$$$$$$$$$$$$$$$$");

//		Log.d("---------server网址", ConstData.goURL);
        Log.e("查询结果", urllly+"@@@@@@@@@@@@@@@问问数据返回"+param);
        //RequstClient.post(  ConstData.URL+"!getBusInfoOnLine.shtml",
        RequstClient.post( urllly,param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),new LoadDatahandler() {
            List<BusBean> mBusList = new ArrayList();
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                //数据需要解析一下
                try {
                    JSONArray resultArr =new JSONArray(data);
                    JSONObject json = new JSONObject();
                    if (resultArr != null) {

                        double dis =0;
                        double disb=0;
                        String leftDistance ="";
                        LineBean line= mBusMan.getSelectedLine();
                        //String lineCode = line.getLineCode() ;
                        Float[] disArr =line.getStationDistances();
                        List<StationBean> staList =line.getStations();
                        StationBean sta= new StationBean();
                        StationBean staNext= new StationBean();

                        //int sxx =line.getLineId();
                        int passOrder =0;
                        for (int i = 0; i < resultArr.length(); i++) {
                            json = resultArr.getJSONObject(i);
                            Log.e("查询结果", ConstData.goURL+"@@@@@@@@@@@@@@@数据返回"+json);
                            passOrder= Integer.parseInt(json.get("passStation").toString());
                            passOrder = passOrder==0?passOrder+1:passOrder;


                            if(passOrder>disArr.length){
                                continue;
                            }

                            //获取站点间距
                            float staDis =disArr[passOrder-1].floatValue();
                            sta = staList.get(passOrder-1);//站点间距为0 ，走到终点站
                            BusBean bus = new BusBean();
                            bus.setBusCode(json.getString("busCode"));
                            bus.setLat( json.getString("lat") );//纬度
                            bus.setLng( json.getString("lon") );//经度
                            //Log.d("busMonitor:", bus.getLat());
                            bus.setSxx(json.get("sxx").toString());
                            String isTravel = null;
                            try {
                                isTravel=json.getString("isTravel");//主副场
                            } catch (Exception e) {
                                isTravel = null;
                            }

                            try {
                                bus.setSpeed(json.getString("Speed"));//车速
                            } catch (Exception e) {
                                bus.setSpeed("-1");
                            }


                            try {
                                bus.setBusNum(json.getString("cph"));//车牌号
                            } catch (Exception e) {
                                bus.setBusNum("");
                            }
                            try {
                                Log.e("查询结果", "7777777777@@@@@@@@@@@@@@@数据返回"+json);

                                Log.e("查询结果", "444444444444444@@@@@@@@@@@@@@@数据返回"+json.getString("showCPH"));

                                bus.setShowCPH(json.getString("showCPH"));//车牌号
                            } catch (Exception e) {
                                bus.setShowCPH("");
                            }

                            if(bus.getSxx().equals("0")){
                                //down
                                if(isTravel != null)
                                {
                                    if (isTravel.equals("0"))//主场
                                    {
                                        passOrder=1;
                                    }

                                }
                                staNext =passOrder==staList.size()?sta:staList.get(passOrder);

                            }else{//up
                                if(isTravel != null)
                                {
                                    if (isTravel.equals("1"))//副场
                                    {
                                        passOrder=staList.size();
                                    }

                                }
                                staNext =passOrder==1?sta:staList.get(passOrder-2);

                            }
                            bus.setLeftStation(passOrder);


                            //计算 距离
                            dis =  BusMonitor.DistanceOfTwoPoints(Double.parseDouble(bus.getLng()),Double.parseDouble(bus.getLat()),sta.getLng(),sta.getLat(),GaussSphere.WGS84);
                            disb = BusMonitor.DistanceOfTwoPoints(Double.parseDouble(bus.getLng()),Double.parseDouble(bus.getLat()),staNext.getLng(),staNext.getLat(),GaussSphere.WGS84);
                            if(staDis==0){
                                leftDistance ="0";
                            }else{
                                leftDistance=String.format("%.1f",dis/(dis+disb));
                            }
                            //System.out.println(json.get("sxx").toString()+"--:"+bus.getBusCode()+"经过站："+passOrder);
                            bus.setLeftDistance( leftDistance);

                            this.mBusList.add(bus);
                        }
                    }

                    Iterator iterator = BusMonitor.this.mObservers.iterator();
                    while (iterator.hasNext()){
                        ((BusMonitor.BusMonitorInfoListener)iterator.next()).onBusMonitorInfoUpdated(this.mBusList);
                    }

                }catch(Exception e){
                    Log.e("read busData ", "读取车辆实时数据出错");
                }
            }
            @Override
            public void onFailure(String error, String message) {
                //Toast.makeText(SmartBusApp.getInstance().getApplicationContext(), "当前网速较慢请以实际车辆为准", Toast.LENGTH_SHORT).show();
                super.onFailure(error, message);
            }
            @Override
            public void onFinish() {
                super.onFinish();
            }
        }));
    }

    /***********经纬度的计算***********/
    public enum GaussSphere{
        Beijing54,
        Xian80,
        WGS84,
    }
    private static double Rad(double d){
        return d * Math.PI / 180.0;
    }
    /**
     * 计算 两个经纬度 点之间的距离
     * point1-poin2 =Mile
     * **/
    public static double DistanceOfTwoPoints(double lng1,double lat1,double lng2,double lat2, GaussSphere gs){
        //Log.d("车的经纬度", lng1/600000+"--"+lat1/600000);
        double radLat1 = Rad(lat1);
        double radLat2 = Rad(lat2);
        double a = radLat1 - radLat2;
        double b = Rad(lng1) - Rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b/2),2)));
        s = s * (gs == GaussSphere.WGS84 ? 6378137.0 : (gs == GaussSphere.Xian80 ? 6378140.0 : 6378245.0));
        s = Math.round(s * 10000) / 10000;
        //s=s/1000;//转换成公里
        return s;
    }


}
