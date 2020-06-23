package main.customizedBus.home.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.refreshview.CustomRefreshView;

import main.customizedBus.dao.AddressHistoryDao;
import main.customizedBus.home.adapter.SearchAddressActivityAdapter;
import main.customizedBus.home.adapter.SearchAddressActivityHistoryAdapter;
import main.customizedBus.home.bean.AddressBean;
import main.smart.rcgj.R;
import main.utils.baidu.LocationMananger;
import main.utils.baidu.POISearchMananger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAddressActivity extends AppCompatActivity {
    private CustomRefreshView recyclerView;//�����б�
    private SearchAddressActivityAdapter addressActivityAdapter;
    private EditText searchEditText;
    private AddressBean locationAddressBean = new AddressBean() ;

    private RecyclerView historyRecyclerView;//��ǰλ����ʷ�б�
    private SearchAddressActivityHistoryAdapter addressActivityHistoryAdapter;

    private POISearchMananger poiSearchMananger;
//����
public int page = 0;//�ڼ�ҳ����
    private  List<AddressBean> dataList;
    private  List<AddressBean> historyDataList;
    private  String keyWord = "";//�����ؼ���
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        dataList = new ArrayList<>();
        historyDataList = AddressHistoryDao.getInstance(this).getAllData();
        //
        initView();
        initRecyclerView();
        initHistoryRecyclerView();
//��ʼ���û�λ��bean
        locationAddressBean = new AddressBean();

        //��λ����
        LocationMananger   locationMananger = LocationMananger.getInstance(this);
        if (locationMananger.getDesLocation()!=null){
            BDLocation location = locationMananger.getDesLocation();
            locationAddressBean = new AddressBean();
            locationAddressBean.setName(location.getLocationDescribe());
            locationAddressBean.setAddr(location.getAddrStr());
            locationAddressBean.setLatitude(location.getLatitude());
            locationAddressBean.setLongitude(location.getLongitude());
            locationAddressBean.setLoaction(true);
            addressActivityHistoryAdapter.setLocationBean(locationAddressBean);
        }
        locationMananger.setOnReceiveLocationListener(new LocationMananger.OnReceiveLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                locationAddressBean.setName(location.getLocationDescribe());
               locationAddressBean.setAddr(location.getAddrStr());
               locationAddressBean.setLatitude(location.getLatitude());
               locationAddressBean.setLongitude(location.getLongitude());
                locationAddressBean.setLoaction(true);
                addressActivityHistoryAdapter.setLocationBean(locationAddressBean);
            }
        });
        //POI����
        poiSearchMananger = new POISearchMananger(this);
        poiSearchMananger.setOnGetPoiSearchResultListener(new POISearchMananger.OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                recyclerView.complete();
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                       dataList.clear();
                    addressActivityAdapter.setDataList(dataList);
                }else if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<PoiInfo> allPoi =  poiResult.getAllPoi();
                 if (allPoi ==null)
                 {
                     dataList.clear();
                     addressActivityAdapter.setDataList(dataList);
                    return;
                 }

                    Log.i("ssssss", "onGetPoiResult: ");
                 for (int i = 0;i< allPoi.size();i++){
                     PoiInfo  poi = allPoi.get(i);
                     AddressBean addressBean = new AddressBean(poi);
                     dataList.add(addressBean);
                 }
                    addressActivityAdapter.setDataList(dataList);
                 if (allPoi.size()<poiSearchMananger.getLimitInPage()){
                     recyclerView.onNoMore();
                 }

                }
            }
        });
    }

    private void initHistoryRecyclerView() {
        historyRecyclerView = findViewById(R.id.id_recyclerView_history);
        //���ò��ֹ�����
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        historyRecyclerView.setLayoutManager(linearLayoutManager);
        //����adapter
        addressActivityHistoryAdapter = new SearchAddressActivityHistoryAdapter(this,historyDataList,locationAddressBean);
        historyRecyclerView.setAdapter(addressActivityHistoryAdapter);
//        historyRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        addressActivityHistoryAdapter.setOnRecyclerViewClickListener(new SearchAddressActivityHistoryAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (position==0||position==2){
                    return;
                }
                AddressBean bean = null;
                if (position==1){
                 bean = locationAddressBean;
                }else {
                    bean = historyDataList.get(position-3);
                }
                Intent intent = new Intent();
                HashMap<String,AddressBean> dataMap = new HashMap();
                dataMap.put("addressBean",bean);
                intent.putExtra("data", dataMap);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.id_recyclerView);
        //���ò��ֹ�����
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //����adapter
        addressActivityAdapter = new SearchAddressActivityAdapter(this,new ArrayList<>());
        recyclerView.setAdapter(addressActivityAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //�������� ������� ����ʧ��
        recyclerView.setEmptyView("��������");
        addressActivityAdapter.setOnRecyclerViewClickListener(new SearchAddressActivityAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent();
                if (dataList.size()<position){
                    return;
                }
                AddressBean bean = dataList.get(position);
                HashMap<String,AddressBean> dataMap = new HashMap();
                dataMap.put("addressBean",bean);
                intent.putExtra("data", dataMap);
                setResult(Activity.RESULT_OK,intent);
                AddressHistoryDao.getInstance(SearchAddressActivity.this).insert(bean);
                finish();
            }
        });

        //������ˢ��
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                //����ˢ�£������ˢ�º���߼�
                dataList.clear();
                page=1;
                //�������ʱ�����ؿؼ�����ˢ�µ�״̬
                //   recyclerView.complete();
            }

            @Override
            public void onLoadMore() {
                //�������ظ��࣬�����������ݵ��߼�
                page+=1;
                poiSearchMananger.searchInCity(keyWord,page);
                //�������ʱ�����ؿؼ��������ص�״̬
                // recyclerView.complete();
            }
        });
        //��ֹ����ˢ��
        recyclerView.setRefreshEnable(false);
    }

    private void initView() {
        //��ʼ��������
       searchEditText = findViewById(R.id.id_search_edittext);
       //��������仯
       searchEditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               Log.i("ss",editable.toString());
               if (editable.toString().equals("")){
                   historyRecyclerView.setVisibility(View.VISIBLE);
               }else {
                   historyRecyclerView.setVisibility(View.INVISIBLE);
               }
               if (!keyWord.equals(editable.toString())){
                   page =0;
                   keyWord = editable.toString();
                   poiSearchMananger.searchInCity(keyWord,page);
                   dataList.clear();
                   addressActivityAdapter.setDataList(dataList);
               }

           }
       });
    }

    //
   public void cancelButtonClick(View view){
        finish();//����
    }


}
