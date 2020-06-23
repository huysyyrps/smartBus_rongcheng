package main.customizedBus.ticket.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import main.customizedBus.ticket.bean.TicketDetailBean;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;

public class CustomizatedTicketCheckDialog extends Dialog implements View.OnClickListener{
    private  int layoutResID;
    private  TicketDetailBean.DataBean dataBean;
    TextView lineNameTV;
    TextView carnoTV;
    TextView markTV;
    TextView datePeoplenumTV;
    TextView onbusTimeTV;
    TextView onbusStationTV;
    TextView offbusTimeTV;
    TextView offbusStationTV;
    Button closeButton;


    public CustomizatedTicketCheckDialog(Context context) {
        super(context);
        this.layoutResID = R.layout.diglog_ticket_checked;
    }
    public CustomizatedTicketCheckDialog(Context context, int layoutResID) {
        super(context);
        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        lineNameTV = findViewById(R.id.id_line_name_tv);
        carnoTV = findViewById(R.id.id_carno_tv);
        markTV = findViewById(R.id.id_mark_tv);
        datePeoplenumTV = findViewById(R.id.id_date_peoplenum_tv);
        onbusTimeTV = findViewById(R.id.id_onbus_time);
        onbusStationTV = findViewById(R.id.id_onbus_station);
        offbusTimeTV = findViewById(R.id.id_offbus_time);
        offbusStationTV = findViewById(R.id.id_offbus_station);
        closeButton = findViewById(R.id.id_close_btn);
        closeButton.setOnClickListener(this::onClick);

//       setCancelable(true);
//       setCanceledOnTouchOutside(true);
    }

    public void setDataBean(TicketDetailBean.DataBean dataBean) {
        this.dataBean = dataBean;
        lineNameTV.setText(dataBean.getLineName());
        onbusStationTV.setText(dataBean.getStartStaion());
        offbusStationTV.setText(dataBean.getEndStation());
        String  peopleNum = String.valueOf(dataBean.getNum());
        Date rideDate = new Date(dataBean.getRideDate());//�˳�����
        String rideDateStr  = CalendarManager.getDateFormat(rideDate,"yyyy��MM��dd��");
        String datePeoplenumStr = rideDateStr +"       "+peopleNum+"�˳���";
        datePeoplenumTV.setText(datePeoplenumStr);
        int status = dataBean.getStatus();//��Ʊ״̬
        int payStatus = dataBean.getPayStatus();//0��δ֧����1����֧����2��֧��ʧ��3���˿��� 4�����˿� 5���˿�ʧ��
        carnoTV.setText("��Σ�"+dataBean.getSchedulTime());


    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.id_close_btn:
               hide();
               break;
               default:
                break;
       }
    }
}
