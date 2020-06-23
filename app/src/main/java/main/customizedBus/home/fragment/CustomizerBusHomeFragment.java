package main.customizedBus.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.example.refreshview.CustomRefreshView;

import main.customizedBus.home.activity.CustomizedWebViewActivity;
import main.customizedBus.home.activity.SearchAddressActivity;
import main.customizedBus.home.activity.SearchAddressLinesActivity;
import main.customizedBus.home.adapter.CustomizedBusHomeFragmentAdapter;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.initiateCustomization.activity.InitiateCustomizationActivity;
import main.customizedBus.line.activity.CustomizatedLineDetailActivity;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedLinesContract;
import main.customizedBus.line.presenter.CustomizedLinesPresenter;
import main.customizedBus.ticket.activity.TicketInfoSelectActivity;
import main.customizedBus.ticket.activity.TicketMineActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.smart.rcgj.R;
import main.utils.baidu.LocationMananger;
import main.utils.utils.PublicData;


public class CustomizerBusHomeFragment extends Fragment implements CustomizedLinesContract.View {

    private CustomRefreshView recyclerView;
    private CustomizedBusHomeFragmentAdapter adapter;
    private final int SetOutAddressCode = 10001;
    private final int EndAddressCode = 10002;
    //
    private AddressBean startAddress;
    private AddressBean endAddress;
    //
    private CustomizedLinesPresenter customizedLinesPresenter;
    private int page =1;//请求的page
    public List<CustomizedLineBean.DataBean> dataList;
    private LocationMananger locationMananger;
    private  AddressBean locationAddressBean;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_customizer_bus_home,container,false);
        initView(view);

//
        customizedLinesPresenter = new CustomizedLinesPresenter(getActivity(),this);

        //定位监听
       locationMananger = LocationMananger.getInstance(getActivity());
       if (locationMananger.getDesLocation()!=null&&locationAddressBean==null){
           BDLocation location = locationMananger.getDesLocation();
            locationAddressBean = new AddressBean();
           locationAddressBean.setName(location.getLocationDescribe());
           locationAddressBean.setAddr(location.getAddrStr());
           locationAddressBean.setLatitude(location.getLatitude());
           locationAddressBean.setLongitude(location.getLongitude());
           locationAddressBean.setLoaction(true);
           setStartAddress(locationAddressBean);
       }
        locationMananger.setOnReceiveLocationListener(new LocationMananger.OnReceiveLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (locationAddressBean == null){
                    locationAddressBean = new AddressBean();
                    locationAddressBean.setName(location.getLocationDescribe());
                    locationAddressBean.setAddr(location.getAddrStr());
                    locationAddressBean.setLatitude(location.getLatitude());
                    locationAddressBean.setLongitude(location.getLongitude());
                    locationAddressBean.setLoaction(true);
                    setStartAddress(locationAddressBean);
                }

            }
        });
        //
        getAllLines();
        return view;

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.id_recyclerView);
        recyclerView.getRecyclerView().setBackgroundColor(getResources().getColor(R.color.recyclerViewBackgroundColor));
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        adapter = new CustomizedBusHomeFragmentAdapter(getActivity(),null);
        //暂无数据 网络错误 请求失败
        recyclerView.setEmptyView("暂无数据");

        //点击监听
        adapter.setOnRecyclerViewClickListener(new CustomizedBusHomeFragmentAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                  if (position<2){
                      return;
                  }
                int idex = position - 2;
                CustomizedLineBean.DataBean dataBean =   adapter.getDataList().get(idex);
                Intent intent =  new Intent(getActivity(), CustomizatedLineDetailActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("lineName",dataBean.getName());
                startActivity( intent);
            }

            @Override
            public void onItemBuyInLineViewHolderClickListener(View view, int position) {
                int idex = position - 2;

                CustomizedLineBean.DataBean dataBean =   adapter.getDataList().get(idex);
                   Intent intent =  new Intent(getActivity(), TicketInfoSelectActivity.class);
                intent.putExtra("lineId",dataBean.getId());
                intent.putExtra("schedulIndex",0);
                   startActivity(intent);
            }

            @Override
            public void onItemInSearchViewClickListener(View view, int position, View subView, int subViewId) {
                Intent intent =  new Intent(getActivity(), SearchAddressActivity.class);
                switch (subViewId){
                    case R.id.id_setout_textview :
                        startActivityForResult(intent,SetOutAddressCode);
                        break;
                    case R.id.id_end_textview:
                        startActivityForResult(intent,EndAddressCode);
                        break;
                }

            }

            @Override
            public void onItemInFastMenusViewClickListener(View view, int position,  int subViewId) {
                switch (subViewId){
                      case R.id.id_dingzhi_button:
                          startActivity(new Intent(getActivity(), InitiateCustomizationActivity.class));
                      break;
                    case R.id.id_zhinan_button:
                        Intent intent = new Intent(getActivity(), CustomizedWebViewActivity.class);
                        intent.putExtra("introduceType",1);
                        startActivity(intent);
                        break;
                    case R.id.id_ticket_button:
                        startActivity(new Intent(getActivity(), TicketMineActivity.class));
                        break;
                      default:
                      break;
                }
            }
        });
        recyclerView.setAdapter(adapter);

        //上下拉刷新
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                //下拉刷新，添加你刷新后的逻辑

                page=1;
                getAllLines();
                //加载完成时，隐藏控件下拉刷新的状态
                //   recyclerView.complete();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多，添加你加载数据的逻辑
                page+=1;
              getAllLines();
                //加载完成时，隐藏控件上拉加载的状态
                // recyclerView.complete();
            }
        });

//        recyclerView.setRefreshEnable(false);
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
                setStartAddress(addressBean);
                break;
            case EndAddressCode:
                setEndAddress(addressBean);
                if (endAddress!=null&&startAddress!=null){
                    Intent intent =  new Intent(getActivity(), SearchAddressLinesActivity.class);
                    intent.putExtra("startAddress",startAddress);
                    intent.putExtra("endAddress",endAddress);
                    startActivity(intent);
                }
                break;
        }
    }

    //set
    public void setStartAddress(AddressBean startAddress) {
        this.startAddress = startAddress;
        adapter.setStartAddress(startAddress);
    }

    public void setEndAddress(AddressBean endAddress) {
        this.endAddress = endAddress;
        adapter.setEndAddress(endAddress);
    }


    @Override
    public void requestOnSuccees(CustomizedLineBean lineBean) {
        recyclerView.complete();

        if (lineBean != null&&lineBean.getData()!=null){
            if (page==1){
                dataList.clear();
            }
            dataList.addAll(lineBean.getData());
            adapter.setDataList(dataList);
            if (page==1&&lineBean.getData().size()==0){
                recyclerView.isEmptyViewShowing();
            }else  if (lineBean.getData().size()<PublicData.limit){
                recyclerView.onNoMore();
            }
        }
        else {
            recyclerView.onError();
        }


    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        recyclerView.complete();
        recyclerView.setErrorView();
    }
    //

   private void  getAllLines(){
        Map<String,Object> param = new HashMap<>();
        param.put("page",page);
        param.put("limit", PublicData.limit);
       param.put("status",1);
        customizedLinesPresenter.sendRequestGetLines(param);
    }
}
