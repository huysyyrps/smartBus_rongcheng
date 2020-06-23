package main.customizedBus.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import main.customizedBus.home.bean.AddressBean;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.smart.rcgj.R;
import main.utils.utils.NonNullString;

import java.util.List;

public class CustomizedBusHomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int SEARCHVIEW = 10001;
    private final static int MENUSVIEW = 10002;
    private final static int LINEVIEWHIDDENLINE = 10003;
    private final static int LINEVIEW = 10004;

    private final Context context;
    private SearchViewHolder searchViewHolder;

    public OnRecyclerViewClickListener onRecyclerViewClickListener;

    //����
  public List<CustomizedLineBean.DataBean>dataList;
    private AddressBean startAddress;
    private AddressBean endAddress;


    public CustomizedBusHomeFragmentAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
    }
//set
    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }
    public void setStartAddress(AddressBean startAddress) {
        this.startAddress = startAddress;
        reloadSearchViewHolder();
    }

    public void setEndAddress(AddressBean endAddress) {
        this.endAddress = endAddress;
        reloadSearchViewHolder();
    }

    public void setDataList(List<CustomizedLineBean.DataBean> dataList) {
        this.dataList = dataList;
      notifyDataSetChanged();
    }

    public List<CustomizedLineBean.DataBean> getDataList() {
        return dataList;
    }

    //reload SearchViewHolder
    private void  reloadSearchViewHolder(){
        if (startAddress != null&& searchViewHolder!=null){
            searchViewHolder.getSetoutTV().setText(startAddress.getViewShowName());
        }
        if (endAddress != null&& searchViewHolder!=null){
            searchViewHolder.getEndTV().setText(endAddress.getViewShowName());
        }
    }
    //��дRecyclerView.Adapter��getItemViewType���������������ظ�onCreateViewHolder��viewType��
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return  SEARCHVIEW;
        }else if (position==1){
            return  MENUSVIEW;
        }else if (position==2){
            return  LINEVIEWHIDDENLINE;
        }else
        {
            return  LINEVIEW;
        }
//        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        RecyclerView.ViewHolder viewHolder;
        if (viewType==SEARCHVIEW){
            if (searchViewHolder==null){
                View view = inflater.inflate(R.layout.holder_view_searchaddress,parent,false);
                searchViewHolder = new SearchViewHolder(view);
            }


            viewHolder = searchViewHolder;
        }else  if (viewType==MENUSVIEW){
            View view = inflater.inflate(R.layout.holder_view_fastmenus,parent,false);
            viewHolder = new FastMenusViewHolder(view);
        }else if (viewType==LINEVIEWHIDDENLINE){
            View view = inflater.inflate(R.layout.holder_view_linelist,parent,false);
            viewHolder = new LineViewHolder(view);
            ((LineViewHolder) viewHolder).hiddenLineView();
        }else {
            View view = inflater.inflate(R.layout.holder_view_linelist,parent,false);
            viewHolder = new LineViewHolder(view);
            ((LineViewHolder) viewHolder).hiddenTagTV();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          if (position==0){
              reloadSearchViewHolder();
          }else  if (position>1){
              int i = position - 2;
              CustomizedLineBean.DataBean dataBean  = dataList.get(i);
              LineViewHolder lineViewHolder = (LineViewHolder) holder;
              ((LineViewHolder) holder).setDataBean(dataBean);
          }
    }

    @Override
    public int getItemCount() {
        if (dataList==null){
            return 2;
        }
        return dataList.size() + 2 ;
    }

    //����¼�
   public interface OnRecyclerViewClickListener{
        void onItemClickListener(View view, int position);
        void onItemBuyInLineViewHolderClickListener(View view, int position);
        void onItemInSearchViewClickListener(View view, int position, View subView, int subViewId);//subPosition:0 ����ʼ���� 1 ����Ŀ�ĵ�
        void onItemInFastMenusViewClickListener(View view, int position, int subViewId);//subPosition:0 ����ʼ���� 1 ����Ŀ�ĵ�
    }

    class SearchViewHolder extends  RecyclerView.ViewHolder{
        private  View itemView;
        private  TextView setoutTV;
        private  TextView endTV;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            initView(itemView);
        }

        public TextView getSetoutTV() {
            return setoutTV;
        }

        public TextView getEndTV() {
            return endTV;
        }

        private void initView(View itemView) {
            setoutTV = itemView.findViewById(R.id.id_setout_textview);
            endTV = itemView.findViewById(R.id.id_end_textview);
            int position = getAdapterPosition();
            //ʼ���� �������
            setoutTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null){
                        onRecyclerViewClickListener.onItemInSearchViewClickListener(itemView,position,view,R.id.id_setout_textview);
                    }
                }
            });

            //Ŀ�ĵ� �������
            endTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null){

                        onRecyclerViewClickListener.onItemInSearchViewClickListener(itemView,position,view,R.id.id_end_textview);
                    }
                }
            });
        }
    }

    class FastMenusViewHolder extends  RecyclerView.ViewHolder{
        private RadioGroup radioGroup;
        public FastMenusViewHolder(@NonNull View itemView) {
            super(itemView);

            radioGroup = itemView.findViewById(R.id.id_radiogroup);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                   if (onRecyclerViewClickListener != null){
                       int position = getAdapterPosition();
                       RadioButton radioButton =  itemView.findViewById(i);
                     radioButton.setChecked(false);
                       onRecyclerViewClickListener.onItemInFastMenusViewClickListener(itemView,position,i);

                   }
                }
            });
        }
    }

    class LineViewHolder extends  RecyclerView.ViewHolder{
        private TextView textViewTag;
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

        public LineViewHolder(@NonNull View itemView) {
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
            textViewTag = itemView.findViewById(R.id.id_tag_tv1);
            textViewLine = itemView.findViewById(R.id.id_line_view);
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
           // .."��" + String.valueOf(price) + "��Ʊ";
            String buyStr = context.getResources().getString(R.string.customized_ticket_buy);
            String Str = context.getResources().getString(R.string.customized_fuhao);
            String scheldulStr = "��Σ�"   +  dataBean.getSchedul().replace(",",", ");
            textViewLineNo.setText(NonNullString.getString(dataBean.getName()));
            textViewStartStationTitle.setText(NonNullString.getString(dataBean.getStartStation()));
            textViewStartStation.setText(NonNullString.getString(dataBean.getStartStation()));
            textViewEndStationTitle.setText(NonNullString.getString(dataBean.getEndStation()));
            textViewEndStation.setText(NonNullString.getString(dataBean.getEndStation()));
            textViewStartTime.setText(NonNullString.getString(dataBean.getStartDateStr()));
            textViewEndTime.setText(NonNullString.getString(dataBean.getEndDateStr()));
            textViewBuyPrice.setText(Str + dataBean.getPriceStr() + buyStr);
            textViewSchedulTime.setText(scheldulStr);
            List<CustomizedLineBean.DataBean.SchedulStationDTOListBean> stationDTOListBeans = dataBean.getSchedulStationDTOList();
            if (stationDTOListBeans!=null&&stationDTOListBeans.size()>0){
                CustomizedLineBean.DataBean.SchedulStationDTOListBean satartStationBean = stationDTOListBeans.get(0);
                CustomizedLineBean.DataBean.SchedulStationDTOListBean endStationBean = stationDTOListBeans.get(stationDTOListBeans.size()-1);
                textViewEndTime.setText(endStationBean.getTime());
                textViewStartTime.setText(satartStationBean.getTime());
            }
        }
        public void hiddenTagTV(){
            textViewTag.setHeight(0);
        }
        public void hiddenLineView(){
            textViewLine.setVisibility(View.INVISIBLE);
        }
    }
}
