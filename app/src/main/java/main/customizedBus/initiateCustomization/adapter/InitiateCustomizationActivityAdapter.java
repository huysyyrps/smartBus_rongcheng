package main.customizedBus.initiateCustomization.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.smart.rcgj.R;
import main.utils.utils.NonNullString;
import main.utils.utils.PublicData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitiateCustomizationActivityAdapter extends RecyclerView.Adapter {
    private final static int CUSTOMIZATION = 10001;
    private final static int CUSTOMIZATIONHISTORY = 10002;
    private final static int TEXT = 10003;
    private final Context context;
    private List<CustomizedDemandListBean.DataBean> dataList;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;
    private CustomizedHolder customizedHolder;

    private AddressBean setoutAddress; //�����ص�
    private AddressBean endAddress; //Ŀ�ĵ�
    private Map setoutTimeMap; //����ʱ��

    public CustomizedHolder getCustomizedHolder() {
        return customizedHolder;
    }


    public void setDataList(List<CustomizedDemandListBean.DataBean> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public void setSetoutAddress(AddressBean setoutAddress) {
        this.setoutAddress = setoutAddress;
    }

    public void setEndAddress(AddressBean endAddress) {
        this.endAddress = endAddress;
    }

    public void setSetoutTimeMap(Map setoutTimeMap) {
        this.setoutTimeMap = setoutTimeMap;
    }

    public InitiateCustomizationActivityAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    //�������ӿ�
    public interface OnRecyclerViewClickListener{
        void onInitiateCustomizationButtonClickListener(Map<String, Object> data);
        void onItemViewInCustomizedHolderClickListener(View itemView);

        void onItemViewDingZhiGuiZeClickListener(View view);
    }
//���������ظ�onCreateViewHolder��viewType
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return  CUSTOMIZATION;
        }else if (position==1){
            return  TEXT;
        }else{
            return  CUSTOMIZATIONHISTORY;
        }
//        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view;
        if (viewType == CUSTOMIZATION){
            if (customizedHolder==null){
                view = inflater.inflate(R.layout.holder_view_initiate_customization,parent,false);
                CustomizedHolder holder = new CustomizedHolder(view);
                customizedHolder = holder;
            }

            return  customizedHolder;
        }else if (viewType == TEXT){
            view = inflater.inflate(R.layout.holder_view_text,parent,false);
            TextHolder holder = new TextHolder(view);
            return holder;
        }else {
            view = inflater.inflate(R.layout.holder_view_customization_history,parent,false);
            CustomizedHistoryHolde holder = new CustomizedHistoryHolde(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position==0){

        }else if (position>1){
            int i = position - 2;
            if (dataList.size()>i){
                CustomizedDemandListBean.DataBean dataBean = dataList.get(i);
                CustomizedHistoryHolde historyHolde = (CustomizedHistoryHolde) holder;
                historyHolde.setDataBean(dataBean);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (dataList==null||dataList.size()==0){
            return 1;
        }
        return dataList.size()+2;
    }

    public   class CustomizedHolder extends RecyclerView.ViewHolder{
        private TextView setoutAddressTV;
        private TextView endAddressTV;
        private TextView setoutTimeTV;
        private Button sumbitButton;
        private AddressBean setoutAddress; //�����ص�
        private AddressBean endAddress; //Ŀ�ĵ�
        private Map setoutTimeMap; //����ʱ��
        private  TextView dingzhiGuizeTV;

        public void setSetoutAddress(AddressBean setoutAddress) {
            this.setoutAddress = setoutAddress;
           setoutAddressTV.setText(NonNullString.getString(setoutAddress.getName()));
        }

        public void setEndAddressMap(AddressBean endAddress) {
            this.endAddress = endAddress;
            endAddressTV.setText(NonNullString.getString(endAddress.getName()));
        }

        public void setSetoutTimeMap(Map setoutTimeMap) {
            this.setoutTimeMap = setoutTimeMap;
            setoutTimeTV.setText(NonNullString.getString(setoutTimeMap.get("text")));
        }


        public CustomizedHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            setoutAddressTV = itemView.findViewById(R.id.id_setout_textview);
            endAddressTV = itemView.findViewById(R.id.id_end_textview);
            setoutTimeTV = itemView.findViewById(R.id.id_setout_time_textview);
            sumbitButton = itemView.findViewById(R.id.id_submit_button);
            dingzhiGuizeTV =  itemView.findViewById(R.id.id_dingzhi_guize);

             dingzhiGuizeTV.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (onRecyclerViewClickListener != null){
                         onRecyclerViewClickListener.onItemViewDingZhiGuiZeClickListener(view);
                     }
                 }
             });
            //�����ص�
            setoutAddressTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if (onRecyclerViewClickListener != null){
                         onRecyclerViewClickListener.onItemViewInCustomizedHolderClickListener(view);
                     }
                }
            });
            //Ŀ�ĵ�
            endAddressTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null){
                        onRecyclerViewClickListener.onItemViewInCustomizedHolderClickListener(view);
                    }
                }
            });
            //����ʱ��
            setoutTimeTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null){
                        onRecyclerViewClickListener.onItemViewInCustomizedHolderClickListener(view);
                    }
                }
            });

            //������
            sumbitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null){
                        if (setoutAddressTV.getText().toString().equals("")){
                        Toast.makeText(context,"��ѡ���ϳ��ص�",Toast.LENGTH_LONG).show();
                        return;
                        }

                        if (endAddressTV.getText().toString().equals("")){
                            Toast.makeText(context,"��ѡ��Ŀ�ĵص�",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (setoutTimeTV.getText().toString().equals("")){
                            Toast.makeText(context,"��ѡ���ϳ�ʱ��",Toast.LENGTH_LONG).show();
                            return;
                        }
                        onRecyclerViewClickListener.onInitiateCustomizationButtonClickListener(getSubmitData());
                    }
                }
            });


        }

        //��ȡ����������
        private  Map getSubmitData(){
            HashMap<String,String> data = new HashMap<String,String>();
            data.put("startAddress","");//�ϳ��ص�
            data.put("endAddress","");//�³��ص�
            data.put("rideTime","");//�˳�ʱ��
           // data.put("demandType","0");//��������0�����ƿ��� 1������
            data.put("passengerAccount", PublicData.userAccount);//

            if (!setoutAddressTV.getText().toString().equals("")){
                data.put("startAddress",setoutAddressTV.getText().toString());
            }

            if (!endAddressTV.getText().toString().equals("")){
                data.put("endAddress",endAddressTV.getText().toString());
            }
            if (!setoutTimeTV.getText().toString().equals("")){
                data.put("rideTime",setoutTimeTV.getText().toString());
            }
            return data;
        }
    }

    class TextHolder extends RecyclerView.ViewHolder{
        public TextHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

      class CustomizedHistoryHolde extends RecyclerView.ViewHolder{
          private TextView textViewStartStation;;
          private TextView textViewEndStation;
          private TextView textViewStartTime;
          private TextView textViewEndTime;
          private CustomizedDemandListBean.DataBean dataBean;

        public CustomizedHistoryHolde(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

          private void initView(View itemView) {

              textViewStartStation = itemView.findViewById(R.id.id_onbus_station);
              textViewEndStation = itemView.findViewById(R.id.id_offbus_station);
              textViewStartTime = itemView.findViewById(R.id.id_onbus_time);
              textViewEndTime = itemView.findViewById(R.id.id_offbus_time);

          }

          public void setDataBean(CustomizedDemandListBean.DataBean dataBean) {
              this.dataBean = dataBean;
              textViewStartStation.setText(NonNullString.getString(dataBean.getStartAddress()));
              textViewEndStation.setText(NonNullString.getString(dataBean.getEndAddress()));
              textViewStartTime.setText(NonNullString.getString(dataBean.getRideTimeStr()));

          }
    }
}
