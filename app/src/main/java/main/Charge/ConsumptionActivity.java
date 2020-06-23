package main.Charge;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import main.Adapter.ConsumptionAdapter;
import main.Adapter.Orderadapter;
import main.Adapter.ViewHolder;
import main.ApiAddress;
import main.LoopView.AlertView;
import main.LoopView.OnConfirmeListener;
import main.flyrefresh.FlyRefreshLayout;
import main.sheet.MainActivity;
import main.smart.activity.TransactionActivitynew;
import main.smart.bus.bean.LineBean;
import main.smart.bus.util.StatusBarUtil;
import main.smart.common.SmartBusApp;
import main.smart.common.http.LoadCacheResponseLoginouthandler;
import main.smart.common.http.LoadDatahandler;
import main.smart.common.http.RequstClient;
import main.smart.common.util.ConstData;
import main.smart.common.util.Constant;
import main.smart.rcgj.R;
import main.utils.utils.SharePreferencesUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class ConsumptionActivity extends AppCompatActivity  implements FlyRefreshLayout.OnPullRefreshListener , OnConfirmeListener {

private FlyRefreshLayout mFlylayout;
private RecyclerView mListView;

private ItemAdapter mAdapter;
private TextView datetext;
//private ArrayList<ItemData> mDataSet = new ArrayList<>();
private Handler mHandler = new Handler();
private LinearLayoutManager mLayoutManager;
private SharePreferencesUtils sharePreferencesUtils;
private String cardNo;
private ArrayList<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
private TextView tv_nickname;
private ImageView newdema_location;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initDataSet();
        setContentView(R.layout.activity_consumption);
        StatusBarUtil.setStatusBarMode(this, true, R.color.color_bg_selectednew);
        mFlylayout = (FlyRefreshLayout) findViewById(R.id.fly_layout);
        mFlylayout.setOnPullRefreshListener(this);
        mListView = (RecyclerView) findViewById(R.id.list);
        datetext=findViewById(R.id.datetext);
        tv_nickname=findViewById(R.id.tv_nickname);
        cardNo = getIntent().getStringExtra("cardNo");
        mLayoutManager = new LinearLayoutManager(this);
        mListView.setLayoutManager(mLayoutManager);
        newdema_location=findViewById(R.id.newdema_location);
        View actionButton = mFlylayout.getHeaderActionButton();
        if (actionButton != null) {
        actionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        mFlylayout.startRefresh();
        }
        });
        }
        newdema_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumptionActivity.this.finish();
            }
        });
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        datetext.setText(formatter.format(date));
        initData();
        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView("请选择日期", ConsumptionActivity.this,2018,2100,ConsumptionActivity.this).show();
            }
        });
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
        }

@Override
public void onRefresh(FlyRefreshLayout view) {
        View child = mListView.getChildAt(0);
        if (child != null) {
        bounceAnimateView(child.findViewById(R.id.icon));
        }

        mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
                mFlylayout.onRefreshFinish();
                }
                }, 2000);
        }

        private void bounceAnimateView(View view) {
        if (view == null) {
        return;
        }

        Animator swing = ObjectAnimator.ofFloat(view, "rotationX", 0, 30, -20, 0);
        swing.setDuration(400);
        swing.setInterpolator(new AccelerateInterpolator());
        swing.start();
        }

@Override
public void onRefreshAnimationEnd(FlyRefreshLayout view) {
        //addItemData();
        initData();
        }

    @Override
    public void result(String s ,String sn) {
        datetext.setText(sn);
        initData();
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private LayoutInflater mInflater;
    private DateFormat dateFormat;

    public ItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        dateFormat = SimpleDateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.view_list_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
       // final ItemData data = li.get(i);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        itemViewHolder.title.setText(li.get(i).get("ICXFB_XL").toString()+"路公交");
        itemViewHolder.titlecar.setText(li.get(i).get("ICXFB_CH").toString());
        itemViewHolder.subtitle.setText(li.get(i).get("ICXFB_RQ").toString());
        itemViewHolder.koufei.setText("-"+li.get(i).get("ICXFB_JE").toString());
    }

    @Override
    public int getItemCount() {
        return li.size();
    }
}

private static class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView title;//线路号
    TextView titlecar;//车号
    TextView subtitle;//日期
    TextView koufei;//扣费

    public ItemViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        titlecar = (TextView) itemView.findViewById(R.id.titlecar);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        koufei = (TextView) itemView.findViewById(R.id.koufei);
    }

}

    private void initData() {
        String url = ConstData.ICRecharge+"ICRecharge/pay!getConsumeListNew.action";
        RequestParams param = new RequestParams();
        param.put("month", (datetext.getText().toString()).replace("-",""));
        param.put("cardNo", cardNo);
        param.put("pageNo", "1");
        param.put("pageSize", "1000");
        RequstClient.get(url,
                param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
                        new LoadDatahandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
//                                mFlylayout.startRefresh();
                                Log.e("gogogo", "开始请求"+(datetext.getText().toString()).replace("-","") );
                            }

                            @Override
                            public void onSuccess(String data) {
                                super.onSuccess(data);
                                Message message = handler.obtainMessage();
                                try {
                                    JSONObject json = new JSONObject(data.toString());
                                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                    // 通过Gson 解析后台传过来的数据
                                    Map<String, Object> map1 = gson.fromJson(data.toString(), new TypeToken<Map<String, Object>>() {
                                    }.getType());
                                    Log.e("gogogo","`www````````````"+map1);
                                    if (json != null && json.getString("success") != null) {
                                        String success = json.getString("success");
                                        if (success.equals("true")) {
                                            tv_nickname.setText(json.getString("ye"));
                                            li = (ArrayList<Map<String, Object>>) map1.get("dataList");
                                            handler.sendEmptyMessage(0);
                                            Log.e("gogogo","`````````````"+li);
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



                            }

                            @Override
                            public void onFailure(String error, String message) {
                                super.onFailure(error, message);
//                                mFlylayout.onRefreshFinish();
                                Log.e("连接数据库错误", "可能网络不通或Ip地址错误");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        }));
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.WHAT_GET_PAYMENT_FAILURE:
                   // mFlylayout.onRefreshFinish();
                    Toast.makeText(ConsumptionActivity.this,(msg.obj).toString(),Toast.LENGTH_SHORT).show();
                    li.clear();
                    mAdapter = new ItemAdapter(ConsumptionActivity.this);
                    mListView.setAdapter(mAdapter);
                    mListView.setItemAnimator(new SampleItemAnimator());
                    mAdapter.notifyItemInserted(0);
                    mLayoutManager.scrollToPosition(0);
                    break;

                case 0:
                   // mFlylayout.onRefreshFinish();
                    if (li != null) {
                        if (li.size() == 0) {
                            Toast.makeText(ConsumptionActivity.this, "当前无该用户的交易记录", Toast.LENGTH_SHORT).show();
                            li.clear();
                            mAdapter = new ItemAdapter(ConsumptionActivity.this);
                            mListView.setAdapter(mAdapter);
                            mListView.setItemAnimator(new SampleItemAnimator());
                            mAdapter.notifyItemInserted(0);
                            mLayoutManager.scrollToPosition(0);
                        } else {
                            mAdapter = new ItemAdapter(ConsumptionActivity.this);
                            mListView.setAdapter(mAdapter);
                            mListView.setItemAnimator(new SampleItemAnimator());
                            mAdapter.notifyItemInserted(0);
                            mLayoutManager.scrollToPosition(0);

                        }

                    } else {
                        Toast.makeText(ConsumptionActivity.this, "当前无该用户的交易记录", Toast.LENGTH_SHORT).show();

                    }


                    break;

                default:
                    break;
            }
        }
    };



}
