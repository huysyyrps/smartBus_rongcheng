package main.utils.pay.wxpay;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.smart.common.util.Constants;
import main.smart.common.util.Util;
import main.smart.rcgj.R;
import main.utils.pay.alipay.AliPayManager;

public class WXPayManager {
    private Context context;
    private PayReq req;
    private StringBuffer sb;
    private Map<String, String> resultunifiedorder;

    private  IWXAPI msgApi ;
    private static WXPayManager wxPayManager;

    private String orderId="";
    private double totalMoney=0;
    private String notifyUrl = "";
    private AliPayManager.OnPayResultListener onPayResultListener;//支付回调

    public interface  OnPayResultListener{
        void resultSuccess();
        void resultFail();
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 400://调用微信下订单
                    String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
                    WXPayManager.PrePayIdAsyncTask prePayIdAsyncTask = new WXPayManager.PrePayIdAsyncTask();
                    prePayIdAsyncTask.execute(urlString);
                    break;
            }
        }
    };
    public static WXPayManager getInstance(){//在微信回调activity用
        return wxPayManager;
    }
    public static WXPayManager getInstance(Context context){

        if (null==wxPayManager&&context!=null){
            wxPayManager = new WXPayManager(context);
        }
        if (context!=null){
            wxPayManager.setContext(context);
        }
        return wxPayManager;
    }

    public WXPayManager(Context context) {
        this.context = context;
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        sb = new StringBuffer();
    }

    public AliPayManager.OnPayResultListener getOnPayResultListener() {
        return onPayResultListener;
    }

    public void WXpay(String orderId, double totalMoney, String notifyUrl, AliPayManager.OnPayResultListener onPayResultListener) {
        this.onPayResultListener = onPayResultListener;
        this.orderId = orderId;
//        this.orderId   = new Date().getTime()+"18354121368";
        this.totalMoney = totalMoney;
        this.notifyUrl = notifyUrl;
        Log.e("XXX","orderId=" + orderId);
        handler.sendEmptyMessage(400);
//        strMoney = strMoney.substring(0, strMoney.length() - 1);

    }

    private class PrePayIdAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub

            String url = String.format(params[0]);
            Log.e("XXX","url=" + url);
            String entity = getProductArgs();
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("XXX", "----" + content);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result.get("prepay_id")!=null){
                sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
                resultunifiedorder = result;
                Log.e("XXX", "result" + result);
                genPayReq();// 生成签名参数
                sendPayReq();
            }else {
                Toast.makeText(context,"预付单请求失败",Toast.LENGTH_LONG).show();
            }


        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //微信支付组织的参数
    private String getProductArgs() {
        // TODO Auto-generated method stub
        StringBuffer xml = new StringBuffer();
        Log.e("XXX", "xml微信支付组织的参数" );
        try {
            String nonceStr = getNonceStr();
            xml.append("<xml>");

            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            Log.e("XXX", "xml微信支付组织的参数" );
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));// 应用ID
            packageParams.add(new BasicNameValuePair("body", context.getResources().getString(R.string.chargetoast)));// 商品描述
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));// 商户号
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));// 随机字符串
            packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));// 通知地址  https://111.53.145.128:8443/ICRecharge/pay!mircPayZZCallBack.action
            packageParams.add(new BasicNameValuePair("out_trade_no", orderId));// 商户订单号
            int money = (int) (totalMoney * 100);
            packageParams.add(new BasicNameValuePair("total_fee",String.valueOf(money)));// 总金额
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));// 交易类型
            Log.e("XXX", "444444xml微信支付组织的参数" );
            String sign = getPackageSign(packageParams);
            // String sign ="752CABC28264B5F4111E7FCFA5943B57";
            packageParams.add(new BasicNameValuePair("sign", sign));// 签名
            String xmlString = toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    /**
     * 生成签名
     */
    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);
        String s = String.valueOf(sb.toString());
        Log.e("XXX","sb.toString():"+s);
        String packageSign = main.utils.utils.MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("XXX",packageSign);
        Log.e("Simon", ">>>>" + packageSign);
        return packageSign;
    }

    /**
     * 转换成xml
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("Simon", ">>>>" + sb.toString());
        String result = null;
        try {
            result = new String(sb.toString().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return result;
    }
    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("Simon", "----" + e.toString());
        }
        return null;

    }
    // 生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random = new Random();

        return main.utils.utils.MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    private void genPayReq() {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        Log.e("XXX","Constants.MCH_ID" + Constants.MCH_ID);
        if (resultunifiedorder != null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        } else {
            Toast.makeText(context, "prepayid", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
        Log.e("Simon", "----" + signParams.toString());
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = main.utils.utils.MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("Simon", "----" + appSign);
        return appSign;
    }
    /*
     * 调起微信支付
     */
    private void sendPayReq() {
//		SharedPreferences uiState = getSharedPreferences("weixin", MODE_PRIVATE);
//		Editor editor = uiState.edit();
//		editor.putString("CardNo", uname);
//		editor.putString("PayMoney", strMoney);
//		editor.putString("orderId", orderId);
//		System.out.println("orderId="+orderId);
//		editor.commit();
        msgApi.registerApp(Constants.APP_ID);
    boolean success   =  msgApi.sendReq(req);

        Log.i(">>>>>", req.partnerId);
    }
}
