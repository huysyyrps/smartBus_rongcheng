package main.customizedBus.home.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.refreshview.CustomRefreshView;

import main.customizedBus.home.adapter.SearchAddressLinesActivityAdapter;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.line.activity.CustomizatedLineDetailActivity;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedSelectLinesContract;
import main.customizedBus.line.presenter.CustomizedSelectLinesPresenter;
import main.customizedBus.ticket.activity.TicketInfoSelectActivity;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.PublicData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAddressLinesActivity extends BaseActivity implements CustomizedSelectLinesContract.View {
    private final int SetOutAddressCode = 10001;
    private final int EndAddressCode = 10002;
    private TextView startAddressTV;
    private TextView endAddressTV;
    private Button switchButton;
    private CustomRefreshView recyclerView;
    private SearchAddressLinesActivityAdapter activityAdapter;
    private LinearLayout nodataView;

    private AddressBean startAddress;
    private AddressBean endAddress;
    private CustomizedSelectLinesPresenter linesPresenter;
    private int page =1;//请求的page
    public List<CustomizedLineBean.DataBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<>();
        startAddress = (AddressBean) getIntent().getExtras().getSerializable("startAddress");
        endAddress = (AddressBean) getIntent().getExtras().getSerializable("endAddress");
        linesPresenter = new CustomizedSelectLinesPresenter(this,this);
//        initView();

        reloadSearView();
    }

    private void reloadSearView() {
        if (startAddress != null){
            startAddressTV.setText(startAddress.getName());
        }
       if (endAddress!= null){
          endAddressTV.setText(endAddress.getName());
      }

       getSelectLines();

    }

    public void initView() {
        recyclerView = findViewById(R.id.id_recyclerView);
        recyclerView.getRecyclerView().setBackgroundColor(getResources().getColor(R.color.recyclerViewBackgroundColor));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        activityAdapter = new SearchAddressLinesActivityAdapter(this,null);
        recyclerView.setAdapter(activityAdapter);

        activityAdapter.setOnRecyclerViewClickListener(new SearchAddressLinesActivityAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                CustomizedLineBean.DataBean dataBean =  activityAdapter.getDataList().get(position);
                Intent intent =  new Intent(SearchAddressLinesActivity.this, CustomizatedLineDetailActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("schedulIndex",0);
                startActivity(intent);


            }

            @Override
            public void onItemBuyInLineViewHolderClickListener(View view, int position) {
                CustomizedLineBean.DataBean dataBean =  activityAdapter.getDataList().get(position);
                Intent intent =  new Intent(SearchAddressLinesActivity.this, TicketInfoSelectActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("schedulIndex",0);
                startActivity(intent);
            }
        });

        //上下拉刷新
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                //下拉刷新，添加你刷新后的逻辑

                page=1;
              getSelectLines();
                //加载完成时，隐藏控件下拉刷新的状态
                //   recyclerView.complete();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多，添加你加载数据的逻辑
                page+=1;
               getSelectLines();
                //加载完成时，隐藏控件上拉加载的状态
                // recyclerView.complete();
            }
        });
        recyclerView.setRefreshEnable(false);
        //上车地点
        startAddressTV = findViewById(R.id.id_setout_textview);
        startAddressTV.setOnClickListener(this::onClick);
        //下车点
        endAddressTV = findViewById(R.id.id_end_textview);
        endAddressTV.setOnClickListener(this::onClick);
        //切换按钮
        switchButton = findViewById(R.id.id_switch_address_btn);
        switchButton.setOnClickListener(this::onClick);
        //无数据时显示
        nodataView = findViewById(R.id.id_nodata_view);

    }
//

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        Intent intent;
        switch (id){
            case R.id.id_setout_textview:
                intent =  new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent,SetOutAddressCode);
                break;
            case R.id.id_end_textview:
                intent =  new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent,EndAddressCode);
                break;
            case R.id.id_switch_address_btn:
                AddressBean bean = startAddress;
                startAddress = endAddress;
                endAddress = bean;
                reloadSearView();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        HashMap<String,AddressBean> dataMap = (HashMap) data.getExtras().get("data");
        AddressBean addressBean = dataMap.get("addressBean");
        if (addressBean == null){
            addressBean = new AddressBean();
        }
        switch (requestCode){
            case SetOutAddressCode:
                startAddress = addressBean;
                reloadSearView();
                break;
            case EndAddressCode:
               endAddress = addressBean;

            reloadSearView();
                break;
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_address_lines;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
    /*****************************************************网络请求响应*************************************************/
    @Override
    public void requestOnSuccees(CustomizedLineBean lineBean) {
       if (lineBean!=null&&lineBean.getData()!=null&&lineBean.getData().size()>0){
           nodataView.setVisibility(View.INVISIBLE);
           recyclerView.complete();
           if (page==1){
               dataList.clear();
           }
           dataList.addAll(lineBean.getData());
           activityAdapter.setDataList(dataList);
           if (page==1&&lineBean.getData().size()==0){
               recyclerView.isEmptyViewShowing();
           }else  if (lineBean.getData().size()< PublicData.limit){
               recyclerView.onNoMore();
           }
       }else {
           nodataView.setVisibility(View.VISIBLE);
           activityAdapter.setDataList(null);
       }
    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        recyclerView.complete();
        recyclerView.setErrorView();
    }
    /*****************************************************数据请求*************************************************/
    private void  getSelectLines(){
        Map<String,Object> param = new HashMap<>();
        param.put("startLon",startAddress.getLongitude());
        param.put("startLat",startAddress.getLatitude());
        param.put("endLon",endAddress.getLongitude());
        param.put("endLat",endAddress.getLatitude());
        //activityAdapter.setDataList();
        linesPresenter.sendRequestGetLinesWithStartEndAddress(param);
    }
}
