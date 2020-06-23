package main.utils.pay.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import main.Charge.ActivityCardCharge;
import main.alipay.PayResult;
import main.smart.common.util.Constants;

import main.smart.rcgj.R;
import main.utils.utils.OrderInfoUtil2_0;

public class AliPayManager {
    /** ֧����֧��ҵ�����app_id */
    public static final String APPID = Constants.APPID;// 2017071007703608
    /** �̻�˽Կ��pkcs8��ʽ */
    /** ����˽Կ��RSA2_PRIVATE ���� RSA_PRIVATE ֻ��Ҫ����һ�� */
    /** ����̻������������ˣ�����ʹ�� RSA2_PRIVATE */
    /** RSA2_PRIVATE ���Ա�֤�̻������ڸ��Ӱ�ȫ�Ļ����½��У�����ʹ�� RSA2_PRIVATE */
    /** ��ȡ RSA2_PRIVATE������ʹ��֧�����ṩ�Ĺ�˽Կ���ɹ������ɣ� */
    /**
     * ���ߵ�ַ��https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097
     * &docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCu2Awlk5ZFIf0Iqn/UsRwdZWzTJJYj/EJvhdRIKNc5Fm7Z81D/Dr+qAHtqzJihnSjpOGG7OZOtLhb//PCfPz0aeNDF7XSixtCoKo2TNmywTU4tMIQztUKI/+fr6bNN0HaYNV1U/8f6UTdD5iPFXIuXcYtOAYh22Lt/G+F5ibM7Kg3MADctYye/q9I/AGM/7jrLFmkhVsyjIrpUfgBoHTdAmHnkvJmW+Q+En5bOt54hJT3V1uXALyMrEMcPXm3L/7jP294XeUZMVN+qvIJwszD/pHt2gGpsksqvEU7Ck8omm8w04yrUrrtXovRsAajGRpgX9Q9CDL8XB9RlsrSvfxkzAgMBAAECggEAIWAAQZQAlUW9pU/WguUj9sCLm2padpgKnnsvYG7bVnxfwJ7fkTBhibP52xMe94qmao5mqXR+KgqoPUktLVjG8yyovX9sSj4lSlSFHdq/uI+pIpsmQTCSeNBQlwnPPkVSvg9Bt53zsGacZAukPGJECy6TZtqIEBejAspxqd+NJPe627HMxHRR3pzzx4sDjoSRxwVEx+8Rbm85C52NmjT4/TnrqJdWJXiVsBucufIHFR8a4iPuybcAY5mtVnxN2Qfjcwzrki6ORgQeQaTdQjhRs1Hqs3FuhB0d3pDbwfZmi+ICz+LAnsvqwFs8y2rgHv4WeEDFe8aaa/QXUmNA175P4QKBgQDrXA4sx0gGjrqY2ued5c/BZ+MGEu19L5k4ZJb9FTvMeMh1LdnpZREqndlDb/dDtQ0ols3sOSw+EJu3ztqpInd5rvlvc4pQfJZFJ/Q6+spi8lw6E9k7eh3kpoZSGNY5zc6sxIu5aNP2i58aVKE+aL8XSmQqbYLcbnIBjraN04JwKQKBgQC+LWJgYb7VAaeMDc5NPXemDCsAO7iO+wit4DcKshDJUYOhSqdJp9feLNtDauQvOHCmRFbCKPb7pFb/8R+H0Se69IMEncA2Qp6NS9XERDt6xbVTfQHrgh5ra/sz8GCwgokg35c9ALItmyXrt7IIrLgivsQO7uTc1eGYQ1k8ctI5+wKBgDHg+3wP67rACtmUItv6tTHW020ZG82ZBc7Gupz+IAMwYcM7qV/mNGIku8On+e4Wc6hzdUanwzGQEYA9zWdkz48xPtmY0epGzQ3w7riFJ9kaZeNFC5eoRKeNhw/lBZmQfUluCfKMPceVRW2uG9H/HuhPsrSvKrmOioi79wJNneopAoGBAJ+q3PCMwDp4BCJ7KN0CoWTgzG2Ktn4c14GcQLoTHDLZ5EjTs7YMXBnl0UU+Bkx692Ew+SJUYGCP7pc96zoNaWz2gtWqWEl2D0YUCGqa2tFOEtoAl08SVNXdt+84fwVS4yydbI3D+tR9cQ4BkVDlKkfJoDPlcHLUg25iBRbSVBxnAoGAG+p74RKpE9m/ajkLxucbEa9EzyXhRSSmRN/PqKTAbcw/iclb+dW9cmiiUbg2gnpd4B8xLOICNB7HkyIUX2gg3GwdF5ywowRz7vnH/Q3PDgplynZp6HZZwYyB2eyuujZY/6mLPnOVNWrIYYW4cT3LNXnyOUhEWr++ceEYu8O88BI=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Context context;
    private String orderId;
    private double totalMoney=0;//֧�����
    private String notifyUrl = "";//֧���ص���ַ
    private OnPayResultListener onPayResultListener;//֧���ص�

    private static AliPayManager aliPayManager;

    public interface  OnPayResultListener{
        void resultSuccess();
        void resultFail();
    }
    public  static AliPayManager getInstance(Context context){
        if (null==aliPayManager){
            aliPayManager = new AliPayManager(context);
        }
        return aliPayManager;
    }



    public AliPayManager(Context context) {
        this.context = context;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 500://����֧�����¶���
                    if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
                        new AlertDialog.Builder(context).setTitle("����").setMessage("��Ҫ����APPID | RSA_PRIVATE")
                                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        //
                                        //  context.finish();
                                    }
                                }).show();
                        return;

                    }else{

                        /**
                         * ����ֻ��Ϊ�˷���ֱ�����̻�չʾ֧����������֧�����̣�
                         * ����Demo�м�ǩ����ֱ�ӷ��ڿͻ�����ɣ�
                         * ��ʵApp�privateKey�������Ͻ����ڿͻ��ˣ���ǩ�������Ҫ���ڷ������ɣ�
                         * ��ֹ�̻�˽������й¶����ɲ���Ҫ���ʽ���ʧ�������ٸ��ְ�ȫ���գ�
                         *
                         * orderInfo�Ļ�ȡ�������Է���ˣ�
                         */
                        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                        Map<String, String> params = buildOrderParamMap(APPID, rsa2);
                        Log.e(null, "******************************" + params);
                        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                        final String orderInfo = orderParam + "&" + sign;
                        final Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask((Activity) context);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Log.i("msp", result.toString());
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                    break;
            }
        }
    };

    public void ZFBpay(String orderId,double totalMoney,String notifyUrl,OnPayResultListener onPayResultListener) {
        this.orderId = orderId;
        this.totalMoney = totalMoney;
        this.notifyUrl = notifyUrl;
        this.onPayResultListener = onPayResultListener;
//        orderId = new Date().getTime()+"18354121368";
        Log.e("XXX","orderId=" + orderId);
        handler.sendEmptyMessage(500);


    }

    /**
     * ����֧�����������б�
     *
     * @param
     * @param app_id
     * @param
     * @return
     */
    public Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
        String desc= "���ƹ�����Ʊ";
        //context.getResources().getString(R.string.chargetoast);
        Map<String, String> keyValues = new HashMap<String, String>();
        keyValues.put("app_id", app_id);
        keyValues.put("biz_content",
                "{"+"\""+"timeout_express"+"\""+":"+"\""+"30m"+"\""+","+"\""+"product_code"+"\""+":"+"\""+"QUICK_MSECURITY_PAY"+"\""+"," +"\""+"total_amount"+"\""+":"+"\"" + String.valueOf(totalMoney)
                        + "\""+","+"\""+"subject"+"\""+":"+"\""+desc+"\""+","+"\""+"body"+"\""+":"+"\""+desc+"\""+","+"\""+"out_trade_no"+"\""+":"+"\"" +orderId+ "\""+"}");



        keyValues.put("charset", "utf-8");

        keyValues.put("method", "alipay.trade.app.pay");

        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

        keyValues.put("timestamp", getTimestamp());// "2016-07-29 16:55:53"

        keyValues.put("version", "1.0");
        keyValues.put("notify_url", notifyUrl);//111.53.145.128:8443

        return keyValues;
    }

    /**
     * Ҫ���ⲿ�����ű���Ψһ��
     *
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public static String getTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        return key;
    }


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    // Toast.makeText(ActivityCardCharge.this, "orderId=" + orderId,
                    // Toast.LENGTH_SHORT).show();
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * ����֧����������̻���������˵��첽֪ͨ�����ͬ��֪ͨ���������Ϊ֧��������֪ͨ��
                     */
                    String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

                    String resultStatus = payResult.getResultStatus();
                    // �ж�resultStatus Ϊ9000�����֧���ɹ�
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // �ñʶ����Ƿ���ʵ֧���ɹ�����Ҫ��������˵��첽֪ͨ��
                        Toast.makeText(context, R.string.chargesuc, Toast.LENGTH_SHORT).show();
                        if (onPayResultListener!=null){
                            onPayResultListener.resultSuccess();
                        }


                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        // �ñʶ����Ƿ���ʵ֧���ɹ�����Ҫ��������˵��첽֪ͨ��
                        Toast.makeText(context, R.string.chargefailqu, Toast.LENGTH_SHORT).show();


                    }else {
                        // �ñʶ�����ʵ��֧���������Ҫ��������˵��첽֪ͨ��
                        Toast.makeText(context, R.string.chargefail, Toast.LENGTH_SHORT).show();
                        if (onPayResultListener!=null){
                            onPayResultListener.resultFail();
                        }
                    }
                    break;
                }
                case 100:

                default:
                    break;
            }
        };
    };
}
