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
    private ArrayList<BuyTicketPeopleNumBean> peopleoPtions1Items = new ArrayList<>();//��������
    private ArrayList<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> onStationPtions1Items = new ArrayList<>();//�ϳ�վ��
    private ArrayList<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> offStationPtions1Items = new ArrayList<>();//�³�վ��
    private BuyTicketCalendarBean ticketCalendarBean;
    private List<BuyTicketCalendarBean.BuyTicketDateBean> ticketDateBeans;
    private CustomizedLineDetailBean lineDetailBean;
    private CustomizedLinesDetailPresenter linesDetailPresenter;
    private CustomizedBuyTicketPresenter buyTicketPresenter;
    private  int schedulIndex = 0;//Ĭ����ʾ��һ�����
    private  int peopleNumIndex = 0;//����Ĭ�����ݵ�һ������
    private  int onStationIndex = -1;//�ϳ�վ��ѡ��δ��ʼ��
    private  int offStationIndex = -1;//�³�վ��ѡ��δ��ʼ����
    List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketList;
    private int lineId;
    private OptionsPickerView<BuyTicketPeopleNumBean> selectPeoplePicker;
    private OptionsPickerView<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> selectOnBusStationsPicker;
    private OptionsPickerView<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> selectOffBusStationsPicker;
    private CustomizedLineDetailBean.DataBean.SchedulDTOListBean selectListBean;
    private int ridePeopleNum = 0;//��������
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
        //��ȡ��������
        Intent intent = getIntent();
        lineId = intent.getExtras().getInt("lineId");
        schedulIndex  = intent.getExtras().getInt("schedulIndex");
        //��ʼ���ϳ��ص��³��ص� ���ݵ��¸�Activity Ϊ��Ʊ����ʾ������·׼������
         startAddress = new AddressBean();
         endAddress = new AddressBean();

        //��ʼ���������ϳ�վ�㡢�³�վ��ѡ����
        initPeopleNumOptionPicker();
        initOnBusStationPicker();
        initOffBusStationPicker();
        reloadPeopleViewData();
        //ʵ��������������
        linesDetailPresenter= new  CustomizedLinesDetailPresenter(this,this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        linesDetailPresenter.sendRequestGetcustomizedLineDetail(lineId);
    }

    private void initPeopleNumOptionPicker() {//����ѡ����
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
        selectPeoplePicker.setPicker(peopleoPtions1Items);//һ��ѡ����

    }


    private void initOnBusStationPicker() {//�ϳ�վ������
//        for (int i = 1; i < 6 ; i++) {
//            String peopleStr = getResources().getString(R.string.customized_people);
//            BuyTicketPeopleNumBean peopleNumBean = new BuyTicketPeopleNumBean(i,String.valueOf(i) + peopleStr);
//            options1Items.add(peopleNumBean);
//        }
        String title = "��ѡ���ϳ�վ��";
        selectOnBusStationsPicker = initOptionPicker(title,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onStationIndex = options1;
                reloadOnBusSationViewData();
            }
        });
        selectOnBusStationsPicker.setPicker(onStationPtions1Items);//һ��ѡ����
    }

    private void initOffBusStationPicker() {//�³�վ��
//        for (int i = 1; i < 6 ; i++) {
//            String peopleStr = getResources().getString(R.string.customized_people);
//            BuyTicketPeopleNumBean peopleNumBean = new BuyTicketPeopleNumBean(i,String.valueOf(i) + peopleStr);
//            options1Items.add(peopleNumBean);
//        }
        String title ="��ѡ���³�վ��";
        selectOffBusStationsPicker = initOptionPicker(title,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                offStationIndex = options1;
                reloadOffBusSationViewData();
            }
        });
        selectOffBusStationsPicker.setPicker(offStationPtions1Items);//һ��ѡ����

    }

    private <T> OptionsPickerView<T> initOptionPicker(String title, OnOptionsSelectListener listener){
        String confirm = getResources().getString(R.string.title_tips_confim);
        String cancel = getResources().getString(R.string.title_tips_cancel);
        /**
         * ע�� ���������������������(ʡ������)������� JsonDataActivity �������д����
         */

        OptionsPickerView<T>optionsPickerBuilder = new OptionsPickerBuilder(TicketInfoSelectActivity.this, listener)
                .setSubmitText(confirm)//ȷ����ť����
                .setCancelText(cancel)//ȡ����ť����
                .setTitleText(title)//����
                .setSubCalSize(18)//ȷ����ȡ�����ִ�С
                .setTitleSize(20)//�������ִ�С
                .setTitleBgColor(0xffffffff)//���ⱳ����ɫ Night mode
                .setTitleColor(0x00000000)//����������ɫ
                .setSubmitColor(0xFF47aaed)//ȷ����ť������ɫ
                .setCancelColor(0x80808080)//ȡ����ť������ɫ
                .isCenterLabel(false)//�Ƿ�ֻ��ʾ�м�ѡ�����label���֣�false��ÿ��itemȫ��������label
                .setDividerColor(0x00000000)//���÷ָ��ߵ���ɫ
                .setTextColorCenter(0xff494949)//����ѡ�������ɫ
                .setTextColorOut(0xffb5b5b5)//����û�б�ѡ�������ɫ
                .setLineSpacingMultiplier(3f)//����������֮��ļ������
//                .setBackgroundId(0x00FFFFFF) //�����ⲿ������ɫ
                .setDecorView(null)//����Ҫ��pickerview��ʾ��������id ������viewgrou
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
    /*****************************************ˢ������*******************************/
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




    /*****************************************��ȡ��������********************************/
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

    //�����ʾ
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
    /***********************************************���*********************************************************/

    @OnClick({R.id.id_confirm_btn,R.id.id_lastmonth_click_imgv,R.id.id_nexttmonth_click_imgv,R.id.id_select_people_num_view,R.id.id_onbus_station_view,R.id.id_offbus_station_view})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.id_confirm_btn:
                pushTicketOrderPaymentActivity();

                break;
            case R.id.id_nexttmonth_click_imgv://�¸���
                getNextMonthAllDates();
                break;
            case R.id.id_lastmonth_click_imgv://�ϸ���
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
    /***********************************************����ˢ��*********************************************************/
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

//ˢ������վ��ѡ��������
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

        selectOnBusStationsPicker.setPicker(onStationPtions1Items);//һ��ѡ����
        selectOffBusStationsPicker.setPicker(offStationPtions1Items);//һ��ѡ����

        if (onStationIndex==-1){//-1����δ��ʼ��
            onStationIndex = 0;
            selectOnBusStationsPicker.setSelectOptions(onStationIndex);
        }
        if (offStationIndex==-1){//-1����δ��ʼ��
            offStationIndex = offStationPtions1Items.size()-1;
            selectOnBusStationsPicker.setSelectOptions(offStationIndex);

        }
        reloadOnBusSationViewData();
        reloadOffBusSationViewData();

    }
   //ˢ���ϳ��ص���ͼ����
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
  //ˢ���³��ص���ͼ����
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
            ticketNumTV.setText("��ѡ��"+ticketNum+"��Ʊ");
        }
        if (ticketTotalPriceTV != null){
            ticketTotalPriceTV.setText(PublicData.getDoubleStr(totalMoney));
        }
       ridePeopleNum = peopleNum;

    }


/************************************************��������*********************************************************/
public void getOrderIdFromNet(){
//    linesDetailPresenter.sendRequestGetBuyTicketOrderInfo();
}
    /***************************************��������Ӧ************************************************************/
    @Override
    public void requestOnSuccees(CustomizedLineDetailBean lineBean) {
        lineDetailBean = lineBean;
        if (lineBean != null)
        {
            lineNameTV.setText(NonNullString.getString(lineBean.getData().getName()));
            ticketPriceTV.setText(lineBean.getData().getPriceStr()+"Ԫ");
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

    /******************************************��תactivity*******************************/
    //��һ��Activity
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
                        String noticeStr = ticktYYYYMMDDStr + " ��Ʊ����";
                        showToast(noticeStr);
                        return;
                    }
                    dayNum++;
                }
            }
        }
        if (dayNum<=0){
            showToast("��ѡ��Ʊ");
            return;
        }
        AlertDialogUtil alertDialogUtil = new AlertDialogUtil(this);
        alertDialogUtil.showDialog("�Ƿ�ȷ������"+selectListBean.getSchedulTime()+"��γ�Ʊ", new AlertDialogCallBack() {
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
    //����
    @Override
    public void rightClickLister() {
        super.rightClickLister();
        Intent intent = new Intent(this, CustomizedWebViewActivity.class);
        intent.putExtra("introduceType",3);
        startActivity(intent);
    }
}
