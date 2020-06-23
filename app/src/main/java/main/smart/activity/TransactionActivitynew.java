package main.smart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.Adapter.Orderadapter;
import main.ICReacher.MD5;
import main.smart.common.SmartBusApp;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.ConstData;
import main.smart.common.util.Constant;
import main.smart.rcgj.R;
import main.utils.dialog.ProgressHUD;

public class TransactionActivitynew extends Activity {
    private ListView new_alllist;// 下拉刷新，上拉加载的列表
    Thread OrderThread;
    private String phone;
    private String IMEI;
    private ArrayList<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
    private Orderadapter Adapterw;// 自定义的适配器
    private RelativeLayout order_black3;
    private KProgressHUD progressHUD;
    private String cardNo;
    private LinearLayout zanwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_activitynew);
      //  StatusBarUtil.setStatusBarMode(this, true, R.color.app_theme_color);
        new_alllist = (ListView) findViewById(R.id.cuslist);
        cardNo = getIntent().getStringExtra("cardNo");
        zanwu=findViewById(R.id.zanwu);
        order_black3 = (RelativeLayout) findViewById(R.id.order_black3);
        order_black3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransactionActivitynew.this.finish();
            }
        });
        sp();
        initData();

//        new_alllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Log.e(null, li.get(position).get("status")+"");
//                if((li.get(position).get("status")+"").equals("支付成功，等待补登")){
//                    Intent intent = new Intent();
//                    intent.putExtra("action", "readorder");
//                    intent.putExtra("orderId", li.get(position).get("orderId")+"");
//                    intent.putExtra("cardNo", li.get(position).get("cardNo")+"");
//                    intent.putExtra("balance", li.get(position).get("payment")+"");
//                    if((li.get(position).get("cardNo")+"").length()>10){
//                        intent.putExtra("type", "cpu");
//                    }else{
//                        intent.putExtra("type", "m1");
//                    }
//
//                    intent.setClass(TransactionActivity.this, ChargeActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

    }

    private void sp() {
        SharedPreferences sp = TransactionActivitynew.this.getSharedPreferences("Session", Activity.MODE_PRIVATE);
        phone = sp.getString("username", "");
        IMEI = sp.getString("IMEI", "");
        SharedPreferences.Editor editor = sp.edit();
        editor.commit();

    }

    private void initData() {
        progressHUD = ProgressHUD.show(TransactionActivitynew.this);
        String strMD5 = MD5.encode("sdhy" + cardNo + "order");
        //String url = "http://192.168.2.178:8080/ICRecharge/pay!listByCardNo.action";
        String url = ConstData.ICRechargene + "ICRecharge/pay!listByCardNo.action";

        RequestParams param = new RequestParams();
        param.put("CardNo", cardNo);
        param.put("Make", strMD5);
        param.put("type", "1");
        param.put("userName", phone);
        param.put("terminalType", "0");
        param.put("city", "RC");
        RequstClient.post(url,
                param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
                        new LoadDatahandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
//                                mFlylayout.startRefresh();
                            }

                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                Log.e("gogogo", getResources().getString(R.string.zhucesuc) + data.toString());
                        String resp = data.toString();
                        if (!resp.equals("[]")) {
                            Message message = handler.obtainMessage();
                            try {
                                JSONObject json = new JSONObject(resp);
                                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                // 通过Gson 解析后台传过来的数据
                                Map<String, Object> map1 = gson.fromJson(resp, new TypeToken<Map<String, Object>>() {
                                }.getType());
                                if (json != null && json.getString("success") != null) {
                                    String success = json.getString("success");
                                    if (success.equals("true")) {
                                        li = (ArrayList<Map<String, Object>>) map1.get("data");
                                        handler.sendEmptyMessage(0);
                                    } else {
                                        message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                                        message.obj = json.getString("msg");

                                    }
                                } else {
                                    message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                                    message.obj = "没有查到信息";
                                }
                            } catch (JSONException e) {
                                message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                                message.obj = "读取异常";
                            }

                    message.sendToTarget();
                        } else {
                            // zanwu.setVisibility(View.VISIBLE);
                        }

                            }

                            @Override
                            public void onFailure(String error, String message) {
                                super.onFailure(error, message);
                                zanwu.setVisibility(View.VISIBLE);
                                Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                progressHUD.dismiss();
                            }
                        }));











//        OkGo.getInstance().init(TransactionActivitynew.this.getApplication());
//        String strMD5 = MD5.encode("sdhy" + cardNo + "order");
//        HttpParams params = new HttpParams();
//        params.put("CardNo", cardNo);
//        params.put("Make", strMD5);
//        params.put("type", "1");
//        params.put("userName", phone);
//        params.put("terminalType", "0");
//        params.put("city", "RC");
//
//        Map ma=new HashMap();
//        ma.put("CardNo", cardNo);
//        ma.put("Make", strMD5);
//        ma.put("type", "1");
//        ma.put("userName", phone);
//        ma.put("terminalType", "0");
//        ma.put("city", "RC");
//        //String url = ConstData.ICRechargene + "ICRecharge/pay!listByCardNo.action";
//        String url = "http://hello.shenzhuo.vip:10544/ICRecharge/pay!listByCardNo.action";
//        Log.e("soso",""+ma);
//        Log.e("soso","url==="+url);
//
//        //http://192.168.2.190:8155/ICRecharge/pay!listByCardNo.action?CardNo=03104931601010000346&city=RC&Make=f8a596539f717b5eded85cb7be17cdf8&type=1&userName=&terminalType=0
//        OkGo.<String>post(url)
//                //.tag()
//                .params(params)
//                // .headers("Authorization", "本地存储Token")
//                .execute(new StringCallback() {
//
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                        Log.e("gogogo", getResources().getString(R.string.zhucesuc) + response.body().toString());
//                        String resp = response.body().toString();
//                        if (!resp.equals("[]")) {
//                            Message message = handler.obtainMessage();
//                            try {
//                                JSONObject json = new JSONObject(resp);
//                                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//                                // 通过Gson 解析后台传过来的数据
//                                Map<String, Object> map1 = gson.fromJson(resp, new TypeToken<Map<String, Object>>() {
//                                }.getType());
//                                if (json != null && json.getString("success") != null) {
//                                    String success = json.getString("success");
//                                    if (success.equals("true")) {
//                                        li = (ArrayList<Map<String, Object>>) map1.get("data");
//                                        handler.sendEmptyMessage(0);
//                                    } else {
//                                        message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
//                                        message.obj = json.getString("msg");
//
//                                    }
//                                } else {
//                                    message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
//                                    message.obj = "没有查到信息";
//                                }
//                            } catch (JSONException e) {
//                                message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
//                                message.obj = "读取异常";
//                            }
//
//                            message.sendToTarget();
//                        } else {
//                            // zanwu.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onError(com.lzy.okgo.model.Response<String> response) {
//                        super.onError(response);
//                        zanwu.setVisibility(View.VISIBLE);
//                        Log.e("gogogo", "请求失败了3333");
//                    }
//
//                    @Override
//                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
//                        super.onStart(request);
//                        Log.e("gogogo", "eeee开始请求");
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        progressHUD.dismiss();
//                        Log.e("gogogo", "请求结束了");
//
//                    }
//                });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.WHAT_GET_PAYMENT_FAILURE:
                    Toast.makeText(TransactionActivitynew.this,(msg.obj).toString(),Toast.LENGTH_SHORT).show();
                    break;

                case 0:

                    if (li != null) {
                        if (li.size() == 0) {
                            zanwu.setVisibility(View.VISIBLE);
                            Toast.makeText(TransactionActivitynew.this, "当前无该用户的交易记录", Toast.LENGTH_SHORT).show();
                        } else {
                            zanwu.setVisibility(View.GONE);
                            Adapterw = new Orderadapter(TransactionActivitynew.this,
                                    li);
                            Log.e("soso", "3333333333333"+new_alllist);
                            new_alllist.setAdapter(Adapterw);

                        }

                    } else {
                        Toast.makeText(TransactionActivitynew.this, "当前无该用户的交易记录", Toast.LENGTH_SHORT).show();

                    }


                    break;

                default:
                    break;
            }
        }
    };

}



