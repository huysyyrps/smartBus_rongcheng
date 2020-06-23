package main.Charge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Adapter.ConsumptionAdapter;
import main.Adapter.MyAdapter;
import main.Adapter.ViewHolder;
import main.ApiAddress;
import main.smart.common.http.SHAActivity;
import main.smart.rcgj.R;
import main.utils.utils.SharePreferencesUtils;

public class RecordActivity extends  AppCompatActivity implements AdapterView.OnItemClickListener {
        private RefreshLayout mRefreshLayout;
        private static boolean isFirstEnter = true;
        private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SharePreferencesUtils sharePreferencesUtils;
        private View view;
        private ListView recyclerView;
        private RelativeLayout order_black3;
        private LinearLayout zanwuchong;
        private String dingdan;
        private int weizhi = 0, xianshi = 0;
        private TextProjectAdapter_ TextProjectAdapter_;

        private View.OnClickListener onClickListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_record);
                mRefreshLayout = findViewById(R.id.refreshLayout);
                order_black3= findViewById(R.id.order_black3);
                zanwuchong= findViewById(R.id.zanwuchong);
                recyclerView = findViewById(R.id.recyclerView);
                initData();
                order_black3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                RecordActivity.this.finish();
                        }
                });
                if (view instanceof RecyclerView) {
                        recyclerView = (ListView) view;

                        //  recyclerView.setLayoutManager(new LinearLayoutManager(RecordActivity.this, ListView.SCROLL_AXIS_VERTICAL,false));
                        initData();


                        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                                @Override
                                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                                        Log.e("==============", "==============刷新");
                                        initData();
                                        mRefreshLayout.finishRefresh();
                                }
                        });
                }

        }

        private void initData() {
                mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
                sharePreferencesUtils = new SharePreferencesUtils();
                String userName = sharePreferencesUtils.getString(RecordActivity.this, "userName", "");
                long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
                String  str=String.valueOf(time);
                OkGo.getInstance().init(RecordActivity.this.getApplication());
                HttpParams params = new HttpParams();
                params.put("CARDTYPE", "141");
                params.put("CardNo", userName);
                //params.put("CardNo", "15269179758");
                //String url = "https://111.53.145.128:8443/ICRecharge/pay!getRechargeList.action";
                String url= ApiAddress.ICRecharge+"ICRecharge/pay!getRechargeList.action";

                OkGo.<String>post(url)
                        //.tag()
                        .params(params)
                        // .headers("Authorization", "本地存储Token")
                        .execute(new StringCallback() {

                                @Override
                                public void onSuccess(Response<String> response) {
                                        Log.e("gogogo",getResources().getString(R.string.zhucesuc)+"mmmmmmmmmmmmmmmmmm"+response.body().toString());
                                        String resp = response.body().toString();
                                        if (!resp.equals("[]")) {
                                                JSONArray jsonArray = null;
                                                try {
                                                        jsonArray = new JSONArray(resp);
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                                JSONObject json1 = (JSONObject) jsonArray.get(i);
                                                                // System.out.println("json1="+json1);
                                                                Map<String, String> map = new HashMap<String, String>();
                                                                int payment = Integer.parseInt(json1.getString("payment"));
                                                                map.put("orderDate", json1.getString("orderDate"));
                                                                map.put("orderId", json1.getString("orderId"));
                                                                map.put("payment", json1.getString("payment"));
                                                                map.put("status", json1.getString("status"));
                                                                map.put("type", json1.getString("type"));
                                                                list.add(map);
                                                                //zanwuchong.setVisibility(View.VISIBLE);
                                                                handler.sendEmptyMessage(0);


                                                        }
                                                } catch (JSONException e) {
                                                        e.printStackTrace();
                                                }

                                        }else {
                                                zanwuchong.setVisibility(View.VISIBLE);
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
                                        mRefreshLayout.finishRefresh();
                                        Log.e("gogogo","请求结束了");

                                }
                        });
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //        switch (Item.values()[position % Item.values().length]) {
                //            case "":
                //                mRefreshLayout.setEnableHeaderTranslationContent(false);
                //                break;

                //        }
                mRefreshLayout.autoRefresh();
        }

        Handler handler = new Handler()
        {
                @Override
                public void handleMessage(Message msg)
                {
                        switch(msg.what){
                                case 0 :
                                        TextProjectAdapter_ = new TextProjectAdapter_( RecordActivity.this,list);
                                        weizhi = -1;
                                        xianshi = 0;
                                        for (int i = 0; i < list.size(); i++) {
                                                Map<String, String> map = new HashMap<String, String>();
                                                map = list.get(i);
                                                if (map.get("status").equals("0") ||map.get("status").equals("8")) {


                                                }else{
                                                        if (map.get("status").equals("2") ) {
                                                                if (map.get("type").equals("WX")||map.get("type").equals("ZFB") ) {

                                                                        if(i == 0){
                                                                                weizhi = 0;
                                                                        }else{
                                                                                weizhi = i;
                                                                        }



                                                                }
                                                        }
                                                        if (i != 0 && weizhi == -1 && map.get("status").equals("2")
                                                                && (list.get(i-1).get("status").equals("14")
                                                                || list.get(i-1).get("status").equals("16"))) {

                                                                weizhi = i;

                                                        }
                                                        if (map.get("status").equals("11") || map.get("status").equals("12")
                                                                || map.get("status").equals("13") || map.get("status").equals("15")) {
                                                                xianshi = 1;
                                                                Log.e("momo",xianshi+"这里走了吗");
                                                        }
                                                }
                                        }
                                        recyclerView.setAdapter(TextProjectAdapter_);
                                        break;
                                case 1 :

                                        Map<String, Object> map=(Map<String,Object>)msg.obj;
//
                                        break;
                                case 2:
                                        Toast.makeText(RecordActivity.this, "退款申请提交成功", Toast.LENGTH_SHORT).show();

                                        list.get(weizhi).put("status", "11");

                                        xianshi = 1;

                                        TextProjectAdapter_.notifyDataSetChanged();
                                        break;
                                case 3:
                                        Toast.makeText(RecordActivity.this, "退款失败", Toast.LENGTH_SHORT).show();
                                        break;

                        }
                }
        };

        class TextProjectAdapter_ extends BaseAdapter {
                public List<Map<String, String>> data=new ArrayList<Map<String,String>>();
                private Context context;

                public TextProjectAdapter_(Context context, List<Map<String, String>> data) {
                        this.data = data;
                        this.context = context;


                }
                @Override
                public int getCount() {
                        if (data == null)
                                return 0;
                        return data.size();
                }

                @Override
                public Object getItem(int position) {
                        // TODO 自动生成的方法存根
                        return data.get(position);
                }

                @Override
                public long getItemId(int position) {
                        // TODO 自动生成的方法存根
                        return position;
                }

                @Override
                public View getView(final int position, View view, ViewGroup parent) {
                        // TODO 自动生成的方法存根

                        holder ho = null;
                        if (view == null) {
                                ho = new holder();
                                int sta=weizhi;

                                view = LayoutInflater.from(context).inflate(R.layout.activity_adaccount, null);
                                ho.adapter_jiaoyijine = (TextView) view.findViewById(R.id.tv_stock_name_code);
                                ho.adapter_chongzhidanhao = (TextView) view.findViewById(R.id.orderidchong);
                                ho.adapter_jiaoyishijian = (TextView) view.findViewById(R.id.riqi);
                                ho.adapter_tuikuan = (TextView) view.findViewById(R.id.adapter_tuikuan);
                                ho.zhifutype= (TextView) view.findViewById(R.id.zhifutype);
                                ho.chongimg=view.findViewById(R.id.chongimg);
                                view.setTag(ho);
                        } else {

                                ho = (holder) view.getTag();
                        }
                        //	String status = map.get("status");
                        System.out.println("weizhi=" + weizhi);

                        if ((data.get(position).get("status")).equals("0")){
                                dingdan = data.get(position).get("orderId");
                                ho.adapter_tuikuan.setVisibility(View.GONE);
                        }else if ((data.get(position).get("status")).equals("8")){
                                ho.adapter_tuikuan.setVisibility(View.GONE);

                        }else{

                                if ((data.get(position).get("status")).equals("2")  && xianshi == 0) {
                                        dingdan = data.get(position).get("orderId");
                                        Log.e("momo",weizhi+"竟然走到了这里了"+xianshi);
                                        SimpleDateFormat formatn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String dateStr2 = formatn.format(new Date());
                                        try
                                        {
                                                Date date2 = formatn.parse(dateStr2);
                                                Date date = formatn.parse(data.get(position).get("orderDate"));
                                                int days = (int) ((date2.getTime() - date.getTime()) / (1000*3600*24));
                                                if(days>=90){
                                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                                        ho.adapter_tuikuan.setText("订单超过90天,无法退款");
                                                        ho.adapter_tuikuan.setEnabled(false);
                                                }else{
                                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                                        ho.adapter_tuikuan.setText("    退款");
                                                        ho.adapter_tuikuan.setTextColor(context.getColor(R.color.white));
                                                        ho.adapter_tuikuan.setBackground(getDrawable(R.drawable.tuikan));
                                                        ho.adapter_tuikuan.setOnClickListener(onClickListener);
                                                }


                                        } catch (ParseException e) {
                                                e.printStackTrace();
                                        }



                                } else if ((data.get(position).get("status")).equals("11")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("退款申请中,等待审核");
                                        ho.adapter_tuikuan.setEnabled(false);
                                        ho.adapter_tuikuan.setTextColor(context.getColor(R.color.red));
                                        ho.adapter_tuikuan.setBackground(getDrawable(R.drawable.tuikankong));

                                } else if ((data.get(position).get("status")).equals("12")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("退款已审核,等待退款");
                                        ho.adapter_tuikuan.setEnabled(false);
                                } else if ((data.get(position).get("status")).equals("13")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("退款中");
                                        ho.adapter_tuikuan.setEnabled(false);
                                } else if ((data.get(position).get("status")).equals("14")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("已退款");
                                        ho.adapter_tuikuan.setEnabled(false);
                                } else if ((data.get(position).get("status")).equals("15")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("已初审");
                                        ho.adapter_tuikuan.setEnabled(false);
                                } else if ((data.get(position).get("status")).equals("16")) {
                                        ho.adapter_tuikuan.setVisibility(View.VISIBLE);
                                        ho.adapter_tuikuan.setText("退款驳回");
                                        ho.adapter_tuikuan.setEnabled(false);
                                } else {
                                        ho.adapter_tuikuan.setVisibility(View.GONE);
                                }
                        }

                        ho.adapter_jiaoyijine.setText(data.get(position).get("payment")+"元");
                        ho.adapter_chongzhidanhao.setText(data.get(position).get("orderId"));
                        ho.adapter_jiaoyishijian.setText(data.get(position).get("orderDate"));
                        if(data.get(position).get("type").equals("WX")){
                                ho.chongimg.setBackground(getResources().getDrawable(R.drawable.wxpay));
                                ho.zhifutype.setText(getResources().getString(R.string.wxtype));
                        }else if (data.get(position).get("type").equals("ZFB")){
                                ho.chongimg.setBackground(getResources().getDrawable(R.drawable.zfb));
                                ho.zhifutype.setText(getResources().getString(R.string.zfbtype));
                        }else if (data.get(position).get("type").equals("NX")){
//                                ho.chongimg.setBackground(getResources().getDrawable(R.drawable.noxin));
//                                ho.zhifutype.setText(getResources().getString(R.string.zfbtypem));
                        }
                        ho.adapter_tuikuan.setTag(position);
                        ho.adapter_tuikuan.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                                        builder.setTitle("退款提示");
                                        builder.setMessage("退款申请提交后您的账户将暂不能使用，待退款成功后方能恢复正常使用，给您造成的不便敬请谅解！");
                                        builder.setCancelable(false);
                                        final android.app.Dialog dialog = builder.create();
                                        builder.setPositiveButton("确认退款", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                        // TODO 自动生成的方法存根

                                                        sharePreferencesUtils = new SharePreferencesUtils();
                                                        String userName = sharePreferencesUtils.getString(RecordActivity.this, "userName", "");
                                                        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
                                                        String  str=String.valueOf(time);
                                                        OkGo.getInstance().init(RecordActivity.this.getApplication());
                                                        HttpParams params = new HttpParams();
                                                        params.put("signature", SHAActivity.encryptToSHA(str + "2208800081shandonghengyudianzi"));;
                                                        params.put("timestamp", str);
                                                        params.put("encrypt_type", "applyRefund_app");
                                                        params.put("nonce", "2208800081");
                                                        params.put("encrypt_kb", "141");
                                                        params.put("encrypt_iv", userName);
                                                        params.put("encrypt_data", data.get(position).get("orderId"));
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
                                                                                mRefreshLayout.finishRefresh();
                                                                                Log.e("gogogo","请求结束了");

                                                                        }
                                                                });


                                                }

                                        });
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                        // TODO 自动生成的方法存根
                                                        dialog.dismiss();
                                                }
                                        });

                                        builder.show();
                                }
                        });



                        return view;
                }


                public class holder {
                        public TextView adapter_jiaoyijine, adapter_chongzhidanhao, adapter_jiaoyishijian,zhifutype, adapter_tuikuan;
                        public ImageView chongimg;
                }


        }

}
