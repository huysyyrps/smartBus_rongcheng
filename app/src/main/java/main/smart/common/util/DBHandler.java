
package main.smart.common.util;

import java.text.DecimalFormat;


/**
 * 与服务器交互查询数据库
 * */

public class DBHandler {
    //public static String URL = "http://weixin.hezebus.com:8008/ICRecharge/pay!";

    private static String TAG = "访问数据库错误";
	public static final String ACTION_CHARGEMONEY = "getChargeMoney.action";
	public static final String ACTION_UPDATE_MOBILE_STATUS_ORDER = "updMobileStatusByOrder.action";
	public static String ACTION_GENORDERlist ="listByCardNo.action";
	public static final String ACTION_UPDATE_CHARGE_STATUS = "updChargeStatus.action";
	public static final String ACTION_GENORDER = "genOrder.action";
	private static String result="";

//	public static String POSTRequest(String url, Map<String, String> map) {
//
//		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//		HttpParams params = new HttpParams();
//
//		while (it.hasNext()) {
//			Map.Entry<String, String> entry = it.next();
//			params.put(entry.getKey(),  entry.getValue());
//
//		}
//
//		OkGo.<String>post(url)
//				//.tag()
//				.params(params)
//				// .headers("Authorization", "本地存储Token")
//				.execute(new StringCallback() {
//					@Override
//					public void onSuccess(Response<String> response) {
//
//						String resp = response.body().toString();
//						result=resp;
//						return result;
//					}
//
//					@Override
//					public void onError(Response<String> response) {
//						super.onError(response);
//						result="失败";
//
//					}
//
//					@Override
//					public void onStart(Request<String, ? extends Request> request) {
//						super.onStart(request);
//
////
//					}
//
//					@Override
//					public void onFinish() {
//						super.onFinish();
//
//
//					}
//				});
//
//	}




	public static String FenToYuan(Object num){
		double nums = Double.parseDouble(num.toString())/100;
		DecimalFormat fnum = new DecimalFormat("0.00");
		return fnum.format(nums);
	}

	public static void main(String args[]) {

        System.out.println(FenToYuan("24"));
	}
}

