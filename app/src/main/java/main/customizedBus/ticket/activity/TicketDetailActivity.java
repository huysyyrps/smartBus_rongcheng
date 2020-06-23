package main.customizedBus.ticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.line.presenter.CustomizedLinesDetailPresenter;
import main.customizedBus.ticket.bean.TicketDetailBean;
import main.customizedBus.ticket.dialog.CustomizatedTicketCheckDialog;
import main.customizedBus.ticket.module.CustomizedTicketDetailContract;
import main.customizedBus.ticket.presenter.CustomizedTicketDetailPresenter;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;
import main.utils.baidu.BaiDuLineTrackManager;
import main.utils.baidu.BaiDuRoutePlanSearchMananger;
import main.utils.baidu.overlayutil.DrivingRouteOverlay;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;

public class TicketDetailActivity extends BaseActivity implements CustomizedTicketDetailContract.View{
    @BindView(R.id.id_baidu_map_view)
    MapView baiduMapView;
    @BindView(R.id.id_notice_tv)
    TextView noticeTv;
    @BindView(R.id.id_line_name_tv)
    TextView lineNameTv;
    @BindView(R.id.id_carno_tv)
    TextView carnoTv;
    @BindView(R.id.id_date_peoplenum_tv)
    TextView datePeoplenumTv;
    @BindView(R.id.id_onbus_time)
    TextView onbusTime;
    @BindView(R.id.id_onbus_icon)
    ImageView onbusIcon;
    @BindView(R.id.id_onbus_icon_linearlayout)
    LinearLayout onbusIconLinearlayout;
    @BindView(R.id.id_onbus_station)
    TextView onbusStationTv;
    @BindView(R.id.id_line)
    View line;
    @BindView(R.id.id_offbus_time)
    TextView offbusTime;
    @BindView(R.id.id_offbus_icon)
    ImageView offbusIcon;
    @BindView(R.id.id_offbus_icon_linearlayout)
    LinearLayout offbusIconLinearlayout;
    @BindView(R.id.id_offbus_station)
    TextView offbusStationTv;
    @BindView(R.id.id_check_ticket_bg_img)
    ImageView checkTicketBgImg;
    @BindView(R.id.id_check_ticket_button)
    Button checkTicketButton;
    @BindView(R.id.id_refund_button)
    Button refundButton;
    private CustomizedTicketDetailPresenter ticketDetailPresenter;
    private CustomizedLinesDetailPresenter linesDetailPresenter;
    private CustomizatedTicketCheckDialog ticketCheckDialog;
    private TicketDetailBean.DataBean dataBean;
    private int status = 0; //��Ʊ״̬
    private int ticketId =0;
    private String mobileStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ticketId = intent.getExtras().getInt("id");
        mobileStatus = intent.getExtras().getString("mobileStatus");

        ticketCheckDialog = new CustomizatedTicketCheckDialog(this);
        ticketDetailPresenter = new CustomizedTicketDetailPresenter(this, this);
        linesDetailPresenter = new CustomizedLinesDetailPresenter(this, this);
       showOrHiddenCheckTicketButton(false);
        settingEnableRefundButton(false);
        getTicketDetailFromNet(ticketId);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ticket_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick({R.id.id_check_ticket_button, R.id.id_refund_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_check_ticket_button:
                ticketCheckDialog.show();//��Ʊ��Ϣ��ʾ
                ticketCheckDialog.setDataBean(dataBean);
                checkTicketFromNet(ticketId);
                break;
            case R.id.id_refund_button:
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(this);
                alertDialogUtil.showDialog("�Ƿ�ȷ���˿�", new AlertDialogCallBack() {
                    @Override
                    public int getData(int s) {
                        return 0;
                    }
                    @Override
                    public void confirm() {
                      ticketRefundApplyFromNet(ticketId);
                    }
                    @Override
                    public void cancel() {

                    }
                });
                break;
        }
    }

    /****************************************************************��������*****************************************************/
    //��������
    private void getTicketDetailFromNet(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        ticketDetailPresenter.sendRequestGetTicketDetail(param);
    }
    private void checkTicketFromNet(int id) {//��Ʊ

        if (status==1){//����Ʊ�����ٷ�����
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        ticketDetailPresenter.sendRequestCheckTicket(param);
    }

    private void ticketRefundApplyFromNet(int id) {//�˿�
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        ticketDetailPresenter.sendRequestTicketRefundApply(param);
    }
//��Ӧ
    @Override
    public void requestOnSuccees(TicketDetailBean ticketBean) {//Ʊ����
        if (ticketBean != null) {
            status = ticketBean.getData().getStatus();
            //������·����
            linesDetailPresenter.sendRequestGetcustomizedLineDetail(ticketBean.getData().getLineId());
            dataBean = ticketBean.getData();
            lineNameTv.setText(dataBean.getLineName());
            String peopleNum = String.valueOf(dataBean.getNum());
            Date rideDate = new Date(dataBean.getRideDate());//�˳�����
            String rideDateStr = CalendarManager.getDateFormat(rideDate, "yyyy��MM��dd��");
            String datePeoplenumStr = rideDateStr + "       " + peopleNum + "�˳���";
            datePeoplenumTv.setText(datePeoplenumStr);
            onbusStationTv.setText(dataBean.getStartStaion());
            offbusStationTv.setText(dataBean.getEndStation());
            carnoTv.setText("��Σ�" + dataBean.getSchedulTime());
            if (dataBean.getRefundRemark()!=null&&dataBean.getRefundRemark().equals("")){
                noticeTv.setText(dataBean.getRefundRemark());
            }


           double nowTime = ticketBean.getData().getNowTime(); //����������ʱ��
           String startTimeStr = rideDateStr + " " + ticketBean.getData().getStartStationTime(); //
           String endTimeStr = rideDateStr  + " " + ticketBean.getData().getEndStationTime(); //
            Date startDate = CalendarManager.getDate(startTimeStr,"yyyy��MM��dd�� hh:mm");
            Date endDate = CalendarManager.getDate(endTimeStr,"yyyy��MM��dd�� hh:mm");
            boolean checkButtonShow = (ticketBean.getData().getShowFlag()==0);
            Log.i("sssss", ticketBean.getData().getShowFlag()+"uuu");
            boolean canRefund = ticketBean.getData().getReturnFlag()==0?true:false;
            settingEnableRefundButton(canRefund);
            showOrHiddenCheckTicketButton(checkButtonShow);
        }


    }

    @Override
    public void requestOnSuccees(CustomizedLineDetailBean lineBean) {
             if (lineBean != null&&lineBean.getData()!=null&&lineBean.getData().getSchedulDTOList().size()>0){
                 List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean> listBeans = lineBean.getData().getSchedulDTOList();
                 CustomizedLineDetailBean.DataBean.SchedulDTOListBean listBean = listBeans.get(0);
                 List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList = listBean.getSchedulStationDTOList();

                 playBusTrackLineAndMarkers(dataList,lineBean);
             }
    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        showToastNetFail();
    }
//��Ʊ
    @Override
    public void requestOnCheckTicketSuccees(PublicResponseBean responseBean) {//	code��״̬��0��ʧ�ܣ�1���ɹ���-1�������쳣��
        if (responseBean.getCode()==1){//�ɹ�
            getTicketDetailFromNet(ticketId);
        }
        showToast(responseBean.getMsg());
    }

    @Override
    public void requestOnCheckTicketFailure(Throwable e, boolean isNetWorkError) {
        showToastNetFail();
    }
//��Ʊ
    @Override
    public void requestOnTicketRefundApplySuccees(PublicResponseBean responseBean) {//��0��ʧ�ܣ�1���ɹ���-1�������쳣��
        if (responseBean.getCode()==1){//�ɹ�
            getTicketDetailFromNet(ticketId);
        }
        showToast(responseBean.getMsg());
    }

    @Override
    public void requestOnTicketRefundApplyFailure(Throwable e, boolean isNetWorkError) {
          showToastNetFail();
    }

    //������������ʾ��������Ʊ��ť
    private void showOrHiddenCheckTicketButton(boolean show) {
        if (show) {
            checkTicketBgImg.setVisibility(View.VISIBLE);
            checkTicketButton.setVisibility(View.VISIBLE);
        } else {
            checkTicketBgImg.setVisibility(View.INVISIBLE);
            checkTicketButton.setVisibility(View.INVISIBLE);
        }
    }
    //�����˿ť�Ƿ�ɵ��
    private void settingEnableRefundButton(boolean enbled){
        refundButton.setEnabled(enbled);
         if (enbled){
             refundButton.setText("�˿�");
         }else {
             String textStr =mobileStatus;
             refundButton.setText(textStr);
         }
    }




    //��ͼ�������켣��վ��
    private void playBusTrackLineAndMarkers(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList, CustomizedLineDetailBean lineDetailBean) {
        baiduMapView.getMap().clear();//���������
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
                if (drivingRouteResult.getRouteLines().size() > 0) {
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
}
