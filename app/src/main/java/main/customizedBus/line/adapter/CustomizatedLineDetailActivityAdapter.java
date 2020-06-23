package main.customizedBus.line.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.smart.rcgj.R;
import main.utils.utils.NonNullString;

import java.util.List;

public class CustomizatedLineDetailActivityAdapter extends RecyclerView.Adapter {
    private final static int STARTSTATION = 10001;
    private final static int MIDDLESTATION = 10002;
    private final static int ENDSTATION = 10003;
    private final Context context;
    private  List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList;

    public CustomizatedLineDetailActivityAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setDataList(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
        {
            return  STARTSTATION;
        }else  if (position == dataList.size()-1){
            return  ENDSTATION;
        }else {
            return MIDDLESTATION;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == STARTSTATION){
         view = inflater.inflate(R.layout.holder_view_station_start,parent,false) ;
         StationStartViewHolder startViewHolder = new StationStartViewHolder(view);
         viewHolder = startViewHolder;

        }else if(viewType == ENDSTATION){
            view = inflater.inflate(R.layout.holder_view_station_end,parent,false) ;
            StationEndViewHolder endViewHolder = new StationEndViewHolder(view);
            viewHolder = endViewHolder;

        }else {
            view = inflater.inflate(R.layout.holder_view_station_middle,parent,false) ;
            StationMiddleViewHolder middleViewHolder = new StationMiddleViewHolder(view);
            middleViewHolder.setOnBusSatation();
            viewHolder = middleViewHolder;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean = dataList.get(position);
            if (position==0){
                StationStartViewHolder startViewHolder = (StationStartViewHolder) holder;
                startViewHolder.setBean(bean);
            }else if (position == dataList.size()-1){
               StationEndViewHolder endViewHolder = ( StationEndViewHolder) holder;
                endViewHolder.setBean(bean);
            }else {
                StationMiddleViewHolder middleViewHolder = ( StationMiddleViewHolder) holder;
                middleViewHolder.setBean(bean);

            }

    }

    @Override
    public int getItemCount() {
        if (dataList==null){
            return 0;
        }
        return dataList.size();
    }
    //开始站点a
    class StationStartViewHolder extends RecyclerView.ViewHolder{

        private TextView timeTV;
        private TextView stationTV;
        private CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean;

        public StationStartViewHolder(@NonNull View itemView) {
            super(itemView);

            timeTV  = itemView.findViewById(R.id.id_time_tv);
            stationTV  = itemView.findViewById(R.id.id_station_tv);
        }



        public void setBean(CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean) {
            this.bean = bean;
            timeTV.setText(NonNullString.getString(bean.getTime()));
            stationTV.setText(NonNullString.getString(bean.getStation().getName()));
        }

    }
    //中间站点
    class StationMiddleViewHolder extends RecyclerView.ViewHolder{

        private TextView leftCirleView;
        private TextView rightCirleView;
        private TextView timeTV;
        private TextView stationTV;
        private CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean;
        public StationMiddleViewHolder(@NonNull View itemView) {
            super(itemView);
            leftCirleView = itemView.findViewById(R.id.id_left_tv);
            rightCirleView = itemView.findViewById(R.id.id_right_tv);
            timeTV  = itemView.findViewById(R.id.id_time_tv);
            stationTV  = itemView.findViewById(R.id.id_station_tv);
        }
//上车站点
        public  void setOnBusSatation(){
            leftCirleView.setBackgroundResource(R.drawable.view_circle_green_left);
            rightCirleView.setBackgroundResource(R.drawable.view_circle_green_right);
        }
//下车站点
        public  void setOffBusSatation(){
            leftCirleView.setBackgroundResource(R.drawable.view_circle_red_left);
            rightCirleView.setBackgroundResource(R.drawable.view_circle_red_right);
        }
        //上下车站点
        public  void setOnOffBusSatation(){
            leftCirleView.setBackgroundResource(R.drawable.view_circle_green_left);
            rightCirleView.setBackgroundResource(R.drawable.view_circle_red_right);
        }

        public void setBean(CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean) {
            this.bean = bean;
            timeTV.setText(NonNullString.getString(bean.getTime()));
            stationTV.setText(NonNullString.getString(bean.getStation().getName()));
            switch (bean.getStation().getFlag()){
                case 0 :
                    setOnBusSatation();
                    break;
                case 1:
                    setOffBusSatation();
                    break;
                case 2:
                    setOnOffBusSatation();
                    break;
                default:
                    break;
            }
        }
    }
//结束站点
    class StationEndViewHolder extends RecyclerView.ViewHolder{

    private TextView timeTV;
    private TextView stationTV;
    private CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean;

    public StationEndViewHolder(@NonNull View itemView) {
        super(itemView);

        timeTV  = itemView.findViewById(R.id.id_time_tv);
        stationTV  = itemView.findViewById(R.id.id_station_tv);
    }


    public void setBean(CustomizedLineDetailBean.DataBean.SchedulDTOListBean.SchedulStationDTOListBean bean) {
        this.bean = bean;
        timeTV.setText(NonNullString.getString(bean.getTime()));
        stationTV.setText(NonNullString.getString(bean.getStation().getName()));


    }

}
}
