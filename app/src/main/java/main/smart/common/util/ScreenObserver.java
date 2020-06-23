package main.smart.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;

public class ScreenObserver
{
  private static String TAG = "ScreenObserver";
  private static Method mReflectScreenState;
  private static ScreenBroadcastReceiver mScreenReceiver = new ScreenBroadcastReceiver(null);
  private static List<ScreenStateListener> mScreenStateListeners = new ArrayList<ScreenStateListener>();

  static
  {
    try
    {
      mReflectScreenState = PowerManager.class.getMethod("isScreenOn", new Class[0]);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.d(TAG, "API < 7," + localNoSuchMethodException);
    }
  }

  public static void firstGetScreenState(Context paramContext)
  {
    if (isScreenOn((PowerManager)paramContext.getSystemService("power")))
    {
      Iterator localIterator2 = mScreenStateListeners.iterator();
      while (true)
      {
        if (!(localIterator2.hasNext()))
          return;
        ((ScreenStateListener)localIterator2.next()).onScreenOn();
      }
    }
    Iterator localIterator1 = mScreenStateListeners.iterator();
    while (true)
    {
      if (localIterator1.hasNext());
      ((ScreenStateListener)localIterator1.next()).onScreenOff();
    }
  }

  private static boolean isScreenOn(PowerManager paramPowerManager)
  {
    try
    {
      boolean bool = ((Boolean)mReflectScreenState.invoke(paramPowerManager, new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static void removeScreenStateUpdate(ScreenStateListener paramScreenStateListener)
  {
    mScreenStateListeners.remove(paramScreenStateListener);
  }

  public static void requestScreenStateUpdate(ScreenStateListener paramScreenStateListener)
  {
    mScreenStateListeners.add(paramScreenStateListener);
  }

  public static void startScreenBroadcastReceiver(Context paramContext)
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.SCREEN_ON");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    paramContext.registerReceiver(mScreenReceiver, localIntentFilter);
  }

  public static void stopScreenBroadcastReceiver(Context paramContext)
  {
    paramContext.unregisterReceiver(mScreenReceiver);
  }

  private static class ScreenBroadcastReceiver extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Iterator localIterator ;
      String actionName="";
      actionName =paramIntent.getAction();
      if(actionName.equals("android.intent.action.SCREEN_ON")||actionName.equals("android.intent.action.SCREEN_OFF")){
    	  localIterator=ScreenObserver.mScreenStateListeners.iterator();
    	  while(localIterator.hasNext()){
    		  if(actionName.equals("android.intent.action.SCREEN_ON")){
    			  ((ScreenObserver.ScreenStateListener)localIterator.next()).onScreenOn();
    		  }else if(actionName.equals("android.intent.action.SCREEN_OFF")){
    			  ((ScreenObserver.ScreenStateListener)localIterator.next()).onScreenOff(); 
    		  }
    	  }
      }
    }
	private ScreenBroadcastReceiver()
	{
	}

	ScreenBroadcastReceiver(ScreenBroadcastReceiver screenbroadcastreceiver)
	{
		this();
	}
  }

  public static abstract interface ScreenStateListener
  {
    public abstract void onScreenOff();

    public abstract void onScreenOn();
  }
}