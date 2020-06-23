package main.Charge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.Adapter.MyAdapter;
import main.Adapter.ViewHolder;
import main.smart.rcgj.R;


public class AccountActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {




    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        initData();
        mRefreshLayout = findViewById(R.id.refreshLayout);
        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
        }

        View view = findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(AccountActivity.this, recyclerView.VERTICAL,false));
            List arr = new ArrayList();
            for (int i = 0; i < 15; i++) {
                arr.add("" + i);
            }

            MyAdapter mMyAdapter = new MyAdapter(AccountActivity.this, R.layout.activity_adaccount, arr) {
                @Override
                public void convert(ViewHolder holder, Map<String, String> position) {
                    TextView tv=holder.getView(R.id.tv_stock_name_code);
                    tv.setText(position+"");
                }
            };

            recyclerView.setAdapter(mMyAdapter);

            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    Log.e("==============", "==============刷新");
                    mRefreshLayout.finishRefresh();
                }
            });
        }
    }

    private void initData() {
                 mData = new ArrayList<>();
                 mData.add("woshishui");
                 mData.add("woshishui");
                 mData.add("woshishui");
                 mData.add("woshishui");
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
