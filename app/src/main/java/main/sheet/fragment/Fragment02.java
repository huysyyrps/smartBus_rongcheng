package main.sheet.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import main.ApiAddress;
import main.Charge.AccountActivity;
import main.Charge.ActivityCardCharge;
import main.Charge.RidecodeActivity;
import main.Charge.TransactionActivity;
import main.Charge.WalletActivity;
import main.login.LoginActivity;
import main.sheet.MainActivity;
import main.smart.common.http.SHAActivity;
import main.smart.common.util.Constants;
import main.smart.huoche.DatapickActivity;
import main.smart.huoche.Pickadapter;
import main.smart.rcgj.R;
import main.utils.dialog.ProgressHUD;
import main.utils.utils.SharePreferencesUtils;


/**
 * Created by Administrator on 2019/4/12.
 * 待办列表
 */

public class Fragment02 extends Fragment {
    View view;
    private IWXAPI api;
    private boolean isGetData = false;

    @BindView(R.id.iv)
    ImageView imgCode;
    @BindView(R.id.qrcodechong)
    LinearLayout qrcodechong;
    @BindView(R.id.qrcodeyu)
    LinearLayout qrcodeyu;
    @BindView(R.id.qrcodeji)
    LinearLayout qrcodeji;
    @BindView(R.id.onLineBtn)
    LinearLayout onLineBtn;
    private LinearLayout erweima;
    private LinearLayout kaitongcard;
    private KProgressHUD progressHUD;
    private TextView tv_cardnono;
    private Button chargecard;
    Intent intent;
    SharePreferencesUtils sharePreferencesUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment02, container, false);
        kaitongcard=view.findViewById(R.id.kaitongcard);
        erweima=view.findViewById(R.id.erweima);
        imgCode=view.findViewById(R.id.iv);
        qrcodechong=view.findViewById(R.id.qrcodechong);
        qrcodeyu=view.findViewById(R.id.qrcodeyu);
        qrcodeji=view.findViewById(R.id.qrcodeji);
        onLineBtn=view.findViewById(R.id.onLineBtn);
        tv_cardnono=view.findViewById(R.id.tv_cardnono);
        chargecard=view.findViewById(R.id.chargecard);
        chargecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataregiest();
            }
        });
        onLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
            }
        });
        // GetData("1");
        onclick();
        return view;
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            GetData();

        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        //  GetData();

//            else if (Constants.state.equals("2")){
//                Log.e("-----------222",Constants.jscodenew);
//                //qrcode("FFFE");
//
//                sharePreferencesUtils = new SharePreferencesUtils();
//                String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
//                long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
//                String  str=String.valueOf(time);
//                OkGo.getInstance().init(Fragment02.this.getActivity().getApplication());
//                HttpParams params = new HttpParams();
//                params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
//                params.put("timestamp", str);
//                params.put("encrypt_type", "register_app");
//                params.put("nonce", "2208800081");
//                params.put("encrypt_code", Constants.jscodenew);
//                params.put("encrypt_kb", "141");
//                params.put("encrypt_data", userName);
//
//
//                String url=ApiAddress.qrcode;
//                Constants.state="1";
//                OkGo.<String>post(url)
//                        //.tag()
//                        .params(params)
//                        // .headers("Authorization", "本地存储Token")
//                        .execute(new StringCallback() {
//
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                Log.e("gogogo",getResources().getString(R.string.zhucesuc)+response.body().toString());
//                                Constants.jscodenew="";
//                                String resp = response.body().toString();
//                                if((response.body().toString()).equals("0000")){
//                                    imgCode.setBackgroundResource(R.drawable.yuebuzu);
//                                }else if((response.body().toString()).equals("FFFC")){
//                                    message.what=2;
//                                    handler.sendMessage(message);
//                                }
//                            }
//
//                            @Override
//                            public void onError(Response<String> response) {
//                                super.onError(response);
//                                Constants.jscodenew="";
//                                Log.e("gogogo","请求失败了");
//                            }
//                            @Override
//                            public void onStart(Request<String, ? extends Request> request) {
//                                super.onStart(request);
//                                code=Constants.jscodenew;
//                                Constants.jscodenew="";
//                                Log.e("gogogo","eeee开始请求" );
//
//                            }
//                            @Override
//                            public void onFinish() {
//                                super.onFinish();
//                                progressHUD.dismiss();
//                                Constants.jscodenew="";
//                                Log.e("gogogo","请求结束了");
//
//                            }
//                        });
//            }


        //    }else {

        //     }
    }

    //    private  void GetData(String state){
//        Constants.jscodenew=state;
//        progressHUD = ProgressHUD.show(Fragment02.this.getActivity());
//        api = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID,false);
//        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "none";
//        api.sendReq(req);
//
//    }
    private  void GetDataregiest(){
        KProgressHUD progressHUD1;
        progressHUD1 = ProgressHUD.show(Fragment02.this.getActivity());
        Message message = handler.obtainMessage();
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        OkGo.getInstance().init(Fragment02.this.getActivity().getApplication());
        final Map<String, String> map = new HashMap<String, String>();
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
        tv_cardnono.setText(userName);
        map.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        map.put("timestamp", str);
        map.put("encrypt_type", "cloud_card_code_app");
        map.put("user_account", userName);
        map.put("nonce", "2208800081");
        map.put("encrypt_data", userName);
        map.put("encrypt_kb", "141");

        HttpParams params = new HttpParams();
        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        params.put("timestamp", str);
        params.put("encrypt_type", "cloud_card_open");
        params.put("user_account", userName);
        params.put("nonce", "2208800081");
        params.put("encrypt_data", userName);
        params.put("encrypt_kb", "141");

        Log.e("gogogo",map+"");

        String url=ApiAddress.qrcode;
        OkGo.<String>post(url)
                //.tag()
                .params(params)
                // .headers("Authorization", "本地存储Token")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
                        progressHUD1.dismiss();
                        String resp = response.body().toString();
                        if(resp.equals("0000")){
                            GetData();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        progressHUD1.dismiss();
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
                        progressHUD1.dismiss();
                        Log.e("gogogo","请求结束了");

                    }
                });
    }
    private  void GetData(){
        progressHUD = ProgressHUD.show(Fragment02.this.getActivity());
        Message message = handler.obtainMessage();
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        OkGo.getInstance().init(Fragment02.this.getActivity().getApplication());
        final Map<String, String> map = new HashMap<String, String>();
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
        tv_cardnono.setText(userName);
        map.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        map.put("timestamp", str);
        map.put("encrypt_type", "cloud_card_code_app");
        map.put("user_account", userName);
        map.put("nonce", "2208800081");
        map.put("encrypt_data", userName);
        map.put("encrypt_kb", "141");
//18753823265
        HttpParams params = new HttpParams();
//        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));
        params.put("timestamp", str);
        params.put("encrypt_type", "cloud_card_code_app");
        params.put("user_account", userName);
        params.put("nonce", "2208800081");
        params.put("encrypt_data", userName);
        params.put("encrypt_kb", "141");

        Log.e("gogogo",map+"");

        String url=ApiAddress.qrcode;
        OkGo.<String>post(url)
                //.tag()
                .params(params)
                // .headers("Authorization", "本地存储Token")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("gogogo",getResources().getString(R.string.requestsuc)+response.body().toString());
                        progressHUD.dismiss();
                        String resp = response.body().toString();

                        try {
                            JSONObject json = new JSONObject(resp);

                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            // 通过Gson 解析后台传过来的数据
                            Map<String, Object> map1 = gson.fromJson(resp, new TypeToken<Map<String, Object>>() {
                            }.getType());
                            if (json != null) {
                                Log.e("gogogo","map1"+(map1.get("qrdata")));
                                if(map1.get("success")==null){
//                                    message.what=1;
//                                    Log.e("gogogo",getResources().getString(R.string.requestsuc2)+map1.get("code").toString());
//
//                                    message.obj=map1.get("code");
//                                    Bundle bundle = new Bundle();
//
//                                    bundle.putString("qrcodestring",map1.get("code").toString());  //往Bundle中存放数据
//                                    message.setData(bundle);//mes利用Bundle传递数据
//                                    handler.sendMessage(message);

//                                    if(userName==null||userName.equals("")){
//                                        intent = new Intent(getActivity(), LoginActivity.class);
//                                        intent.putExtra("tag", "inner");
//                                        startActivity(intent);
//                                    }

                                    if(userName==null||userName.equals("")){
                                        Toast.makeText(getActivity(),R.string.qudenglushibai,Toast.LENGTH_LONG).show();
                                    }
                                    imgCode.setVisibility(View.GONE);

                                }else{
                                    if((map1.get("success").toString()).equals("true")){
                                        Log.e("gogogo","mmmmmmmmmmmm");
                                        if((map1.get("qrdata"))!=null){
                                            message.what=1;
                                            Log.e("gogogo","qrdata"+map1.get("qrdata"));

                                            message.obj=map1.get("qrdata");
                                            Bundle bundle = new Bundle();
                                            bundle.putString("qrcodestring",map1.get("qrdata").toString());  //往Bundle中存放数据
                                            message.setData(bundle);//mes利用Bundle传递数据
                                            handler.sendMessage(message);
                                        }

                                    }else {
                                        message.what=1;
                                        Log.e("gogogo",getResources().getString(R.string.requestsuc2)+map1.get("code").toString());

                                        message.obj=map1.get("code");
                                        Bundle bundle = new Bundle();

                                        bundle.putString("qrcodestring",map1.get("code").toString());  //往Bundle中存放数据
                                        message.setData(bundle);//mes利用Bundle传递数据
                                        handler.sendMessage(message);
                                    }
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        progressHUD.dismiss();
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

    private void qrcode(String result){
        String str1 = result;

        Message message = handler.obtainMessage();
        if(str1.contains("FFFE")){
            erweima.setVisibility(View.GONE);
            kaitongcard.setVisibility(View.VISIBLE);
            //  imgCode.setBackgroundResource(R.drawable.yuebuzu);

            //  GetData(Constants.state);

        }else if (str1.equals("FR11")||str1.equals("FR12")||str1.equals("FR13")||str1.equals("FR15")) {
            erweima.setVisibility(View.VISIBLE);
            kaitongcard.setVisibility(View.GONE);
            imgCode.setBackgroundResource(R.drawable.tuikuan);
        } else if(str1.contains("FFFF")){
            erweima.setVisibility(View.GONE);
            kaitongcard.setVisibility(View.VISIBLE);
            message.what=3;
            handler.sendMessage(message);
        }else if(str1.contains("FFFD")){
            erweima.setVisibility(View.VISIBLE);
            kaitongcard.setVisibility(View.GONE);
            imgCode.setBackgroundResource(R.drawable.yuebuzu);
        }else if(str1.equals("40029")){
            erweima.setVisibility(View.GONE);
            kaitongcard.setVisibility(View.VISIBLE);
//            imgCode.setBackgroundResource(R.drawable.yuebuzu);
        }else {
//            String strphone=str1.substring(0, str1.indexOf("&"));
//            String strqrd=str1.substring(strphone.length()+1, str1.length()-2);
//            String strcode=str1.substring(strphone.length()+1, str1.length());
//            Log.e("gogogo","@@@@@@@"+strqrd);
//            if (strcode.equals("FFFD")){
//                erweima.setVisibility(View.VISIBLE);
//                kaitongcard.setVisibility(View.GONE);
//                imgCode.setBackgroundResource(R.drawable.yuebuzu);
//            }else {
            erweima.setVisibility(View.VISIBLE);
            kaitongcard.setVisibility(View.GONE);
            final Bitmap qrCodeBitmap = createQRCodeBitmap(str1);

            Fragment02.this.getActivity().runOnUiThread(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    @SuppressWarnings("deprecation")
                    BitmapDrawable iconDrawable = new BitmapDrawable(qrCodeBitmap);
                    imgCode.setBackground(iconDrawable);
                }
            });
            //     }

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    private Bitmap createQRCodeBitmap(String url) {

        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();

        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");


        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 330, 330, qrParam);


            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;
                    else
                        data[y * w + x] = -1;
                }
            }


            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what){
                case 1 :

                    Log.e("gogogo","------***----=======------"+msg.getData().getString("qrcodestring"));
                    qrcode(msg.getData().getString("qrcodestring"));
                    break;
                case 2 :
                    Toast.makeText(getActivity(), R.string.phonereqist, Toast.LENGTH_SHORT).show();

                    break;
                case 3:
                    Toast.makeText(getActivity(), R.string.phoneX, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void onclick() {
        qrcodechong.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent in =new Intent(getActivity(), ActivityCardCharge.class);
                                               startActivity(in);
                                           }
                                       }
        );
        qrcodeyu.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent in =new Intent(getActivity(), WalletActivity.class);
                                            startActivity(in);
                                        }
                                    }
        );
        qrcodeji.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent in =new Intent(getActivity(), TransactionActivity.class);
                                            startActivity(in);
                                        }
                                    }
        );
    }


}
