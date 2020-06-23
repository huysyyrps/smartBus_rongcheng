package main.customizedBus.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.tool.CalendarManager;
import main.smart.rcgj.R;

public class StandbyTicketFragmentAdapter extends RecyclerView.Adapter<StandbyTicketFragmentAdapter.TicketViewHolder> {

    private final Context context;
    public List<TicketBean.DataBean> dataList;
    public OnRecyclerViewClickListener onRecyclerViewClickListener;


    public StandbyTicketFragmentAdapter(Context context, List<TicketBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public void setDataList(List<TicketBean.DataBean> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.holder_view_ticket, parent, false);
        TicketViewHolder viewHolder = new TicketViewHolder(view);
        return viewHolder;
    }

    //点击接口
    public interface OnRecyclerViewClickListener {
        void onItemClickListener(View view, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        if (position<dataList.size()){
            holder.setDataBean(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        private TicketBean.DataBean dataBean;
        TextView lineNameTV;
        TextView carnoTV;
        TextView markTV;
        TextView datePeoplenumTV;
        TextView onbusTimeTV;
        TextView onbusStationTV;
        TextView offbusTimeTV;
        TextView offbusStationTV;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            lineNameTV = itemView.findViewById(R.id.id_line_name_tv);
            carnoTV = itemView.findViewById(R.id.id_carno_tv);
            markTV = itemView.findViewById(R.id.id_mark_tv);
            //  markTV.setVisibility(View.INVISIBLE);
            datePeoplenumTV = itemView.findViewById(R.id.id_date_peoplenum_tv);
            onbusTimeTV = itemView.findViewById(R.id.id_onbus_time);
            onbusStationTV = itemView.findViewById(R.id.id_onbus_station);
            offbusTimeTV = itemView.findViewById(R.id.id_offbus_time);
            offbusStationTV = itemView.findViewById(R.id.id_offbus_station);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null) {
                        onRecyclerViewClickListener.onItemClickListener(view, getAdapterPosition());
                    }
                }
            });
        }

        public void setDataBean(TicketBean.DataBean dataBean) {
            this.dataBean = dataBean;
            lineNameTV.setText(dataBean.getLineName());
            onbusStationTV.setText(dataBean.getStartStaion());
            offbusStationTV.setText(dataBean.getEndStation());
            String  peopleNum = String.valueOf(dataBean.getNum());
            Date rideDate = new Date(dataBean.getRideDate());//乘车日期
            String rideDateStr  = CalendarManager.getDateFormat(rideDate,"yyyy年MM月dd日");
            String datePeoplenumStr = rideDateStr +"       "+peopleNum+"人乘坐";
            datePeoplenumTV.setText(datePeoplenumStr);
            int status = dataBean.getStatus();//验票状态
            int payStatus = dataBean.getPayStatus();//0：未支付，1：已支付，2：支付失败3：退款中 4：已退款 5：退款失败
            carnoTV.setText("班次："+dataBean.getSchedulTime());

            markTV.setText(dataBean.getMobileStatus());
            int color = R.color.rb_select;
            switch (dataBean.getMobileStatus()){
                case "待出行":
                    color = R.color.rb_select;
                    break;
                case "出行中":
                    color = R.color.green_bg;
                    break;
                case "已过期":
                    color = R.color.umeng_socialize_edit_bg;
                    break;
                case "已验票":
                    color = R.color.textGray;
                    break;
                case "退款待审核":
                    color = R.color.holo_orange_dark;
                    break;
                case "退款中":
                    color =R.color.holo_orange_light;
                    break;
                case "已退款":
                    color =R.color.black_text;
                    break;
                case "退款失败":
                    color =R.color.red;
                    break;
            }
            markTV.setTextColor(context.getColor(color));
//            if (textColors.get(dataBean.getMobileStatus())!=null){
//                int color = (int) textColors.get(dataBean.getMobileStatus());
//                switch ()
//                markTV.setTextColor(color);
//            }
//           if (status==1){
//               setStatusCompleted();
//           }
//           else {
//               setStatusRefund(payStatus);
//           }

        }
        //待使用
        public void setStatusNoUsed(){
            markTV.setText("待出行");
            markTV.setTextColor(context.getColor(R.color.rb_select));
        }
        //完成
        public void setStatusCompleted(){
            markTV.setText("已使用");
            markTV.setTextColor(context.getColor(R.color.textGray));
        }
        //退票
        public void setStatusRefund(int status){
            switch (status){
                case 1:
                    if (dataBean.getOperType()==1){
                        markTV.setText("退款中");
                        markTV.setTextColor(context.getColor(R.color.holo_orange_light));
                    }else {
                        setStatusNoUsed();
                    }

                    break;
                case 3:
                    markTV.setText("退款中");
                    markTV.setTextColor(context.getColor(R.color.holo_orange_light));
                    break;
                case 4:
                    markTV.setText("已退款");
                    markTV.setTextColor(context.getColor(R.color.green_bg));
                    break;
                case 5:
                    markTV.setText("退款失败");
                    markTV.setTextColor(context.getColor(R.color.red));
                    break;
            }

        }
    }
}
