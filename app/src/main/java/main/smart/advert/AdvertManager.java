package main.smart.advert;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.smart.advert.bean.AdvertInfo;
import main.smart.common.util.CityManager;
import main.smart.common.util.DBHelper;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

public class AdvertManager
{
  private static final int ADVERT_IMAGE_HIGH = 21;
  private static final int ADVERT_IMAGE_LOW = 23;
  private static final int ADVERT_IMAGE_MEDIUM = 22;
  private static CityManager mCityMan;
  private static DBHelper mDBHelper = DBHelper.getInstance();
  private static Object mLock;

  static
  {
    mCityMan = CityManager.getInstance();
    mLock = new Object();
  }
/**
 * ����洢�Ĺ����Ϣ
 * */
  private static void clearAdvertInDB()
  {
    try
    {
      mDBHelper.getRuntimeExceptionDao(AdvertInfo.class).deleteBuilder().delete();
      return;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }

  private static int getAdvertImageDensity(Context paramContext)
  {
    switch (paramContext.getResources().getDisplayMetrics().densityDpi)
    {
    default:
      return 21;
    case 240:
      return 21;
    case 160:
      return 22;
    case 120:
    }
    return 23;
  }
  public static List<AdvertInfo> queryAdvertRandom(int paramInt, long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
    	//��ѯ���==����
      RuntimeExceptionDao localRuntimeExceptionDao = mDBHelper.getRuntimeExceptionDao(AdvertInfo.class);
      QueryBuilder localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
      localQueryBuilder.where().eq("showLevel", Integer.valueOf(paramInt));
      localQueryBuilder.orderByRaw("RANDOM()").limit(Long.valueOf(paramLong));
      localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.prepare()));
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localArrayList;
  }
//��ѯ���==����
  public static void syncQueryAllAdverts(Context paramContext)
  {
    Object localObject1 = mLock;
    ArrayList localArrayList = new ArrayList();
    try
    {
    	 RuntimeExceptionDao localRuntimeExceptionDao = mDBHelper.getRuntimeExceptionDao(AdvertInfo.class);
         QueryBuilder localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
         localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.prepare()));

      mLock.wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    finally
    {
       
    }
  }
  /*
  public static void queryAllAdverts(Context paramContext, AsyncCallbackHandler paramAsyncCallbackHandler)
  {
    String str = AdvertUrls.getAdvertRestUrl(WBHttpRestClient.ServerType.LOCAL, AdvertUrls.QUERY_ALL_ADVERTS);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = mCityMan.getSelectedCityCode();
    arrayOfObject[1] = Integer.valueOf(getAdvertImageDensity(paramContext));
    WBHttpRestClient.get(String.format(str, arrayOfObject), new WBHttpResponseHandler(JSONObject.class, paramAsyncCallbackHandler)
    {
      public void onFailure(Throwable paramThrowable, String paramString)
      {
        this.val$callback.onFailure(0, paramString);
      }

      public void onSuccess(Object paramObject)
      {
        try
        {
          List localList = (List)JsonUtil.fromJson(((JSONObject)paramObject).get("advertisements").toString(), new TypeToken()
          {
          }
          .getType());
          AdvertManager.access$1();
          AdvertManager.access$2(localList);
          this.val$callback.onSuccess(0, localList);
          return;
        }
        catch (JSONException localJSONException)
        {
          onFailure(new JSONException("JSON parse Response exception"), "JSON parse Response exception");
        }
      }
    });
  }
  */
/***��ȡ����ͼƬ
  public static String getAdvertPicUrl(String paramString)
  {
    return AdvertUrls.getAdvertPicUrl(WBHttpRestClient.ServerType.LOCAL, paramString);
  }

  public static void obtainAdvertLevelDef(AsyncCallbackHandler paramAsyncCallbackHandler)
  {
    String str = AdvertUrls.getAdvertRestUrl(WBHttpRestClient.ServerType.LOCAL, AdvertUrls.QUERY_ADVERT_LEVELS);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = mCityMan.getSelectedCityCode();
    WBHttpRestClient.get(String.format(str, arrayOfObject), new WBHttpResponseHandler(JSONObject.class, paramAsyncCallbackHandler)
    {
      public void onFailure(Throwable paramThrowable, String paramString)
      {
        this.val$callback.onFailure(0, paramString);
      }

      public void onSuccess(Object paramObject)
      {
        try
        {
          List localList = (List)JsonUtil.fromJson(((JSONObject)paramObject).get("levels").toString(), new TypeToken()
          {
          }
          .getType());
          this.val$callback.onSuccess(0, localList);
          return;
        }
        catch (JSONException localJSONException)
        {
          onFailure(new JSONException("JSON parse Response exception"), "JSON parse Response exception");
        }
      }
    });
  }

  public static List<AdvertInfo> queryAdvertRandom(int paramInt, long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      RuntimeExceptionDao localRuntimeExceptionDao = mDBHelper.getRuntimeExceptionDao(AdvertInfo.class);
      QueryBuilder localQueryBuilder = localRuntimeExceptionDao.queryBuilder();
      localQueryBuilder.where().eq("showLevel", Integer.valueOf(paramInt));
      localQueryBuilder.orderByRaw("RANDOM()").limit(Long.valueOf(paramLong));
      localArrayList.addAll(localRuntimeExceptionDao.query(localQueryBuilder.prepare()));
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localArrayList;
  }



  private static void saveMultiAdvertToDB(List<AdvertInfo> paramList)
  {
    RuntimeExceptionDao localRuntimeExceptionDao = mDBHelper.getRuntimeExceptionDao(AdvertInfo.class);
    localRuntimeExceptionDao.callBatchTasks(new Callable(paramList, localRuntimeExceptionDao)
    {
      public Void call()
        throws Exception
      {
        Iterator localIterator = AdvertManager.this.iterator();
        while (true)
        {
          if (!(localIterator.hasNext()))
            return null;
          AdvertInfo localAdvertInfo = (AdvertInfo)localIterator.next();
          this.val$dao.createOrUpdate(localAdvertInfo);
        }
      }
    });
  }



  public static void syncUploadDeviceInfo(Context paramContext)
  {
    Object localObject1 = mLock;
    monitorenter;
    try
    {
      uploadDeviceInfo(paramContext, new AsyncCallbackHandler()
      {
        public void onFailure(int paramInt, String paramString)
        {
          synchronized (AdvertManager.mLock)
          {
            AdvertManager.mLock.notify();
            return;
          }
        }

        public void onSuccess(int paramInt, Object paramObject)
        {
          synchronized (AdvertManager.mLock)
          {
            AdvertManager.mLock.notify();
            return;
          }
        }
      });
      mLock.wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    finally
    {
      monitorexit;
    }
  }

  public static void uploadAdvertBrowseStats(List<AdvertBrowseInfo> paramList, AsyncCallbackHandler paramAsyncCallbackHandler)
  {
    AdvertBrowseStats localAdvertBrowseStats = new AdvertBrowseStats();
    localAdvertBrowseStats.udid = Utils.getDeviceId();
    localAdvertBrowseStats.browsingDurations = paramList;
    WBHttpRestClient.post(AdvertUrls.getAdvertRestUrl(WBHttpRestClient.ServerType.LOCAL, AdvertUrls.UPLOAD_ADVERT_BROWSE_STATS), localAdvertBrowseStats, new WBHttpResponseHandler(String.class, paramAsyncCallbackHandler)
    {
      public void onFailure(Throwable paramThrowable, String paramString)
      {
        if (this.val$callback == null)
          return;
        this.val$callback.onFailure(0, paramString);
      }

      public void onSuccess(Object paramObject)
      {
        if (this.val$callback == null)
          return;
        this.val$callback.onSuccess(0, paramObject);
      }
    });
  }

  public static void uploadAdvertClickStats(List<AdvertClickInfo> paramList, AsyncCallbackHandler paramAsyncCallbackHandler)
  {
    AdvertClickStats localAdvertClickStats = new AdvertClickStats();
    localAdvertClickStats.udid = Utils.getDeviceId();
    localAdvertClickStats.clickNumbers = paramList;
    WBHttpRestClient.post(AdvertUrls.getAdvertRestUrl(WBHttpRestClient.ServerType.LOCAL, AdvertUrls.UPLOAD_ADVERT_CLICK_STATS), localAdvertClickStats, new WBHttpResponseHandler(String.class, paramAsyncCallbackHandler)
    {
      public void onFailure(Throwable paramThrowable, String paramString)
      {
        if (this.val$callback == null)
          return;
        this.val$callback.onFailure(0, paramString);
      }

      public void onSuccess(Object paramObject)
      {
        if (this.val$callback == null)
          return;
        this.val$callback.onSuccess(0, paramObject);
      }
    });
  }

  public static void uploadDeviceInfo(Context paramContext, AsyncCallbackHandler paramAsyncCallbackHandler)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    DeviceInfo localDeviceInfo = new DeviceInfo();
    localDeviceInfo.udid = localTelephonyManager.getDeviceId();
    localDeviceInfo.area = mCityMan.getSelectedCityCode();
    localDeviceInfo.os = Integer.valueOf(1);
    localDeviceInfo.vendor = Build.MANUFACTURER;
    localDeviceInfo.isp = localTelephonyManager.getNetworkOperatorName();
    localDeviceInfo.description = Build.PRODUCT + Build.VERSION.RELEASE;
    WBHttpRestClient.post(AdvertUrls.getAdvertRestUrl(WBHttpRestClient.ServerType.LOCAL, AdvertUrls.UPLOAD_DEVICE_INFO), localDeviceInfo, new WBHttpResponseHandler(String.class, paramAsyncCallbackHandler)
    {
      public void onFailure(Throwable paramThrowable, String paramString)
      {
        this.val$callback.onFailure(0, paramString);
      }

      public void onSuccess(Object paramObject)
      {
        this.val$callback.onSuccess(0, paramObject);
      }
    });
  }*/
  /*
   * ��Ҫע��ĸ����ǣ�  

   1.����obj��wait()�� notify()����ǰ��������obj����Ҳ���Ǳ���д��synchronized(obj) {����} ������ڡ�  

   2.����obj.wait()���߳�A���ͷ���obj�����������߳�B�޷����obj����Ҳ���޷���synchronized(obj) {����} ������ڻ���A.  

   3.��obj.wait()�������غ��߳�A��Ҫ�ٴλ��obj�������ܼ���ִ�С�  

   4.���A1��A2��A3����obj.wait()����B����obj.notify()ֻ�ܻ���A1��A2��A3�е�һ����������һ����JVM��������  

   5.obj.notifyAll()����ȫ������A1��A2��A3������Ҫ����ִ��obj.wait()����һ����䣬������obj������ˣ�A1��A2��A3ֻ��һ���л�����������ִ�У�����A1���������Ҫ�ȴ�A1�ͷ�obj��֮����ܼ���ִ�С�

   6.��B����obj.notify/notifyAll��ʱ��B������obj������ˣ�A1��A2��A3�䱻���ѣ��������޷����obj����ֱ��B�˳�synchronized�飬�ͷ�obj����A1��A2��A3�е�һ�����л�����������ִ�С�
   * */
}
