package main.customizedBus.ticket.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.ApiAddress;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.customizedBus.ticket.module.CustomizedBuyTicketContract;
import main.customizedBus.ticket.presenter.CustomizedBuyTicketPresenter;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;
import main.utils.pay.alipay.AliPayManager;
import main.utils.pay.wxpay.WXPayManager;
import main.utils.utils.MD5;
import main.utils.utils.PublicData;

public class TicketOrderPaymentActivity extends BaseActivity implements CustomizedBuyTicketContract.View {

    @BindView(R.id.id_onbus_station_tv)
    TextView onbusStationTv;
    @BindView(R.id.id_offbus_station_tv)
    TextView offbusStationTv;
    @BindView(R.id.id_schedul_tv)
    TextView schedulTv;

    @BindView(R.id.id_ticket_num_tv)
    TextView ticketNumTv;
    @BindView(R.id.id_total_price_tv)
    TextView totalPriceTv;
    @BindView(R.id.id_wxpay_radiobutton)
    RadioButton wxpayRadiobutton;
    @BindView(R.id.id_alipay_radiobutton)
    RadioButton alipayRadiobutton;
    @BindView(R.id.id_radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.id_total_price_bottom_tv)
    TextView totalPriceBottomTv;
    @BindView(R.id.id_pay_btn)
    Button payBtn;
    @BindView(R.id.id_horizontalscroll_contentview)
    LinearLayout horizontalscrollContentview;
    @BindView(R.id.id_line_name_tv)
    TextView lineNameTv;
    private CustomizedLineDetailBean lineDetailBean;
    private CustomizedLineDetailBean.DataBean.SchedulDTOListBean selectListBean;
    private int ridePeopleNum = 0;//��������
    private int payType = 1;//֧����ʽȥ0 ΢�� 1 ֧����
    private CustomizedBuyTicketPresenter buyTicketPresenter;
    private double totalMoney;
    private AddressBean startAddress;
    private AddressBean endAddress;
    private boolean paySuccess = false;//�Ƿ�֧���ɹ�
    private boolean showPaySuccessActivity = false;//֧���ɹ����Ƿ������תPaySuccessActivity���� ע�ⷽ������PaySuccessActivity��һ����ʾ


    private long countDownlength = 0;//����ʱʱ�����
    private  CountDownTimer countDownTimer;
    private BuyTicketOrderBean orderBean = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //ʵ��������������
        buyTicketPresenter = new CustomizedBuyTicketPresenter(this, this);
        //��ȡ��������
        Intent intent = getIntent();
        lineDetailBean = (CustomizedLineDetailBean) intent.getExtras().getSerializable("lineDetailData");
        selectListBean = (CustomizedLineDetailBean.DataBean.SchedulDTOListBean) intent.getExtras().getSerializable("selectListBean");
        ridePeopleNum = intent.getExtras().getInt("ridePeopleNum");
        startAddress = (AddressBean) getIntent().getExtras().getSerializable("startAddress");
        endAddress = (AddressBean) getIntent().getExtras().getSerializable("endAddress");
        //ˢ�½�������
        reloadViewData();
        //֧������ѡ��
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.id_wxpay_radiobutton:
                        payType = 1;
                        break;
                    case R.id.id_alipay_radiobutton:
                        payType = 0;
                        break;
                }
            }
        });

    }

    /**************************���***************************/
    @RequiresApi(api = Build.VERSION_CODES.O)
//ע������ý������ñ���ͨ��������û�б���Ͱ汾��ϵͳ���и߰汾��api�����⣬��ʹ��ʱ������Ҫ�Լ��жϰ汾����ʹ�ò�ͬ��api
    @OnClick({R.id.id_pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_pay_btn:

                getOrderInfo();
//             pushTicketMineActivity();
                break;
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ticket_order_payment;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }


    private void reloadViewData() {

        int peopleNum = ridePeopleNum;
        int dayNum = 0;
        double price = 0;
        if (lineDetailBean != null) {
            price = lineDetailBean.getData().getPrice();
            lineNameTv.setText(lineDetailBean.getData().getName());
        }

        if (selectListBean != null) {
            String schedulStr = "���" + selectListBean.getSchedulTime() + "����";
            schedulTv.setText(selectListBean.getSchedulTime());
            List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketBeans = new ArrayList<>();
            for (int i = 0; i < selectListBean.getTicketCountDTOList().size(); i++) {
                if (selectListBean.getTicketCountDTOList().get(i).isSelected() == true) {
                    dayNum++;
                    ticketBeans.add(selectListBean.getTicketCountDTOList().get(i));
                }
            }
            reloadHorizontalscrollview(ticketBeans);
        }

        int ticketNum = peopleNum * dayNum;
        totalMoney = ticketNum * price;
//        if (ticketNumTV != null){
//            ticketNumTV.setText("��ѡ��"+ticketNum+"��Ʊ");
//        }
//        if (ticketTotalPriceTV != null){
//            ticketTotalPriceTV.setText(String.valueOf(totalMoney));
//        }
        if (lineDetailBean != null) {
            onbusStationTv.setText(lineDetailBean.getData().getStartStation());
            offbusStationTv.setText(lineDetailBean.getData().getEndStation());
        }

        wxpayRadiobutton.setChecked(true);
        ticketNumTv.setText("��Ʊ��" + String.valueOf(ticketNum) + "��");
        totalPriceTv.setText("��" + PublicData.getDoubleStr(totalMoney) + "Ԫ");
        totalPriceBottomTv.setText(PublicData.getDoubleStr(totalMoney));


    }

//    @RequiresApi(api = Build.VERSION_CODES.O)//ע������ý������ñ���ͨ��������û�б���Ͱ汾��ϵͳ���и߰汾��api�����⣬��ʹ��ʱ������Ҫ�Լ��жϰ汾����ʹ�ò�ͬ��api
//    @OnClick(R.id.id_pay_btn)
//    public void onViewClicked() {
//
//    }

    private void reloadHorizontalscrollview(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketBeans) {
        if (ticketBeans == null) {
            return;
        }
        for (int i = 0; i < ticketBeans.size(); i++) {
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean ticketBean = ticketBeans.get(i);
            LayoutInflater inflater = LayoutInflater.from(this);
            RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.item_order_ticket_info, horizontalscrollContentview, false);
            TextView ticketNumTV = relativeLayout.findViewById(R.id.id_ticket_num_tv);
            TextView yyyyTV = relativeLayout.findViewById(R.id.id_yyyy_tv);
            TextView mmDDTV = relativeLayout.findViewById(R.id.id_mm_tv);
            TextView weekTV = relativeLayout.findViewById(R.id.id_week_tv);
            ticketNumTV.setText(String.valueOf(ridePeopleNum) + "��");
            Date date = new Date(ticketBean.getRideDate());
            String yyyyStr = CalendarManager.getDateFormat(date, CalendarManager.FORMAYYYY);
            String mmDDStr = CalendarManager.getDateFormat(date, "MM/dd");
            String weekStr = CalendarManager.getWeekDayStr(date);
            yyyyTV.setText(yyyyStr);
            mmDDTV.setText(mmDDStr);
            weekTV.setText(weekStr);
            horizontalscrollContentview.addView(relativeLayout);
        }

    }
//֧������
    public void payOrder(){

        if (payType == 0) {

            AliPayManager aliPayManager = AliPayManager.getInstance(this);
            aliPayManager.ZFBpay(orderBean.getData().getOrderId(), totalMoney, ApiAddress.alipayNoticeUrl, new AliPayManager.OnPayResultListener() {
                @Override
                public void resultSuccess() {
                    paySuccess = true;
                    Log.e("ssssss", "֧���� ");

                }

                @Override
                public void resultFail() {


                }
            });

        } else if (payType == 1) {
            WXPayManager wxPayManager = WXPayManager.getInstance(this);
            wxPayManager.WXpay(orderBean.getData().getOrderId(), totalMoney, ApiAddress.wxpayNoticeUrl, new AliPayManager.OnPayResultListener() {
                @Override
                public void resultSuccess() {
                    paySuccess = true;

                    Log.e("ssssss", "΢�� ");
                }

                @Override
                public void resultFail() {
                    //  pushPaySuccessActivity();
                    // Log.e("ssssss", "΢��ʧ�� ");
                }
            });
        }

    }
    /*************************************��������*******************************/
    @Override
    public void requestOnSuccees(BuyTicketOrderBean orderBean) {
        this.orderBean = orderBean;
        if (orderBean != null && orderBean.getCode() == 1) {
            countDownlength = orderBean.getData().getPayTicketTime() * 60 * 1000;
            //��ʼ����ʱ
            startCountDown();
            payOrder();
        } else {
            String msg = "��������ʧ��";
            if (orderBean != null) {
                msg = orderBean.getMsg();
            }
            showToast(msg);
        }

    }


    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        showToast(PublicData.netWorkErrorMsg);
    }
//ȡ������
    @Override
    public void requestOnCancelOrderSuccees(PublicResponseBean responseBean) {
        if (responseBean!=null){
            showToast(responseBean.getMsg());
        }
        finish();
    }

    @Override
    public void requestOnCancelOrderFailure(Throwable e, boolean isNetWorkError) {
        showToast(PublicData.netWorkErrorMsg);
        finish();
    }

    //��ȡ������
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getOrderInfo() {
        if (orderBean != null && orderBean.getCode() == 1) {//���Ի�ȡ�������������¶���
            payOrder();
            return;
        }

        int[] payTypes = new int[]{0, 1, 2};//֧����ʽ��0:֧���� 1��΢�� 2��ũ��
        String passenger = PublicData.userAccount;//��·Id
        int lineId = lineDetailBean.getData().getId();//
        int schedulId = selectListBean.getId();//���Id
        int num = ridePeopleNum;//��Ʊ����
        String startStaion = lineDetailBean.getData().getStartStation();//�ϳ�վ��
        String endStation = lineDetailBean.getData().getEndStation();//�³�վ��
        int transType = 0;//��������
        double promiseMoney = totalMoney;//Ӧ֧�����Ż�֮ǰ�Ľ��
        double realMoney = promiseMoney;
        String mark = getMark(promiseMoney, realMoney);
        String ticketCountIds = getTicketCountIds();
        Map<String, Object> param = new HashMap<>();
        param.put("passenger", passenger);
        param.put("lineId", lineId);
        param.put("schedulId", schedulId);
        param.put("ticketCountIds", ticketCountIds);
        param.put("num", num);
        param.put("startStaion", startStaion);
        param.put("endStation", endStation);
        param.put("transType", transType);
        param.put("payType", payType);
        param.put("promiseMoney", promiseMoney);
        param.put("realMoney", realMoney);
        param.put("make", mark);
        buyTicketPresenter.sendRequestGetBuyTicketOrderInfo(param);
    }

    public void   cancelOrder(){//ȡ������
        if (orderBean==null){
            return;
        }
        AlertDialogUtil alertDialogUtil = new AlertDialogUtil(this);
        alertDialogUtil.showDialog("δ���֧������ȷ��ȡ��֧����", new AlertDialogCallBack() {
            @Override
            public int getData(int s) {
                return 0;
            }

            @Override
            public void confirm() {

                Map<String, Object> param = new HashMap<>();

                param.put("orderId", orderBean.getData().getOrderId());
                buyTicketPresenter.sendRequestCancelOrder(param);//����ȡ������
            }

            @Override
            public void cancel() {

            }
        });

    }
    //��ȡmd5��������
    private String getMark(double promiseMoney, double realMoney) {
        String mark = "sdhy" + PublicData.userAccount + String.valueOf(realMoney) + String.valueOf(promiseMoney) + "buyTicket";
        mark = MD5.md5(mark);
        return mark;
    }

    //��ȡƱids�ַ���
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTicketCountIds() {
        List<String> ticketCountIdArr = new ArrayList<>();
        String ticketCountIds = "";
        for (int i = 0; i < selectListBean.getTicketCountDTOList().size(); i++) {
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean tickeListBean = selectListBean.getTicketCountDTOList().get(i);
            if (tickeListBean.isSelected() == true) {
                String id = String.valueOf(tickeListBean.getId());
                Date date = new Date(tickeListBean.getRideDate());
                String dateStr = CalendarManager.getDateFormat(date, CalendarManager.FORMATYYYYMMDD);
                String idDateStr = id + ":" + dateStr;
                ticketCountIdArr.add(idDateStr);
                if (ticketCountIds.equals("")) {
                    ticketCountIds = idDateStr;
                } else {
                    ticketCountIds = ticketCountIds + "," + idDateStr;
                }
            }
        }
        // ticketCountIds = String.join(",", ticketCountIdArr);
        return ticketCountIds;
    }

    /******************************************��תactivity*******************************/
    //��һ��Activity
    private void pushPaySuccessActivity() {

        Intent intent = new Intent(TicketOrderPaymentActivity.this, TicketPaySuccessActivity.class);
        intent.putExtra("startAddress", startAddress);
        intent.putExtra("endAddress", endAddress);
        intent.putExtra("lineId", lineDetailBean.getData().getId());
        startActivity(intent);
        showPaySuccessActivity = true;
        finish();
        Log.e("ssssss", "��ת ");

    }


    //����


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ssssss", "onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ssssss", "onResume");
        if (paySuccess == true) {

            pushPaySuccessActivity();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ssssss", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ssssss", "onStop");
    }
//
    @Override
    protected void onDestroy() {//����
        super.onDestroy();

        Log.e("ssssss", "onDestroy");
        if (paySuccess==false){//��������ʱ��δ֧��ȡ������
            cancelOrder();
            if (countDownTimer!=null){
                countDownTimer.cancel();
                countDownTimer = null;
            }

        }
    }

    @Override
    public void LeftClickLister() {

        if (orderBean!=null){
            cancelOrder();
        }else {
             super.LeftClickLister();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK&&orderBean!=null) {//������ؼ�����
            //�˴�д�����̨�Ĵ���
            cancelOrder();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    //
    private void startCountDown(){
        //new CountDownTimer(����ʱ���, ����ʱ�ٶ�) ����Ϊ��λ
         countDownTimer = new CountDownTimer(countDownlength,1000) {
            @Override
            public void onTick(long l) {
                long minute = l/(1000*60);
                long seconds = (l/1000)%60;
                String title = "֧����" +  minute + "��" + seconds + "��)";
                if (paySuccess==true){
                    countDownTimer.cancel();
                    countDownTimer = null;
                   title = "��֧��";
                }
                payBtn.setText(title);
            }

            @Override
            public void onFinish() {
                if (paySuccess==true) return;//����֧���������¼���ִ��
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(TicketOrderPaymentActivity.this);
           alertDialogUtil.showDialogWithOnlyConfirmBtn("������ʧЧ��������ѡ��Ʊ", new AlertDialogCallBack() {
               @Override
               public int getData(int s) {
                   return 0;
               }

               @Override
               public void confirm() {
                  finish();
               }

               @Override
               public void cancel() {

               }
           });
                payBtn.setText("֧��");
            }
        }.start();
    }
}
