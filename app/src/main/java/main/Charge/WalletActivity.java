package main.Charge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.ApiAddress;
import main.smart.common.http.SHAActivity;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.dialog.ProgressHUD;
import main.utils.utils.OrderInfoUtil2_0;
import main.utils.utils.SharePreferencesUtils;

public class WalletActivity extends BaseActivity {
    @BindView(R.id.mewallet)
    LinearLayout mewallet;
    @BindView(R.id.meAccount)
    LinearLayout meAccount;
    @BindView(R.id.charge)
    Button charge;
    Intent intent;
    @BindView(R.id.moneywallet)
    TextView moneywallet;
    @BindView(R.id.alljob_black)
    RelativeLayout alljob_black;
    private Button tv_tuikuan;
    private KProgressHUD progressHUD;
    SharePreferencesUtils sharePreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tv_tuikuan = findViewById(R.id.tv_tuikuan);
        tv_tuikuan.setOnClickListener(this);
        okgo();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        okgo();
    }

    // 点击事件
    @Override
    public void onClick(View v) {
        if (v == tv_tuikuan) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
            builder.setTitle(getResources().getString(R.string.tuikuan_header));
            builder.setMessage(getResources().getString(R.string.tuikuan_tishi));
            builder.setCancelable(false);
            final android.app.Dialog dialog = builder.create();
            builder.setPositiveButton(getResources().getString(R.string.tuikuan_sure), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO 自动生成的方法存根

                    sharePreferencesUtils = new SharePreferencesUtils();
                    String userName = sharePreferencesUtils.getString(WalletActivity.this, "userName", "");
                    long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
                    String str = String.valueOf(time);
                    OkGo.getInstance().init(WalletActivity.this.getApplication());
                    HttpParams params = new HttpParams();
                    params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
                    ;
                    params.put("timestamp", str);
                    params.put("encrypt_type", "applyRefund_OneKey");
                    params.put("nonce", "2208800081");
                    params.put("encrypt_kb", "141");
                    params.put("encrypt_iv", userName);
                    String url = ApiAddress.qrcode;

                    OkGo.<String>post(url)
                            //.tag()
                            .params(params)
                            // .headers("Authorization", "本地存储Token")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("gogogo", getResources().getString(R.string.zhucesuc) + response.body().toString());
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

                                    Log.e("gogogo", "请求失败了");
                                }

                                @Override
                                public void onStart(Request<String, ? extends Request> request) {
                                    super.onStart(request);
                                    Log.e("gogogo", "eeee开始请求");

                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
//									handler.sendEmptyMessage(3);
                                    Log.e("gogogo", "请求结束了");

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
        }
    }

    private void okgo() {
        progressHUD = ProgressHUD.show(WalletActivity.this);
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(WalletActivity.this, "userName", "");
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        OkGo.getInstance().init(WalletActivity.this.getApplication());
        HttpParams params = new HttpParams();
        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        params.put("timestamp", str);
        params.put("encrypt_type", "getBalance");
        params.put("nonce", "2208800081");
        params.put("encrypt_kb", "141");
        params.put("encrypt_data", userName);


        String url = ApiAddress.qrcode;

        OkGo.<String>post(url)
                //.tag()
                .params(params)
                // .headers("Authorization", "本地存储Token")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("gogogo", getResources().getString(R.string.zhucesuc) + response.body().toString());

                        String resp = response.body().toString();
                        if (resp.equals("FFFE")) {
                            moneywallet.setText("0");

                        } else {
                            moneywallet.setText(resp);
//                            if (resp.equals("0")){
//                                tv_tuikuan.setVisibility(View.GONE);
//                            }
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("gogogo", "请求失败了");
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        Log.e("gogogo", "eeee开始请求");

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        progressHUD.dismiss();
                        Log.e("gogogo", "请求结束了");

                    }
                });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected boolean isHasHeader() {
        return false;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick({R.id.mewallet, R.id.meAccount, R.id.charge, R.id.alljob_black})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mewallet:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.meAccount:
                intent = new Intent(this, TransactionActivity.class);
                startActivity(intent);
                break;
            case R.id.charge:
                intent = new Intent(this, ActivityCardCharge.class);
                startActivity(intent);
                break;
            case R.id.alljob_black:
                WalletActivity.this.finish();
                break;

        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(WalletActivity.this, getResources().getString(R.string.tuikuan_success), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(WalletActivity.this, getResources().getString(R.string.tuikuan_faile), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
