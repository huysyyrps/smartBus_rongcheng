package main.smart.common.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.smart.advert.bean.AdvertInfo;
import main.smart.bus.bean.AdvertBean;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.BusTime;
import main.smart.bus.bean.FavorLineBean;
import main.smart.bus.bean.InterfaceBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.LineHistory;
import main.smart.bus.bean.StationBean;
import main.smart.common.SmartBusApp;
import main.smart.common.bean.City;
import main.smart.common.bean.SwitchCity;
//ORMLite很有趣的是它存在一个RuntimeExceptionDao这个东西是针对JDBC和一些其他的SQL的。
//对于Android平台主要是处理了过多繁琐的try…catch…的书写，和一些语法错误带来的崩溃，建议使用。

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final int Entry = 0;
	private static DBHelper databaseHelper;

	// 构造函数创建数据库
	public DBHelper(Context paramContext) {
		super(paramContext, "smartBus.db", null, 12);
	}

	public static DBHelper getInstance() {
		if (databaseHelper == null)
			databaseHelper = (DBHelper) OpenHelperManager.getHelper(
					SmartBusApp.getInstance(), DBHelper.class);
		return databaseHelper;
	}

	/**
	 * 数据库初始化建表语句
	 * 
	 * **/
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connsource) {
		// TODO Auto-generated method stub
		try {
			Log.i(DBHelper.class.getName(), "onCreate");
			TableUtils.createTableIfNotExists(connsource, City.class);
			TableUtils.createTableIfNotExists(connsource,
					Class.forName("main.smart.bus.bean.LineBean"));
			TableUtils.createTableIfNotExists(connsource,
					Class.forName("main.smart.bus.bean.BusBean"));
			TableUtils.createTableIfNotExists(connsource,
					Class.forName("main.smart.bus.bean.StationBean"));
			// create table of cityIp
			TableUtils.createTableIfNotExists(connsource, SwitchCity.class);
			/*
			 * TableUtils.createTableIfNotExists(connsource,
			 * Class.forName("main.smart.advert.bean.AdvertInfo"));
			 */
			TableUtils.createTable(connsource, LineHistory.class);
			TableUtils.createTableIfNotExists(connsource, FavorLineBean.class);
			// insert cityIp into table
			insertCityIPInfo();
			// 首末班发车时间
			TableUtils.createTableIfNotExists(connsource, BusTime.class);

			// 首页界面设置
			TableUtils.createTableIfNotExists(connsource, InterfaceBean.class);
			
			// 广告设置
			TableUtils.createTableIfNotExists(connsource, AdvertBean.class);
			// xml文件夹下region.xml内容读入数据库
			// CityManager.getInstance().loadCitiesToDB();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCityInfo(List<SwitchCity> list) throws SQLException {
		RuntimeExceptionDao dao = getSwitchDao();
		dao.deleteBuilder().delete();
		for (int i = 0; i < list.size(); i++) {

			SwitchCity sc = list.get(i);
			// 屏蔽淮南（340400）滨州（371602）马鞍山（340500）
			//阜阳（236000）肥城（271600）安庆（340800）
			if ( sc.getCityCode() == 340400 ||sc.getCityCode() == 371602 || sc.getCityCode() == 340500||sc.getCityCode()==271600||sc.getCityCode()==236000) {
				continue;
//				 
			}
			
			dao.createOrUpdate(sc);
		}

	}

	public void insertCityIPInfo() {
		RuntimeExceptionDao dao = getSwitchDao();

		SwitchCity sc21 = new SwitchCity();
		sc21.setCenterX("103.7362");
		sc21.setCenterY("29.6058");
		sc21.setIp("220.166.160.231");
		sc21.setGoServerPort("7006");
		sc21.setUrl("http://220.166.160.231:4001/sdhyschedule/PhoneQueryAction");
		sc21.setCityCode(511101);
		sc21.setCityName("乐山");

		SwitchCity sc20 = new SwitchCity();
		sc20.setCenterX("116.7585");
		sc20.setCenterY("36.2051");
		sc20.setIp("120.192.117.197");
		sc20.setGoServerPort("7006");
		sc20.setUrl("http://120.192.117.197:4001/sdhyschedule/PhoneQueryAction");
		sc20.setCityCode(370983);
		sc20.setCityName("肥城");

		// SwitchCity sc19 =new SwitchCity();
		// sc19.setCenterX("124.39045");
		// sc19.setCenterY("40.14802");
		// sc19.setIp("192.168.0.10");
		// sc19.setGoServerPort("7006");
		// sc19.setUrl("http://192.168.0.10:7080/sdhyschedule/PhoneQueryAction");
		// sc19.setCityCode(210600);
		// sc19.setCityName("丹东");

		SwitchCity sc18 = new SwitchCity();
		sc18.setCenterX("117.9302");
		sc18.setCenterY("37.4723");
		sc18.setIp("222.134.32.178");
		sc18.setGoServerPort("7006");
		sc18.setUrl("http://222.134.32.178:7001/sdhyschedule/PhoneQueryAction");
		sc18.setCityCode(371600);
		sc18.setCityName("滨州市");
		// 滕州交运 ok
		SwitchCity sc17 = new SwitchCity();
		sc17.setCenterX("117.146");
		sc17.setCenterY("35.163");
		sc17.setIp("60.214.128.166");
		sc17.setGoServerPort("7003");
		sc17.setUrl("http://60.214.128.166:7002/sdhyschedule/PhoneQueryAction");
		sc17.setCityCode(370481);
		sc17.setCityName("滕州");
		// 兖州 ok
		SwitchCity sc16 = new SwitchCity();
		sc16.setCenterX("116.8058");
		sc16.setCenterY("35.5785");
		sc16.setIp("111.17.210.151");
		sc16.setGoServerPort("7078");
		sc16.setUrl("http://111.17.210.151:7077/sdhyschedule/PhoneQueryAction");
		sc16.setCityCode(370882);
		sc16.setCityName("兖州");
		// laiwu
		// SwitchCity sc15 =new SwitchCity();
		// sc15.setCenterX("117.6817");
		// sc15.setCenterY("36.2368");
		// sc15.setIp("192.168.2.19");
		// sc15.setGoServerPort("7006");
		// sc15.setUrl("http://192.168.2.19:60010/sdhyschedule/PhoneQueryAction");
		// sc15.setCityCode(271100);
		// sc15.setCityName("莱芜市");
		// 德州
		SwitchCity sc14 = new SwitchCity();
		sc14.setCenterX("116.2774");
		sc14.setCenterY("37.2099");
		sc14.setIp("222.133.4.18");
		sc14.setGoServerPort("7006");
		sc14.setUrl("http://222.133.4.18:8080/sdhyschedule/PhoneQueryAction");
		sc14.setCityCode(371400);
		sc14.setCityName("德州市");
		sc14.setCityHelp("此系统版权属德州市公共汽车公司所有，数据仅作为乘客乘车查询，未经允许不可用于其他商业活动，否则追究其法律责任。如有商业合作意向，请致电：0534-2313816");
		// 晋中 ok
		SwitchCity sc13 = new SwitchCity();
		sc13.setCenterX("112.7382");
		sc13.setCenterY("37.6885");
		sc13.setGoServerPort("7006");
		sc13.setIp("60.223.230.147");
		sc13.setUrl("http://60.223.230.147:7001/sdhyschedule/PhoneQueryAction");
		sc13.setCityCode(140700);
		sc13.setCityName("晋中市");
		// 宿迁 ok
		SwitchCity sc0 = new SwitchCity();
		sc0.setCenterX("118.2909");
		sc0.setCenterY("34.0001");
		sc0.setIp("221.6.253.254");
		sc0.setGoServerPort("7006");
		sc0.setUrl("http://221.6.253.254:8070/sdhyschedule/PhoneQueryAction");
		sc0.setCityCode(321300);
		sc0.setCityName("宿迁市");
		// 济宁交运
		SwitchCity sc12 = new SwitchCity();
		sc12.setCenterX("116.7412");
		sc12.setCenterY("35.3829");
		sc12.setIp("218.59.157.109");
		sc12.setGoServerPort("7006");
		sc12.setUrl("http://218.59.157.109:4001/sdhyschedule/PhoneQueryAction");
		sc12.setCityCode(370800);
		sc12.setCityName("济宁市");

		// 阜新
		SwitchCity sc10 = new SwitchCity();
		sc10.setCenterX("121.7266");
		sc10.setCenterY("42.0015");
		sc10.setIp("60.18.152.34");
		sc10.setGoServerPort("7006");
		sc10.setUrl("http://60.18.152.34:8082/sdhyschedule/PhoneQueryAction");
		sc10.setCityCode(210900);
		sc10.setCityName("阜新市");
		//
		SwitchCity sc9 = new SwitchCity();
		sc9.setCenterX("120.2459");
		sc9.setCenterY("32.0638");
		sc9.setIp("58.222.208.138");
		sc9.setGoServerPort("7006");
		sc9.setUrl("http://58.222.208.138:4001/sdhyschedule/PhoneQueryAction");
		sc9.setCityCode(214500);
		sc9.setCityName("靖江市");
		SwitchCity sc8 = new SwitchCity();
		sc8.setCenterX("118.3102");
		sc8.setCenterY("29.7102");
		sc8.setIp("36.32.232.15");
		sc8.setGoServerPort("7006");
		sc8.setUrl("http://36.32.232.15:4001/sdhyschedule/PhoneQueryAction");
		sc8.setCityCode(341000);
		sc8.setCityName("黄山市");
		// huai nan ok
		// SwitchCity sc7 =new SwitchCity();
		// sc7.setCenterX("116.6957");
		// sc7.setCenterY("32.7762");
		// sc7.setIp("111.39.170.191");
		// sc7.setGoServerPort("4003");
		// sc7.setUrl("http://111.39.170.191:4001/sdhyschedule/PhoneQueryAction");
		// sc7.setCityCode(340400);
		// sc7.setCityName("淮南市");
		// cangzhou
		SwitchCity sc6 = new SwitchCity();
		sc6.setCenterX("116.8612");
		sc6.setCenterY("38.3119");
		sc6.setIp("221.195.69.109");
		sc6.setGoServerPort("7006");
		sc6.setUrl("http://221.195.69.109:8088/sdhyschedule/PhoneQueryAction");
		sc6.setCityCode(130900);
		sc6.setCityName("沧州市");

		SwitchCity sc2 = new SwitchCity();
		sc2.setCenterX("121.157902");
		sc2.setCenterY("30.045888");
		sc2.setIp("60.12.222.161");
		sc2.setGoServerPort("7006");
		sc2.setUrl("http://60.12.222.161:4001/sdhyschedule/PhoneQueryAction");
		sc2.setCityCode(330281);
		sc2.setCityName("余姚市");

		SwitchCity sc4 = new SwitchCity();
		sc4.setCityCode(341100);
		sc4.setCenterX("118.2273");
		sc4.setCenterY("32.3315");
		sc4.setIp("183.167.193.16");
		sc4.setGoServerPort("7006");
		sc4.setUrl("http://183.167.193.16:8070/sdhyschedule/PhoneQueryAction");
		sc4.setCityName("滁州市");
		// 371700 菏泽
		SwitchCity sc5 = new SwitchCity();
		sc5.setCenterX("115.4673");
		sc5.setCenterY("35.2421");
		sc5.setIp("120.192.74.58");
		sc5.setGoServerPort("7006");
		sc5.setUrl("http://120.192.74.58:8070/sdhyschedule/PhoneQueryAction");
		sc5.setCityCode(371700);
		sc5.setCityName("菏泽市");

		// SwitchCity sc23 =new SwitchCity();
		// sc23.setCityCode(340500);
		// sc23.setCenterX("102.1364");
		// sc23.setCenterY("38.4012");
		// sc23.setGoServerPort("5500");
		// sc23.setIp("123.232.38.10");
		// sc23.setUrl("http://123.232.38.10:4005/sdhyschedule/PhoneQueryAction");
		// sc23.setCityName("金昌");

		SwitchCity sc22 = new SwitchCity();
		sc22.setCityCode(340500);
		sc22.setCenterX("116.788");
		sc22.setCenterY("33.9433");
		sc22.setGoServerPort("7006");
		sc22.setIp("111.38.216.169");
		sc22.setUrl("http://111.38.216.169:4001/sdhyschedule/PhoneQueryAction");
		sc22.setCityName("淮北");

		SwitchCity sc24 = new SwitchCity();
		sc24.setCityCode(273500);
		sc24.setCenterX("117.1243");
		sc24.setCenterY("35.3474");
		sc24.setGoServerPort("7006");
		sc24.setIp("218.201.187.76");
		sc24.setUrl("http://218.201.187.76:4001/sdhyschedule/PhoneQueryAction");
		sc24.setCityName("邹城");

		SwitchCity sc25 = new SwitchCity();
		sc25.setCityCode(264300);
		sc25.setCenterX("122.4512");
		sc25.setCenterY("37.1013");
		sc25.setGoServerPort("7006");
		sc25.setIp("222.135.92.18");
		sc25.setUrl("http://222.135.92.18:4001/sdhyschedule/PhoneQueryAction");
		sc25.setCityName("荣成");

		SwitchCity sc26 = new SwitchCity();
		sc26.setCityCode(628000);
		sc26.setCenterX("105.8398");
		sc26.setCenterY("32.4378");
		sc26.setGoServerPort("7006");
		sc26.setIp("221.10.187.49");
		sc26.setUrl("http://221.10.187.49:8081/sdhyschedule/PhoneQueryAction");
		sc26.setCityName("广元");
		
		
		dao.createOrUpdate(sc14);
		dao.createOrUpdate(sc21);
		dao.createOrUpdate(sc20);
		dao.createOrUpdate(sc16);
		// dao.createOrUpdate(sc19);
		dao.createOrUpdate(sc17);
		dao.createOrUpdate(sc18);
		dao.createOrUpdate(sc0);
		// dao.createOrUpdate(sc15);
		dao.createOrUpdate(sc14);
		dao.createOrUpdate(sc13);
		dao.createOrUpdate(sc12);
		dao.createOrUpdate(sc10);
		dao.createOrUpdate(sc9);
		dao.createOrUpdate(sc8);
		// dao.createOrUpdate(sc7);
		dao.createOrUpdate(sc6);
		dao.createOrUpdate(sc5);
		dao.createOrUpdate(sc2);
		dao.createOrUpdate(sc26);

	}

	/**
	 * 数据库 更新语句
	 * 
	 * **/
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1,
			int oldversion, int newversion) {
		// TODO Auto-generated method stub
		if (newversion >= 9) {
			Cursor cursor = db.rawQuery("SELECT * FROM phone_switchcity", null);
			int index = cursor.getColumnIndex("cityHelp");
			if (index == -1) {
				db.execSQL("ALTER TABLE phone_switchcity ADD cityHelp default ''");
			}
		}
		if (newversion >= 10) {

			try {
				TableUtils.createTableIfNotExists(arg1, FavorLineBean.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (newversion >= 11) {

			try {
				TableUtils.createTableIfNotExists(arg1, InterfaceBean.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (newversion >= 12)
		{
			try {
				TableUtils.createTableIfNotExists(arg1, AdvertBean.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取处理 RuntimeExceptionDao
	 * */
	public RuntimeExceptionDao<City, Integer> getCityDBDao() {
		RuntimeExceptionDao<City, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(City.class);
		return simpleDao;
	}

	public RuntimeExceptionDao<LineBean, Integer> getLineDBDao() {
		RuntimeExceptionDao<LineBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(LineBean.class);
		return simpleDao;
	}

	public RuntimeExceptionDao<BusBean, Integer> getBusDBDao() {
		RuntimeExceptionDao<BusBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(BusBean.class);
		return simpleDao;
	}

	public RuntimeExceptionDao<StationBean, Integer> getStationDBDao() {
		RuntimeExceptionDao<StationBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(StationBean.class);
		return simpleDao;
	}

	public RuntimeExceptionDao<AdvertInfo, Integer> getAdvertDBDao() {
		RuntimeExceptionDao<AdvertInfo, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(AdvertInfo.class);
		return simpleDao;
	}

	public RuntimeExceptionDao<LineHistory, Integer> getLineHisDBDao() {
		RuntimeExceptionDao<LineHistory, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(LineHistory.class);
		return simpleDao;
	}

	// switchCity dao
	public RuntimeExceptionDao<SwitchCity, Integer> getSwitchDao() {
		RuntimeExceptionDao<SwitchCity, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(SwitchCity.class);
		return simpleDao;
	}

	// 收藏线路
	public RuntimeExceptionDao<FavorLineBean, Integer> getFavorLineDao() {
		RuntimeExceptionDao<FavorLineBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(FavorLineBean.class);
		return simpleDao;
	}

	// InterfaceBean dao
	public RuntimeExceptionDao<InterfaceBean, Integer> getInterfaceDao() {
		RuntimeExceptionDao<InterfaceBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(InterfaceBean.class);
		return simpleDao;
	}
	
	// AdvertBean dao
	public RuntimeExceptionDao<AdvertBean, Integer> getAdvertDao() {
		RuntimeExceptionDao<AdvertBean, Integer> simpleDao = null;
		simpleDao = getRuntimeExceptionDao(AdvertBean.class);
		return simpleDao;
	}

	public void close() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null; 
		}
		super.close();
	}

	public void updateInterface(List<InterfaceBean> list,boolean flag) throws SQLException {
		RuntimeExceptionDao dao = getInterfaceDao();
		QueryBuilder localQueryBuilder = dao.queryBuilder();
		dao.deleteBuilder().delete();
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++) {
			
				dao.createOrUpdate(list.get(i));
			}
		}
		
		RuntimeExceptionDao<AdvertBean, ?> dao2 = getAdvertDao();
		QueryBuilder queryBuilder = dao2.queryBuilder();
	
		dao2.deleteBuilder().delete();
		if (ConstData.adPageMap.size()>0)
		{
			
			Iterator iter = ConstData.adPageMap.entrySet().iterator();
			while (iter.hasNext())
			{
				Map.Entry<String, AdvertBean> entry = (Map.Entry<String, AdvertBean>) iter.next();
			    AdvertBean bean = entry.getValue();
			    dao2.createOrUpdate(bean);
			}
		}

	}

}
