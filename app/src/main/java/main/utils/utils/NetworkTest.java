package main.utils.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkTest {
    public boolean goToNetWork(Context context) {
        // TODO Auto-generated method stub
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        } else {
            //new AlertDialog.Builder(this).setMessage("������������ʹ��").setPositiveButton("Ok", null).show();
            return true;
        }
    }
}
