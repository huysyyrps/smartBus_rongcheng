package main.smart.bus.util;

import java.util.ArrayList;
import java.util.List;

import main.smart.bus.bean.BusBean;

import android.os.Handler;

public class BusTracker implements Runnable{

	  private BusManager mBusMan;
	  private Handler mHandler;
	  private List<BusTrackInfoListener> mObservers = new ArrayList();
	  private String mTrackedBusId;
	  private Boolean mTracking;
	  public BusTracker(BusManager paramBusManager)
	  {
	    this.mBusMan = paramBusManager;
	    this.mHandler = new Handler();
	 //   this.mAlarm = BusTrackAlarm.getInstance();
	//    this.mAlarm.setOnAlarmOccuredListener(this);
	    this.mTracking = Boolean.valueOf(false);
	  }

	  public void addBusTrackInfoListener(BusTrackInfoListener paramBusTrackInfoListener)
	  {
	    this.mObservers.add(paramBusTrackInfoListener);
	  }

	  public void onBusArrivedAlarmOccured(BusBean paramBusInfo)
	  {
		  /*
	    BusLine localBusLine = this.mBusMan.getSelectedLine();
	    int i = -1 + localBusLine.getStations().size();
	    if (paramBusInfo.getTarget() != null)
	      i = paramBusInfo.getTarget().intValue();
	    String str = new StringBuilder(String.valueOf("")).append("¾àÀë").append(((BusStation)localBusLine.getStations().get(i)).getStationName()).toString() + "»¹ÓÐ" + paramBusInfo.getLeftStation() + "Õ¾";
	    if (paramBusInfo.getLeftDistance() != null)
	      str = str + paramBusInfo.getLeftDistance() + "Ã×";
	    Utils.showLong(str);
	    if (this.mAlarm.isVoicePlay())
	      Utils.playText(str);
	    if (!(this.mAlarm.isVibrate()))
	      return;
	    Utils.vibrate(new long[] { 1000L, 1000L, 1000L, 1000L, 1000L, 1000L }, false);
	    */
	  }

	  public void removeBusTrackInfoListener(BusTrackInfoListener paramBusTrackInfoListener)
	  {
	    this.mObservers.remove(paramBusTrackInfoListener);
	  }

	  public void run()
	  {
	//    new BusTrackTask().execute(new String[0]);
//	    this.mHandler.postDelayed(this, this.mAlarm.getRefreshInterval());
	  }

	  public void setTrackedBus(String paramString)
	  {
	    this.mTrackedBusId = paramString;
	  }

	  public void startTrack()
	  {
	    if (this.mTracking.booleanValue())
	      return;
	    this.mTracking = Boolean.valueOf(true);
	  //  this.mAlarm.setupStationOfAlarm(this.mBusMan.getSoiList());
	    this.mHandler.post(this);
	  }

	  public void stopTrack()
	  {
	    if (!(this.mTracking.booleanValue()))
	      return;
	    this.mHandler.removeCallbacks(this);
	    this.mTracking = Boolean.valueOf(false);
	  }
	 public static abstract interface BusTrackInfoListener
	  {
	    public abstract void onBusTrackInfoUpdated(List<BusBean> paramList);
	  }
	 /*
	  class BusTrackTask extends AsyncTask<String, Integer, List<BusBean>>
	  {
	    private List<BusBean> mBusList = new ArrayList();
	    private Object mLock = new Object();
	 //   private List<Integer> mSoiList = new ArrayList(BusTracker.this.mBusMan.getSoiList());

	    private void getTrackedBusInfo()
	      throws InterruptedException
	    {
	      BusTracker.this.mBusMan.getBusInfo(BusTracker.this.mTrackedBusId, new AsyncCallbackHandler()
	      {
	        public void onFailure(int paramInt, String paramString)
	        {
	          synchronized (BusTracker.BusTrackTask.this.mLock)
	          {
	            BusTracker.BusTrackTask.this.mLock.notify();
	            return;
	          }
	        }

	        public void onSuccess(int paramInt, Object paramObject)
	        {
	          synchronized (BusTracker.BusTrackTask.this.mLock)
	          {
	            BusBean localBusInfo = (BusBean)paramObject;
	            if ((localBusInfo.getBusLineId() == null) || (BusTracker.this.mBusMan.getSelectedLine().getId().equals(localBusInfo.getBusLineId())))
	              BusTracker.BusTrackTask.this.mBusList.add(localBusInfo);
	            BusTracker.BusTrackTask.this.mLock.notify();
	            return;
	          }
	        }
	      });
	      this.mLock.wait();
	    }

	    private void procBusSoiInfo(BusBean paramBusInfo)
	    {
	      int i = paramBusInfo.getArrvStationNum();
	      for (int j = 0; ; ++j)
	      {
	        int k = this.mSoiList.size();
	        Integer localInteger = null;
	        if (j >= k);
	        while (true)
	        {
	          BusTracker.this.mBusMan.procBusSoiInfo(paramBusInfo, localInteger);
	          return;
	          if (i >= ((Integer)this.mSoiList.get(j)).intValue())
	            break;
	          localInteger = (Integer)this.mSoiList.get(j);
	        }
	      }
	    }

	    protected List<BusBean> doInBackground(String[] paramArrayOfString)
	    {
	      Object localObject1 = this.mLock;
	      try
	      {
	  // //     getTrackedBusInfo();
	  //      BusTracker.this.mBusMan.transBusGPS(this.mBusList);
	        procBusInfo();
	        return this.mBusList;
	      }
	      catch (InterruptedException localInterruptedException)
	      {
	      }
	      finally
	      {
	  //      monitorexit;
	      }
	    }

	    protected void onPostExecute(List<BusBean> paramList)
	    {
	      super.onPostExecute(paramList);
	      if (this.mBusList.size() != 1)
	        return;
	      Iterator localIterator = BusTracker.this.mObservers.iterator();
	      while (true)
	      {
	        if (!(localIterator.hasNext()))
	        {
	    //      BusTracker.this.mAlarm.process((BusBean)this.mBusList.get(0));
	          return;
	        }
	        ((BusTracker.BusTrackInfoListener)localIterator.next()).onBusTrackInfoUpdated(this.mBusList);
	      }
	    }

	    public void procBusInfo()
	    {
	      Iterator localIterator = this.mBusList.iterator();
	      while (true)
	      {
	        if (!(localIterator.hasNext()))
	          return;
	        BusBean localBusInfo = (BusBean)localIterator.next();
	    //    BusTracker.this.mBusMan.procBusArrvStation(localBusInfo);
	   //     BusTracker.this.mBusMan.procBusPositionNum(localBusInfo);
	   //     procBusSoiInfo(localBusInfo);
	      }
	    } 
	  }*/
}
