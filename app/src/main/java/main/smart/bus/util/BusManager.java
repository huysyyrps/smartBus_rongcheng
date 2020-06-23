package main.smart.bus.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.FavorLineBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.LineHistory;
import main.smart.bus.bean.StationBean;
import main.smart.common.SmartBusApp;
import main.smart.common.http.DataBase;
import main.smart.common.util.AsyncCallbackHandler;
import main.smart.common.util.CityManager;
import main.smart.common.util.DBHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

/**
 * ����������
 */
public class BusManager {
    private static BusManager mInstance;
    private BusMonitor mBusMonitor = new BusMonitor(this);
    private BusTracker mBusTracker = new BusTracker(this);
    private CityManager mCityMan = CityManager.getInstance();
    private DBHelper mDBHelper = DBHelper.getInstance();
    private Object mLock = new Object();
    private LineBean mSelectedLine;//ѡ�е���·
    private List<Integer> mSoiList = new LinkedList();
    private List<OnSoiChangedListener> mSoiListeners = new ArrayList();

    /**
     * ������
     **/
    public static BusManager getInstance() {
        if (mInstance == null)
            mInstance = new BusManager();
        return mInstance;
    }

    //////����ʵʱ������
    public void addBusMonitorInfoListener(BusMonitor.BusMonitorInfoListener monitorLis) {
        this.mBusMonitor.addBusMonitorInfoListener(monitorLis);
    }

    public void removeBusMonitorInfoListener(BusMonitor.BusMonitorInfoListener monitorLis) {
        this.mBusMonitor.removeBusMonitorInfoListener(monitorLis);
    }

    public void addOnSoiChangedListener(OnSoiChangedListener paramOnSoiChangedListener) {
        this.mSoiListeners.add(paramOnSoiChangedListener);
    }

    public void removeOnSoiChangedListener(OnSoiChangedListener paramOnSoiChangedListener) {
        this.mSoiListeners.remove(paramOnSoiChangedListener);
    }

    /**
     * ����/ֹͣ ����ʵʱ����
     */
    public void startMonitor() {
        this.mBusMonitor.startWatch();
    }

    public void stopMonitor() {
        this.mBusMonitor.stopWatch();
        //this.mBusMonitor.removeAllBusBusMonitorInfoListener();
    }

    /**
     * ���㳵Ҫ�����վ��
     */
    private int calcBusArrvStation(BusBean paramBusInfo) {
        int stationOrder = 0;//վ�����
        return stationOrder;
    }

    /**
     * ���㳵��λ��
     * ֱ���ϵ�λ��
     */
    private float calcBusPositionNum(BusBean paramBusInfo) {
        float position = 0;
        return position;
    }

    /**
     * ����վ���λ��
     * ֱ���ϵ�λ��
     */

    public float calcDistanceToStation(BusBean paramBusInfo, int paramInt) {
        float position = 0;
        return position;
    }

    /**
     * ���� �������·��Ϣ�Ƿ��ѱ����
     **/
    public List<LineBean> getLocalLine(LineBean bean) {
        List<LineBean> lineList = new ArrayList<LineBean>();
        try {
            //	lineList=this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryForMatching(newbean);
            /**/
            QueryBuilder builder = this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryBuilder();
            builder.where().eq("lineCode", bean.getLineCode()).and().eq("lineId", bean.getLineId());
            lineList = builder.query();
			 /*	 GenericRawResults<String[]> rawResults =

					 dao.queryRaw(

					 "select lineCode,lineId from lineinfo where lineCode='"+bean.getLineCode()+"' and lineId="+bean.getLineId());

					 // there should be 1 result

					 List<String[]> results = rawResults.getResults();

					 // the results array should have 1 value
                          if(results.size()!=0){
                        	  String[] resultArray = results.get(0);
             				 System.out.println("Account-id 10 has " + resultArray[0] + " orders");

                          }*/


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lineList;
    }

    /**
     * ��ȡ��ʷ������¼
     **/
    public List<LineBean> getBusLineHistory() {
        List<LineBean> hisLineList = new ArrayList<LineBean>();
        LineHistory bean = new LineHistory();
        bean.setCityCode(mCityMan.getSelectedCityCode());
        try {
            List<LineHistory> hisList = this.mDBHelper.getRuntimeExceptionDao(LineHistory.class).queryForMatching(bean);
            if (hisList != null && hisList.size() != 0) {
                for (int i = 0; i < hisList.size(); i++) {
                    LineBean lb = hisList.get(i).getBusLine();
                    if (lb != null)
                        hisLineList.add(lb);
                }
            }
        } catch (Exception localSQLException) {
        }
        return hisLineList;
    }

    /**
     * ��� ��·������ʷ��¼
     */

    public void clearBusLineHistory() {
        try {
            TableUtils.clearTable(this.mDBHelper.getConnectionSource(), LineHistory.class);
            return;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
    }

    /***
     * ��������ڱ��ص� ��·վ����Ϣ
     * */
    public void clearBusStationList() {
        try {
            TableUtils.clearTable(this.mDBHelper.getConnectionSource(), LineBean.class);
            return;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
    }

    /**
     * ��� վ�������ʷ��¼
     */
    public void clearBusStationHistory() {
        try {
            TableUtils.clearTable(this.mDBHelper.getConnectionSource(), StationBean.class);
            return;
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
    }

    /**
     * ��ȡ���ݿ��иó������е���·������
     */
    private List<LineHistory> getAllBusLineInDB() {
        return this.mDBHelper.getRuntimeExceptionDao(LineHistory.class).queryForEq("cityCode", this.mCityMan.getSelectedCityCode());
    }

    /**
     * ��ȡ���ݿ��иó������е���·
     */
    private boolean getBusLineFromDB(String paramString, AsyncCallbackHandler paramAsyncCallbackHandler) {
        RuntimeExceptionDao<LineBean, Integer> lineDao = mDBHelper.getLineDBDao();
        LineBean localBusLine = lineDao.queryForId(Integer.parseInt(paramString));

        if (localBusLine == null)
            return false;
        else {
            paramAsyncCallbackHandler.onSuccess(0, localBusLine);
            return true;
        }

    }

    private List<LineBean> getAllLineFromDB() {
        return this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryForEq("lineId", "0");
    }

    public void setSelectedLine(LineBean paramBusLine) {
        this.mSelectedLine = paramBusLine;
    }

    //��ȡѡ����·
    public LineBean getSelectedLine() {
        return this.mSelectedLine;
    }

    /**
     * ������д����·ģ����ѯ
     * return :lineCode,lineName,upDown,beginStation,endStation
     **/
    public List<LineBean> getLineListByLineCode(String lineCode) {
        List<LineBean> lineList = new ArrayList<LineBean>();
        LineBean bean = new LineBean();
        try {
            bean.setLineCode(lineCode);
            //2.��ѯ�������ݿ⣬�����������ѯsqlserver���ݿ�
            //	  lineList=this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryForMatching(bean);
            QueryBuilder builder = this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryBuilder();
            builder.where().like("lineName", lineCode + "%");
            PreparedQuery<LineBean> pq = builder.prepare();
            lineList = this.mDBHelper.getRuntimeExceptionDao(LineBean.class).query(pq);

            //��ѯ��·����
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lineList;
    }

    public List<LineBean> getLineByLineCode(String lineCode) {
        List<LineBean> lineList = new ArrayList<LineBean>();
        LineBean bean = new LineBean();
        try {
            bean.setLineCode(lineCode);
            //2.��ѯ�������ݿ⣬�����������ѯsqlserver���ݿ�
            lineList = this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryForMatching(bean);

            //��ѯ��·����
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lineList;
    }


//	  public List<LineBean> getLineBywebip(String lineCode){
//		  List<LineBean> lineweblist =new ArrayList<LineBean>();
//		  LineBean bean =new LineBean();
//		  try 
//		  {
//			  bean.setLineCode( lineCode);
//			  //2.��ѯ�������ݿ⣬�����������ѯsqlserver���ݿ�
//		 	  lineList=this.mDBHelper.getRuntimeExceptionDao(LineBean.class).queryForMatching(bean);
//		 
//						  //��ѯ��·����
//			} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  return lineweblist;
//	  }

    /**
     * ������·��ѯվ����Ϣ
     **/
    public List<StationBean> queryBusStation(String lineCode) {
        List<StationBean> stationList = new ArrayList<StationBean>();
        return stationList;
    }

    /**
     * ������·��Ϣ�뱾�����ݿ�
     */
    public void saveBusLine(LineBean line) {
        try {
            this.mDBHelper.getRuntimeExceptionDao(LineBean.class).createOrUpdate(line);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * �����ѯ��·��ʷ��¼
     */
    public void saveBusLineToHistory(LineBean paramBusLine) {
        LineHistory localBusLineRecord = new LineHistory(new Date(), paramBusLine);
        try {
            LineHistory bean = new LineHistory();
            bean.setCityCode(mCityMan.getSelectedCityCode());
            //�鿴�Ѵ��ڵģ�Ϊʲô�治��ȥ
	  	  /*   LineBean lb =new LineBean();
	  		    	List<LineHistory>    hisList= this.mDBHelper.getRuntimeExceptionDao(LineHistory.class).queryForMatching(bean);
	  		    	 if(hisList!=null&&hisList.size()!=0){
	  		    		 for(int i=0;i<hisList.size();i++){
	  		    			   lb= hisList.get(i).getBusLine();
	  		    			 System.out.println("");
	  		    		 }
	  		    		paramBusLine.setStationSerial(lb.getStationSerial());
	  		    		   localBusLineRecord = new LineHistory(new Date(), paramBusLine); 		 
	  		   	 	DataBase data = new DataBase(SmartBusApp.getInstance(),"smartBus"); 
		  		    	SQLiteDatabase db = data.getWritableDatabase();
		  					ContentValues value = new ContentValues();
		  					value.put("activeTime", new Date().toString());
		  					value.put("lineHisId", paramBusLine.getGid());//վ�����
		  					value.put("cityCode", bean.getCityCode());
		  					db.insert("linehisinfo", null, value);
		  				 
		  				db.close();   */
            //		 this.mDBHelper.getRuntimeExceptionDao(LineHistory.class).createOrUpdate(localBusLineRecord);

            //    	 }else{
            this.mDBHelper.getRuntimeExceptionDao(LineHistory.class).createOrUpdate(localBusLineRecord);

            //  		    	 }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ�ղؼ�¼
     */
    public boolean deleteFavorLineInfo(String favorName) throws SQLException {

        RuntimeExceptionDao localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(FavorLineBean.class);

        DeleteBuilder localQueryBuilder = localRuntimeExceptionDao.deleteBuilder();

        localQueryBuilder.where().eq("cityCode", this.mCityMan.getSelectedCityCode());
        localQueryBuilder.where().eq("favorName", favorName);
        localQueryBuilder.delete();
        //localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.orderBy("activeTime", false).prepare()));

        return false;
    }

    /**
     * ��ȡ�ղؼ�¼
     */
    public List<FavorLineBean> getFavorLineInfo() {
        ArrayList localArrayList = new ArrayList();
        RuntimeExceptionDao localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(FavorLineBean.class);
        try {
            QueryBuilder localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
            localQueryBuilder.where().eq("cityCode", this.mCityMan.getSelectedCityCode());
            localArrayList.addAll(localQueryBuilder.query());
            //localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.orderBy("activeTime", false).prepare()));
        } catch (SQLException localSQLException) {

        }
        return localArrayList;
    }

    /**
     * �����ղ���Ϣ
     */
    public boolean saveFavorLineInfo(FavorLineBean bean) throws SQLException {

        RuntimeExceptionDao<FavorLineBean, ?> dao = this.mDBHelper.getRuntimeExceptionDao(FavorLineBean.class);
        List<FavorLineBean> list = dao.queryForEq("favorName", bean.getFavorName());
        if (list.size() == 0) {
            dao.createIfNotExists(bean);
            return true;
        }
        return false;

    }

    public List<String> FuzzyQueryStationName(String str) {
        ArrayList<String> zdList = new ArrayList<String>();
        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        //String name = "%%" + str + "%%";
        String sql = "select distinct zdname from ZDXX where zdname like '%" + str + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String zdname = cursor.getString(cursor.getColumnIndex("zdname"));
            zdList.add(zdname);
        }
        if (null != cursor) {
            cursor.close();
        }


//		  
//		  RuntimeExceptionDao<ZDXX, ?> localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(ZDXX.class);
//		  try
//		  {
//		      QueryBuilder<ZDXX, ?> localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
//		      localQueryBuilder.selectColumns("zdname").where().eq("zdname", "%" + str + "%");
//		      
//		      zdList.addAll(localQueryBuilder.query());
//		  }
//		  catch (SQLException localSQLException)
//		  {
//		    	
//		  }
        return zdList;
    }

    /**
     * ͨ��վ������ȡվ����Ϣ
     */
    public HashMap<String, String> getLineListByzdName2(String txt) {
        HashMap<String, String> list = new HashMap<String, String>();
        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        //String name = "%%" + str + "%%";
        String sql = "select distinct line,linename from ZDXX where zdname='" + txt + "' ";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String xl = cursor.getString(cursor.getColumnIndex("line"));
            String xlname = cursor.getString(cursor.getColumnIndex("linename"));
            list.put(xl, xlname);
        }
        if (null != cursor) {
            cursor.close();
        }
//		  RuntimeExceptionDao<ZDXX, ?> localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(ZDXX.class);
//		  try
//		  {
//		      QueryBuilder<ZDXX, ?> localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
//		      localQueryBuilder.where().eq("zdname", txt);
//		      zdList.addAll(localQueryBuilder.query());
//		      //localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.orderBy("activeTime", false).prepare()));
//		  }
//		  catch (SQLException localSQLException)
//		  {
//		    	
//		  }
        return list;
    }

    /**
     * ͨ��վ������ȡվ����Ϣ
     */
    public ArrayList<String> getLineListByzdName(String txt) {
        ArrayList<String> list = new ArrayList<String>();
        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        //String name = "%%" + str + "%%";
        String sql = "select distinct line from ZDXX where zdname='" + txt + "' ";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String xl = cursor.getString(cursor.getColumnIndex("line"));
            list.add(xl);
        }
        if (null != cursor) {
            cursor.close();
        }
//		  RuntimeExceptionDao<ZDXX, ?> localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(ZDXX.class);
//		  try
//		  {
//		      QueryBuilder<ZDXX, ?> localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
//		      localQueryBuilder.where().eq("zdname", txt);
//		      zdList.addAll(localQueryBuilder.query());
//		      //localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.orderBy("activeTime", false).prepare()));
//		  }
//		  catch (SQLException localSQLException)
//		  {
//		    	
//		  }
        return list;
    }

    /**
     * ͨ��վ������ȡ��·��Ϣ
     */
//	  public List<LineBean> getLineListByName(String txt)
//	  {
//		  ArrayList<LineBean> lineList = new ArrayList<LineBean>();
//		  List<ZDXX> zdList = getZDXXByName(txt);
//		  for (int i=0;i<zdList.size();i++)
//		  {
//			  LineBean bean = new LineBean();
//			  ZDXX zd = zdList.get(i);
//			  bean.setLineCode(IntToString(zd.getXl()));
//			  bean.setLineId(zd.getSxx());
//			  
//		  }
//		  return lineList;
//	  }
    private String IntToString(int xl) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * ��ȡվ���ѯ��ʷ��¼
     */
    public List<StationBean> getBusStationHistory() {
        ArrayList<StationBean> localArrayList = new ArrayList<StationBean>();
        RuntimeExceptionDao<StationBean, ?> localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(StationBean.class);
        try {
            QueryBuilder localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
            localQueryBuilder.where().eq("cityCode", this.mCityMan.getSelectedCityCode());
            localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.prepare()));
            return localArrayList;
        } catch (SQLException localSQLException) {
        }
        return localArrayList;
    }
//	  /**
//	   * ����վ����Ϣ
//	   * */
//	  public void saveBusStation(StationBean sta){
//		  RuntimeExceptionDao dao = this.mDBHelper.getRuntimeExceptionDao(StationBean.class);
//		  sta.generateGid();
//		  sta.setActiveTime(new Date());
//		  dao.createOrUpdate(sta);
//	  }

    /**
     * ���� վ���ѯ��ʷ��¼������
     */
    public void saveBusStationToHistory(StationBean paramBusStation) {
        RuntimeExceptionDao<StationBean, ?> localRuntimeExceptionDao = this.mDBHelper.getRuntimeExceptionDao(StationBean.class);
        try {
            QueryBuilder<StationBean, ?> builder = localRuntimeExceptionDao.queryBuilder();
            builder.where().eq("stationName", paramBusStation.getStationName()).and().eq("cityCode", this.mCityMan.getSelectedCityCode());
            ArrayList<StationBean> localArrayList = new ArrayList<StationBean>();
            localArrayList.addAll(localRuntimeExceptionDao.query(builder.prepare()));
            if (localArrayList.size() == 0) {
                paramBusStation.setId("1");
                localRuntimeExceptionDao.createOrUpdate(paramBusStation);
            }
        } catch (SQLException localSQLException) {
        }
        //paramBusStation.generateGid();
        //paramBusStation.setActiveTime(new Date());
        //if ((paramBusStation.getBusLineSerial() == null) && (paramBusStation.getBusLineList() != null) && (paramBusStation.getBusLineList().size() > 0))
        // paramBusStation.setBusLineSerial(Utils.objectToByte(paramBusStation.getBusLineList()));
        //dao.createOrUpdate(paramBusStation);
    }

    public List<Integer> getSoiList() {
        return this.mSoiList;
    }

    public void clearSoiList() {
        this.mSoiList.clear();
    }

    /**
     * ����֪ͨ soi�ı�
     **/
    public void notifySoiChanged() {
        this.mBusMonitor.postMonitor();
        Iterator localIterator = this.mSoiListeners.iterator();
        while (true) {
            if (!(localIterator.hasNext()))
                return;
            ((OnSoiChangedListener) localIterator.next()).onSoiChanged(this.mSoiList);
        }
    }

    ////------------������·��Ϣ----------------
    private boolean validateBusLineInfo(LineBean paramBusLine) {
        ArrayList localArrayList = paramBusLine.getStations();
        if (localArrayList == null)
            return false;
        Iterator localIterator = localArrayList.iterator();
        while (true) {
            while (!(localIterator.hasNext()))
                if (localArrayList.size() >= 1)
                    return true;
            StationBean localBusStation = (StationBean) localIterator.next();
            if ((localBusStation != null) && (localBusStation.getLat() != null) && (localBusStation.getLng() != null) && (localBusStation.getLat().doubleValue() != 0.0D) && (localBusStation.getLng().doubleValue() != 0.0D))
                continue;
            localIterator.remove();
        }
    }

    /***
     * ������ĩ�෢����
     *
     * ***/
    public boolean saveBusTime(BusTime bt) {
        RuntimeExceptionDao dao = this.mDBHelper.getRuntimeExceptionDao(BusTime.class);
        dao.createOrUpdate(bt);
        return true;
    }

    /***
     * ��ѯ��ĩ�෢����
     *
     * ***/
    public List<BusTime> getBusTime(BusTime bt) {
        RuntimeExceptionDao dao = this.mDBHelper.getRuntimeExceptionDao(BusTime.class);
        List<BusTime> list = new ArrayList<BusTime>();
        try {
            list = dao.queryForMatching(bt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("busMan", "��ѯsqlite��ĩ�෢��ʱ�����");
        }
        return list;
    }

    //ɾ������ʱ��
    public void deleteBusTime(BusTime bt) {
        RuntimeExceptionDao dao = this.mDBHelper.getRuntimeExceptionDao(BusTime.class);

        try {
            dao.delete(bt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("busMan", "ɾ��sqlite��ĩ�෢��ʱ�����");
        }


    }

    public static abstract interface OnSoiChangedListener {
        public abstract void onSoiChanged(List<Integer> paramList);
    }

    /*
     * ���ĳ��·����
     * **/
    public void deleteLineInfoByLineCode(LineBean lb) {
        RuntimeExceptionDao dao = this.mDBHelper.getRuntimeExceptionDao(LineBean.class);

        try {
            dao.delete(lb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("busMan", "ɾ��sqlite��·��Ϣ����");
        }
    }

    /**
     * ��ձ�������
     */
    public void deletelinedata() {
        mDBHelper.getWritableDatabase().delete("lineinfo", null, null);
        mDBHelper.getWritableDatabase().delete("linehisinfo", null, null);
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * ��ȡվ�������������·����List
     */
    public HashMap<String, String> getStationAllLineName2(String stationName) {

        HashMap<String, String> staLineList = new HashMap<String, String>();
        HashMap<String, String> codeList = getLineListByzdName2(stationName);
        Iterator iter = codeList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
            String lineCode = entry.getKey();
            String lineName = entry.getValue();
            if (lineName.equals("") || lineName == null) {
                return null;
            } else {
                staLineList.put(lineCode, lineName);
            }

        }

        return staLineList;

    }

    /**
     * ��ȡվ�������������·����List
     */
    public List<String> getStationAllLineName(String stationName) {
        List<String> staLineList = new ArrayList<String>();
        List<String> codeList = getLineListByzdName(stationName);
        for (int i = 0; i < codeList.size(); i++) {
            String lineCode = codeList.get(i);
            LineBean bean = this.mCityMan.getLineNameByLineCode(lineCode);
            if (bean != null) {
                staLineList.add(bean.getLineName());
            }
        }

        return staLineList;
    }

    /**
     * ͨ��վ������ȡվ����Ϣ
     */
    /**
     * ͨ��վ������ȡվ����Ϣ
     * */
    public ArrayList<String> getBeginAndEndStaion(String lineCode,int sxx)
    {
        ArrayList<String> list = new ArrayList<String>();
        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        //String name = "%%" + str + "%%";
        boolean bFirst = true;
        String sql = "select zdname from ZDXX where line='"+lineCode+"' and sxx='"+sxx+"' order by zd";
        Cursor cursor = db.rawQuery( sql, null);
        String zdmax = "";
        String zdmin = "";
        while (cursor.moveToNext())
        {
            String zdname = cursor.getString(0);

            if (bFirst )
            {
                zdmin = zdname;
                bFirst = false;
            }
            zdmax = zdname;
        }

        if (sxx == 0)
        {
            list.add(zdmin);
            list.add(zdmax);
        }
        else
        {
            list.add(zdmax);
            list.add(zdmin);
        }

        if (null != cursor) {
            cursor.close();
        }

        return list;
    }

    /**
     * ��ȡ����վ������
     */
    public ArrayList<String> getAllStationName() {
        ArrayList<String> list = new ArrayList<String>();
        DataBase data = new DataBase(SmartBusApp.getInstance(), "AppData");
        SQLiteDatabase db = data.getReadableDatabase();
        String sql = "select distinct zdname from ZDXX";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String zdname = cursor.getString(0);
            list.add(zdname);
        }

        return list;
    }
}
