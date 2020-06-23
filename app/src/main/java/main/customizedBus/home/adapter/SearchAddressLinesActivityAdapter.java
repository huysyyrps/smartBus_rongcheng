package main.customizedBus.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import main.customizedBus.line.bean.CustomizedLineBean;
import main.smart.rcgj.R;
import main.utils.utils.NonNullString;

import java.util.List;

public class SearchAddressLinesActivityAdapter extends RecyclerView.Adapter<SearchAddressLinesActivityAdapter.AddressHolder> {
    private final Context context;
    private  List<CustomizedLineBean.DataBean> dataList;
    public OnRecyclerViewClickListener onRecyclerViewClickListener;
    /**
     *  ����¼�
     */

    public interface OnRecyclerViewClickListener{
        void onItemClickListener(View view, int position);
        void onItemBuyInLineViewHolderClickListener(View view, int position);

    }
    /**
     * g���캯��
     * @param context
     * @param dataList
     */
    public SearchAddressLinesActivityAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public List<CustomizedLineBean.DataBean> getDataList() {
        return dataList;
    }

    /**
     *
     * @param dataList
     */
    public void setDataList(List<CustomizedLineBean.DataBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }
    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.holder_view_linelist2,parent,false);
        AddressHolder addressHolder = new AddressHolder(view);
        return addressHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
           CustomizedLineBean.DataBean dataBean = dataList.get(position);
           holder.setDataBean(dataBean);
    }

    @Override
    public int getItemCount() {
        if (dataList == null){
            return 0;
        }
        return dataList.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        private TextView textViewLineNo;
        private TextView textViewStartStationTitle;
        private TextView textViewEndStationTitle;
        private TextView textViewStartStation;
        private TextView textViewEndStation;
        private TextView textViewStartTime;
        private TextView textViewEndTime;
        private TextView textViewLine;
        private TextView textViewBuyPrice;
        private CustomizedLineBean.DataBean dataBean;
        private TextView textViewSchedulTime;
        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int postion = getAdapterPosition();
                    if (onRecyclerViewClickListener != null){
                        onRecyclerViewClickListener.onItemClickListener(itemView,postion);
                    }
                }
            });
        }
        private void initView(View itemView) {

            textViewLineNo = itemView.findViewById(R.id.id_lineno_tv);
            textViewStartStationTitle = itemView.findViewById(R.id.id_startaddress_tv);
            textViewEndStationTitle = itemView.findViewById(R.id.id_endaddress_tv);
            textViewStartStation =  itemView.findViewById(R.id.id_onbus_station);
            textViewEndStation = itemView.findViewById(R.id.id_offbus_station);
            textViewStartTime = itemView.findViewById(R.id.id_onbus_time);
            textViewEndTime = itemView.findViewById(R.id.id_offbus_time);
            textViewBuyPrice = itemView.findViewById(R.id.id_buy_tv);
            textViewSchedulTime = itemView.findViewById(R.id.id_schedul_tv);


            textViewBuyPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int postion = getAdapterPosition();
                    if (onRecyclerViewClickListener != null){
                        onRecyclerViewClickListener.onItemBuyInLineViewHolderClickListener(itemView,postion);
                    }
                }
            });

        }

        public void setDataBean(CustomizedLineBean.DataBean dataBean) {
            this.dataBean = dataBean;
            String scheldulStr = "��Σ�"   +  dataBean.getSchedul();
            textViewLineNo.setText(NonNullString.getString(dataBean.getName()));
            textViewStartStationTitle.setText(NonNullString.getString(dataBean.getStartStation()));
            textViewStartStation.setText(NonNullString.getString(dataBean.getStartStation()));
            textViewEndStationTitle.setText(NonNullString.getString(dataBean.getEndStation()));
            textViewEndStation.setText(NonNullString.getString(dataBean.getEndStation()));
            textViewStartTime.setText(NonNullString.getString(dataBean.getStartDateStr()));
            textViewEndTime.setText(NonNullString.getString(dataBean.getEndDateStr()));
            textViewBuyPrice.setText(String.valueOf(dataBean.getPriceStr()));
            textViewSchedulTime.setText(scheldulStr);
            List<CustomizedLineBean.DataBean.SchedulStationDTOListBean> stationDTOListBeans = dataBean.getSchedulStationDTOList();
            if (stationDTOListBeans!=null&&stationDTOListBeans.size()>0){
                CustomizedLineBean.DataBean.SchedulStationDTOListBean satartStationBean = stationDTOListBeans.get(0);
                CustomizedLineBean.DataBean.SchedulStationDTOListBean endStationBean = stationDTOListBeans.get(stationDTOListBeans.size()-1);
                textViewEndTime.setText(endStationBean.getTime());
                textViewStartTime.setText(satartStationBean.getTime());
            }
        }
    }
}
