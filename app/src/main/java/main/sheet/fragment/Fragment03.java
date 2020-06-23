package main.sheet.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import main.ApiAddress;
import main.Charge.RecordActivity;
import main.Charge.TransactionActivity;
import main.Charge.WalletActivity;
import main.login.LoginActivity;
import main.sheet.AboutMyActivity;
import main.sheet.ChangeWordActivity;
import main.sheet.bean.UpVersion;
import main.sheet.module.UpVersionContract;
import main.sheet.presenter.UpVersionPresenter;
import main.smart.activity.RecordActivitynew;
import main.smart.activity.SettingActivity;
import main.smart.common.http.SHAActivity;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.dialog.ProgressHUD;
import main.utils.utils.SharePreferencesUtils;

/**
 * Created by Administrator on 2019/4/12.
 * 待办列表
 */

public class Fragment03 extends Fragment implements UpVersionContract.View {
    View view;
    Unbinder unbinder;
    @BindView(R.id.wallet)
    LinearLayout wallet;
    @BindView(R.id.me_Account)
    LinearLayout Account;
    @BindView(R.id.transaction)
    LinearLayout transaction;
    @BindView(R.id.wangji)
    LinearLayout wangji;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.methreemoney)
    TextView methreemoney;
    @BindView(R.id.llSharde)
    LinearLayout llSharde;
    @BindView(R.id.tixing)
    LinearLayout tixing;
    @BindView(R.id.llGuanYu)
    LinearLayout llGuanYu;
    @BindView(R.id.llShengJi)
    LinearLayout llShengJi;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
   // private KProgressHUD progressHUD;
    SharePreferencesUtils sharePreferencesUtils;
    private boolean isGetData = false;
    UpVersionPresenter upVersionPresenter;

    TextView yes;
    //LinearLayout llAlertDialog;
    String downData = "";
    String downUrl = "";
    String userName;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Fresco.initialize(Fragment02.this.getActivity());
        sharePreferencesUtils = new SharePreferencesUtils();
        userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
        view = inflater.inflate(R.layout.fragment03, container, false);
        unbinder = ButterKnife.bind(this, view);
       // llAlertDialog = view.findViewById(R.id.llAlertDialog);
        scrollView = view.findViewById(R.id.scrollView);
        //llAlertDialog.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        yes = view.findViewById(R.id.yes);
        sharePreferencesUtils = new SharePreferencesUtils();
        userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
        if (userName == null || userName.equals("")) {
            Log.e("------", "2222222222222222");
//            intent = new Intent(getActivity(), LoginActivity.class);
//            intent.putExtra("tag", "inner");
//            startActivity(intent);
            tv_nickname.setText(getResources().getString(R.string.qudenglu));
        } else {

            tv_nickname.setText(userName);

        }
        onclick();
        upVersionPresenter = new UpVersionPresenter(getActivity(), this);
        tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((tv_nickname.getText().toString()).equals(getResources().getString(R.string.qudenglu))){
                    intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("tag", "inner");
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            // Constants.state="1";
            okgo();
            userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
            if (userName == null || userName.equals("")) {
                tv_nickname.setText(getResources().getString(R.string.qudenglu));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  llAlertDialog.setVisibility(View.VISIBLE);
                        //   scrollView.setVisibility(View.GONE);

                        userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                        if (userName == null || userName.equals("")) {

                            tv_nickname.setText(getResources().getString(R.string.qudenglu));
                        }

                        onclick();



                    }
                }, 500);
            } else {

                tv_nickname.setText(userName);
                okgo();
                onclick();
            }
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("------", "111111111111");
        if (userName == null || userName.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                  //  llAlertDialog.setVisibility(View.VISIBLE);
                 //   scrollView.setVisibility(View.GONE);

                            userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName == null || userName.equals("")) {

                        tv_nickname.setText(getResources().getString(R.string.qudenglu));
                    }

                        onclick();



                }
            }, 500);
        } else {
//            llAlertDialog.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
            okgo();
            onclick();
        }
    }


//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        //   进入当前Fragment
//        if (enter && !isGetData) {
//            isGetData = true;
//            //   这里可以做网络请求或者需要的数据刷新操作
//            // Constants.state="1";
//            okgo();
//
//        } else {
//            isGetData = false;
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (userName == null || userName.equals("")) {
            Log.e("------", "2222222222222222");
//            intent = new Intent(getActivity(), LoginActivity.class);
//            intent.putExtra("tag", "inner");
//            startActivity(intent);
            tv_nickname.setText(getResources().getString(R.string.qudenglu));
        } else {

            tv_nickname.setText(userName);

        }

    }

    private void onclick() {
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("tag", "inner");
                startActivity(intent);
            }
        });
        llGuanYu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutMyActivity.class);
                startActivity(intent);
            }
        });
        llShengJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePreferencesUtils = new SharePreferencesUtils();
                sharePreferencesUtils.setString(getActivity(), "userName", "");
                sharePreferencesUtils.setString(getActivity(), "passWord", "");
                tv_nickname.setText(getResources().getString(R.string.qudenglu));
                methreemoney.setText("*****");
                userName="";
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.putExtra("tag", "inner");
//                startActivity(intent);
              //  upVersionPresenter.getUpVersion();
            }
        });
//        llSharde.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //1、使用Dialog、设置style
//                final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
//                //2、设置布局
//                View view = View.inflate(getActivity(), R.layout.dialog_custom_layout, null);
//                dialog.setContentView(view);
//
//                Window window = dialog.getWindow();
//                //设置弹出位置
//                window.setGravity(Gravity.BOTTOM);
//                //设置弹出动画
////                window.setWindowAnimations(R.style.main_menu_animStyle);
//                //设置对话框大小
//                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//
//                dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        OnekeyShare oks = new OnekeyShare();
////                        // title标题，微信、QQ和QQ空间等平台使用
////                        oks.setTitle(getString(R.string.share));
////                        // titleUrl QQ和QQ空间跳转链接
////                        oks.setTitleUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");
////                        // text是分享文本，所有平台都需要这个字段
////                        oks.setText(getString(R.string.share));
////                        // imagePath是图片的本地路径，确保SDcard下面存在此张图片
////                        oks.setImagePath("/sdcard/test.jpg");
////                        // 启动分享GUI
////                        oks.show(getActivity());
//                        OnekeyShare oks = new OnekeyShare();
//                        oks.setImageUrl("http://117.158.56.11:8099/IBAPP/lbxapp.png");//确保SDcard下面存在此张图片
//                        oks.setTitleUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");
//                        oks.setText(getResources().getString(R.string.download_line));
//                        oks.setTitle(getString(R.string.share));
//                        oks.setPlatform(QQ.NAME);
//                        oks.show(getActivity());
//                    }
//                });
//
//                dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Wechat.ShareParams sp = new Wechat.ShareParams();
//                        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
//                        sp.setTitle(getString(R.string.share)); //分享标题
//                        sp.setText(getString(R.string.share));   //分享文本
//                        sp.setUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");   //网友点进链接后，可以看到分享的详情
//                        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
////                wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
//                        // 执行分享
//                        wechat.share(sp);
//                    }
//                });
//
//                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//
////                OnekeyShare oks = new OnekeyShare();
////                // title标题，微信、QQ和QQ空间等平台使用
////                oks.setTitle(getString(R.string.share));
////                // titleUrl QQ和QQ空间跳转链接
////                oks.setTitleUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");
////                // text是分享文本，所有平台都需要这个字段
////                oks.setText(getString(R.string.share));
////                // imagePath是图片的本地路径，确保SDcard下面存在此张图片
////                oks.setImagePath("/sdcard/test.jpg");
////                // url在微信、Facebook等平台中使用
//////                oks.setUrl("http://sharesdk.cn");
////                // 启动分享GUI
////                oks.show(getActivity());
////
////                Wechat.ShareParams sp = new Wechat.ShareParams();
////                sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
////                sp.setTitle(getString(R.string.share)); //分享标题
////                sp.setText(getString(R.string.share));   //分享文本
////                sp.setUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");   //网友点进链接后，可以看到分享的详情
////                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//////                wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
////                // 执行分享
////                wechat.share(sp);
//            }
//        });
        wallet.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent in = new Intent(getActivity(), WalletActivity.class);
                                          startActivity(in);
                                        //  Toast.makeText(getActivity(),getResources().getString(R.string.weikait),Toast.LENGTH_LONG).show();
                                      }
                                  }
        );
        Account.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
//                                           Intent in = new Intent(getActivity(), RecordActivity.class);
//                                           startActivity(in);

                                           String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                                           if (userName!=null&&!userName.equals("")){
                                               intent = new Intent(getActivity(), RecordActivitynew.class);
                                           }else {
                                               intent = new Intent(getActivity(), LoginActivity.class);
                                               intent.putExtra("tag", "inner");
                                           }
                                           startActivity(intent);
                                       }
                                   }
        );
        transaction.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent in = new Intent(getActivity(), TransactionActivity.class);
                                               startActivity(in);
                                           }
                                       }
        );
        wangji.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
//                                          Intent in = new Intent(getActivity(), ChangeWordActivity.class);
//                                          startActivity(in);

                                          String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                                          if (userName!=null&&!userName.equals("")){
                                              intent = new Intent(getActivity(), ChangeWordActivity.class);
                                          }else {
                                              intent = new Intent(getActivity(), LoginActivity.class);
                                              intent.putExtra("tag", "inner");
                                          }
                                          startActivity(intent);
                                      }
                                  }
        );
        tixing.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent in = new Intent(getActivity(), SettingActivity.class);
                                          startActivity(in);
                                      }
                                  }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void okgo() {
        KProgressHUD progressHUD = ProgressHUD.show(getActivity());
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        OkGo.getInstance().init(getActivity().getApplication());
        HttpParams params = new HttpParams();
        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        params.put("timestamp", str);
        params.put("encrypt_type", "getBalance");
        params.put("nonce", "2208800081");
        params.put("encrypt_kb", "141");
        params.put("encrypt_data", userName);


        String url = ApiAddress.qrcode;
        // Constants.state = "1";
        OkGo.<String>post(url)
                //.tag()
                .params(params)
                // .headers("Authorization", "本地存储Token")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        progressHUD.dismiss();
                        Log.e("gogogo", getResources().getString(R.string.zhucesuc) + response.body().toString());

                        String resp = response.body().toString();
                        if (resp.equals("FFFE")) {
                            methreemoney.setText("0");
                        } else {
                            methreemoney.setText(resp);
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        progressHUD.dismiss();
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
    public void onPause() {
        super.onPause();
        isGetData = false;

    }

    @Override
    public void setUpVersion(UpVersion bean) {
        String versionNo = bean.getData().get(0).getVnumber();
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!versionNo.equals(versionName) && Double.valueOf(versionNo) > Double.valueOf(versionName)) {
            downData = bean.getData().get(0).getUpdateinformation();
            downUrl = bean.getData().get(0).getDownloadlink();
            new AlertDialogUtil(getActivity()).showDialog(getResources().getString(R.string.want_updata) + "\n" + downData, new AlertDialogCallBack() {
                @Override
                public int getData(int s) {
                    return 0;
                }

                @Override
                public void confirm() {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri content_url = Uri.parse(downUrl);
                    intent.setData(content_url);
                    startActivity(intent);
                }

                @Override
                public void cancel() {
                    getActivity().finish();
                }
            });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.new_version), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUpVersionMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
