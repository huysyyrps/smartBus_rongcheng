package main.customizedBus.initiateCustomization.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import main.customizedBus.home.activity.CustomizedWebViewActivity;
import main.customizedBus.home.activity.SearchAddressActivity;
import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.adapter.InitiateCustomizationActivityAdapter;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.customizedBus.initiateCustomization.module.CustomizedContract;
import main.customizedBus.initiateCustomization.presenter.CustomizedPresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.NonNullString;
import main.utils.utils.PublicData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InitiateCustomizationActivity extends BaseActivity implements CustomizedContract.View {

    private RecyclerView recyclerView;
    private InitiateCustomizationActivityAdapter adapter;
    private TimePickerView pvTime1;
    private CustomizedPresenter customizedPresenter;
    private final int SetOutAddressCode = 10001;
    private final int EndAddressCode = 10002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customizedPresenter = new CustomizedPresenter(this,this);
        initView();
        initTimePickerView();
        //��ȡ�ѷ���Ķ�������
        getCustomizedDemandList();//
    }

    public void initView() {
        recyclerView = findViewById(R.id.id_recyclerView);
        //���ò��ֹ�����
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //����adapter
        adapter = new InitiateCustomizationActivityAdapter(this,null);
        recyclerView.setAdapter(adapter);

        //�������
        adapter.setOnRecyclerViewClickListener(new InitiateCustomizationActivityAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onInitiateCustomizationButtonClickListener(Map<String,Object> data) {//������
                if (PublicData.userAccount.toString().equals("".toString())){
                    Toast.makeText(InitiateCustomizationActivity.this,"δ��ȡ�û���ʶ",Toast.LENGTH_LONG).show();
                    return;
                }
                customizedPresenter.sendRequestBusCustomizedSave(data);
            }

            @Override
            public void onItemViewInCustomizedHolderClickListener(View itemView) {
                Intent intent =  new Intent(InitiateCustomizationActivity.this, SearchAddressActivity.class);
                switch (itemView.getId()){
                    case R.id.id_setout_textview://����ϳ��ص�
                        startActivityForResult(intent,SetOutAddressCode);
                    break;
                     case R.id.id_end_textview://����³��ص�
                         startActivityForResult(intent,EndAddressCode);
                    break;
                     case R.id.id_setout_time_textview://����˳�ʱ��
                         pvTime1.show();
                    break;
                    default:
                    break;
                }
            }

            @Override
            public void onItemViewDingZhiGuiZeClickListener(View view) {
                Intent intent = new Intent(InitiateCustomizationActivity.this, CustomizedWebViewActivity.class);
                intent.putExtra("introduceType",2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_initiate_customization;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    //ʱ��ѡ����
    private void initTimePickerView(){

        //����ʱ�䷶Χ(��������÷�Χ����ʹ��Ĭ��ʱ��1900-2100�꣬�˶δ����ע��)
        //��ΪϵͳCalendar���·��Ǵ�0-11��,��������ǵ���Calendar��set����������ʱ��,�·ݵķ�ΧҲҪ�Ǵ�0-11
        Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//ϵͳ��ǰʱ��
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);


        pvTime1 = new TimePickerBuilder(InitiateCustomizationActivity.this, new  OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//ѡ���¼��ص�
             //   ʱ��ѡ����
        HashMap dataMap = new HashMap();
        dataMap.put("text", getTime(date));
        adapter.getCustomizedHolder().setSetoutTimeMap(dataMap);
            }
        }).setType(new boolean[]{false, false, false, true, true, false}) //������ʱ���� ����ʾ��񣬲�������Ĭ��ȫ����ʾ
                .setLabel("", "", "", "", "", "")//Ĭ������Ϊ������ʱ����
                .setSubmitText("ȷ��")//ȷ����ť����
                .setCancelText("ȡ��")//ȡ����ť����
                .setTitleText("ѡ���ϳ�ʱ��")//����
                .setSubCalSize(18)//ȷ����ȡ�����ִ�С
                .setTitleSize(20)//�������ִ�С
                .setTitleBgColor(0xffffffff)//���ⱳ����ɫ Night mode
                .setTitleColor(0x00000000)//����������ɫ
                .setSubmitColor(0xFF1E90FF)//ȷ����ť������ɫ
                .setCancelColor(0x80808080)//ȡ����ť������ɫ
                .isCenterLabel(false)//�Ƿ�ֻ��ʾ�м�ѡ�����label���֣�false��ÿ��itemȫ��������label
                .setDividerColor(0x00000000)//���÷ָ��ߵ���ɫ
                .setTextColorCenter(0xff494949)//����ѡ�������ɫ
                .setTextColorOut(0xffb5b5b5)//����û�б�ѡ�������ɫ
               // .setContentSize(21)//�������ִ�С
                .setDate(selectedDate)// ��������õĻ���Ĭ����ϵͳʱ��
                .setLineSpacingMultiplier(3f)//����������֮��ļ������
                .setTextXOffset(-10, 0,10, 10, 10, 0)//����X����б�Ƕ�[ -90 , 90��]
                .setRangDate(startDate, endDate)//��ʼ��ֹ�������趨
//                .setBackgroundId(0x00FFFFFF) //�����ⲿ������ɫ
                .setDecorView(null)//����Ҫ��pickerview��ʾ��������id ������viewgrou
                .build();
    }

    private String getTime(Date date) {//�ɸ�����Ҫ���н�ȡ������ʾ
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
//��ַ��ѯ����ֵ
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        HashMap<String, AddressBean> dataMap = (HashMap) data.getExtras().get("data");
        AddressBean addressBean = dataMap.get("addressBean");
        if (addressBean == null){
            addressBean = new AddressBean();
        }
        switch (requestCode){
            case SetOutAddressCode:

                adapter.getCustomizedHolder().setSetoutAddress(addressBean);
                break;
            case EndAddressCode:

                adapter.getCustomizedHolder().setEndAddressMap(addressBean);
                break;
                default:
                 break;
        }
    }
  /****************************************������������*******************************************/
  private void  getCustomizedDemandList(){
      Map<String,Object> param = new HashMap<>();
      param.put("page",0);
      param.put("limit",20);
      param.put("passengerAccount",PublicData.userAccount);
      customizedPresenter.sendRequestGetcustomizedDemandList(param);
  }
/****************************************����������*******************************************/
//����������
    @Override
    public void requestOnSuccees(PublicResponseBean publicResponseBean) {
        if (publicResponseBean.getCode()==1){
          getCustomizedDemandList();
         // Toast.makeText(this, "�����Ƴɹ�",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, NonNullString.getString(publicResponseBean.getMsg()),Toast.LENGTH_LONG).show();
//        Log.i("sss", "requestOnSuccees: ");
    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        Toast.makeText(this, PublicData.netWorkErrorMsg,Toast.LENGTH_LONG);
//        Log.i("sss", "requestOnFailure ");
    }

    @Override
    public void requestOnSucceesDemandList(CustomizedDemandListBean listBean) {
        if (listBean==null){
            adapter.setDataList(null);
        }else {
            adapter.setDataList(listBean.getData());
        }

    }

    @Override
    public void requestOnFailureDemandList(Throwable e, boolean isNetWorkError) {
        Toast.makeText(this, PublicData.netWorkErrorMsg,Toast.LENGTH_LONG);
    }
}
