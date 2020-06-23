package main.utils.utils;

import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description: application
 */

public class MyApplication extends LitePalApplication {
    public static MyApplication myApp;
    public static final int TIMEOUT = 60;
    private  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        myApp = this;
//        MobSDK.init(this);
        LitePal.initialize(this);
    }

    public static Context getContent(){
        return context;
    }

}
