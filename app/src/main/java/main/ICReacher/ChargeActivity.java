package main.ICReacher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.ApiAddress;
import main.smart.activity.OnlineActivity;
import main.smart.common.util.ConstData;
import main.smart.common.util.Constant;
import main.smart.common.util.DBHandler;
import main.smart.common.util.DesPBOC2;
import main.smart.common.util.HYENCRY;
import main.smart.common.util.Utils;
import main.smart.rcgj.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/***
 * 确认充值
 */
public class ChargeActivity extends Activity implements OnClickListener {
    private TextView cardNoText;// 卡号
    private TextView balanceText;// 余额
    private TextView balanceLabel;
    private TextView moneyText;// 充值金额
    private TextView moneyLabel;
    private Button submitBtn;// 提交
    private String money;// 充值金额
    private String orderId;// 订单号
    private TextView toHomeText;
    private Button toHomeBtn;
    private MyCount mc;
    private String cardNo;
    private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;
    private int receiveTime = 0;// 接收到服务器数据时间(int)(System.currentTimeMillis()/1000L)-t1>20)
    private RelativeLayout black;
    private String TAG = "mifare";
    private String action;
    String strEntryKey = null;
    private String balance;
    private Intent intent;
    private TextView succLabel,lblCardNo;
    private String order;//订单编号
    private String type="";
    private LinearLayout yuesy;
    private String phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        SharedPreferences sp = ChargeActivity.this.getSharedPreferences("Session", Activity.MODE_PRIVATE);
        phone = sp.getString("username", "");
        cardNoText = (TextView) findViewById(R.id.textCardNo);
        balanceText = (TextView) findViewById(R.id.textBalance);
        balanceLabel = (TextView) findViewById(R.id.lblBalance);
        yuesy= (LinearLayout) findViewById(R.id.yuesy);
        moneyText = (TextView) findViewById(R.id.textMoney);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        toHomeText = (TextView) findViewById(R.id.textToHome);
        toHomeBtn = (Button) findViewById(R.id.btnToHome);
        moneyLabel = (TextView) findViewById(R.id.lblMoney);
        lblCardNo= (TextView) findViewById(R.id.lblCardNo);
        black = (RelativeLayout) findViewById(R.id.home_black);
        submitBtn.setOnClickListener(this);
        toHomeBtn.setOnClickListener(this);
        black.setOnClickListener(this);
        submitBtn.setVisibility(View.GONE);
        toHomeText.setVisibility(View.GONE);
        toHomeBtn.setVisibility(View.GONE);
        mc = new MyCount(10000, 1000);
        intent = getIntent();
        // 获取卡号和余额
        cardNo = intent.getStringExtra("cardNo");
        balance = intent.getStringExtra("balance");
        action = intent.getStringExtra("action");
        order= intent.getStringExtra("orderId");
        type = intent.getStringExtra("type");
        String ccbParam = intent.getStringExtra("CCBPARAM");
        if (ccbParam != null) {
            Map<String, String> mapParam = Utils.queryStringParser(ccbParam);
            Log.i(TAG, "金额是 ==============" + mapParam.get("PAYMENT"));
            cardNo = mapParam.get("REMARK1") == null ? "" : mapParam.get("REMARK1");
            money = mapParam.get("PAYMENT") == null ? "" : mapParam.get("PAYMENT");
            action = "write";
        }
        Log.e("卡内余额", balance + "");
        cardNoText.setText(cardNo);
        succLabel = (TextView) findViewById(R.id.lblSuccInfo);
        /*
         * 此处为外部链接跳转到该页面
         */
        if (action.equals("read")) {
            succLabel.setVisibility(View.GONE);
            balanceLabel.setVisibility(View.VISIBLE);
            balanceText.setVisibility(View.VISIBLE);
            balanceText.setText(Utils.FenToYuan(balance));

//            submitBtn.setText("开始补登");

            moneyThread.start();
        } else if (action.equals("balance")) {
            succLabel.setVisibility(View.GONE);
            balanceLabel.setVisibility(View.VISIBLE);
            balanceText.setVisibility(View.VISIBLE);
            balanceText.setText(Utils.FenToYuan(balance));
            moneyLabel.setVisibility(View.GONE);
            moneyText.setVisibility(View.GONE);
          //  submitBtn.setText("开始补登");
            submitBtn.setVisibility(View.GONE);
            toHomeText.setVisibility(View.VISIBLE);
            toHomeBtn.setVisibility(View.VISIBLE);
            toHomeText.setText("5 秒后返回首页");
            mc.start();
        } else if (action.equals("write")) {// 建行返回支付成功通知

            if(cardNo.equals("")){
                succLabel.setText("您已取消了充值操作，请手动点击返回");
                succLabel.setVisibility(View.VISIBLE);
                yuesy.setVisibility(View.GONE);
                lblCardNo.setVisibility(View.GONE);
                moneyLabel.setVisibility(View.GONE);
                toHomeBtn.setVisibility(View.VISIBLE);
            }else{
                succLabel.setBackgroundColor(getResources().getColor(R.color.bg_green));
                succLabel.setVisibility(View.VISIBLE);
                balanceLabel.setVisibility(View.GONE);
                balanceText.setVisibility(View.GONE);
                balanceLabel.setVisibility(View.VISIBLE);
                balanceText.setVisibility(View.VISIBLE);
                yuesy.setVisibility(View.GONE);
                balanceText.setText(Utils.FenToYuan(balance));
              //  submitBtn.setText("开始补登");
                moneyThread.start();
            }

        }else if (action.equals("readccb")) {
            succLabel.setVisibility(View.GONE);
            balanceLabel.setVisibility(View.VISIBLE);
            balanceText.setVisibility(View.VISIBLE);
            yuesy.setVisibility(View.GONE);
            balanceText.setText(Utils.FenToYuan(balance));
          //  submitBtn.setText("开始补登");
            moneyThread.start();


        }else if (action.equals("readorder")){
            succLabel.setVisibility(View.GONE);
            balanceLabel.setVisibility(View.VISIBLE);
            balanceText.setVisibility(View.VISIBLE);
            yuesy.setVisibility(View.GONE);
            balanceText.setText(Utils.FenToYuan(balance));
          //  submitBtn.setText("开始补登");
            moneyThread.start();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                Message message = handler.obtainMessage();
                message.what = 7;
                message.sendToTarget();

                Log.e("read", "dddddddd");
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                 if (initConnect()) {
//        // 登录包
//        sendData(ConstData.getLoginBags());
//        Log.e("read", "444444rrrrrrrrrrrrr"+Utils.bytesToHexString(ConstData.getLoginBags()));
//        if(recRunnablecpu.isAlive()){
//           // recRunnablecpu.stop();
//        }else{
//            recRunnablecpu.start();
//        }
//
//        //new Thread(recRunnablecpu).start(); // 接收数据
//
//            } else {
//                Message message2 = handler.obtainMessage();
//
//                message2.what = Constant.WHAT_CONN_DB_FAILURE;
//                message2.obj = "服务器连接异常，请检查网络";
//                message2.sendToTarget();
//
//            }
//    }
//}.start();
             //   new Thread(loginRunnable).start();// 发送数据
                break;

            case R.id.btnToHome:
                mc.cancel();
                Intent intent = new Intent(ChargeActivity.this, OnlineActivity.class);
                intent.putExtra("cardNo",cardNo);
                intent.putExtra("balance",balance);
                startActivity(intent);
                ChargeActivity.this.finish();
                break;

            case R.id.home_black:
                mc.cancel();
//                Intent intent2 = new Intent(ChargeActivity.this, OnlineActivity.class);
//                startActivity(intent2);
                ChargeActivity.this.finish();
                break;

        }
    }

    Thread moneyThread = new Thread() {
        @Override
        public void run() {
            Log.e("***************", cardNoText.getText().toString() + "拿到CPU卡的补登金额");
            Map<String, String> map = new HashMap<String, String>();
            map.put("cardNo", HYENCRY.encode(cardNoText.getText().toString()));
            map.put("userName", "");
            map.put("terminalType", "0");
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                formBody.add(entry.getKey(), entry.getValue());//传递键值对参数
                Log.e("***************", entry.getKey() + "=" + entry.getValue());
            }
            //formBody.add("username","zhangsan");//传递键值对参数
            Request request = new Request.Builder()//创建Request 对象。
                    .url(ApiAddress.mainApiread + DBHandler.ACTION_CHARGEMONEY)
                    .post(formBody.build())//传递请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = handler.obtainMessage();
                    message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                    message.obj = "服务器连接失败，请检查网络";
                    message.sendToTarget();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    try {
                        JSONObject json = new JSONObject(result);
                        Log.e("***************", "回调" + json);
                        if (json != null && json.getString("success") != null) {
                            String success = json.getString("success");
                            if (success.equals("true")) {
                                String payment = json.getString("payment");
                                if (payment == null || payment.trim().equals("")) {
                                    message.what = Constant.WHAT_GET_PAYMENT_SUCCESS;
                                    message.obj = "0.00";
                                } else {
                                    Log.e(null, "那这里me" + payment);
                                    orderId = json.getString("orderId");
                                    String newcard = cardNoText.getText().toString();
                                    message.what = Constant.WHAT_GET_PAYMENT_ISZERO;
                                    message.obj = payment;
                                    Log.e(null, "zheli拿到CPU卡的补登金额" + payment);
                                }
                            } else {
                                String msg = json.getString("msg");
                                msg = msg == null || msg.trim().equals("") ? "获取充值金额失败" : msg;
                                message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                                message.obj = msg;

//                                message.what = Constant.WHAT_GET_PAYMENT_ISZERO;
//                                message.obj = "1";

                            }
                        } else {
                            message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                            message.obj = "获取充值金额失败";
                        }
                    } catch (JSONException e) {
                        Log.e(null, "异常低昂单");
                        message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                        message.obj = "获取充值金额失败，信息：" + e.getMessage();
                    }
                    message.sendToTarget();
                }
            });


        }
    };


    public boolean initConnect() {
        try {
            Log.e("read", "rrrrrrrrrrrrr");
            socket = new Socket();
            SocketAddress socAddress = new InetSocketAddress(ConstData.Ip, Integer.parseInt(ConstData.Port));
            Log.e("read", "22222rrrrrrrrrrrrr");
            socket.connect(socAddress, 10000);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            receiveTime = (int) (System.currentTimeMillis() / 1000L);
            ConstData.isConnect = true;
            ConstData.initNetFlag = true;
            Log.e("read", "3333333rrrrrrrrrrrrr");
            return true;
        } catch (IOException e) {
            Log.e("read", "ssssssssssssssssss");
            ConstData.isConnect = false;
            Message message2 = handler.obtainMessage();

            message2.what = Constant.WHAT_CONN_DB_FAILURE;
            message2.obj = "服务器连接异常，请检查网络";
            message2.sendToTarget();
            e.printStackTrace();
            return false;
        }
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.WHAT_GET_PAYMENT_SUCCESS:
                    moneyText.setText("0.00 "+getResources().getString(R.string.yuan));
                    submitBtn.setVisibility(View.GONE);
                    toHomeText.setVisibility(View.VISIBLE);
                    toHomeBtn.setVisibility(View.VISIBLE);
                    toHomeText.setText("5 秒后返回首页");
                    mc.start();
                    break;
                case Constant.WHAT_GET_PAYMENT_ISZERO:
                    money = msg.obj.toString();
                    moneyText.setText(money +getResources().getString(R.string.yuan));
                    if (Integer.parseInt(money) > 0) {
                        submitBtn.setVisibility(View.VISIBLE);
                        toHomeText.setVisibility(View.GONE);
                        toHomeBtn.setVisibility(View.GONE);
                    } else {
                        submitBtn.setVisibility(View.GONE);
                        toHomeText.setVisibility(View.VISIBLE);
                        toHomeBtn.setVisibility(View.VISIBLE);
                        toHomeText.setText("5 秒后返回首页");
                        mc.start();
                    }
                    break;
                case Constant.WHAT_GET_PAYMENT_FAILURE:
                    moneyText.setText("0.00 "+getResources().getString(R.string.yuan));
                    toHomeBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(ChargeActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    String strKey = (String) msg.obj;
                    Intent intent = new Intent(ChargeActivity.this, ReadActivity.class);
                    intent.putExtra("status", "charge");
                    intent.putExtra("money", money);
                    intent.putExtra("cardNo", cardNoText.getText().toString());
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("key", strKey);
                    startActivity(intent);
                    ChargeActivity.this.finish();
                    break;

            }
        }
    };

    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            /*
             * Intent intent = new Intent(ChargeActivity.this,
             * MainActivity.class); startActivity(intent);
             */
            ChargeActivity.this.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            toHomeText.setText(millisUntilFinished / 1000 + " 秒后返回首页");
        }
    }

    @Override
    public void onBackPressed() {
        // 按返回键时返回到主页面
        /*
         * Intent intent = new Intent(ChargeActivity.this, MainActivity.class);
         * startActivity(intent);
         */
        ChargeActivity.this.finish();
    }



    public boolean reConnect() {
        SocketAddress socAddress = new InetSocketAddress(ConstData.Ip, Integer.parseInt(ConstData.Port));
        try {
            socket = new Socket();
            socket.connect(socAddress, 3000);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            ConstData.isConnect = true;
           // sendData(ConstData.getLoginBags());
        //    recRunnablecpu.start();
        } catch (IOException e) {
            ConstData.isConnect = false;
        }
        return ConstData.isConnect;
    }

    public void stop() {
        try {
            ConstData.isConnect = false;
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            initConnect();
           // if (initConnect()) {
                // 登录包
                sendData(ConstData.getLoginBags());
                Log.e("read", "444444rrrrrrrrrrrrr"+Utils.bytesToHexString(ConstData.getLoginBags()));

                recRunnablecpu.start();; // 接收数据

//            } else {
//                Message message2 = handler.obtainMessage();
//
//                message2.what = Constant.WHAT_CONN_DB_FAILURE;
//                message2.obj = "服务器连接异常，请检查网络";
//                message2.sendToTarget();
//
//            }

        }
    };

    Thread recRunnablecpu = new Thread() {
    //Runnable recRunnablecpu = new Runnable() {
        @Override
        public void run() {
            int size = 0;
            /*
             * boolean isSucc = false; boolean isSuccf = false; boolean
             * isSuccfull = false; String strEntryKey = null;
             */
            exit:
            while (true) {
                if (!ConstData.isConnect) {
                    return;
                }
                try {
                    int intRec = in.available();
                    while (intRec == 0) {
                        intRec = in.available();
                        // 比较收包时间大于一分半,判定服务器socket连接已关闭
                    }

                    // Log.e("收包时间",Integer.toString(receiveTime));
                    byte[] byeRec = new byte[intRec];
                    int count = in.read(byeRec);
                    size = count;
                    while (true) {
                        if (size < 7) {
                            break;
                        }

                        // 80是手持机发送的包，A0是服务器发回的
                       // a0 00 2a 02 00 03 bd010000000002000000000400000000065db7e0b844014501465db7

                        if (byeRec[0] == (byte) 160) {
                            Log.d(null, "ResB:" + Utils.bytesToHexString(byeRec));
                            // 包长度两个字节，一个字节8位
                            int len = ((byeRec[1] & 0xff) << 8) + ((byeRec[2] & 0xff));
                            if (len < 7) {
                                break;
                            } else if (byeRec.length == len) {
                                if ((byeRec[0] == (byte) 160) && (byeRec[6]) == (byte) 0xAA) {// 登录包，有回应后发送加密机通讯包
                                    Log.e("read", "ddddddddrrrrrrrrrrrrr"+Utils.bytesToHexString(byeRec));
                                    //byte[] buffer = ConstData.getEncryptBags(cardNoText.getText().toString());
                                  //  Log.e("read", "getEncryptBags:" + Utils.bytesToHexString(buffer).toUpperCase());
                                    //sendData(buffer);// 发送加密密钥包
                                    Message message = handler.obtainMessage();
                                    message.what = 7;
                                    //  message.obj = strEntryKey;
                                    message.sendToTarget();
                                    break;
                                } else if ((byeRec[0] == (byte) 160) && byeRec[6] == (byte) 0xBD) {// 加密机回应包，解析出主密钥
                                    // 加密因子
                                    Log.e("read", "mmmmmmmmmrrrrrrrrrrrrr"+Utils.bytesToHexString(byeRec));

                                    byte[] byeEncryRatio = ConstData.getValue(byeRec, (byte) 0x46);
                                    byte[] byeResData = ConstData.getValue(byeRec, (byte) 0x12);
                                    char[] chrRes = new char[byeResData.length];
                                    for (int i = 0; i < byeResData.length; i++) {
                                        chrRes[i] = (char) (byeResData[i] & 0xff);
                                    }

                                    String strCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                                    String strKey = strCurDate + Utils.bytesToHexString(byeEncryRatio) + strCurDate
                                            + strCurDate;
                                    String strResData = new String(chrRes);
                                    String strEntryKey1 = DesPBOC2.Decry3DESStrHex(strResData.substring(0, 16), strKey);
                                    String strEntryKey2 = DesPBOC2.Decry3DESStrHex(strResData.substring(16, 32),
                                            strKey);
                                    strEntryKey = strEntryKey1 + strEntryKey2;

                                    Log.e("read", "这里拿到了cpu卡的strEntryKey:" + strEntryKey);
                                    Message message = handler.obtainMessage();
                                    message.what = 7;
                                    message.obj = strEntryKey;
                                    message.sendToTarget();

                                    break exit;
                                }
                            }
                        } else {
                            break;
                        }
                    }
                } catch (Exception e) {
            //        recRunnablecpu.stop();
           //         stop();
                    reConnect();

                }
            }


        }
    };


    public void sendData(byte[] b) {
        try {
            out = getOut();
            Log.e(null, "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + b);
            out.write(b);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "数据发送失败");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

//            Intent in = new Intent(ChargeActivity.this, MainActivity.class);
//            startActivity(in);
            ChargeActivity.this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }


    /**
     * 字节转换
     *
     * @param src
     * @return
     */
    public static String bytesToHexString2(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
