package main.ICReacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.ApiAddress;
import main.Charge.ActivityCardCharge;
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

import static main.smart.common.util.Utils.bytesToHexString;

@SuppressLint("SimpleDateFormat")
public class ReadActivity extends Activity implements OnClickListener {
    private ImageView cardImg, flagfalseImg;
    private View flagImg;
    //private TextView readTitleText;
    private TextView readToastText;
    private TextView readMessText;// 数据库返回的微信充值记录
    private Button controlBtn;
    private NfcAdapter nfcAdapter;
    String action = "";// 从哪个动作跳转过来的
    private int defaultSum = 0;// 默认每次充值多少钱，充值10元，实际写入的数值是1000分
    private int afterChargeMoney = 0;// 充值完成后卡内余额
    private String cardNo = "";// 充值卡卡号
    private String key = "";
    // 充值金额
    private boolean chargeFlag = false;// 充值写卡标志 ，false :尚未将金额写入卡中 ，true:已写
    private boolean isnews = true;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private Tag tagFromIntent;
    private SimpleDateFormat sdfLong = new SimpleDateFormat("yyyyMMddHHmmss");
    private String TAG = "mifare";
    private RelativeLayout black;// 返回按钮
    private TextView text;
    private String orderId="";//订单号
    private String serialNo;//序列号
    private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;
    private int receiveTime = 0;// 接收到服务器数据时间(int)(System.currentTimeMillis()/1000L)-t1>20)
    private String Random="";

    private  StringBuffer sbCharge;
    private ByteBuffer chargeCmdBuffer;
    private IsoDep isodep;
    private byte[] byeBalanceRsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);

        flagImg = (View) findViewById(R.id.flagImg);
        cardImg = (ImageView) findViewById(R.id.cardImg);
        flagfalseImg = (ImageView) findViewById(R.id.flagfalseImg);
        //readTitleText = (TextView) findViewById(R.id.readTitle);
        readToastText = (TextView) findViewById(R.id.readToast);
        readMessText = (TextView) findViewById(R.id.readMess);
        controlBtn = (Button) findViewById(R.id.toHomeBtnId);
        black = (RelativeLayout) findViewById(R.id.alljob_black);
        text = (TextView) findViewById(R.id.tttt);
        //readTitleText.setText("请刷卡");
        readToastText.setText("等待卡内容");
        controlBtn.setOnClickListener(this);
        controlBtn.setVisibility(View.GONE);
        flagImg.setVisibility(View.GONE);
        cardImg.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        black.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
//                startActivity(intent);
                ReadActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        action = intent.getStringExtra("status");


        if (action == null) {
            ReadActivity.this.finish();
        }

        PackageManager pm = getPackageManager();
        boolean nfc = pm.hasSystemFeature(PackageManager.FEATURE_NFC);
        Log.e("是否支持", nfc ? "支持 NFC" : "不支持 NFC");
        initNFC();
    }

    public void initNFC() {
        // 获取默认的NFC控制器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            //readTitleText.setText("设备不支持 NFC！");
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            //readTitleText.setText("请在系统设置中先启用NFC功能！");
            finish();
            return;
        }
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        // ndef.addCategory("*/*");
        mFilters = new IntentFilter[]{ndef};// 过滤器
        mTechLists = new String[][]{new String[]{IsoDep.class.getName()},
                new String[]{MifareClassic.class.getName()}};// 允许扫描的标签类型
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        action = intent.getStringExtra("status");
        if (nfcAdapter == null) {
            //readTitleText.setText("设备不支持 NFC！");
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            //readTitleText.setText("请在系统设置中先启用 NFC 功能！");
            finish();
            return;
        }
        // 得到是否检测到ACTION_TECH_DISCOVERED触发
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, mTechLists);
        if (isnews) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                // 处理该intent
                processIntent(getIntent());
                isnews = false;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    private void processIntent(Intent intent) {

        // 授权密钥
        // 对于所有基于MifareClassic的卡来说，每个区最后一个块叫Trailer，16个byte，
        // 主要来存放读写该区的key，可以有A，B两个KEY，每个key长6byte
        // readBlock的索引从0开始到63，每个扇区有4个block
        // DE,87,75,7C,33,03
        // 3B,B3,7B,10,72,1D
        // byte[] NkeyA_1 = { (byte) 222, (byte) 135, (byte) 117, (byte) 124,
        // (byte) 51, (byte) 3 };
        // byte[] NkeyA_1 = { (byte) 63, (byte) 139, (byte) 127, (byte) 48,
        // (byte) 102, (byte) 214 };//19cz库的000011241的KeyA
        // byte[] NkeyB_1 = { (byte) 59, (byte) 179, (byte) 123, (byte) 16,
        // (byte) 114, (byte) 29 };
        // byte[] NkeyB_1 = { (byte) 103, (byte) 110, (byte) 36, (byte) 26,
        // (byte) 161, (byte) 96 };巡更卡KeyB
        // byte[] NkeyB_1 = { (byte) 25, (byte) 121, (byte) 37, (byte) 59,
        // (byte) 179, (byte) 86 };//19cz库的000011241的KeyB

        // 取出封装在intent中的TAG
        tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.e("读取tag标签", tagFromIntent.toString());
        String reverseSerial = bytesToHexString(tagFromIntent.getId());
        serialNo = Utils.hexStringReverseOrder(reverseSerial, reverseSerial.length());

        Log.e("read", serialNo);
        Log.e("read", getResources().getString(R.string.xulie )+ Long.parseLong(serialNo, 16));

        // 你可以在检测到nfc标签后使用getTechList()方法来查看你所检测的tag到底支持哪些nfc标准。
        String tagStr = "";
        for (int i = 0; i < tagFromIntent.getTechList().length; i++) {
            tagStr += tagFromIntent.getTechList()[i] + " ";
        }
        if (tagStr.indexOf("IsoDep") > 0) {// cpu卡
            handleCardCpu(tagFromIntent);
        } else if (tagStr.indexOf("MifareClassic") > 0) {// m1卡
            Toast.makeText(ReadActivity.this, "不支持的卡类型", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ReadActivity.this, "此卡不支持充值", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 处理cpu卡
     */
    public void handleCardCpu(Tag tagFromIntent) {
        Log.e(null, "@@@@@@@==============="+action);
        isodep = IsoDep.get(tagFromIntent);
        try {
            isodep.connect();
            //Toast.makeText(ReadActivity.this, R.string.xulie, Toast.LENGTH_SHORT).show();
            Log.e("read", "@@@@@@@==============="+action);
            if (action.equals("balance") || action.equals("read") || action.equals("onLine")) {
                Intent intent = new Intent();
                intent.putExtra("action", action);
//                byte[] mf = {(byte) '2', (byte) 'P', (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y',
//                        (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F', (byte) '0', (byte) '1',};
//                byte[] mfRsp = isodep.transceive(getSelectCommand(mf));
//                Log.e("read", "mfRsp:" + HexToString(getSelectCommand(mf)));
               // 00a404000e325041592e5359532e444446303100
                // select Main Application
                // byte[] szt = { (byte) 'P', (byte) 'A', (byte) 'Y', (byte)
                // '.',
                // (byte) 'S', (byte) 'Z', (byte) 'T' };
                //byte[] szt = {(byte) 0xA0, 0x00, 0x00, 0x06, 0x32, 0x01, 0x01, 0x05};
               // 00A40000023F01
               // 00A40000023F00
                byte[] szt1 = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x00};
                byte[] sztRsp1 = isodep.transceive(szt1);
                Log.e("read", "sztRsp1:" + HexToString(sztRsp1));
                byte[] szt = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x01 };
                byte[] sztRsp = isodep.transceive(szt);
                Log.e("read", "sztRsp:" + HexToString(sztRsp));
                Log.e("read", "你大爷的 ");
                // 读取公共应用基本文件(0x15)
                StringBuffer sbBaseInfo = new StringBuffer(Integer.toBinaryString(0x15));
               // StringBuffer sbBaseInfo = new StringBuffer(Integer.toBinaryString(0x3F01));
                Log.e("read", "@@@@@******@@==============="+sbBaseInfo.toString());
                sbBaseInfo.reverse();
                if (sbBaseInfo.length() < 5) {
                    sbBaseInfo.insert(0, "00000");
                }
                if (sbBaseInfo.length() > 5) {
                    sbBaseInfo.delete(0, sbBaseInfo.length() - 5);
                }
                //String strCmd = Integer.toHexString(Integer.parseInt(sbBaseInfo.toString(), 2));

                String strCmd = Integer.toHexString(Integer.parseInt(sbBaseInfo.insert(0, "100").toString(), 2));
                strCmd = strCmd.substring(strCmd.length() - 2, strCmd.length());
                //String strCmd = "013f";

                Log.e("read", "strCmd:" + strCmd);
                //Log.d(TAG, "strCmd:" + strCmd);
                //byte[] byeBaseCmd = {0x00, (byte) 0xB0, (byte) Integer.parseInt(strCmd, 16), 0x00, 0x00};
                byte[] byeBaseCmd = {0x00, (byte) 0xB0, (byte) Integer.parseInt(strCmd, 16), 0x00, 0x00};
                byte[] byeBaseInfo = isodep.transceive(byeBaseCmd);
                Log.e("read", "@@@@@@@==============="+HexToString(byeBaseInfo).length());
                Log.e("read", "byeBaseInfo:" + HexToString(byeBaseInfo));
                if (HexToString(byeBaseInfo).length() >= 20) {
                    cardNo = HexToString(byeBaseInfo).substring(24, 40);
                    Log.e(null, "cardNo:=====" + cardNo);
                    intent.putExtra("cardNo", cardNo);
                } else {
                    Log.e(null, "cardNo:=====nononoooo" );
                    Toast.makeText(ReadActivity.this, "暂不支持此卡类型", Toast.LENGTH_LONG).show();
                }

//                if (HexToString(byeBaseInfo).length() >= 20) {
//                    cardNo = HexToString(byeBaseInfo).substring(20, 40);
//                    Log.d(TAG, "cardNo:" + cardNo);
//                    intent.putExtra("cardNo", cardNo);
//                } else {
//                    Toast.makeText(ReadActivity.this, "暂不支持此卡类型", Toast.LENGTH_LONG).show();
//                }
                Log.e("read", "===============");

                byte[] byeBalance = {(byte) 0x80, (byte) 0x5C, 0x00, 0x02, 0x04};
                 byeBalanceRsp = isodep.transceive(byeBalance);
                Log.e("read", "===============balanceRsp:" + HexToString(byeBalanceRsp));
                Log.e("read", "===============22balanceRsp:" + bytesToHexString(byeBalanceRsp));

//                if (byeBalanceRsp != null && byeBalanceRsp.length > 4) {
//                    int intCash = byteToInt(byeBalanceRsp, 4);
//                    Log.d(TAG, "余额(分)：" + intCash);
//                    intent.putExtra("balance", intCash);
//                    if (action.equals("balance") || action.equals("read")) {
//                        intent.setClass(ReadActivity.this, ChargeActivity.class);
//                    } else if (action.equals("onLine")) {
//                        intent.setClass(ReadActivity.this, ActivityCardCharge.class);
//                    }
//                    startActivity(intent);
//                    ReadActivity.this.finish();
//                }

                if (byeBalanceRsp != null && byeBalanceRsp.length > 4) {
                    double intCash = byteToInt(byeBalanceRsp, 4);
                    Log.e("read", "yyyyyeeee：" + byteToInt(byeBalanceRsp, 4));
                    intent.putExtra("balance", String.valueOf(intCash));
                    if (action.equals("balance") || action.equals("read")) {
                        intent.setClass(ReadActivity.this, ChargeActivity.class);
                    } else if (action.equals("onLine")) {
                        intent.setClass(ReadActivity.this, ActivityCardCharge.class);
                    }
                    startActivity(intent);
                    ReadActivity.this.finish();
                }
            } else if (action.equals("charge") && !chargeFlag) {

                Map<String, String> map = new HashMap<String, String>();
                map.put("cardNo", HYENCRY.encode(cardNo.toString()));
                defaultSum = Integer.parseInt(getIntent().getStringExtra("money")) * 100;// 充值金额，单位为分
                cardNo = getIntent().getStringExtra("cardNo");
                key = getIntent().getStringExtra("key");
                orderId = getIntent().getStringExtra("orderId");

                byte[] mf = {(byte) '2', (byte) 'P', (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y',
                        (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F', (byte) '0', (byte) '1',};
                byte[] mfRsp = isodep.transceive(getSelectCommand(mf));
                Log.d(TAG, "mfRsp:" + HexToString(mfRsp));

                byte[] szt1 = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x00};
                byte[] sztRsp1 = isodep.transceive(szt1);
                Log.e("read", "sztRsp1:" + HexToString(sztRsp1));
                byte[] szt = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x01 };
                byte[] sztRsp = isodep.transceive(szt);

                /**************** 校验卡号是否一致 开始 *******************/
                // 读取公共应用基本文件(0x15)
                StringBuffer sbBaseInfo = new StringBuffer(Integer.toBinaryString(0x15));
                sbBaseInfo.reverse();
                if (sbBaseInfo.length() < 5) {
                    sbBaseInfo.insert(0, "00000");
                }
                if (sbBaseInfo.length() > 5) {
                    sbBaseInfo.delete(0, sbBaseInfo.length() - 5);
                }
                String strCmd = Integer.toHexString(Integer.parseInt(sbBaseInfo.insert(0, "100").toString(), 2));
                strCmd = strCmd.substring(strCmd.length() - 2, strCmd.length());
                Log.d(TAG, "strCmd:" + strCmd);
                byte[] byeBaseCmd = {0x00, (byte) 0xB0, (byte) Integer.parseInt(strCmd, 16), 0x00, 0x00};
                byte[] byeBaseInfo = isodep.transceive(byeBaseCmd);
                Log.d(TAG, "byeBaseInfo:" + HexToString(byeBaseInfo));

                String curCardNo = HexToString(byeBaseInfo).substring(24, 40);

                if (curCardNo == null || cardNo == null || !curCardNo.equals(cardNo)) {
                    Toast.makeText(ReadActivity.this, "卡号两次读取不一致，请更换卡片后重试", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.e("read", "byeBaseInfo:111111111111111111111111" );

                    updateStatus();
                }


            }else if(action.equals("ordelist")){


                Intent intent = new Intent();
                intent.putExtra("action", action);
                byte[] mf = { (byte) '2', (byte) 'P', (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y',
                        (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F', (byte) '0', (byte) '1', };
                byte[] mfRsp = isodep.transceive(getSelectCommand(mf));
                Log.d(TAG, "mfRsp:" + HexToString(mfRsp));
                Log.e(null, "mfRsp:" + HexToString(mfRsp));
                // select Main Application
                // byte[] szt = { (byte) 'P', (byte) 'A', (byte) 'Y', (byte)
                // '.',
                // (byte) 'S', (byte) 'Z', (byte) 'T' };
                byte[] szt = { (byte) 0xA0, 0x00, 0x00, 0x06, 0x32, 0x01, 0x01, 0x05 };
                byte[] sztRsp = isodep.transceive(getSelectCommand(szt));
                // 读取公共应用基本文件(0x15)
                StringBuffer sbBaseInfo = new StringBuffer(Integer.toBinaryString(0x15));
                sbBaseInfo.reverse();
                if (sbBaseInfo.length() < 5) {
                    sbBaseInfo.insert(0, "00000");
                }
                if (sbBaseInfo.length() > 5) {
                    sbBaseInfo.delete(0, sbBaseInfo.length() - 5);
                }
                String strCmd = Integer.toHexString(Integer.parseInt(sbBaseInfo.insert(0, "100").toString(), 2));
                strCmd = strCmd.substring(strCmd.length() - 2, strCmd.length());
                Log.d(TAG, "strCmd:" + strCmd);
                byte[] byeBaseCmd = { 0x00, (byte) 0xB0, (byte) Integer.parseInt(strCmd, 16), 0x00, 0x00 };
                byte[] byeBaseInfo = isodep.transceive(byeBaseCmd);
                Log.d(TAG, "byeBaseInfo:" + HexToString(byeBaseInfo));
                if (HexToString(byeBaseInfo).length() >= 20) {
                    cardNo = HexToString(byeBaseInfo).substring(20, 40);
                    Log.d(TAG, "cardNo:" + cardNo);
                    intent.putExtra("cardNo", cardNo);
                } else {
                    Toast.makeText(ReadActivity.this, "暂不支持此卡类型", Toast.LENGTH_LONG).show();
                }

                byte[] byeBalance = { (byte) 0x80, (byte) 0x5C, 0x00, 0x02, 0x04 };
                byeBalanceRsp = isodep.transceive(byeBalance);
                Log.d(TAG, "balanceRsp:" + HexToString(byeBalanceRsp));
                if (byeBalanceRsp != null && byeBalanceRsp.length > 4) {
//                    int intCash = byteToInt(byeBalanceRsp, 4);
//                    Log.d(TAG, "余额(分)：" + intCash);
//                    intent.putExtra("balance", intCash);
//
//
//                    intent.setClass(ReadActivity.this, OrderActivitynew.class);
//                    startActivity(intent);
//                    ReadActivity.this.finish();
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("read", "#shibai###########：" + e);

            Toast.makeText(ReadActivity.this, "读取卡片失败，请重新放置卡片", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (isodep != null) {
                    isodep.close();
                }
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }





    public InputStream getIn() {
        return in;
    }


    private byte[] getSelectCommand(byte[] aid) {
        final ByteBuffer cmd_pse = ByteBuffer.allocate(aid.length + 6);
        cmd_pse.put((byte) 0x00) // CLA Class
                .put((byte) 0xA4) // INS Instruction
                .put((byte) 0x04) // P1 Parameter 1
                .put((byte) 0x00) // P2 Parameter 2
                .put((byte) aid.length) // Lc
                .put(aid).put((byte) 0x00); // Le
        return cmd_pse.array();
    }

    private String HexToString(byte[] data) {
        String temp = "";
        for (byte d : data) {
            temp += String.format("%02x", d);
        }
        return temp;
    }

    private int byteToInt(byte[] b, int n) {
        int ret = 0;
        for (int i = 0; i < n; i++) {
            ret = ret << 8;
            ret |= b[i] & 0x00FF;
        }
        if (ret > 100000 || ret < -100000)
            ret -= 0x80000000;
        return ret;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.WHAT_WRITE_CARD_SUCCESS:
                    flagImg.setVisibility(View.VISIBLE);
                    flagfalseImg.setVisibility(View.GONE);
                    cardImg.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    //readTitleText.setText("充值成功");
                    readToastText.setText(Utils.FenToYuan(defaultSum));
                    readMessText.setText(Utils.FenToYuan(afterChargeMoney));
                    controlBtn.setVisibility(View.VISIBLE);// 显示返回首页按钮

                    break;
                case Constant.WHAT_CONN_DB_FAILURE:
                    flagImg.setVisibility(View.GONE);
                    flagfalseImg.setVisibility(View.VISIBLE);
                    cardImg.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    //readTitleText.setText("网络连接异常！");
                    Toast.makeText(ReadActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Constant.WHAT_GET_KEYB_FAILURE:
                    flagImg.setVisibility(View.GONE);
                    flagfalseImg.setVisibility(View.VISIBLE);
                    cardImg.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    //readTitleText.setText("读卡失败！");
                    Toast.makeText(ReadActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Constant.WHAT_GET_PAYMENT_FAILURE:
                    Toast.makeText(ReadActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Constant.WHAT_GET_PAYMENT_SUCCESS_XIE:
                    isodep = IsoDep.get(tagFromIntent);
                    try {
                        isodep.connect();
//                        byte[] szt1 = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x00};
//                        byte[] sztRsp1 = isodep.transceive(szt1);
                        //Log.e("read", "sztRsp1:" + HexToString(sztRsp1));
                        byte[] szt = { (byte)0x00,(byte)  0xA4,  0x00, 0x00, 0x02, 0x3F, 0x01 };
                        byte[] sztRsp = isodep.transceive(szt);


                        /**************** 校验卡号是否一致 开始 *******************/
                        // 读取公共应用基本文件(0x15)
                        StringBuffer sbBaseInfo = new StringBuffer(Integer.toBinaryString(0x15));
                        sbBaseInfo.reverse();
                        if (sbBaseInfo.length() < 5) {
                            sbBaseInfo.insert(0, "00000");
                        }
                        if (sbBaseInfo.length() > 5) {
                            sbBaseInfo.delete(0, sbBaseInfo.length() - 5);
                        }
                        String strCmd = Integer.toHexString(Integer.parseInt(sbBaseInfo.insert(0, "100").toString(), 2));
                        strCmd = strCmd.substring(strCmd.length() - 2, strCmd.length());
                        Log.d(TAG, "strCmd:" + strCmd);
                        byte[] byeBaseCmd = {0x00, (byte) 0xB0, (byte) Integer.parseInt(strCmd, 16), 0x00, 0x00};
                        byte[] byeBaseInfo = isodep.transceive(byeBaseCmd);
                        Log.d(TAG, "byeBaseInfo:" + HexToString(byeBaseInfo));
                        String curCardNo = HexToString(byeBaseInfo).substring(24, 40);
                        if (curCardNo == null || cardNo == null || !curCardNo.equals(cardNo)) {
                            Toast.makeText(ReadActivity.this, "卡号两次读取不一致，请更换卡片后重试", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
// 读取余额
                            byte[] byeBalance = {(byte) 0x80, (byte) 0x5C, 0x00, 0x02, 0x04};
                            byeBalanceRsp = isodep.transceive(byeBalance);
                            Log.d("read", "balanceRspyuyuyuyu:" + HexToString(byeBalanceRsp));
                            /**************** 校验卡号是否一致 结束 *******************/
                           // 002000000 3123123
                            byte[] sztping = { (byte)0x00,(byte)  0x20,  0x00, 0x00, 0x03,0x12, 0x31, 0x23 };
                            byte[] sztRsping = isodep.transceive(sztping);
                            Log.e("read", "pingHH6666666666666666666666666&&"+HexToString(sztRsping));
                             sbCharge = new StringBuffer(Integer.toHexString(defaultSum));
                            Log.e("read", defaultSum+"HH6666666666666666666666666&&"+sbCharge);
                            if (sbCharge.length() < 8) {
                                sbCharge.insert(0, "00000000");
                                sbCharge.delete(0, sbCharge.length() - 8);
                            }
                            Log.e("read", defaultSum+"pp6666666666666666666666666&&"+sbCharge);
                            sbCharge.append("000000100001");
                            byte[] byeChargeCmd = {(byte) 0x80, 0x50, 0x00, 0x02, 0x0B, 0x01};
                            chargeCmdBuffer = ByteBuffer.allocate(byeChargeCmd.length + sbCharge.length() / 2 + 1);
                            chargeCmdBuffer.put(byeChargeCmd).put(Utils.hexString2Bytes(sbCharge.toString())).put((byte) 0x00);
                            Log.e("read", "圈存指令6666666666666666666666666"+ HexToString(chargeCmdBuffer.array()));
                           // Log.d(TAG, "圈存指令:" + HexToString(chargeCmdBuffer.array()));
                            byte[] byeInitQC = isodep.transceive(chargeCmdBuffer.array());

                            chargeCmdBuffer.clear();
                            // 第4-5字节为交易序号，第8-11字节为伪随机数
                            Log.e("read", "初始化圈存cccccccccccc:" + HexToString(byeInitQC));
                           // 805000020b01 0000006400000010000100
                            /****************** 校验 MAC 开始 ***********************/
                            String strMAC1 = bytesToHexString(byeInitQC, 12, 15);
                            chargeCmdBuffer = ByteBuffer.allocate(8);
                            chargeCmdBuffer.put(byeInitQC[8]).put(byeInitQC[9]).put(byeInitQC[10]).put(byeInitQC[11])
                                    .put(byeInitQC[4]).put(byeInitQC[5]).put((byte) 0x80).put((byte) 0x00);
                            Log.e("read", "src:" + bytesToHexString(chargeCmdBuffer.array()));
                            Random=bytesToHexString(chargeCmdBuffer.array());
                            Log.e("read", "strMAC1==="+strMAC1);
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        if (initConnect()) {
                                            // 登录包
                                            sendData(ConstData.getLoginBags());
                                            Log.e("read", "444444rrrrrrrrrrrrr"+Utils.bytesToHexString(ConstData.getLoginBags()));
                                            if(recRunnablecpu.isAlive()){
                                                // recRunnablecpu.stop();
                                            }else{
                                                recRunnablecpu.start();
                                            }

                                            //new Thread(recRunnablecpu).start(); // 接收数据

                                        } else {
                                            Message message2 = handler.obtainMessage();

                                            message2.what = Constant.WHAT_CONN_DB_FAILURE;
                                            message2.obj = "服务器连接异常，请检查网络";
                                            message2.sendToTarget();

                                        }
                                    }
                                }.start();


//                            // 更新数据库状态
                            chargeFlag = true; // 更新已经充值写卡标志之后，再处理连接数据库
                            updateChargeStatus(cardNo, defaultSum, afterChargeMoney,
                                    orderId);
                        }

                      //  isodep.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ReadActivity.this, "读取卡片失败，请重新放置卡片", Toast.LENGTH_SHORT).show();
                    } finally {
//                        try {
//                            if (isodep != null) {
//                               // isodep.close();
//                            }
//                        } catch (IOException e) {
//                            Log.e(TAG, Log.getStackTraceString(e));
//                        }
                    }
                    break;

            }

        }
    };


    /***
     * 更新数据库充值状态
     */
    private void updateChargeStatus(final String cardNo, final int chargeMoney, final int afterChargeMoney,
                                    final String orderId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cardNo", HYENCRY.encode(cardNo));
        map.put("chargeMoney", Integer.toString(chargeMoney));
        map.put("afterChargeMoney", Integer.toString(afterChargeMoney));
        map.put("userName", HYENCRY.encode("100001"));
        map.put("terminalType", "0");
        map.put("orderId", HYENCRY.encode(orderId));
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            formBody.add(entry.getKey(), entry.getValue());//传递键值对参数
        }
        Request request = new Request.Builder()//创建Request 对象。
                .url(ApiAddress.mainApiread + DBHandler.ACTION_UPDATE_CHARGE_STATUS)
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
                    JSONObject json2 = new JSONObject(result);
                    if (json2 != null && json2.getString("success") != null) {
                        String success2 = json2.getString("success");
                        if (success2.equals("true")) {
                            message.what = Constant.WHAT_WRITE_CARD_SUCCESS;
                        } else {
                            String msg = json2.getString("msg");
                            msg = msg == null || msg.trim().equals("") ? "数据库状态更新失败" : msg;
                            message.what = Constant.WHAT_CONN_DB_FAILURE;
                            message.obj = msg;
                        }
                    }
                } catch (JSONException e) {
                    message.what = Constant.WHAT_CONN_DB_FAILURE;
                    message.obj = "数据库状态更新失败：" + e.getMessage();
                    Log.e("数据库状态更新失败", e.getMessage());
                }
                message.sendToTarget();
            }
        });


    }

    private void updateStatus() {
//        Message message = handler.obtainMessage();
//        message.what = Constant.WHAT_GET_PAYMENT_SUCCESS_XIE;
//        message.sendToTarget();
        Map<String, String> map = new HashMap<String, String>();
        map.put("cardNo", HYENCRY.encode(cardNo));
        map.put("orderId", HYENCRY.encode(orderId));
        map.put("userName", "");
        map.put("terminalType", "0");
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            formBody.add(entry.getKey(), entry.getValue());//传递键值对参数
        }
        Request request = new Request.Builder()//创建Request 对象。
                .url(ApiAddress.mainApiread  + DBHandler.ACTION_UPDATE_MOBILE_STATUS_ORDER)
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
                    if (json != null && json.getString("success") != null) {
                        String success = json.getString("success");
                        if (success.equals("true")) {
                            message.what = Constant.WHAT_GET_PAYMENT_SUCCESS_XIE;
                        } else {
                            String msg = json.getString("msg");
                            msg = msg == null || msg.trim().equals("") ? "写卡前状态失败" : msg;
                            message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                            message.obj = msg;
                        }
                    }
                } catch (JSONException e) {
                    message.what = Constant.WHAT_GET_PAYMENT_FAILURE;
                    message.obj = "生成订单失败，信息：" + e.getMessage();
                }
                message.sendToTarget();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toHomeBtnId:// 返回首页
//                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
//                startActivity(intent);
                ReadActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // 按返回键时返回到主页面
        /*
         * Intent intent = new Intent(ReadActivity.this, MainActivity.class);
         * startActivity(intent);
         */
        ReadActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (action.equals("onLine") || action.equals("charge")) {
                String order2 = "";
                SharedPreferences sp = ReadActivity.this.getSharedPreferences("recharge", Activity.MODE_PRIVATE);
                order2 = sp.getString("orderid", "");
                Editor editor = sp.edit();
                editor.commit();
                orderId = order2;

            }
//            Intent intent = new Intent(ReadActivity.this, MainActivity.class);
//            startActivity(intent);
            ReadActivity.this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }















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
                                    byte[] buffer = ConstData.getEncryptBags(cardNo,Random,defaultSum);
                                    Random="";
                                    Log.e("read", "getEncryptBags:" + Utils.bytesToHexString(buffer).toUpperCase());
                                       sendData(buffer);// 发送加密密钥包
//                                    Message message = handler.obtainMessage();
//                                    message.what = 7;
//                                    //  message.obj = strEntryKey;
//                                    message.sendToTarget();
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
                                    Log.e("read", "46xiang"+Utils.bytesToHexString(byeEncryRatio));
                                    Log.e("read", "12xiang"+Utils.bytesToHexString(byeResData));


                                    String strCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                                    String strKey = strCurDate + Utils.bytesToHexString(byeEncryRatio) + strCurDate
                                            + strCurDate;
//                                    String strKey = strCurDate + strCurDate + strCurDate
//                                            + strCurDate;
                                    String strResData = new String(chrRes);
                                    String strEntryKey1 = DesPBOC2.Decry3DESStrHex(strResData.substring(0, 16), strKey);
                                    String strEntryKey2 = DesPBOC2.Decry3DESStrHex(strResData.substring(16, 32),
                                            strKey);
                                    String strEntryKey = strEntryKey1 + strEntryKey2;

                                    Log.e("read", "这里拿到了cpu卡的strEntryKey:" + hex2Str(strEntryKey));

                                    try {

                                            String strMac = hex2Str(strEntryKey);
                                            Log.e("read", "strMac:" + strMac);
                                            sbCharge.delete(0, sbCharge.length());
                                            sbCharge.append("805200000B").append(ConstData.riqi).append(strMac.substring(8, 16));
                                            Log.e("read", "sbCharge:" + sbCharge.toString());
                                            // 圈存交易
                                            byte[] byeRet = isodep.transceive(Utils.hexString2Bytes(sbCharge.toString()));
                                            Log.e("read", "圈存交易:" + HexToString(byeRet));
                                            // 获取充值后余额
                                            byte[] balance = {(byte) 0x80, (byte) 0x5C, 0x00, 0x02, 0x04};
                                            byte[] balanceRsp = isodep.transceive(balance);
                                            Log.e("read", "balanceRsp:" + HexToString(balanceRsp));
                                            if (balanceRsp != null && balanceRsp.length > 4) {
                                                afterChargeMoney = byteToInt(balanceRsp, 4);
                                                Log.e("read", "余额(分)：" + afterChargeMoney);
                                            }
                                            isodep.close();
                                                                    // 更新数据库状态
                            chargeFlag = true; // 更新已经充值写卡标志之后，再处理连接数据库
                            updateChargeStatus(cardNo, defaultSum, afterChargeMoney,
                                    orderId);

                                    } catch (IOException e) {

                                    }







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
    public Socket getSocket() {
        return socket;
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

    public  String hex2Str(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String h = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(h, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }

}
