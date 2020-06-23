package main.Charge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Adapter.ConsumptionAdapter;
import main.Adapter.MyAdapter;
import main.Adapter.ViewHolder;
import main.ApiAddress;
import main.sheet.fragment.Fragment02;
import main.smart.common.http.SHAActivity;
import main.smart.common.util.Constants;
import main.smart.rcgj.R;
import main.utils.utils.SharePreferencesUtils;

public class TransactionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {




    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    SharePreferencesUtils sharePreferencesUtils;
    private View view;
    RecyclerView recyclerView;
    private RelativeLayout order_black3;
    private LinearLayout zanwu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        order_black3= findViewById(R.id.order_black3);
        zanwu= findViewById(R.id.zanwu);
//        if (isFirstEnter) {
//            isFirstEnter = false;
//
//        }

        view = findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(TransactionActivity.this, recyclerView.VERTICAL,false));
//            List arr = new ArrayList();
//            for (int i = 0; i < 15; i++) {
//                arr.add("" + i);
//            }
            initData();

            order_black3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TransactionActivity.this.finish();
                }
            });
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    Log.e("==============", "==============刷新");
                    initData();
                    mRefreshLayout.finishRefresh();
                    //  mRefreshLayout.finishRefresh();
                }
            });
        }

    }

    private void initData() {
        list.clear();
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(TransactionActivity.this, "userName", "");
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        OkGo.getInstance().init(TransactionActivity.this.getApplication());
        HttpParams params = new HttpParams();
        params.put("CARDTYPE", "141");
        params.put("CardNo", userName);
        //params.put("CardNo", "15269179758");
        //String url = "https://aqzbbus.cn:262/ICRecharge/pay!getConsumeList.action";
        String url= ApiAddress.ICRecharge+"ICRecharge/pay!getConsumeList.action";

        OkGo.<String>post(url)
                //.tag()
                .params(params)
                // .headers("Authorization", "本地存储Token")
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("gogogo",getResources().getString(R.string.zhucesuc)+response.body().toString());
                        String resp = response.body().toString();
                        zanwu.setVisibility(View.GONE);
                        if (!resp.equals("[]")) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(resp);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json1 = (JSONObject) jsonArray.get(i);
                                    // System.out.println("json1="+json1);
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("ICXFB_RQ", json1.getString("ICXFB_RQ")+json1.getString("ICXFB_SJ"));
                                    map.put("ICXFB_JE", json1.getString("ICXFB_JE"));
                                    map.put("ICXFB_XL", json1.getString("ICXFB_XL"));
                                    map.put("ICXFB_CH", json1.getString("ICXFB_CH"));
                                    list.add(map);

//                                    MyAdapter mMyAdapter = new MyAdapter(TransactionActivity.this, R.layout.activity_adaccount, list) {
//                                        @Override
//                                        public void convert(ViewHolder holder, Object position) {
//                                            TextView tv=holder.getView(R.id.tv_stock_name_code);
//                                            tv.setText(position+"");
//                                        }
//                                    };
                                    ConsumptionAdapter mMyAdapter = new ConsumptionAdapter(TransactionActivity.this, R.layout.activity_xiaofei, list) {
                                        @Override
                                        public void convert(ViewHolder holder, Map<String, String> position) {
                                            TextView tv=holder.getView(R.id.tv_stock_name_codexiao);
                                            TextView tvxl=holder.getView(R.id.adapter_xiaofei_xianlu);
                                            TextView tvch =holder.getView(R.id.adapter_xiaofei_chehao);
                                            TextView tvsj =holder.getView(R.id.adapter_xiaofei_shijian);
                                            tv.setText(position.get("ICXFB_JE"));
                                            tvxl.setText(position.get("ICXFB_XL"));
                                            tvch.setText(position.get("ICXFB_CH"));
                                            tvsj.setText(position.get("ICXFB_RQ"));
                                        }
                                    };
                                    recyclerView.setAdapter(mMyAdapter);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            zanwu.setVisibility(View.VISIBLE);
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



}

