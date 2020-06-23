package main.smart.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import main.Adapter.ListViewAdapter;
import main.Charge.ActivityCardCharge;
import main.ICReacher.MD5;
import main.alipay.PayResult;

import main.smart.common.SmartBusApp;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.ConstData;
import main.smart.common.util.Constant;
import main.smart.common.util.Constants;
import main.smart.common.util.Util;
import main.smart.rcgj.R;
import main.smart.rcgj.wxapi.WXPayEntryActivity;
import main.utils.dialog.AlertDialog;
import main.utils.dialog.ProgressHUD;
import main.utils.utils.FlowRadioGroup;
import main.utils.utils.OrderInfoUtil2_0;
import main.utils.utils.SharePreferencesUtils;


public class OnlineActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener{
	private EditText cardNoText;//卡号
	private ImageView iv_back;
	private FlowRadioGroup rg;
	private RelativeLayout alljob_black2;
	private RadioButton rb;
	private Button btn_next;
	//private CProgressDialog dialog;
	private static String strMoney="";
	private String TAG = "mifare";
	private Thread moneyThread;
	//	private int[] itemID = { R.drawable.noxin, R.drawable.alpay, R.drawable.wechat };
//	private String[] itemName = new String[3];
//	private String pay = "nongxin";
	private int[] itemID = { R.drawable.alpay, R.drawable.wechat };
	//private int[] itemID = {  R.drawable.wechat };
	private String[] itemName = new String[2];
	//private String[] itemName = new String[1];
	private String pay = "zhifubao";
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;

	private List<Map<String, Object>> list;
	private String cardNo;
	private String orderId;
	private static String orderIdZFB="";
	private StringBuffer sb;
	private int version;
	private Map<String, String> resultunifiedorder;
	private PayReq req;
	private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = Constants.APPID;// 2017071007703608

	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = Constants.PID;// 2088221932370500
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "45455847";// 此参数为随机数

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/**
	 * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097
	 * &docType=1
	 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCu2Awlk5ZFIf0Iqn/UsRwdZWzTJJYj/EJvhdRIKNc5Fm7Z81D/Dr+qAHtqzJihnSjpOGG7OZOtLhb//PCfPz0aeNDF7XSixtCoKo2TNmywTU4tMIQztUKI/+fr6bNN0HaYNV1U/8f6UTdD5iPFXIuXcYtOAYh22Lt/G+F5ibM7Kg3MADctYye/q9I/AGM/7jrLFmkhVsyjIrpUfgBoHTdAmHnkvJmW+Q+En5bOt54hJT3V1uXALyMrEMcPXm3L/7jP294XeUZMVN+qvIJwszD/pHt2gGpsksqvEU7Ck8omm8w04yrUrrtXovRsAajGRpgX9Q9CDL8XB9RlsrSvfxkzAgMBAAECggEAIWAAQZQAlUW9pU/WguUj9sCLm2padpgKnnsvYG7bVnxfwJ7fkTBhibP52xMe94qmao5mqXR+KgqoPUktLVjG8yyovX9sSj4lSlSFHdq/uI+pIpsmQTCSeNBQlwnPPkVSvg9Bt53zsGacZAukPGJECy6TZtqIEBejAspxqd+NJPe627HMxHRR3pzzx4sDjoSRxwVEx+8Rbm85C52NmjT4/TnrqJdWJXiVsBucufIHFR8a4iPuybcAY5mtVnxN2Qfjcwzrki6ORgQeQaTdQjhRs1Hqs3FuhB0d3pDbwfZmi+ICz+LAnsvqwFs8y2rgHv4WeEDFe8aaa/QXUmNA175P4QKBgQDrXA4sx0gGjrqY2ued5c/BZ+MGEu19L5k4ZJb9FTvMeMh1LdnpZREqndlDb/dDtQ0ols3sOSw+EJu3ztqpInd5rvlvc4pQfJZFJ/Q6+spi8lw6E9k7eh3kpoZSGNY5zc6sxIu5aNP2i58aVKE+aL8XSmQqbYLcbnIBjraN04JwKQKBgQC+LWJgYb7VAaeMDc5NPXemDCsAO7iO+wit4DcKshDJUYOhSqdJp9feLNtDauQvOHCmRFbCKPb7pFb/8R+H0Se69IMEncA2Qp6NS9XERDt6xbVTfQHrgh5ra/sz8GCwgokg35c9ALItmyXrt7IIrLgivsQO7uTc1eGYQ1k8ctI5+wKBgDHg+3wP67rACtmUItv6tTHW020ZG82ZBc7Gupz+IAMwYcM7qV/mNGIku8On+e4Wc6hzdUanwzGQEYA9zWdkz48xPtmY0epGzQ3w7riFJ9kaZeNFC5eoRKeNhw/lBZmQfUluCfKMPceVRW2uG9H/HuhPsrSvKrmOioi79wJNneopAoGBAJ+q3PCMwDp4BCJ7KN0CoWTgzG2Ktn4c14GcQLoTHDLZ5EjTs7YMXBnl0UU+Bkx692Ew+SJUYGCP7pc96zoNaWz2gtWqWEl2D0YUCGqa2tFOEtoAl08SVNXdt+84fwVS4yydbI3D+tR9cQ4BkVDlKkfJoDPlcHLUg25iBRbSVBxnAoGAG+p74RKpE9m/ajkLxucbEa9EzyXhRSSmRN/PqKTAbcw/iclb+dW9cmiiUbg2gnpd4B8xLOICNB7HkyIUX2gg3GwdF5ywowRz7vnH/Q3PDgplynZp6HZZwYyB2eyuujZY/6mLPnOVNWrIYYW4cT3LNXnyOUhEWr++ceEYu8O88BI=";
	public static final String RSA_PRIVATE = "";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;


	private KProgressHUD progressHUD;

	private RelativeLayout alljob_black;
	private String rescode="";
	private AlertDialog aldialog;
	private AlertDialog aldialognew;
    private static String cardnamezfb="";
    private static String cardidzfb="";
	private static String cardnamewx="";
	private static String cardidwx="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online);
		//StatusBarUtil.setStatusBarMode(this, true, R.color.color_bg_selected2);

		version=getVersionCode();
		//itemName[0]=ActivityCardCharge.this.getResources().getString(R.string.nx);
		itemName[0]= OnlineActivity.this.getResources().getString(R.string.zfb);
		itemName[1]= OnlineActivity.this.getResources().getString(R.string.wx);
//		itemName[0]= OnlineActivity.this.getResources().getString(R.string.wx);
		//itemName[1]= OnlineActivity.this.getResources().getString(R.string.wx);
		rg = (FlowRadioGroup) findViewById(R.id.rg);
		btn_next = (Button) findViewById(R.id.btn_login);
		mListView = (ListView) findViewById(R.id.listView);
		cardNoText=(EditText)findViewById(R.id.textCardNo2);

		alljob_black= (RelativeLayout) findViewById(R.id.alljob_black3);
		alljob_black.setOnClickListener(this);

		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(this);

		btn_next.setOnClickListener(this);
		sb = new StringBuffer();
		req = new PayReq();
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 2; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", itemID[i]);
			map.put("Name", itemName[i]);
			list.add(map);
		}
//		for (int i = 0; i < 1; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("ID", itemID[i]);
//			map.put("Name", itemName[i]);
//			list.add(map);
//		}

		mListViewAdapter = new ListViewAdapter(getApplicationContext(), list);
		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(this);
		rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

		// 自定义回调函数
		mListViewAdapter.setOncheckChanged(new ListViewAdapter.OnMyCheckChangedListener() {

			@Override
			public void setSelectID(int selectID) {

				if (selectID == 0) {
					pay = "zhifubao";
				} else if (selectID == 1) {
					pay = "weixin";
				}
//				if (selectID == 0) {
//					pay = "weixin";
//				} else if (selectID == 1) {
//				//	pay = "weixin";
//				}
				mListViewAdapter.setSelectID(selectID); // 选中位置
				mListViewAdapter.notifyDataSetChanged(); // 刷新适配器
			}
		});

		Intent intent = getIntent();

		String ccbParam = intent.getStringExtra("CCBPARAM");
		if (ccbParam != null) {
			Log.d(TAG, "ccbParam:" + ccbParam);
		}


	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//		if (position == 0) {
//			pay = "zhifubao";
//		} else if (position == 1) {
//			pay = "weixin";
//		}
		if (position == 0) {
			pay = "zhifubao";
		} else if (position == 1) {
			pay = "weixin";
		}
		mListViewAdapter.setSelectID(position); // 选中位置
		mListViewAdapter.notifyDataSetChanged(); // 刷新适配器

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			OnlineActivity.this.finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// 点击事件
	@Override
	public void onClick(View v) {

		if (v == alljob_black) {
			finish();
		} else if (v == btn_next) {// 立即支付
			if((cardNoText.getText().toString()).equals("")){
				Toast.makeText(OnlineActivity.this, R.string.chargexuanka, Toast.LENGTH_SHORT).show();

			}else{
				cardNo=cardNoText.getText().toString();
				if(cardNo.length()<9){
					if((9-(cardNo.length()))==3){
						cardNo="000"+cardNo;
					}else if((9-(cardNo.length()))==2){
						cardNo="00"+cardNo;
					}else if((9-(cardNo.length()))==1){
						cardNo="0"+cardNo;
					}

				}else if(cardNo.length()==19){
					cardNo="0"+cardNo;
				}

				if(rg.getCheckedRadioButtonId()!=-1){
					rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
					Log.e("acher","###########"+rb.getText().toString());
					strMoney = rb.getText().toString();
					if (strMoney.equals("")) {// 判断是否选取金额
						Toast.makeText(this, R.string.chargexuan, Toast.LENGTH_SHORT).show();
						return;
					}
				}else{
					if (strMoney.equals("")||strMoney.equals("0"+getResources().getString(R.string.yuanmoney))) {// 判断是否选取金额
						Toast.makeText(this, R.string.chargexuan, Toast.LENGTH_SHORT).show();
						return;
					}
					if(!strMoney.contains(getResources().getString(R.string.yuanmoney))){
						strMoney=strMoney+getResources().getString(R.string.yuanmoney);
					}

				}
				aldialog=new AlertDialog(OnlineActivity.this);

				if(pay.equals("weixin")){
					WXpay();
//					aldialog.builder().setTitle(getResources().getString(R.string.tishi))
//							.setMsg(getResources().getString(R.string.querenkahao))
//							.setPositiveButton(getResources().getString(R.string.queren), new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									WXpay();
//								}
//							}).setNegativeButton(getResources().getString(R.string.quxiao), new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							aldialog.cacle();
//
//						}
//					}).show();

				}else if(pay.equals("zhifubao")){
					ZFBpay();

//					aldialog.builder().setTitle(getResources().getString(R.string.tishi))
//							.setMsg(getResources().getString(R.string.querenkahao))
//							.setPositiveButton(getResources().getString(R.string.queren), new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									ZFBpay();
//								}
//							}).setNegativeButton(getResources().getString(R.string.quxiao), new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							aldialog.cacle();
//
//						}
//					}).show();

				}
//				else if(pay.equals("zhifubao")){
//					//	NXpay();
//				}
			}


		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 400://调用微信下订单
					StringBuilder sb = new StringBuilder(cardnamewx);
					cardnamewx=sb.replace(0, 1, "*").toString();
					StringBuilder sbne = new StringBuilder(cardidwx);
					cardidwx=sbne.replace(6, 14, "*********").toString();
					aldialog.builder().setTitle(getResources().getString(R.string.tishi))
							.setMsg(getResources().getString(R.string.cardname)+cardnamewx+"\n"+getResources().getString(R.string.cardid)+cardidwx+"\n"+getResources().getString(R.string.cardnei))
							.setPositiveButton(getResources().getString(R.string.queren), new OnClickListener() {
								@Override
								public void onClick(View v) {
									SharePreferencesUtils sharePreferencesUtils = new SharePreferencesUtils();
									sharePreferencesUtils.setString(OnlineActivity.this,"wxPayResult",getString(R.string.zhifufanhui));

									String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
									PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask();
									prePayIdAsyncTask.execute(urlString);
								}
							}).setNegativeButton(getResources().getString(R.string.quxiao), new OnClickListener() {

						@Override
						public void onClick(View v) {
							aldialog.cacle();

						}
					}).show();

					break;
				case 600://调用微信下订单
					progressHUD.dismiss();
					Toast.makeText(OnlineActivity.this,rescode, Toast.LENGTH_SHORT).show();
					break;
				case 500://调用支付宝下订单
					StringBuilder sb1 = new StringBuilder(cardnamezfb);
					cardnamezfb=sb1.replace(0, 1, "*").toString();
					StringBuilder sbne1 = new StringBuilder(cardidzfb);
					cardidzfb=sbne1.replace(6, 14, "*********").toString();
                    aldialog.builder().setTitle(getResources().getString(R.string.tishi))
							.setMsg(getResources().getString(R.string.cardname)+cardnamezfb+"\n"+getResources().getString(R.string.cardid)+cardidzfb+"\n"+getResources().getString(R.string.cardnei))
							.setPositiveButton(getResources().getString(R.string.queren), new OnClickListener() {
								@Override
								public void onClick(View v) {
                                    if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
                                        new android.app.AlertDialog.Builder(OnlineActivity.this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialoginterface, int i) {
                                                        //
                                                        finish();
                                                    }
                                                }).show();
                                        return;

                                    }else{

                                        /**
                                         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；
                                         * 所以Demo中加签过程直接放在客户端完成；
                                         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                                         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                                         *
                                         * orderInfo的获取必须来自服务端；
                                         */
                                        Log.e("soso", "******************************" );
                                        Log.e("soso", "2222******************************" + RSA2_PRIVATE.length());
                                        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                                        Map<String, String> params = buildOrderParamMap(APPID, rsa2);

                                        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                                        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                                        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                        final String orderInfo = orderParam + "&" + sign;
                                        final Runnable payRunnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                PayTask alipay = new PayTask(OnlineActivity.this);
                                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                                Log.e("soso", result.toString());
                                                Message msg = new Message();
                                                msg.what = SDK_PAY_FLAG;
                                                msg.obj = result;
                                                mHandler.sendMessage(msg);
                                            }
                                        };
                                        Thread payThread = new Thread(payRunnable);
                                        payThread.start();
                                    }
								}
							}).setNegativeButton(getResources().getString(R.string.quxiao), new OnClickListener() {

						@Override
						public void onClick(View v) {
							aldialog.cacle();

						}
					}).show();


					break;
			}
		}
	};

	private TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			strMoney="";
			String text = s.toString();
			int len = s.toString().length();
			if (len == 1 && text.equals("0")) {
				s.clear();
			}
			if(text.equals("")){
				strMoney=text;
			}else{
				strMoney=s.toString()+getResources().getString(R.string.yuanmoney);
			}
			System.out.println("strMoney="+strMoney);
//			handler.sendEmptyMessage(600);
		}
	};

	public void WXpay() {

		String value = "sdhy" + cardNo + strMoney.substring(0, strMoney.length() - 1) + "order";
		String Make = MD5.encode(value);
		progressHUD = ProgressHUD.show(OnlineActivity.this);
		RequestParams params = new RequestParams();
		params.put("CardNo", cardNo);
		params.put("PayMoney", strMoney.substring(0, strMoney.length() - 1));
		params.put("Make", Make);
		params.put("SerialNo", "1");
		params.put("PayBank", "WX");
		params.put("ZZFlag", "1");
		params.put("MobileNo", "");
		params.put("TerminalType", "1");
		String jsss = "CardNo=" + cardNo + "&PayMoney=" + 1 + "&Make=" + Make + "&MobileNo=" + "" + "&SerialNo=" + 1 + "&PayBank=" + "ZFB" + "&ZZFlag=" + 1;
		//https://rcbus.org.cn:50013/ICRecharge/pay!genOrderJson.action
		String url= ConstData.ICRecharge+"ICRecharge/pay!genOrder.action";
//		String url= "http://192.168.2.190:8044/ICRecharge/pay!genOrderJson.action";
		Log.e("gogogo",url+"?"+jsss);

		RequstClient.get(url,
				params, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
						new LoadDatahandler() {
							@Override
							public void onStart() {
								super.onStart();
							}

							@Override
							public void onSuccess(String data) {
								super.onSuccess(data);
								Log.e("gogogo",getResources().getString(R.string.requestsuc)+data.toString());
								JSONObject json = null;
								try {
									json = new JSONObject(data.toString());
									Log.e("gogogo",strMoney+"*******************");
									if (json.getString("success").equals("true")) {
										// strMD5 = MD5ccb.encode("sdhy" + uname +
										// strMoney + "order");// cardNo,strMoney
										orderId = json.getString("orderId");
										Log.e("gogogo",getResources().getString(R.string.requestsuc2)+orderId);
										handler.sendEmptyMessage(400);
										strMoney = strMoney.substring(0, strMoney.length() - 1);
										cardnamewx=  json.getString("name");
										cardidwx=  json.getString("idno");
									}else {
										rescode=json.getString("msg");
										handler.sendEmptyMessage(600);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onFailure(String error, String message) {
								super.onFailure(error, message);
								Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
							}

							@Override
							public void onFinish() {
								super.onFinish();
								progressHUD.dismiss();
							}
						}));
//		orderId = new Date().getTime()+"18354121368";
//		Log.e("XXX","orderId=" + orderId);
//		HttpParams params = new HttpParams();
//		String value = "sdhy" + cardNo + strMoney.substring(0, strMoney.length() - 1) + "order";
//		String Make = MD5.encode(value);
//		progressHUD = ProgressHUD.show(OnlineActivity.this);
//
//		params.put("CardNo", cardNo);
//		params.put("PayMoney", strMoney.substring(0, strMoney.length() - 1));
//		params.put("Make", Make);
//		params.put("SerialNo", "1");
//		params.put("PayBank", "WX");
//		params.put("ZZFlag", "1");
//		params.put("MobileNo", "");
////
//		String url= "http://192.168.2.190:8044/ICRecharge/pay!genOrderJson.action";
//		OkGo.<String>post(url)
//				//.tag()
//				.params(params)
//				// .headers("Authorization", "本地存储Token")
//				.execute(new StringCallback() {
//					@Override
//					public void onSuccess(Response<String> response) {
//						Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
//						JSONObject json = null;
//						try {
//							json = new JSONObject(response.body().toString());
//							if (json.getString("success").equals("true")) {
//								// strMD5 = MD5ccb.encode("sdhy" + uname +
//								// strMoney + "order");// cardNo,strMoney
//								orderId = json.getString("orderId");
////								Log.e("gogogo",getResources().getString(R.string.requestsuc2)+orderId);
//								handler.sendEmptyMessage(400);
////								Log.e("gogogo",getResources().getString(R.string.requestsuc2)+strMoney);
//
//								strMoney = strMoney.substring(0, strMoney.length() - 1);
////								Log.e("gogogo",getResources().getString(R.string.requestsuc2)+strMoney);
//
////								handler.sendEmptyMessage(500);
////								strMoney = strMoney.substring(0, strMoney.length() - 1);
//
//							}else {
//								rescode=json.getString("msg");
//								handler.sendEmptyMessage(600);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//
//
//					}
//
//					@Override
//					public void onError(Response<String> response) {
//						super.onError(response);
//
//						Log.e("gogogo","sss请求失败了");
//					}
//					@Override
//					public void onStart(Request<String, ? extends Request> request) {
//						super.onStart(request);
//
//						Log.e("gogogo","eeee开始请求" );
//
//					}
//					@Override
//					public void onFinish() {
//						super.onFinish();
//						progressHUD.dismiss();
//						Log.e("gogogo","fff请求结束了");
//
//					}
//				});
	}

	public void ZFBpay() {

//		orderIdZFB = new Date().getTime()+"18354121368";
////		Log.e("XXX","orderId=" + orderId);
		String value = "sdhy" + cardNo + strMoney.substring(0, strMoney.length() - 1) + "order";
		String Make = MD5.encode(value);
		progressHUD = ProgressHUD.show(OnlineActivity.this);
		RequestParams params = new RequestParams();
		params.put("CardNo", cardNo);
		params.put("PayMoney", strMoney.substring(0, strMoney.length() - 1));
		params.put("Make", Make);
		params.put("SerialNo", "1");
		params.put("PayBank", "ZFB");
		params.put("ZZFlag", "1");
		params.put("MobileNo", "");
		params.put("TerminalType", "1");
		String jsss = "CardNo=" + cardNo + "&PayMoney=" + 1 + "&Make=" + Make + "&MobileNo=" + "" + "&SerialNo=" + 1 + "&PayBank=" + "ZFB" + "&ZZFlag=" + 1;

		String url= ConstData.ICRecharge+"ICRecharge/pay!genOrder.action";
//		String url= "http://192.168.2.178:8080/ICRecharge/pay!genOrderJson.action";
		Log.e("gogogo",url+"?"+jsss);

		RequstClient.get(url,
				params, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
						new LoadDatahandler() {
							@Override
							public void onStart() {
								super.onStart();
							}

							@Override
							public void onSuccess(String data) {
								super.onSuccess(data);
								Log.e("gogogo",getResources().getString(R.string.requestsuc)+data.toString());
								JSONObject json = null;
								try {
									json = new JSONObject(data.toString());
									Log.e("gogogo",strMoney+"*******************");
									if (json.getString("success").equals("true")) {
										// strMD5 = MD5ccb.encode("sdhy" + uname +
										// strMoney + "order");// cardNo,strMoney
										orderIdZFB = json.getString("orderId");
										Log.e("gogogo",getResources().getString(R.string.requestsuc2)+orderId);

										handler.sendEmptyMessage(500);
										strMoney = strMoney.substring(0, strMoney.length() - 1);
										cardnamezfb=  json.getString("name");
                                        cardidzfb=  json.getString("idno");
									}else {
										rescode=json.getString("msg");
										handler.sendEmptyMessage(600);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}



							}

							@Override
							public void onFailure(String error, String message) {
								super.onFailure(error, message);
								Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
							}

							@Override
							public void onFinish() {
								super.onFinish();
								progressHUD.dismiss();
							}
						}));









//		OkGo.<String>post(url)
//				//.tag()
//				.params(params)
//				// .headers("Authorization", "本地存储Token")
//				.execute(new StringCallback() {
//					@Override
//					public void onSuccess(Response<String> response) {
//						Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
//						JSONObject json = null;
//						try {
//							json = new JSONObject(response.body().toString());
//							Log.e("gogogo",strMoney+"*******************");
//							if (json.getString("success").equals("true")) {
//								// strMD5 = MD5ccb.encode("sdhy" + uname +
//								// strMoney + "order");// cardNo,strMoney
//								orderIdZFB = json.getString("orderId");
//								Log.e("gogogo",getResources().getString(R.string.requestsuc2)+orderId);
//
//								handler.sendEmptyMessage(500);
//								strMoney = strMoney.substring(0, strMoney.length() - 1);
//
//							}else {
//								rescode=json.getString("msg");
//								handler.sendEmptyMessage(600);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//
//
//					}
//
//					@Override
//					public void onError(Response<String> response) {
//						super.onError(response);
//
//						Log.e("gogogo","sss请求失败了");
//					}
//					@Override
//					public void onStart(Request<String, ? extends Request> request) {
//						super.onStart(request);
//
//						Log.e("gogogo","eeee开始请求" );
//
//					}
//					@Override
//					public void onFinish() {
//						super.onFinish();
//						progressHUD.dismiss();
//						Log.e("gogogo","fff请求结束了");
//
//					}
//				});




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
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			resultunifiedorder = result;
			Log.e("XXX", "result" + result);
			genPayReq();// 生成签名参数
			sendPayReq();
		}
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
			packageParams.add(new BasicNameValuePair("body", OnlineActivity.this.getResources().getString(R.string.chargetoast)));// 商品描述
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));// 商户号
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));// 随机字符串
			packageParams.add(new BasicNameValuePair("notify_url", ConstData.ICRecharge+"ICRecharge/pay!mircPayCallBack.action"));// 通知地址  https://111.53.145.128:8443/ICRecharge/pay!mircPayZZCallBack.action
			packageParams.add(new BasicNameValuePair("out_trade_no", orderId));// 商户订单号
			packageParams.add(new BasicNameValuePair("total_fee", String.valueOf((Integer.parseInt(strMoney)*100))));// 总金额
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
		String packageSign = main.smart.common.util.MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
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

		return main.smart.common.util.MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	private void genPayReq() {
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		Log.e("XXX","Constants.MCH_ID" + Constants.MCH_ID);
		if (resultunifiedorder != null) {
			req.prepayId = resultunifiedorder.get("prepay_id");
			req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
		} else {
			Toast.makeText(OnlineActivity.this, "prepayid", Toast.LENGTH_SHORT).show();
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
		String appSign = main.smart.common.util.MD5.getMessageDigest(sb.toString().getBytes());
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
		boolean success =		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);

		Log.i(">>>>>", req.partnerId);
	}

	/**
	 * 构造支付订单参数列表
	 *
	 * @param
	 * @param app_id
	 * @param
	 * @return
	 */
	public Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
		String desc= OnlineActivity.this.getResources().getString(R.string.chargetoast);
		Map<String, String> keyValues = new HashMap<String, String>();
		keyValues.put("app_id", app_id);
		keyValues.put("biz_content",
				"{"+"\""+"timeout_express"+"\""+":"+"\""+"30m"+"\""+","+"\""+"product_code"+"\""+":"+"\""+"QUICK_MSECURITY_PAY"+"\""+"," +"\""+"total_amount"+"\""+":"+"\"" + strMoney
						+ "\""+","+"\""+"subject"+"\""+":"+"\""+desc+"\""+","+"\""+"body"+"\""+":"+"\""+desc+"\""+","+"\""+"out_trade_no"+"\""+":"+"\"" +orderIdZFB+ "\""+"}");



		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		keyValues.put("timestamp", getTimestamp());// "2016-07-29 16:55:53"

		keyValues.put("version", "1.0");
		keyValues.put("notify_url", ConstData.ICRecharge+"ICRecharge/pay!aLiPayCallBack.action");//111.53.145.128:8443

		return keyValues;
	}

	/**
	 * 要求外部订单号必须唯一。
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
				case  SDK_PAY_FLAG: {
					// Toast.makeText(OnlineActivity.this, "orderId=" + orderId,
					// Toast.LENGTH_SHORT).show();

					Log.e("gogo",msg.obj.toString());
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息

					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(OnlineActivity.this, R.string.chargesuc, Toast.LENGTH_SHORT).show();
						aldialognew=new AlertDialog(OnlineActivity.this);


						aldialognew.builder().setTitle(getResources().getString(R.string.tishi))
								.setMsg(getResources().getString(R.string.zhifufanhui))
								.setPositiveButton(getResources().getString(R.string.queren), new OnClickListener() {
									@Override
									public void onClick(View v) {
										aldialognew.cacle();
									}
								}).setNegativeButton(getResources().getString(R.string.quxiao), new OnClickListener() {
							@Override
							public void onClick(View v) {
								aldialognew.cacle();

							}
						}).show();

					}else if (TextUtils.equals(resultStatus, "6001")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(OnlineActivity.this, R.string.chargefailqu, Toast.LENGTH_SHORT).show();

					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(OnlineActivity.this, R.string.chargefail, Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case 100:

				default:
					break;
			}
		};
	};
	//获取软件版本号
	private int getVersionCode(){
		int versionCode = 0;
		try  {
			//获取软件版本号，对应AndroidManifest.xml下android:versionCode

			versionCode = OnlineActivity.this.getPackageManager().getPackageInfo(OnlineActivity.this.getApplicationInfo().packageName, 0).versionCode;
			Log.e("updateversionmanager","versioncode="+ String.valueOf(versionCode));
		} catch (PackageManager.NameNotFoundException e){
			e.printStackTrace();
		}
		return versionCode;
	}



	@Override
	protected void onResume() {
		super.onResume();
		strMoney="";
	}


}
