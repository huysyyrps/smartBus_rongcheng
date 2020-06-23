package main.Charge;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import com.tencent.mm.sdk.modelpay.PayReq;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;

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
import main.ApiAddress;
import main.alipay.PayResult;
import main.smart.activity.OnlineActivity;
import main.smart.common.http.SHAActivity;
import main.smart.common.util.Constants;
import main.smart.common.util.Util;
import main.smart.rcgj.R;
import main.smart.rcgj.wxapi.WXPayEntryActivity;
import main.utils.dialog.ProgressHUD;
import main.utils.utils.FlowRadioGroup;
import main.utils.utils.OrderInfoUtil2_0;
import main.utils.utils.SharePreferencesUtils;


/**
 * @作者：LKY @时间：20190917
 * @描述：显示充值信息页面
 */
public class ActivityCardCharge extends Activity implements  OnClickListener, AdapterView.OnItemClickListener {
	//	private TextView cardNoText;// 卡号
//	private TextView balanceText;// 余额
	private ImageView iv_back;
	private FlowRadioGroup rg;
	private RelativeLayout alljob_black2;
	private RadioButton  rb;
	private Button btn_next;
	private TextView tv_tuikuan;
	//private CProgressDialog dialog;
	private static String strMoney="";
	private String TAG = "mifare";
	private Thread moneyThread;
//	private int[] itemID = { R.drawable.noxin, R.drawable.alpay, R.drawable.wechat };
//	private String[] itemName = new String[3];
//	private String pay = "nongxin";
	private int[] itemID = { R.drawable.alpay, R.drawable.wechat };
	private String[] itemName = new String[2];
	private String pay = "zhifubao";
	private ListView mListView;
	private ListViewAdapter mListViewAdapter;

	private List<Map<String, Object>> list;
	private EditText zidingyi;
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
	private SharePreferencesUtils sharePreferencesUtils;
	private String userName;
	private KProgressHUD progressHUD;
	private TextView tv_nickname;
	private RelativeLayout alljob_black;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_charge);
		version=getVersionCode();
		sharePreferencesUtils = new SharePreferencesUtils();
		userName = sharePreferencesUtils.getString(ActivityCardCharge.this, "userName", "");
		Log.e("---",ActivityCardCharge.this.getResources().getString(R.string.nx));
		//itemName[0]=ActivityCardCharge.this.getResources().getString(R.string.nx);
		itemName[0]=ActivityCardCharge.this.getResources().getString(R.string.zfb);
		itemName[1]=ActivityCardCharge.this.getResources().getString(R.string.wx);
//		cardNoText = (TextView) findViewById(R.id.textCardNo);
//		balanceText = (TextView) findViewById(R.id.textBalance);
		rg = (FlowRadioGroup) findViewById(R.id.rg);
		btn_next = (Button) findViewById(R.id.btn_login);
		mListView = (ListView) findViewById(R.id.listView);
		zidingyi = (EditText) findViewById(R.id.zidingyi);
		tv_nickname= (TextView) findViewById(R.id.tv_nickname);
		tv_tuikuan = findViewById(R.id.tv_tuikuan);
		alljob_black= (RelativeLayout) findViewById(R.id.alljob_black);
		alljob_black.setOnClickListener(this);
		zidingyi.addTextChangedListener(mTextWatcher);
		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(this);

		btn_next.setOnClickListener(this);
		tv_tuikuan.setOnClickListener(this);
		sb = new StringBuffer();
		req = new PayReq();
		list = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < 3; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("ID", itemID[i]);
//			map.put("Name", itemName[i]);
//			list.add(map);
//		}
		for (int i = 0; i < 2; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", itemID[i]);
			map.put("Name", itemName[i]);
			list.add(map);
		}
		okgo();
		mListViewAdapter = new ListViewAdapter(getApplicationContext(), list);
		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(this);
		rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
//		strMoney = rb.getText().toString();
//		 strMoney = strMoney.substring(0, strMoney.length() - 1);
		zidingyi.setOnTouchListener(new View.OnTouchListener() {
		@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				if(rg.getCheckedRadioButtonId()!=-1){
					rg.clearCheck();
					Log.e("mmmm",getResources().getString(R.string.requestsuc)+strMoney);
					if(!zidingyi.getText().toString().equals("")){
						strMoney=zidingyi.getText().toString()+getResources().getString(R.string.yuanmoney);
					}else{
						strMoney="";
					}
				}
				return false;
			}
		});
		// 自定义回调函数
		mListViewAdapter.setOncheckChanged(new ListViewAdapter.OnMyCheckChangedListener() {

			@Override
			public void setSelectID(int selectID) {
//				if (selectID == 0) {
//					pay = "nongxin";
//				} else if (selectID == 1) {
//					pay = "zhifubao";
//				} else if (selectID == 2) {
//					pay = "weixin";
//				}
				if (selectID == 0) {
					pay = "zhifubao";
				} else if (selectID == 1) {
					pay = "weixin";
				}

				mListViewAdapter.setSelectID(selectID); // 选中位置
				mListViewAdapter.notifyDataSetChanged(); // 刷新适配器
			}
		});

//		alljob_black2 = (RelativeLayout) findViewById(R.id.alljob_black2);
//
//		alljob_black2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ActivityCardCharge.this.finish();
//
//			}
//		});

		Intent intent = getIntent();

		String ccbParam = intent.getStringExtra("CCBPARAM");
		if (ccbParam != null) {
			Log.d(TAG, "ccbParam:" + ccbParam);
		}
		// 获取卡号和余额
		String cardNo = intent.getStringExtra("cardNo");
		int balance = intent.getIntExtra("balance", 0);
//		cardNoText.setText(cardNo);
//		balanceText.setText(Utils.FenToYuan(balance) + " 元");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		okgo();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		if (position == 0) {
//			pay = "nongxin";
//		} else if (position == 1) {
//			pay = "zhifubao";
//		} else if (position == 2) {
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
			ActivityCardCharge.this.finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == tv_tuikuan){
			if(tv_nickname.getText().toString().equals("0")){
				AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCardCharge.this);
				builder.setTitle(getResources().getString(R.string.tuikuan_header));
				builder.setMessage(getResources().getString(R.string.tuikuan_tishi));
				builder.setCancelable(false);
				final android.app.Dialog dialog = builder.create();
				builder.setPositiveButton(getResources().getString(R.string.tuikuan_sure), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根

						sharePreferencesUtils = new SharePreferencesUtils();
						String userName = sharePreferencesUtils.getString(ActivityCardCharge.this, "userName", "");
						long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
						String  str=String.valueOf(time);
						OkGo.getInstance().init(ActivityCardCharge.this.getApplication());
						HttpParams params = new HttpParams();
						params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));;
						params.put("timestamp", str);
						params.put("encrypt_type", "applyRefund_OneKey");
						params.put("nonce", "2208800081");
						params.put("encrypt_kb", "141");
						params.put("encrypt_iv", userName);
						String url= ApiAddress.qrcode;

						OkGo.<String>post(url)
								//.tag()
								.params(params)
								// .headers("Authorization", "本地存储Token")
								.execute(new StringCallback() {
									@Override
									public void onSuccess(Response<String> response) {
										Log.e("gogogo",getResources().getString(R.string.zhucesuc)+response.body().toString());
										String resp = response.body().toString();
										String sss = resp;
										System.out.println("sss=" + sss);
										if (sss.equals("1111")) {
											handler.sendEmptyMessage(2);
										} else {
											handler.sendEmptyMessage(3);
										}


									}

									@Override
									public void onError(Response<String> response) {
										super.onError(response);

										Log.e("gogogo","请求失败了");
									}
									@Override
									public void onStart(Request<String, ? extends Request> request) {
										super.onStart(request);
										Log.e("gogogo","eeee开始请求" );

									}
									@Override
									public void onFinish() {
										super.onFinish();
//									handler.sendEmptyMessage(3);
										Log.e("gogogo","请求结束了");

									}
								});


					}

				});
				builder.setNegativeButton(getResources().getString(R.string.tuikuan_no), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						dialog.dismiss();
					}
				});
				builder.show();
			}else {
				Toast.makeText(this, getResources().getString(R.string.tuikuan_toast), Toast.LENGTH_SHORT).show();
			}
		} else if (v == alljob_black) {
			finish();
		} else if (v == btn_next) {// 立即支付

			Log.e("acher","222222###########"+strMoney);
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



			if(pay.equals("weixin")){
				WXpay();
			}else if(pay.equals("zhifubao")){
				ZFBpay();
			}else if(pay.equals("zhifubao")){
			//	NXpay();
			}


		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 2:
					Toast.makeText(ActivityCardCharge.this, getResources().getString(R.string.tuikuan_success), Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(ActivityCardCharge.this, getResources().getString(R.string.tuikuan_faile), Toast.LENGTH_SHORT).show();
					break;
				case 400://调用微信下订单
					SharePreferencesUtils sharePreferencesUtils = new SharePreferencesUtils();
					sharePreferencesUtils.setString(ActivityCardCharge.this,"wxPayResult","");

//                 startActivity(new Intent(ActivityCardCharge.this,WXPayEntryActivity.class));
					String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
					PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask();
					prePayIdAsyncTask.execute(urlString);
					break;
				case 800://调用微信下订单
					Toast.makeText(ActivityCardCharge.this, R.string.wufa, Toast.LENGTH_SHORT).show();
					//ActivityCardCharge.this.finish();
					break;
				case 500://调用支付宝下订单
					if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
						new AlertDialog.Builder(ActivityCardCharge.this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
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
								PayTask alipay = new PayTask(ActivityCardCharge.this);
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
		progressHUD = ProgressHUD.show(ActivityCardCharge.this);
//		orderId = new Date().getTime()+"18354121368";
//		Log.e("XXX","orderId=" + orderId);
		HttpParams params = new HttpParams();
		params.put("version", version);
		params.put("CardNo", userName);
		String url=ApiAddress.ICRecharge+"ICRecharge/pay!getOrderId.action";
		OkGo.<String>post(url)
				//.tag()
				.params(params)
				// .headers("Authorization", "本地存储Token")
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
						JSONObject json = null;
						try {
							json = new JSONObject(response.body().toString());
							if(json!=null){

									if (json.getString("success").equals("true")) {
										// strMD5 = MD5ccb.encode("sdhy" + uname +
										// strMoney + "order");// cardNo,strMoney
										orderId = json.getString("orderId");
										Log.e("gogogo",getResources().getString(R.string.requestsuc2)+json);
										handler.sendEmptyMessage(400);
										Log.e("gogogo",getResources().getString(R.string.requestsuc2)+strMoney);

										strMoney = strMoney.substring(0, strMoney.length() - 1);
										Log.e("gogogo",getResources().getString(R.string.requestsuc2)+strMoney);

//								handler.sendEmptyMessage(500);
//								strMoney = strMoney.substring(0, strMoney.length() - 1);

									}else{
										Log.e("gogogo","222222222222");
										handler.sendEmptyMessage(800);
									}


							}else{

								handler.sendEmptyMessage(800);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}


					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);

						Log.e("gogogo","sss请求失败了");
					}
					@Override
					public void onStart(Request<String, ? extends Request> request) {
						super.onStart(request);

						Log.e("gogogo","eeee开始请求" );

					}
					@Override
					public void onFinish() {
						super.onFinish();
						progressHUD.dismiss();
						Log.e("gogogo","fff请求结束了");

					}
				});





	}

	public void ZFBpay() {

//		orderIdZFB = new Date().getTime()+"18354121368";
////		Log.e("XXX","orderId=" + orderId);
		progressHUD = ProgressHUD.show(ActivityCardCharge.this);
		HttpParams params = new HttpParams();
		params.put("version", version);
		params.put("CardNo", userName);
		String url=ApiAddress.ICRecharge+"ICRecharge/pay!getOrderId.action";
		OkGo.<String>post(url)
				//.tag()
				.params(params)
				// .headers("Authorization", "本地存储Token")
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
						JSONObject json = null;
						try {
							json = new JSONObject(response.body().toString());
							if (json.getString("success").equals("true")) {
								// strMD5 = MD5ccb.encode("sdhy" + uname +
								// strMoney + "order");// cardNo,strMoney
								orderIdZFB = json.getString("orderId");
								Log.e("gogogo",getResources().getString(R.string.requestsuc2)+orderId);

								handler.sendEmptyMessage(500);
								strMoney = strMoney.substring(0, strMoney.length() - 1);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}


					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);

						Log.e("gogogo","sss请求失败了");
					}
					@Override
					public void onStart(Request<String, ? extends Request> request) {
						super.onStart(request);

						Log.e("gogogo","eeee开始请求" );

					}
					@Override
					public void onFinish() {
						super.onFinish();
						progressHUD.dismiss();
						Log.e("gogogo","fff请求结束了");

					}
				});




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
			packageParams.add(new BasicNameValuePair("body", ActivityCardCharge.this.getResources().getString(R.string.chargetoast)));// 商品描述
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));// 商户号
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));// 随机字符串
			packageParams.add(new BasicNameValuePair("notify_url", ApiAddress.ICRecharge+"ICRecharge/pay!mircPayZZCallBack.action"));// 通知地址  https://111.53.145.128:8443/ICRecharge/pay!mircPayZZCallBack.action
			packageParams.add(new BasicNameValuePair("out_trade_no", orderId));// 商户订单号
			packageParams.add(new BasicNameValuePair("total_fee",String.valueOf((Integer.parseInt(strMoney)*100))));// 总金额
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
			Toast.makeText(ActivityCardCharge.this, "prepayid", Toast.LENGTH_SHORT).show();
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
	public  Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
		String desc=ActivityCardCharge.this.getResources().getString(R.string.chargetoast);
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
		keyValues.put("notify_url", ApiAddress.ICRecharge+"ICRecharge/pay!aLiPayZZCallBack.action");//111.53.145.128:8443

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
				case SDK_PAY_FLAG: {
					// Toast.makeText(ActivityCardCharge.this, "orderId=" + orderId,
					// Toast.LENGTH_SHORT).show();
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息

					String resultStatus = payResult.getResultStatus()
							;
					Log.e("soso","过来的状态"+resultStatus);
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(ActivityCardCharge.this, R.string.chargesuc, Toast.LENGTH_SHORT).show();
						okgo();

					}else if (TextUtils.equals(resultStatus, "6001")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(ActivityCardCharge.this, R.string.chargefailqu, Toast.LENGTH_SHORT).show();
						okgo();

					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(ActivityCardCharge.this, R.string.chargefail, Toast.LENGTH_SHORT).show();
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

			versionCode = ActivityCardCharge.this.getPackageManager().getPackageInfo(ActivityCardCharge.this.getApplicationInfo().packageName, 0).versionCode;
			Log.e("updateversionmanager","versioncode="+String.valueOf(versionCode));
		} catch (PackageManager.NameNotFoundException e){
			e.printStackTrace();
		}
		return versionCode;
	}

	private  void  okgo(){
		progressHUD = ProgressHUD.show(ActivityCardCharge.this);
		sharePreferencesUtils = new SharePreferencesUtils();
		String userName = sharePreferencesUtils.getString(ActivityCardCharge.this, "userName", "");
		long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
		String  str=String.valueOf(time);
		OkGo.getInstance().init(ActivityCardCharge.this.getApplication());
		HttpParams params = new HttpParams();
		params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
		params.put("timestamp", str);
		params.put("encrypt_type", "getBalance");
		params.put("nonce", "2208800081");
		params.put("encrypt_kb", "141");
		params.put("encrypt_data", userName);



		String url= ApiAddress.qrcode;

		OkGo.<String>post(url)
				//.tag()
				.params(params)
				// .headers("Authorization", "本地存储Token")
				.execute(new StringCallback() {

					@Override
					public void onSuccess(Response<String> response) {
						Log.e("gogogo",getResources().getString(R.string.zhucesuc)+response.body().toString());

						String resp = response.body().toString();

						if(resp.equals("FFFE")){
							tv_nickname.setText("0"+getResources().getString(R.string.yuanmoney));
							handler.sendEmptyMessage(800);
						}else{
							tv_nickname.setText(resp+getResources().getString(R.string.yuanmoney));

						}

					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
						Log.e("gogogo","请求失败了");
					}
					@Override
					public void onStart(Request<String, ? extends Request> request) {
						super.onStart(request);
						Log.e("gogogo","eeee开始请求" );

					}
					@Override
					public void onFinish() {
						super.onFinish();
						progressHUD.dismiss();
						Log.e("gogogo","请求结束了");

					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		strMoney="";
	}
}
