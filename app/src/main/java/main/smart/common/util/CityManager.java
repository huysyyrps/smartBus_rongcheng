package main.smart.common.util;

import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import main.smart.bus.bean.AdvertBean;
import main.smart.bus.bean.InterfaceBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.LineHistory;
import main.smart.bus.bean.ZDXX;
import main.smart.common.SmartBusApp;
import main.smart.common.bean.City;
import main.smart.common.bean.SwitchCity;
import main.smart.common.http.DBHandler;
import main.smart.common.http.DataBase;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.rcgj.R;

public class CityManager {
    private static CityManager mInstance;
    private City mCurrentRegion;
    private DBHelper mDBHelper;
    private DBHandler dbHandler = new DBHandler();
    private boolean mLocated = false;
    private PreferencesHelper mPreferenceManager;
    private boolean mSelected;
    private City mSelectedRegion;
    private List<ZDXX> staList;// 全部站点信息（用于换乘查询）
    private int dbVer;// 数据库版本
    private boolean zdiden;

    public boolean isZdiden() {
        return zdiden;
    }

    public void setZdiden(boolean zdiden) {
        this.zdiden = zdiden;
    }


    private List<LineBean> allLine;

    private CityManager() {
        mDBHelper = DBHelper.getInstance();
        mPreferenceManager = PreferencesHelper.getInstance();
        initSelectedCity();
    }

    public static CityManager getInstance() {
        if (mInstance == null)
            mInstance = new CityManager();
        return mInstance;
    }

    public String getFunctions() {
        return this.mSelectedRegion.getFunctions();
    }


    /* 下载城市列表
     * */
    public void loadCityList() throws SQLException {
        String url = "http://" + "123.232.38.10:9999/androidData?city=0";
        List<SwitchCity> list;
        Log.e(null, "--------------我想知道这里走了吗");
        list = dbHandler.getCityList(url);

        if (list.size() < 1) {
            url = "http://" + "113.128.251.52:9999/androidData?city=0";
            list = dbHandler.getCityList(url);
        }
        if (list.size() > 0) {
            mDBHelper.updateCityInfo(list);
        }


    }
    /**
     * 获取城市信息 param :province,cityName
     *
     * private City getCityInfo(String province, String cityName) { List
     * localList =
     * this.mDBHelper.getRuntimeExceptionDao(City.class).queryForMatching(new
     * City(cityName, province)); if (localList.size() <= 0) return null; return
     * ((City)localList.get(0)); }
     */
    /**
     * 初始化选中城市信息
     */
    private void initSelectedCity() {
        this.mSelected = false;
        int cityCode = this.mPreferenceManager.getCity();
        if (cityCode == 0)
            return;
        // Dao<T,V>

        // 包含两个泛型,第一个泛型表DAO操作的类,第二个表示操作类的主键类型
        // 根据城市编码主键id获取记录
        RuntimeExceptionDao<City, Integer> cityDao = mDBHelper.getCityDBDao();
        City localRegion = cityDao.queryForId(cityCode);
        if (localRegion == null)
            return;
        this.mSelectedRegion = localRegion;
        this.mSelected = true;
    }

    public String getSelectedCity() {
        return this.mSelectedRegion.getCityName();
    }

    // mSelectedRegion==null
    public int getSelectedCityCode() {
        return this.mSelectedRegion.getCityCode();
    }

    public void setSelectedCityCode(int cityCode) {
        mSelectedRegion.setCityCode(cityCode);
    }

    /**
     * 获取所有的省
     */
    public ArrayList<String> getAllProvinces() {
        ArrayList<String> localArrayList = new ArrayList<String>();
        RuntimeExceptionDao localRuntimeExceptionDao = this.mDBHelper
                .getRuntimeExceptionDao(City.class);
        try {
            Iterator localIterator = localRuntimeExceptionDao.queryBuilder()
                    .distinct().selectColumns(new String[]{"province"})
                    .query().iterator();
            if (!(localIterator.hasNext()))
                return localArrayList;
            localArrayList.add(((City) localIterator.next()).getProvince());
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return localArrayList;
    }

    /***
     * 获取全部存储在本地的城市，ip等信息
     * */
    public List<SwitchCity> getSwitchCityList() {
        List<SwitchCity> list = this.mDBHelper.getRuntimeExceptionDao(
                SwitchCity.class).queryForAll();
        if (list.size() <= 0)
            return null;
        return list;
    }

    /***
     * 获取符合条件存储在本地的城市，ip等信息
     * */
    public List<SwitchCity> getSwitchCityByName(String name) {
        List<SwitchCity> list = new ArrayList<SwitchCity>();
        name = ConstData.GPS_CITY;
        System.out.println("&&&&&&&&" + name);

        RuntimeExceptionDao<SwitchCity, Integer> dao = this.mDBHelper.getSwitchDao();
        SwitchCity sc = new SwitchCity();
        sc.setCityName(name);
        Iterator ite = dao.queryForMatching(sc).iterator();
        // mDBHelper.getRuntimeExceptionDao(SwitchCity.class).queryForFieldValues(arg0)
        while (ite.hasNext()) {

            list.add(((SwitchCity) ite.next()));
        }
        return list;
    }

    public List<SwitchCity> getSwitchCityById(int code) {
        List<SwitchCity> list = new ArrayList<SwitchCity>();
        System.out.println("&&&&&&&&" + code);
        Iterator localIterator = this.mDBHelper
                .getRuntimeExceptionDao(SwitchCity.class)
                .queryForEq("cityCode", code).iterator();
        // mDBHelper.getRuntimeExceptionDao(SwitchCity.class).queryForFieldValues(arg0)
        while (localIterator.hasNext()) {

            list.add(((SwitchCity) localIterator.next()));
        }
        return list;
    }

    /**
     * 由城市名获取城市编码
     *
     * public int getAreaCodeFromCity(String paramString1, String paramString2)
     * { City localRegion = getCityInfo(paramString1, paramString2); if
     * (localRegion == null) return -1; return localRegion.getCityCode(); }
     */
    /**
     * 获取省内城市
     */
    public ArrayList<String> getCitiesInProvince(String paramString) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = this.mDBHelper
                .getRuntimeExceptionDao(City.class)
                .queryForEq("province", paramString).iterator();
        while (true) {
            if (!(localIterator.hasNext()))
                return localArrayList;
            localArrayList.add(((City) localIterator.next()).getCityName());
        }
    }

    /**
     * 由编码获取城市对象列表
     */
    public ArrayList<String> getCityFromCode(int paramInteger) {
        ArrayList localArrayList = new ArrayList();
        RuntimeExceptionDao<City, Integer> cityDao = mDBHelper.getCityDBDao();
        City localRegion = cityDao.queryForId(paramInteger);
        if (localRegion != null) {
            localArrayList.add(localRegion.getCityName());
            localArrayList.add(localRegion.getProvince());
        }
        return localArrayList;
    }

    /**
     * return 当前城市名称
     */
    public String getCurrentCity() {
        return this.mCurrentRegion.getCityName();
    }

    public int getCurrentCityCode() {
        return this.mCurrentRegion.getCityCode();
    }

    public String getCurrentProvince() {
        return this.mCurrentRegion.getProvince();
    }

    public void setCurrentRegion(SwitchCity sc) {
        mLocated = true;
        mCurrentRegion.setCityCode(sc.getCityCode());
        mCurrentRegion.setCityName(sc.getCityName());
    }

    /**
     * 是否当前城市 return boolean
     */
    public Boolean isCurrentCity() {
        boolean is = mSelectedRegion.getCityCode() == mCurrentRegion
                .getCityCode() ? true : false;
        return is;
    }

    /**
     * 城市是否选中
     */
    public Boolean isSelected() {
        return mSelected;
    }

    /**
     * 读取xml文件中的城市信息，存入数据库 城市存入 数据库 *
     */
    public void loadCitiesToDB() {
        String province = "";
        final ArrayList<City> cityList = new ArrayList<City>();
        XmlResourceParser xrp = SmartBusApp.getInstance().getResources()
                .getXml(R.xml.region);

        try {
            int eventType = xrp.getEventType();
            City oneCity = null;
            // 判断是否到了文件结尾
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG
                        && xrp.getName().equals("province")) {// 一级标签：省
                    province = xrp.getAttributeValue(0);
                } else if (eventType == XmlResourceParser.START_TAG
                        && xrp.getName().equals("city")) {// 二级标签：城市
                    oneCity = new City();
                    oneCity.setProvince(province);
                    oneCity.setCityCode(Integer.parseInt(xrp
                            .getAttributeValue(1)));
                    oneCity.setCityName(xrp.getAttributeValue(0));
                    cityList.add(oneCity);
                }
            }
            // --------------读取xml完毕

            final RuntimeExceptionDao cityDao = this.mDBHelper
                    .getRuntimeExceptionDao(City.class);
            // 读取xml文件中的城市信息，存入数据库(localArrayList, cityDao)
            cityDao.callBatchTasks(new Callable() {

                public Void call() {
                    Iterator localIterator = cityList.iterator();
                    while (localIterator.hasNext()) {
                        City localRegion = (City) localIterator.next();
                        cityDao.createOrUpdate(localRegion);// 存入数据
                    }
                    return null;

                }
            });
        } catch (Exception localException) {
            localException.printStackTrace();
            return;
        } finally {
            xrp.close();
        }
    }

    /**
     * 设置GPS定位到的当前城市 param :province ,cityName
     */
    public void setCurrentCity(String province, String cityName) {/*
     * city表中的内容源自xml/
     * region
     * .xml:province
     * ,city,code
     * City
     * localRegion =
     * getCityInfo
     * (province,
     * cityName); if
     * (localRegion
     * == null)
     * return;
     */
        this.mLocated = true;
        this.mCurrentRegion = new City();

    }

    /**
     * 设置手动选择的城市
     */
    public void setSelectedCity(SwitchCity city) {
        City sCity = new City();
        sCity.setCityCode(city.getCityCode());
        sCity.setCityName(city.getCityName());
        this.mSelectedRegion = sCity;
        // 若更新成功则
        CityManager.this.mPreferenceManager.updateCity(city.getCityCode());
        CityManager.this.mSelected = true;

    }

    public City getSelectedRegion() {
        return mSelectedRegion;
    }

    //
    public int getSelectCityCode() {
        return mPreferenceManager.getCity();
    }

    /**
     * 更新城市服务器
     **/
    public void updateLocalServer(AsyncCallbackHandler paramAsyncCallbackHandler) {
        updateLocalServer(this.mSelectedRegion, paramAsyncCallbackHandler);
    }

    private void updateLocalServer(City paramRegion,
                                   AsyncCallbackHandler paramAsyncCallbackHandler) {
        // /根据城市编号，获取服务器url 并更新保存
    }

    public void updateCityServer(boolean flag, final Handler handler) {

        zdiden = true;
        if (flag) {
            zdiden = false;
            mPreferenceManager.updateStationVer(0);
            DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
            SQLiteDatabase db = data.getWritableDatabase();
            db.beginTransaction();
            db.delete("LSJL", null, null);
            db.delete("ZDXX", null, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            clearData();

        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
                clearData();//清空记录
                getHisData(data);//加载历史记录
                dbVer = getAndroidVersion();//服务器站点版本
                if (zdiden) {//没切换城市需要判断版本信息
                    System.out.println("需要判断版本");
                    int versor = mPreferenceManager.getStationVersion();//本地站点版本
                   Log.e("soso" ,"站点版本"+dbVer + "<>" + versor);
//                    if (dbVer <= versor) {//版本相同不需要下载
//                        loadZDDate(data);//加载本地站点信息
//                        handler.sendEmptyMessage(100);
//                        return;
//                    }
                }
                try {

                    TableUtils.clearTable(mDBHelper.getConnectionSource(), LineHistory.class);
                    TableUtils.clearTable(mDBHelper.getConnectionSource(), LineBean.class);
                } catch (SQLException localSQLException) {
                    localSQLException.printStackTrace();
                }

                handledata(data, handler);


            }
        }).start();
    }

    public LineBean getLineNameByLineCode(String lineCode) {
        LineBean line = null;
        for (int i = 0; i < allLine.size(); i++) {
            LineBean bean = allLine.get(i);
            if (bean.getLineCode().equals(lineCode)) {
                return bean;
            }
        }
        return line;
    }

    public List<LineBean> getLine() {
        return allLine;
    }

    /**
     * 获取全部线路信息
     */
    public void getAllLine() {
        // 3.查询sqlserver数据库结果集存入本地
        allLine = new ArrayList<LineBean>();
        String url = ConstData.URL + "!getLineInfo.shtml";
//		String url = ConstData.goURL + "/getLineList";
        RequestParams param = new RequestParams();


        RequstClient.post(url,
                param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
                        new LoadDatahandler() {
                            @Override
                            public void onStart() {
                                super.onStart();

                                Log.d("getLineInfo:", "get line  data");
                            }

                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                try {
//							JSONArray lineArr = new JSONArray(data.toString());
                                    JSONObject json = new JSONObject(data.toString());
                                    JSONArray lineArr = json.getJSONArray("lineList");
                                    Log.e(null, "这里的切换城市获取的线路" + lineArr);
                                    if (lineArr != null) {

                                        if (allLine == null) {
                                            allLine = new ArrayList<LineBean>();

                                        }
                                        allLine.clear();


                                        for (int t = 0; t < lineArr.length(); t++) {

                                            LineBean lb = new LineBean();
                                            lb.setLineCode(lineArr.getJSONObject(t)
                                                    .getString("lineCode"));//no
                                            lb.setLineName(lineArr.getJSONObject(t)
                                                    .getString("lineName"));//name
                                            if ((ConstData.URL).equals("http://218.59.157.109:4001/sdhyschedule/PhoneQueryAction")) {
                                                lb.setDwbhs(lineArr.getJSONObject(t)
                                                        .getString("dwbhs"));
                                            }

                                            JSONObject jjson = lineArr.getJSONObject(t);

                                            if (lineArr.getJSONObject(t).has("webIp")) {
                                                lb.setWebIp(lineArr.getJSONObject(t)
                                                        .getString("webIp"));//name
                                                lb.setBsPort(lineArr.getJSONObject(t)
                                                        .getString("bsPort"));//name
                                                lb.setSocketPort(lineArr.getJSONObject(t)
                                                        .getString("socketPort"));//name


                                                Log.e(null, "`````````````" + lineArr.getJSONObject(t)
                                                        .getString("socketPort"));
                                            }

                                            allLine.add(lb);

                                        }
                                        if (allLine.size() > 0) {

//									if((lineArr.getJSONObject(0).getString("webIp"))!=null){
//										if(!(lineArr.getJSONObject(0).getString("webIp")).equals("")){
//											SwitchCity sc = new SwitchCity();
//											sc.setIp(lineArr.getJSONObject(0).getString("webIp"));
//											sc.setGoServerPort(lineArr.getJSONObject(0).getString("socketPort"));
//											sc.setUrl("http://"  + lineArr.getJSONObject(0).getString("webIp")+":" +lineArr.getJSONObject(0).getString("bsPort")  +"/sdhyschedule/PhoneQueryAction");
//										}else {
//											Log.e(null, "`````````````"+lineArr.getJSONObject(0)
//											.getString("webIp"));
//										}
//									}
//
//									Log.e(null, "`````````````"+lineArr.getJSONObject(0)
//									.getString("webIp"));
//									Log.e(null, "`````````````"+allLine.get(0).getWebIp());
                                            updateLocalAllLine();
                                        }

                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.e("数据返回错误", "未解析返回的线路站点");
                                    Log.e("数据返回错误", e.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(String error, String message) {
                                super.onFailure(error, message);
                                Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        }));

    }

    public void updateLocalAllLine() {

        synchronized (this) {
            DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
            SQLiteDatabase db = data.getWritableDatabase();
            db.delete("LSXL", null, null);//删除历史线路
            db.beginTransaction();
            for (int i = 0; i < allLine.size(); i++) {
                LineBean bean = allLine.get(i);
                ContentValues value = new ContentValues();
                value.put("xlbh", bean.getLineCode());
                value.put("xlmc", bean.getLineName());
                Log.e(null, "%%%%%%444444444444%%%%%^^^^^^^^^^^");
                value.put("webIp", bean.getWebIp());
                value.put("bsPort", bean.getBsPort());
                value.put("socketPort", bean.getSocketPort());
             Log.e(null, "%%%%%%555555555%%%%%^^^^^^^^^^^");
                db.insert("LSXL", null, value);//写入线路
            }
            Log.e(null, "%%%%%%%%%%%^^^^^^^^^^^");
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            data.close();

        }
    }

    /**
     * 获取本地上次全部线路信息
     */
    public void getLocalAllLine() {
        // 3.查询sqlserver数据库结果集存入本地
        allLine = new ArrayList<LineBean>();

        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = db.query("LSXL", new String[]{"xlbh,xlmc,webIp,bsPort,socketPort"}, null, null, null, null, null);
        while (cur.moveToNext()) {
            LineBean bean = new LineBean();
            bean.setLineCode(cur.getString(cur.getColumnIndex("xlbh")));
            bean.setLineName(cur.getString(cur.getColumnIndex("xlmc")));

            bean.setWebIp(cur.getString(cur.getColumnIndex("webIp")));
            bean.setBsPort(cur.getString(cur.getColumnIndex("bsPort")));
            bean.setSocketPort(cur.getString(cur.getColumnIndex("socketPort")));

            allLine.add(bean);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        cur.close();
        db.close();


    }

    // //获取数据
    public Integer getAndroidVersion() {
        DBHandler db = new DBHandler();
        String Url = ConstData.URL + "!getVersion.shtml";
        String vers = db.getVerson(Url, "13");
        if (vers.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(vers);
        }
    }

    //清空数据
    public void clearData() {
        ConstData.homeAddr = "";
        ConstData.workAddr = "";
        ConstData.homeAddr2 = "";
        ConstData.workAddr2 = "";
        ConstData.hisAddr = new HashSet();
    }

    //查询历史记录
    public void getHisData(DataBase data) {
        Set set = new HashSet();
        SQLiteDatabase db = data.getReadableDatabase();
        db.beginTransaction();
        Cursor curor = db.query(true, "LSJL", new String[]{"zdname,zdtype"}, null, null, null, null, null, null);
        while (curor.moveToNext()) {
            String lx = curor.getString(curor.getColumnIndex("zdtype"));
            String zd = curor.getString(curor.getColumnIndex("zdname"));
            if (lx.equals("1")) {
                set.add(zd);
            }
            if (lx.equals("2")) {
                ConstData.homeAddr = zd;
            }
            if (lx.equals("3")) {
                ConstData.workAddr = zd;
            }
            if (lx.equals("22")) {
                ConstData.homeAddr2 = zd;
            }
            if (lx.equals("23")) {
                ConstData.workAddr2 = zd;
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        curor.close();
        db.close();
        ConstData.hisAddr = set;
    }

    //加载本地站点信息
    public void loadZDDate(DataBase data) {
        List<ZDXX> list = new ArrayList();
        SQLiteDatabase db = data.getReadableDatabase();
        db.beginTransaction();
        Cursor cur = db.query("ZDXX", new String[]{"line,linename,zd,zdname,sxx,jd,wd"}, null, null, null, null, null);
        while (cur.moveToNext()) {
            ZDXX zdxx = new ZDXX();
            zdxx.setZdname(cur.getString(cur.getColumnIndex("zdname")));

            if (cur.getColumnIndex("linename") == -1) {
                zdxx.setXlname("");
            } else {
                zdxx.setXlname(cur.getString(cur.getColumnIndex("linename")));
            }
            zdxx.setXl(Integer.parseInt(cur.getString(cur.getColumnIndex("line"))));
            zdxx.setSxx(Integer.parseInt(cur.getString(cur.getColumnIndex("sxx"))));
            zdxx.setJd(Integer.parseInt(cur.getString(cur.getColumnIndex("jd"))));
            zdxx.setWd(Integer.parseInt(cur.getString(cur.getColumnIndex("wd"))));
            zdxx.setZd(Integer.parseInt(cur.getString(cur.getColumnIndex("zd"))));
            list.add(zdxx);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        ConstData.list = list;
        cur.close();
        db.close();
    }


    //处理数据
    public void handledata(DataBase data, Handler handler2) {
        synchronized (this) {
            SQLiteDatabase db = data.getWritableDatabase();
            db.delete("LSJL", null, null);//删除历史站点
            db.delete("ZDXX", null, null);// 删除全部站点

            DBHandler handler = new DBHandler();
            String url = ConstData.URL + "!getZDXX.shtml";
            Log.e(null, "获取站点信息数据" + ConstData.URL + "!getZDXX.shtml");

            Log.d("getzdxx", url);
            staList = handler.getResult(url);
            ConstData.list = staList;
            Log.e(null, "获取数据完毕" + staList.size());
            System.out.println("获取数据完毕" + staList.size());
            db.beginTransaction();
            for (int i = 0; i < staList.size(); i++) {
                ZDXX zd = staList.get(i);
                ContentValues value = new ContentValues();
                value.put("line", zd.getXl());
                value.put("linename", zd.getXlname());
                value.put("zd", zd.getZd());
                value.put("zdname", zd.getZdname());
                value.put("sxx", zd.getSxx());
                value.put("jd", zd.getJd());
                value.put("wd", zd.getWd());
                db.insert("ZDXX", null, value);//写入站点
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            //写版本信息
            mPreferenceManager.updateStationVer(dbVer);
            db.close();
            data.close();
            System.out.println("写入完毕");
            handler2.sendEmptyMessage(100);
        }

    }

    /* 加载主界面设置
     * */
    public void LoadInterface(boolean flag) throws SQLException, MalformedURLException {


        //ConstData.tmURL = "http://192.168.2.136:8080";
        String strUrl = ConstData.tmURL + "/LineServer/docManage/DocManage!jsonModule.action";
        //String strUrl = "http://192.168.2.58:8080/LineServer/docManage/DocManage!jsonModule.action";

        if (flag) {
            getmPreferenceManager().updateADVersion(0);
            ConstData.adPageMap.clear();
        }

        List<InterfaceBean> list;
        list = dbHandler.getInterface(strUrl, mPreferenceManager);


        if (flag) {

            ConstData.bUpdateAD = true;
            mDBHelper.updateInterface(list, true);

        } else {
            if (list == null) {
                return;
            } else {
                mDBHelper.updateInterface(list, false);
            }
        }

//		for (int i=0;i<list.size();i++)
//		{
//			if (list.get(i).getIconType().equals("2"))
//			{
//				InterfaceBean bean = list.get(i);
//				if (adImageIsExit(bean.getPath()) == false)
//		        {
//					Bitmap bmp = getHttpBitmap(bean.getWebURL(),bean.getPath());
//
//		        }
//			}
//
//		}

    }

    //获取数据界面信息
    public List<InterfaceBean> getInterface() throws SQLException {
        //List<InterfaceBean> list = new ArrayList<InterfaceBean>();
        RuntimeExceptionDao<InterfaceBean, ?> dao = this.mDBHelper.getRuntimeExceptionDao(InterfaceBean.class);
        QueryBuilder localQueryBuilder = dao.queryBuilder();
        localQueryBuilder.where().eq("iconType", "0").or().eq("iconType", "1");
        //List<InterfaceBean> list = localQueryBuilder.query();
        List<InterfaceBean> list = dao.query(localQueryBuilder.prepare());

        return list;
    }

    //获取广告界面信息
    public List<AdvertBean> getAdvertInfo() throws SQLException {
        //List<InterfaceBean> list = new ArrayList<InterfaceBean>();
        RuntimeExceptionDao<AdvertBean, ?> dao = this.mDBHelper.getRuntimeExceptionDao(AdvertBean.class);
        //List<InterfaceBean> list = localQueryBuilder.query();
        ArrayList<AdvertBean> list = (ArrayList<AdvertBean>) dao.queryForAll();
        ConstData.adPageMap.clear();
        for (int i = 0; i < list.size(); i++) {
            AdvertBean bean = list.get(i);
            ConstData.adPageMap.put(bean.getPage(), bean);
        }

        return list;
    }

    public PreferencesHelper getmPreferenceManager() {
        return mPreferenceManager;
    }

    public void setmPreferenceManager(PreferencesHelper mPreferenceManager) {
        this.mPreferenceManager = mPreferenceManager;
    }

    public List<InterfaceBean> getAdvertisement() throws SQLException {
        //List<InterfaceBean> list = new ArrayList<InterfaceBean>();
        RuntimeExceptionDao<InterfaceBean, ?> dao = this.mDBHelper.getRuntimeExceptionDao(InterfaceBean.class);
        QueryBuilder localQueryBuilder = dao.queryBuilder();
        localQueryBuilder.where().eq("iconType", "2");
        //List<InterfaceBean> list = localQueryBuilder.query();
        List<InterfaceBean> list = dao.query(localQueryBuilder.prepare());

        return list;
    }

    public boolean adImageIsExit(String imagePath) throws SQLException {
        boolean flag = false;

//		RuntimeExceptionDao<InterfaceBean, ?> dao = this.mDBHelper.getRuntimeExceptionDao(InterfaceBean.class);
//		QueryBuilder localQueryBuilder = dao.queryBuilder();
//		localQueryBuilder.where().eq("iconType", "2").and().eq("path", imagePath);
//		//List<InterfaceBean> list = localQueryBuilder.query();
//		List<InterfaceBean> list = dao.query(localQueryBuilder.prepare());
//		if (list.size()>0)
//		{

//			flag = true;
//		}
        return flag;
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public Bitmap getHttpBitmap(String url, String savePath) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();

            saveImage(is, savePath);
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    public void saveImage(InputStream in, String filePath) {
        int index = filePath.lastIndexOf("/");
        String folder = filePath.substring(0, index);
        String fileName = filePath.substring(index + 1);

        File f = new File(folder, fileName);
        if (f.exists()) {
            f.delete();
        }

        try {

            FileOutputStream out = new FileOutputStream(f);
            byte[] buf = new byte[1024];
            int len = 0;
            // 将读取后的数据放置在内存中---ByteArrayOutputStream
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            out.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void LoadNoticeLastDate() throws ParseException {
        ConstData.bUpdateNotice = false;
        //String url = "http://192.168.2.202:7006"  + "/GetInfo/NoticeLastDate";
        String url = ConstData.goURL + "/GetInfo/NoticeLastDate";
        Log.e("广告时间", url);
        String strDate = dbHandler.getNoticeLastDate(url);
        if (strDate == null || strDate.equals("")) {
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(strDate);
        String oldStr = mPreferenceManager.getNoticeLastDate();
        if (!oldStr.equals("")) {
            Date date2 = df.parse(oldStr);
            if (date2.getTime() < date1.getTime()) {
                ConstData.bUpdateNotice = true;
                ConstData.noticeDate = strDate;
            }
        } else {
            ConstData.bUpdateNotice = true;
            ConstData.noticeDate = strDate;
        }
    }

}
