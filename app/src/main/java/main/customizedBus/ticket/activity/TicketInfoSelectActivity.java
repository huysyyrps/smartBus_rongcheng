package main.customizedBus.ticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import butterknife.OnCheckedChanged;
import main.customizedBus.home.activity.CustomizedWebViewActivity;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.line.module.CustomizedLinesDetailContract;
import main.customizedBus.line.presenter.CustomizedLinesDetailPresenter;
import main.customizedBus.ticket.bean.BuyTicketCalendarBean;
import main.customizedBus.ticket.bean.BuyTicketPeopleNumBean;

import main.customizedBus.ticket.presenter.CustomizedBuyTicketPresenter;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;
import main.utils.utils.NonNullString;
import main.utils.utils.PublicData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketInfoSelectActivity extends BaseActivity implements CustomizedLinesDetailContract.View {
    private ArrayList<BuyTicketPeopleNumBean> peopleoPtions1Items = new ArrayList<>();//人数数据
    private ArrayList<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> onStationPtions1Items = new ArrayList<>();//上车站点
    private ArrayList<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> offStationPtions1Items = new ArrayList<>();//下车站点
    private BuyTicketCalendarBean ticketCalendarBean;
    private List<BuyTicketCalendarBean.BuyTicketDateBean> ticketDateBeans;
    private CustomizedLineDetailBean lineDetailBean;
    private CustomizedLinesDetailPresenter linesDetailPresenter;
    private CustomizedBuyTicketPresenter buyTicketPresenter;
    private  int schedulIndex = 0;//默认显示第一个班次
    private  int peopleNumIndex = 0;//人数默认数据第一个数据
    private  int onStationIndex = -1;//上车站点选择未初始化
    private  int offStationIndex = -1;//下车站点选择未初始化化
    List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketList;
    private int lineId;
    private OptionsPickerView<BuyTicketPeopleNumBean> selectPeoplePicker;
    private OptionsPickerView<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> selectOnBusStationsPicker;
    private OptionsPickerView<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> selectOffBusStationsPicker;
    private CustomizedLineDetailBean.DataBean.SchedulDTOListBean selectListBean;
    private int ridePeopleNum = 0;//乘坐人数
    private AddressBean startAddress;
    private AddressBean endAddress;

    @BindViews({R.id.checkbox_1,R.id.checkbox_2, R.id.checkbox_3,R.id.checkbox_4,R.id.checkbox_5,R.id.checkbox_6,R.id.checkbox_7,
            R.id.checkbox_8,R.id.checkbox_9,R.id.checkbox_10,R.id.checkbox_11,R.id.checkbox_12,R.id.checkbox_13,R.id.checkbox_14,
            R.id.checkbox_15,R.id.checkbox_16,R.id.checkbox_17,R.id.checkbox_18,R.id.checkbox_19,R.id.checkbox_20,R.id.checkbox_21,
            R.id.checkbox_22,R.id.checkbox_23,R.id.checkbox_24,R.id.checkbox_25,R.id.checkbox_26,R.id.checkbox_27,R.id.checkbox_28,
            R.id.checkbox_29,R.id.checkbox_30,R.id.checkbox_31,R.id.checkbox_32,R.id.checkbox_33,R.id.checkbox_34,R.id.checkbox_35
    })
    public List<CheckBox> dateCheckBoxes;
    @BindView(R.id.id_yyyymm_tv)
    public TextView yyyyMMTV;
    @BindView(R.id.id_shifts_radiogroup)
    RadioGroup shiftsRadioGroup;
    @BindView(R.id.id_onbus_station_tv)
    public TextView onbusStationTV;
    @BindView(R.id.id_onbus_time_tv)
    public TextView onbusTimeTV;
    @BindView(R.id.id_offbus_station_tv)
    public TextView offbusStationTV;
    @BindView(R.id.id_offbus_time_tv)
    public TextView offbusTimeTV;
    @BindView(R.id.id_people_num_tv)
    public TextView peopleNumStationTV;
    @BindView(R.id.id_line_name_tv)
    public TextView lineNameTV;
    @BindView(R.id.id_ticket_price_tv)
    public TextView ticketPriceTV;
    @BindView(R.id.id_tick_num_tv)
    public TextView ticketNumTV;
    @BindView(R.id.id_tick_total_price_tv)
    public TextView ticketTotalPriceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递数据
        Intent intent = getIntent();
        lineId = intent.getExtras().getInt("lineId");
        schedulIndex  = intent.getExtras().getInt("schedulIndex");
        //初始化上车地点下车地点 传递到下个Activity 为购票后显示返程线路准备数据
         startAddress = new AddressBean();
         endAddress = new AddressBean();

        //初始化人数、上车站点、下车站点选择器
        initPeopleNumOptionPicker();
        initOnBusStationPicker();
        initOffBusStationPicker();
        reloadPeopleViewData();
        //实例化网络请求类
        linesDetailPresenter= new  CustomizedLinesDetailPresenter(this,this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        linesDetailPresenter.sendRequestGetcustomizedLineDetail(lineId);
    }

    private void initPeopleNumOptionPicker() {//人数选择器
        for (int i = 1; i < 6 ; i++) {
            String peopleStr = getResources().getString(R.string.customized_people);
            BuyTicketPeopleNumBean peopleNumBean = new BuyTicketPeopleNumBean(i,String.valueOf(i) + peopleStr);
            peopleoPtions1Items.add(peopleNumBean);
        }
        String title = getResources().getString(R.string.title_tips_people_num);
         selectPeoplePicker = initOptionPicker(title,new OnOptionsSelectListener() {
             @Override
             public void onOptionsSelect(int options1, int options2, int options3, View v) {
                  peopleNumIndex = options1;
                 reloadPeopleViewData();
             }
         });
        selectPeoplePicker.setPicker(peopleoPtions1Items);//一级选择器

    }


    private void initOnBusStationPicker() {//上车站点择器
//        for (int i = 1; i < 6 ; i++) {
//            String peopleStr = getResources().getString(R.string.customized_people);
//            BuyTicketPeopleNumBean peopleNumBean = new BuyTicketPeopleNumBean(i,String.valueOf(i) + peopleStr);
//            options1Items.add(peopleNumBean);
//        }
        String title = "请选择上车站点";
        selectOnBusStationsPicker = initOptionPicker(title,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onStationIndex = options1;
                reloadOnBusSationViewData();
            }
        });
        selectOnBusStationsPicker.setPicker(onStationPtions1Items);//一级选择器
    }

    private void initOffBusStationPicker() {//下车站点
//        for (int i = 1; i < 6 ; i++) {
//            String peopleStr = getResources().getString(R.string.customized_people);
//            BuyTicketPeopleNumBean peopleNumBean = new BuyTicketPeopleNumBean(i,String.valueOf(i) + peopleStr);
//            options1Items.add(peopleNumBean);
//        }
        String title ="请选择下车站点";
        selectOffBusStationsPicker = initOptionPicker(title,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                offStationIndex = options1;
                reloadOffBusSationViewData();
            }
        });
        selectOffBusStationsPicker.setPicker(offStationPtions1Items);//一级选择器

    }

    private <T> OptionsPickerView<T> initOptionPicker(String title, OnOptionsSelectListener listener){
        String confirm = getResources().getString(R.string.title_tips_confim);
        String cancel = getResources().getString(R.string.title_tips_cancel);
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        OptionsPickerView<T>optionsPickerBuilder = new OptionsPickerBuilder(TicketInfoSelectActivity.this, listener)
                .setSubmitText(confirm)//确定按钮文字
                .setCancelText(cancel)//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleBgColor(0xffffffff)//标题背景颜色 Night mode
                .setTitleColor(0x00000000)//标题文字颜色
                .setSubmitColor(0xFF47aaed)//确定按钮文字颜色
                .setCancelColor(0x80808080)//取消按钮文字颜色
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label
                .setDividerColor(0x00000000)//设置分割线的颜色
                .setTextColorCenter(0xff494949)//设置选中项的颜色
                .setTextColorOut(0xffb5b5b5)//设置没有被选中项的颜色
                .setLineSpacingMultiplier(3f)//设置两横线之间的间隔倍数
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)//设置要将pickerview显示到的容器id 必须是viewgrou
                .build();
        return optionsPickerBuilder;

    }
    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        ticketCalendarBean = new BuyTicketCalendarBean();
        getCurMonthAllDates();


    }

    @Override
    public void initData() {
        super.initData();


    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ticket_info_select;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
    /*****************************************刷新数据*******************************/
    public void reloaddDateCheckBoxesData(){
        for (int i = 0; i < dateCheckBoxes.size() ; i++) {
            CheckBox dateCheckBox = dateCheckBoxes.get(i);
            BuyTicketCalendarBean.BuyTicketDateBean ticketDateBean = ticketDateBeans.get(i);
            String showStr = ticketDateBean.getShowStr();
            /**
             *
             */
            Spannable sp = new SpannableString(showStr);
            if (showStr.length()>1){
                sp.setSpan(new AbsoluteSizeSpan(13, true), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            if (showStr.length()>2){
                sp.setSpan(new AbsoluteSizeSpan(10, true), 2, showStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }


            dateCheckBox.setText(sp);
            dateCheckBox.setChecked(ticketDateBean.isSelected());
            dateCheckBox.setEnabled(ticketDateBean.isCanBuy());
//            dateCheckBox.setClickable();  btn.setEnabled(true);

        }
        yyyyMMTV.setText(ticketCalendarBean.getYYYYMMFormatDate());
    }




    /*****************************************获取日期数据********************************/
    public void  getCurMonthAllDates(){
      ticketDateBeans =  ticketCalendarBean.getMonthWeekDaysDataOfToday(ticketList);
        reloaddDateCheckBoxesData();
    }

    public void  getNextMonthAllDates(){
        ticketDateBeans =  ticketCalendarBean.getNextMonthWeekDaysDataOfToday(ticketList);
        reloaddDateCheckBoxesData();
    }

    public void  getLastMonthAllDates(){
        ticketDateBeans =  ticketCalendarBean.getLastMonthWeekDaysDataOfToday(ticketList);
        reloaddDateCheckBoxesData();
    }

    //班次显示
    public void initShiftsRadioGroupItem(){
        List<String> schedulArr = lineDetailBean.getData().getSchedulArr();
        shiftsRadioGroup.removeAllViews();
        shiftsRadioGroup.clearCheck();
        for (int i=0;i< schedulArr.size();i++){
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.rationbutton_shifts,shiftsRadioGroup,false);
            radioButton.setId(i);
            radioButton.setText(schedulArr.get(i));
            radioButton.setOnClickListener(this::onViewClickSwitchBusSchedulListener);
            shiftsRadioGroup.addView(radioButton);
            if (i==schedulIndex){
                radioButton.setChecked(true);
            }
        }
    }
    /***********************************************点击*********************************************************/

    @OnClick({R.id.id_confirm_btn,R.id.id_lastmonth_click_imgv,R.id.id_nexttmonth_click_imgv,R.id.id_select_people_num_view,R.id.id_onbus_station_view,R.id.id_offbus_station_view})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.id_confirm_btn:
                pushTicketOrderPaymentActivity();

                break;
            case R.id.id_nexttmonth_click_imgv://下个月
                getNextMonthAllDates();
                break;
            case R.id.id_lastmonth_click_imgv://上个月
                getLastMonthAllDates();
                break;
            case R.id.id_select_people_num_view://
                if (selectPeoplePicker != null){
                    selectPeoplePicker.show();
                }

                break;
            case R.id.id_onbus_station_view://
                if (selectOnBusStationsPicker != null){
                    selectOnBusStationsPicker.show();
                }

                break;
            case R.id.id_offbus_station_view://
                if (selectOffBusStationsPicker != null){
                    selectOffBusStationsPicker.show();
                }

                break;
            default:
                break;
        }
    }
    public void onViewClickSwitchBusSchedulListener(View view){
        int index = view.getId();
        schedulIndex = index;
        switchBusSchedul(schedulIndex);
        Log.i("sss", "onViewClickSwitchBusSchedulListener: ");

    }
    @OnCheckedChanged({R.id.checkbox_1,R.id.checkbox_2, R.id.checkbox_3,R.id.checkbox_4,R.id.checkbox_5,R.id.checkbox_6,R.id.checkbox_7,
            R.id.checkbox_8,R.id.checkbox_9,R.id.checkbox_10,R.id.checkbox_11,R.id.checkbox_12,R.id.checkbox_13,R.id.checkbox_14,
            R.id.checkbox_15,R.id.checkbox_16,R.id.checkbox_17,R.id.checkbox_18,R.id.checkbox_19,R.id.checkbox_20,R.id.checkbox_21,
            R.id.checkbox_22,R.id.checkbox_23,R.id.checkbox_24,R.id.checkbox_25,R.id.checkbox_26,R.id.checkbox_27,R.id.checkbox_28,
            R.id.checkbox_29,R.id.checkbox_30,R.id.checkbox_31,R.id.checkbox_32,R.id.checkbox_33,R.id.checkbox_34,R.id.checkbox_35
    })
    public void OnCheckedChangeListener(CheckBox view,boolean isl){
        int[] ids = new int[]{
                R.id.checkbox_1,R.id.checkbox_2, R.id.checkbox_3,R.id.checkbox_4,R.id.checkbox_5,R.id.checkbox_6,R.id.checkbox_7,
                R.id.checkbox_8,R.id.checkbox_9,R.id.checkbox_10,R.id.checkbox_11,R.id.checkbox_12,R.id.checkbox_13,R.id.checkbox_14,
                R.id.checkbox_15,R.id.checkbox_16,R.id.checkbox_17,R.id.checkbox_18,R.id.checkbox_19,R.id.checkbox_20,R.id.checkbox_21,
                R.id.checkbox_22,R.id.checkbox_23,R.id.checkbox_24,R.id.checkbox_25,R.id.checkbox_26,R.id.checkbox_27,R.id.checkbox_28,
                R.id.checkbox_29,R.id.checkbox_30,R.id.checkbox_31,R.id.checkbox_32,R.id.checkbox_33,R.id.checkbox_34,R.id.checkbox_35
        };
        for (int i = 0; i < ids.length ; i++) {
            int id = ids[i];
            if (id == view.getId()){
                ticketDateBeans.get(i).setSelected(isl);
                break;
            }

        }
        if (lineDetailBean!=null){
            reloadBottomViewData();
        }

    }
    /***********************************************数据刷新*********************************************************/
    private void switchBusSchedul(int i){
        List <CustomizedLineDetailBean.DataBean.SchedulDTOListBean> listBeans = lineDetailBean.getData().getSchedulDTOList();
//        List <CustomizedLineDetailBean.DataBean.tn> listBeans = lineBean.getData().getSchedulDTOList();

        if (listBeans!=null&&listBeans.size()>i){
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean listBean = listBeans.get(i);
            selectListBean = listBean;
            List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList = listBean.getSchedulStationDTOList();
            ticketList = listBean.getTicketCountDTOList();
             reloadOnOffStationPtions1ItemsData(dataList);
            getCurMonthAllDates();

        }
    }

//刷新上下站点选择器数据
    private  void reloadOnOffStationPtions1ItemsData(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList){
        onStationPtions1Items.clear();
        offStationPtions1Items.clear();
        for (int i = 0; i < dataList.size(); i++) {
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean stationDTOListBean = dataList.get(i);

            if (stationDTOListBean.getStation().getFlag()==0||stationDTOListBean.getStation().getFlag()==2){
                onStationPtions1Items.add(stationDTOListBean);
            }
            if (stationDTOListBean.getStation().getFlag()==1||stationDTOListBean.getStation().getFlag()==2){
                offStationPtions1Items.add(stationDTOListBean);
            }
        }

        selectOnBusStationsPicker.setPicker(onStationPtions1Items);//一级选择器
        selectOffBusStationsPicker.setPicker(offStationPtions1Items);//一级选择器

        if (onStationIndex==-1){//-1代表未初始化
            onStationIndex = 0;
            selectOnBusStationsPicker.setSelectOptions(onStationIndex);
        }
        if (offStationIndex==-1){//-1代表未初始化
            offStationIndex = offStationPtions1Items.size()-1;
            selectOnBusStationsPicker.setSelectOptions(offStationIndex);

        }
        reloadOnBusSationViewData();
        reloadOffBusSationViewData();

    }
   //刷新上车地点视图数据
  private   void reloadOnBusSationViewData(){
        if (onStationPtions1Items!=null&&onStationPtions1Items.size()>0&&onStationIndex<onStationPtions1Items.size()){
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean stationDTOListBean = onStationPtions1Items.get(onStationIndex);
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean.StationBean stationBean = stationDTOListBean.getStation();
            onbusStationTV.setText(stationBean.getName());
            onbusTimeTV.setText(stationDTOListBean.getTime());
            startAddress.setName(stationBean.getName());
            startAddress.setLatitude(Double.valueOf(stationBean.getLat().toString()));
            startAddress.setLongitude(Double.valueOf(stationBean.getLon().toString()));
        }

  }
  //刷新下车地点视图数据
    private   void reloadOffBusSationViewData(){
        if (offStationPtions1Items!=null&&offStationPtions1Items.size()>0&&offStationIndex <offStationPtions1Items.size()){
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean stationDTOListBean = offStationPtions1Items.get(offStationIndex);
            CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean.StationBean stationBean = stationDTOListBean.getStation();
            String sationStr = stationBean.getName();
            String timeStr = stationDTOListBean.getTime();
            offbusStationTV.setText(sationStr);
            offbusTimeTV.setText(timeStr);
            endAddress.setName(stationBean.getName());
            endAddress.setLatitude(Double.valueOf(stationBean.getLat().toString()));
            endAddress.setLongitude(Double.valueOf(stationBean.getLon().toString()));

        }
    }

    private void reloadPeopleViewData() {
        if (peopleNumIndex<peopleoPtions1Items.size()){
            BuyTicketPeopleNumBean peopleNumBean = peopleoPtions1Items.get(peopleNumIndex);
            String str = peopleNumBean.getShowStr();
            ridePeopleNum = peopleNumBean.getNum();
            peopleNumStationTV.setText(str);
        }
        reloadBottomViewData();
    }

    private void reloadBottomViewData(){
        int peopleNum = 0;
        if (peopleoPtions1Items!=null){
            peopleNum =    peopleoPtions1Items.get(peopleNumIndex).getNum();
        }

        int dayNum = 0;
        double price = 0;
        if (lineDetailBean!=null){
           price = lineDetailBean.getData().getPrice();
        }

        if (selectListBean!=null){
            for (int i = 0; i < selectListBean.getTicketCountDTOList().size(); i++) {

                if (selectListBean.getTicketCountDTOList().get(i).isSelected()== true){

                    dayNum++;
                }
            }
        }

        int ticketNum = peopleNum*dayNum;
        double totalMoney = ticketNum*price;
        if (ticketNumTV != null){
            ticketNumTV.setText("已选择"+ticketNum+"张票");
        }
        if (ticketTotalPriceTV != null){
            ticketTotalPriceTV.setText(PublicData.getDoubleStr(totalMoney));
        }
       ridePeopleNum = peopleNum;

    }


/************************************************网络请求*********************************************************/
public void getOrderIdFromNet(){
//    linesDetailPresenter.sendRequestGetBuyTicketOrderInfo();
}
    /***************************************网络请求反应************************************************************/
    @Override
    public void requestOnSuccees(CustomizedLineDetailBean lineBean) {
        lineDetailBean = lineBean;
        if (lineBean != null)
        {
            lineNameTV.setText(NonNullString.getString(lineBean.getData().getName()));
            ticketPriceTV.setText(lineBean.getData().getPriceStr()+"元");
            initShiftsRadioGroupItem();
            if (lineBean.getCode()!=0){
                Toast.makeText(this,lineBean.getMsg(),Toast.LENGTH_LONG).show();
            }
            switchBusSchedul(schedulIndex);
            reloadBottomViewData();

        }

    }



    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        Toast.makeText(this, PublicData.netWorkErrorMsg,Toast.LENGTH_LONG).show();
    }

    /******************************************跳转activity*******************************/
    //下一个Activity
    private void pushTicketOrderPaymentActivity() {
        Intent intent = new Intent(this,TicketOrderPaymentActivity.class);
        if (lineDetailBean==null||selectListBean==null){
            return;
        }
        int dayNum = 0;
        if (selectListBean != null) {

            List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketBeans = new ArrayList<>();
            for (int i = 0; i < selectListBean.getTicketCountDTOList().size(); i++) {
                CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean ticketBean = selectListBean.getTicketCountDTOList().get(i);
                if (selectListBean.getTicketCountDTOList().get(i).isSelected() == true) {
                    if (ridePeopleNum>ticketBean.getStandbyCount()){
                        Date tickDate = new Date(ticketBean.getRideDate());
                        String ticktYYYYMMDDStr = CalendarManager.getDateFormat(tickDate,CalendarManager.FORMATYYYYMMDD);
                        String noticeStr = ticktYYYYMMDDStr + " 余票不足";
                        showToast(noticeStr);
                        return;
                    }
                    dayNum++;
                }
            }
        }
        if (dayNum<=0){
            showToast("请选择车票");
            return;
        }
        AlertDialogUtil alertDialogUtil = new AlertDialogUtil(this);
        alertDialogUtil.showDialog("是否确定购买"+selectListBean.getSchedulTime()+"班次车票", new AlertDialogCallBack() {
            @Override
            public int getData(int s) {
                return 0;
            }

            @Override
            public void confirm() {
                intent.putExtra("lineDetailData",lineDetailBean);
                intent.putExtra("selectListBean",selectListBean);
                intent.putExtra("ridePeopleNum",ridePeopleNum);
                intent.putExtra("startAddress",startAddress);
                intent.putExtra("endAddress",endAddress);
                startActivity(intent);
            }

            @Override
            public void cancel() {

            }
        });



    }
    //返回
    @Override
    public void rightClickLister() {
        super.rightClickLister();
        Intent intent = new Intent(this, CustomizedWebViewActivity.class);
        intent.putExtra("introduceType",3);
        startActivity(intent);
    }
}
