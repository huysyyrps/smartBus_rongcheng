package main.utils.utils;

import java.text.DecimalFormat;

public class PublicData {
    public static String userAccount = "";//当前用户账号
    public static String netWorkErrorMsg = "网络请求错误";//网络请求错误提示
    public static String netWorkingMsg = "获取数据中";//网络请求错误提示
    public static int limit =20;//列表每页加载条数
    public static    String getDoubleStr(double d){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置

        return  decimalFormat.format(d);
    }
}
