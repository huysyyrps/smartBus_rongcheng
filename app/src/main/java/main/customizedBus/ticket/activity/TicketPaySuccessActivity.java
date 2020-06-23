package main.customizedBus.ticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.line.activity.CustomizatedLineDetailActivity;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedSelectLinesContract;
import main.customizedBus.line.presenter.CustomizedSelectLinesPresenter;
import main.customizedBus.ticket.adapter.CustomizedBusReturnLinesActivityAdapter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;


public class TicketPaySuccessActivity extends BaseActivity implements CustomizedSelectLinesContract.View {
    private RecyclerView recyclerView;
    private CustomizedBusReturnLinesActivityAdapter activityAdapter;
    private AddressBean startAddress;
    private AddressBean endAddress;
    int lineId;
    private CustomizedSelectLinesPresenter linesPresenter;
    private LinearLayout nodataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linesPresenter = new CustomizedSelectLinesPresenter(this,this);
        startAddress = (AddressBean) getIntent().getExtras().getSerializable("startAddress");
        endAddress = (AddressBean) getIntent().getExtras().getSerializable("endAddress");
        lineId =  getIntent().getExtras().getInt("lineId");
        getSelectLines();
    }


    public void initView() {
        ButterKnife.bind(this);
     nodataView = findViewById(R.id.id_nodata_view);
        recyclerView = findViewById(R.id.id_recyclerView);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        activityAdapter = new CustomizedBusReturnLinesActivityAdapter(this, null);
        recyclerView.setAdapter(activityAdapter);
        activityAdapter.setOnRecyclerViewClickListener(new CustomizedBusReturnLinesActivityAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
//                Intent intent = new Intent(TicketPaySuccessActivity.this, CustomizatedLineDetailActivity.class);
//                intent.putExtra("lineId",lineId);
//                startActivity(intent);
//                //new Intent(TicketPaySuccessActivity.this, CustomizatedLineDetailActivity.class)
                CustomizedLineBean.DataBean dataBean =   activityAdapter.getDataList().get( position);
                Intent intent =  new Intent(TicketPaySuccessActivity.this, CustomizatedLineDetailActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("lineName",dataBean.getName());
                startActivity( intent);

            }

            @Override
            public void onItemBuyInLineViewHolderClickListener(View view, int position) {
                CustomizedLineBean.DataBean dataBean =   activityAdapter.getDataList().get( position);
                Intent intent =  new Intent(TicketPaySuccessActivity.this, CustomizatedLineDetailActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("lineName",dataBean.getName());
                startActivity( intent);
            }
        });


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tiket_pay_success;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }


    /*****************************************************点击*************************************************/
    @OnClick(R.id.id_see_myticket_btn)
    public void onViewClicked() {
         pushTicketMineActivity();
    }

    /*****************************************************网络请求响应*************************************************/
    @Override
    public void requestOnSuccees(CustomizedLineBean lineBean) {
        if (lineBean != null && lineBean.getData() != null && lineBean.getData().size() > 0) {
            activityAdapter.setDataList(lineBean.getData());
            nodataView.setVisibility(View.INVISIBLE);
        } else {
            nodataView.setVisibility(View.VISIBLE);
            activityAdapter.setDataList(null);
        }
    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {

    }

    /*****************************************************数据请求*************************************************/
    private void getSelectLines() {
        Map<String, Object> param = new HashMap<>();
        //搜索已购买车票返程票
        AddressBean retunStartAddress = endAddress;
        AddressBean retunEndAddress = startAddress;
        param.put("startLon", retunStartAddress.getLongitude());
        param.put("startLat", retunStartAddress.getLatitude());
        param.put("endLon", retunEndAddress.getLongitude());
        param.put("endLat", retunEndAddress.getLatitude());
        //activityAdapter.setDataList();

        linesPresenter.sendRequestGetLinesWithStartEndAddress(param);
    }

    /******************************************跳转activity*******************************/
    //下一个Activity
    private void pushTicketMineActivity() {
        Intent intent = new Intent(TicketPaySuccessActivity.this, TicketMineActivity.class);
        startActivity(intent);
    }



}
