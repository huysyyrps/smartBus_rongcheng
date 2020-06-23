package main.customizedBus.line.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.customizedBus.line.adapter.CustomizatedLineDetailActivityAdapter;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.line.module.CustomizedLinesDetailContract;
import main.customizedBus.line.presenter.CustomizedLinesDetailPresenter;
import main.customizedBus.ticket.activity.TicketInfoSelectActivity;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;
import main.utils.baidu.BaiDuLineTrackManager;
import main.utils.baidu.BaiDuRoutePlanSearchMananger;
import main.utils.baidu.overlayutil.DrivingRouteOverlay;
import main.utils.base.BaseActivity;
import main.utils.utils.DensityUtil;
import main.utils.utils.NonNullString;
import main.utils.utils.PublicData;

public class CustomizatedLineDetailActivity extends BaseActivity implements CustomizedLinesDetailContract.View {

    @BindView(R.id.id_baidu_map_view)
    MapView baiduMapView;
    @BindView(R.id.id_button_buy)
    Button buttonBuy;
    @BindView(R.id.id_remarked_tv)
    TextView remarkedTv;
    private RecyclerView recyclerView;
    private ImageView dropDownImgV;
    private RadioGroup shiftsRadioGroup;
    private CustomizatedLineDetailActivityAdapter adapter;
    private Button buyButton;
    private int lineId;
    private CustomizedLinesDetailPresenter linesDetailPresenter;
    private CustomizedLineDetailBean lineDetailBean;
    private int schedulIndex = 0;//Ĭ����ʾ��һ�����


    @BindView(R.id.id_satartstation_tv)
    TextView startStationTV;
    @BindView(R.id.id_endstation_tv)
    TextView endStationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linesDetailPresenter = new CustomizedLinesDetailPresenter(this, this);
        Intent intent = getIntent();
        lineId = intent.getExtras().getInt("lineId");
        String lineName = intent.getExtras().getString("lineName");
        activityHeader.setTvTitle(lineName);

        linesDetailPresenter.sendRequestGetcustomizedLineDetail(lineId);

    }

    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.id_recyclerView);
        //���ò��ֿ�����
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //����adapter
        adapter = new CustomizatedLineDetailActivityAdapter(this, null);
        recyclerView.setAdapter(adapter);


        //��������ť
        dropDownImgV = findViewById(R.id.id_drop_down_imgV);
        shiftsRadioGroup = findViewById(R.id.id_shifts_radiogroup);


        dropDownImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                if (layoutParams.height < 1) {
                    layoutParams.height = DensityUtil.dip2px(CustomizatedLineDetailActivity.this, 150);
                    dropDownImgV.setImageResource(R.drawable.drowp_up);
                } else {
                    layoutParams.height = 0;
                    dropDownImgV.setImageResource(R.drawable.drowp_down);
                }
                recyclerView.setLayoutParams(layoutParams);
            }
        });
        //��Ʊ��ť
        buyButton = findViewById(R.id.id_button_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomizatedLineDetailActivity.this, TicketInfoSelectActivity.class);
                intent.putExtra("lineId", lineId);
                intent.putExtra("schedulIndex", schedulIndex);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_customizated_line_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    //�����ʾ
    public void initShiftsRadioGroupItem() {
        List<String> schedulArr = lineDetailBean.getData().getSchedulArr();
        for (int i = 0; i < schedulArr.size(); i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.rationbutton_shifts, shiftsRadioGroup, false);
            radioButton.setId(i);
            radioButton.setText(schedulArr.get(i));
            radioButton.setOnClickListener(this::onViewClickSwitchBusSchedulListener);
            shiftsRadioGroup.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
    }

    /***********************************************���*********************************************************/
    public void onViewClickSwitchBusSchedulListener(View view) {
        schedulIndex = view.getId();
        switchBusSchedul(schedulIndex);
        Log.i("sss", "onViewClickSwitchBusSchedulListener: ");

    }

    /***********************************************����ˢ��*********************************************************/
    private void switchBusSchedul(int i) {
        List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean> listBeans = lineDetailBean.getData().getSchedulDTOList();
        buttonBuy.setText("��" + lineDetailBean.getData().getPriceStr() + "��Ʊ");
        if (listBeans != null && listBeans.size() > i) {
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean listBean = listBeans.get(i);
            List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList = listBean.getSchedulStationDTOList();
            if (dataList != null) {
                adapter.setDataList(dataList);
                playBusTrackLineAndMarkers(dataList);
                if (dataList.size()>0){
                    CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean startStationBean =  dataList.get(0);
                    CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean endStationBean =  dataList.get(dataList.size()-1);
                    //����ȫ��Ԥ�ƺ�ʱʱ��
                    String  starTimetStr = startStationBean.getTime();
                    String  endTimeStr = endStationBean.getTime();

                    Date startTimeDate = CalendarManager.getDate(starTimetStr,"HH:mm");
                    Date endTimeDate = CalendarManager.getDate(endTimeStr,"HH:mm");
                    int timeDifference = (int) ((endTimeDate.getTime() - startTimeDate.getTime())/(60*1000));
                    remarkedTv.setText("ȫ��Ԥ�ƺ�ʱ"+String.valueOf(timeDifference)+"����");
                }
            }


        }
    }

    private void playBusTrackLineAndMarkers(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList) {
        baiduMapView.getMap().clear();//���������
//        BaiDuLineTrackManager.baiduMapPolyBusTrackline(dataList, baiduMapView);
        BaiDuLineTrackManager.baiduMapAddBusStationMarkers(dataList, baiduMapView);
        BaiDuRoutePlanSearchMananger routePlanSearchMananger = BaiDuRoutePlanSearchMananger.getInstance(this,baiduMapView.getMap(),true);
        List<CustomizedLineDetailBean.DataBean.LinePlanDTOListBean> linePlanListBeans = lineDetailBean.getData().getLinePlanDTOList();
        routePlanSearchMananger.searchDriving(linePlanListBeans);
        //
        routePlanSearchMananger.setOnGetRoutePlanResultListener(new BaiDuRoutePlanSearchMananger.OnGetRoutePlanResultListener() {
               @Override
               public void onGetWalkingRouteResult(WalkingRouteResult var1) {

               }

               @Override
               public void onGetTransitRouteResult(TransitRouteResult var1) {

               }

               @Override
               public void onGetMassTransitRouteResult(MassTransitRouteResult var1) {

               }

               @Override
               public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult, DrivingRouteOverlay overlay) {
                   if (drivingRouteResult.getRouteLines()!=null&&drivingRouteResult.getRouteLines().size() > 0) {
                       //��ȡ·���滮����,(�Է��صĵ�һ��·��Ϊ����
                       //ΪDrivingRouteOverlayʵ����������
                       DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);
                       overlay.setData(drivingRouteResult.getRouteLines().get(0));
                       //�ڵ�ͼ�ϻ���DrivingRouteOverlay
                       overlay.addToMap();
                   }
               }

               @Override
               public void onGetIndoorRouteResult(IndoorRouteResult var1) {

               }

               @Override
               public void onGetBikingRouteResult(BikingRouteResult var1) {

               }
           });



    }

/************************************************��������*********************************************************/
    /***************************************��������Ӧ************************************************************/
    @Override
    public void requestOnSuccees(CustomizedLineDetailBean lineBean) {
        lineDetailBean = lineBean;
        if (lineBean != null) {
            initShiftsRadioGroupItem();
            if (lineBean.getCode() != 0) {
                Toast.makeText(this, lineBean.getMsg(), Toast.LENGTH_LONG).show();
            }
            List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean> listBeans = lineBean.getData().getSchedulDTOList();
            if (lineBean.getData() != null) {
                startStationTV.setText(NonNullString.getString(lineBean.getData().getStartStation()));
                endStationTV.setText(NonNullString.getString(lineBean.getData().getEndStation()));
            }
            switchBusSchedul(schedulIndex);
        }

    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        Toast.makeText(this, PublicData.netWorkErrorMsg, Toast.LENGTH_LONG).show();
    }

}
