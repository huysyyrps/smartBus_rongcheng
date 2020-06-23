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
    private int ridePeopleNum = 0;//乘坐人数
    private int payType = 1;//支付方式去0 微信 1 支付宝
    private CustomizedBuyTicketPresenter buyTicketPresenter;
    private double totalMoney;
    private AddressBean startAddress;
    private AddressBean endAddress;
    private boolean paySuccess = false;//是否支付成功
    private boolean showPaySuccessActivity = false;//支付成功后是否调用跳转PaySuccessActivity方法 注意方法调用PaySuccessActivity不一定显示


    private long countDownlength = 0;//倒计时时间毫秒
    private  CountDownTimer countDownTimer;
    private BuyTicketOrderBean orderBean = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //实例化网络请求类
        buyTicketPresenter = new CustomizedBuyTicketPresenter(this, this);
        //获取传递数据
        Intent intent = getIntent();
        lineDetailBean = (CustomizedLineDetailBean) intent.getExtras().getSerializable("lineDetailData");
        selectListBean = (CustomizedLineDetailBean.DataBean.SchedulDTOListBean) intent.getExtras().getSerializable("selectListBean");
        ridePeopleNum = intent.getExtras().getInt("ridePeopleNum");
        startAddress = (AddressBean) getIntent().getExtras().getSerializable("startAddress");
        endAddress = (AddressBean) getIntent().getExtras().getSerializable("endAddress");
        //刷新界面数据
        reloadViewData();
        //支付类型选择
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

    /**************************点击***************************/
    @RequiresApi(api = Build.VERSION_CODES.O)
//注解的作用仅仅是让编译通过，而并没有避免低版本的系统运行高版本的api的问题，在使用时我们需要自己判断版本号来使用不同的api
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
            String schedulStr = "班次" + selectListBean.getSchedulTime() + "公交";
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
//            ticketNumTV.setText("已选择"+ticketNum+"张票");
//        }
//        if (ticketTotalPriceTV != null){
//            ticketTotalPriceTV.setText(String.valueOf(totalMoney));
//        }
        if (lineDetailBean != null) {
            onbusStationTv.setText(lineDetailBean.getData().getStartStation());
            offbusStationTv.setText(lineDetailBean.getData().getEndStation());
        }

        wxpayRadiobutton.setChecked(true);
        ticketNumTv.setText("车票：" + String.valueOf(ticketNum) + "张");
        totalPriceTv.setText("￥" + PublicData.getDoubleStr(totalMoney) + "元");
        totalPriceBottomTv.setText(PublicData.getDoubleStr(totalMoney));


    }

//    @RequiresApi(api = Build.VERSION_CODES.O)//注解的作用仅仅是让编译通过，而并没有避免低版本的系统运行高版本的api的问题，在使用时我们需要自己判断版本号来使用不同的api
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
            ticketNumTV.setText(String.valueOf(ridePeopleNum) + "张");
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
//支付订单
    public void payOrder(){

        if (payType == 0) {

            AliPayManager aliPayManager = AliPayManager.getInstance(this);
            aliPayManager.ZFBpay(orderBean.getData().getOrderId(), totalMoney, ApiAddress.alipayNoticeUrl, new AliPayManager.OnPayResultListener() {
                @Override
                public void resultSuccess() {
                    paySuccess = true;
                    Log.e("ssssss", "支付宝 ");

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

                    Log.e("ssssss", "微信 ");
                }

                @Override
                public void resultFail() {
                    //  pushPaySuccessActivity();
                    // Log.e("ssssss", "微信失败 ");
                }
            });
        }

    }
    /*************************************网络请求*******************************/
    @Override
    public void requestOnSuccees(BuyTicketOrderBean orderBean) {
        this.orderBean = orderBean;
        if (orderBean != null && orderBean.getCode() == 1) {
            countDownlength = orderBean.getData().getPayTicketTime() * 60 * 1000;
            //开始倒计时
            startCountDown();
            payOrder();
        } else {
            String msg = "订单请求失败";
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
//取消订单
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

    //获取订单号
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getOrderInfo() {
        if (orderBean != null && orderBean.getCode() == 1) {//若以获取订单不再请求新订单
            payOrder();
            return;
        }

        int[] payTypes = new int[]{0, 1, 2};//支付方式，0:支付宝 1：微信 2：农信
        String passenger = PublicData.userAccount;//线路Id
        int lineId = lineDetailBean.getData().getId();//
        int schedulId = selectListBean.getId();//班次Id
        int num = ridePeopleNum;//购票人数
        String startStaion = lineDetailBean.getData().getStartStation();//上车站点
        String endStation = lineDetailBean.getData().getEndStation();//下车站点
        int transType = 0;//交易类型
        double promiseMoney = totalMoney;//应支付金额，优惠之前的金额
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

    public void   cancelOrder(){//取消订单
        if (orderBean==null){
            return;
        }
        AlertDialogUtil alertDialogUtil = new AlertDialogUtil(this);
        alertDialogUtil.showDialog("未完成支付，您确定取消支付吗？", new AlertDialogCallBack() {
            @Override
            public int getData(int s) {
                return 0;
            }

            @Override
            public void confirm() {

                Map<String, Object> param = new HashMap<>();

                param.put("orderId", orderBean.getData().getOrderId());
                buyTicketPresenter.sendRequestCancelOrder(param);//发送取消请求
            }

            @Override
            public void cancel() {

            }
        });

    }
    //获取md5加密数据
    private String getMark(double promiseMoney, double realMoney) {
        String mark = "sdhy" + PublicData.userAccount + String.valueOf(realMoney) + String.valueOf(promiseMoney) + "buyTicket";
        mark = MD5.md5(mark);
        return mark;
    }

    //获取票ids字符串
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

    /******************************************跳转activity*******************************/
    //下一个Activity
    private void pushPaySuccessActivity() {

        Intent intent = new Intent(TicketOrderPaymentActivity.this, TicketPaySuccessActivity.class);
        intent.putExtra("startAddress", startAddress);
        intent.putExtra("endAddress", endAddress);
        intent.putExtra("lineId", lineDetailBean.getData().getId());
        startActivity(intent);
        showPaySuccessActivity = true;
        finish();
        Log.e("ssssss", "跳转 ");

    }


    //返回


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
    protected void onDestroy() {//销毁
        super.onDestroy();

        Log.e("ssssss", "onDestroy");
        if (paySuccess==false){//界面销毁时若未支付取消订单
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
        if (keyCode == KeyEvent.KEYCODE_BACK&&orderBean!=null) {//如果返回键按下
            //此处写退向后台的处理
            cancelOrder();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    //
    private void startCountDown(){
        //new CountDownTimer(倒计时多久, 倒计时速度) 毫秒为单位
         countDownTimer = new CountDownTimer(countDownlength,1000) {
            @Override
            public void onTick(long l) {
                long minute = l/(1000*60);
                long seconds = (l/1000)%60;
                String title = "支付（" +  minute + "分" + seconds + "秒)";
                if (paySuccess==true){
                    countDownTimer.cancel();
                    countDownTimer = null;
                   title = "已支付";
                }
                payBtn.setText(title);
            }

            @Override
            public void onFinish() {
                if (paySuccess==true) return;//若已支付不再往下继续执行
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(TicketOrderPaymentActivity.this);
           alertDialogUtil.showDialogWithOnlyConfirmBtn("订单已失效，请重新选择购票", new AlertDialogCallBack() {
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
                payBtn.setText("支付");
            }
        }.start();
    }
}
