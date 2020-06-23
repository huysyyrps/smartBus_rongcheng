package main.customizedBus.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import main.customizedBus.home.bean.AddressBean;
import main.smart.rcgj.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAddressActivityHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private  List<AddressBean> dataList = new ArrayList<>();
    private AddressBean locationBean;
    public OnRecyclerViewClickListener onRecyclerViewClickListener;
    private final static int  CURLOCATIONTITLE = 10001;
    private final static int  CURLOCATION = 10002;
    private final static int  HISTORYTITLE = 10003;
    private final static int  HISTORY = 10004;



    public SearchAddressActivityHistoryAdapter(Context context, List<AddressBean> dataList,AddressBean locationBean) {
        this.context = context;
        this.dataList = dataList;
        if (this.dataList ==null){
            this.dataList = new ArrayList<>();
        }
        this.locationBean = locationBean;
    }

    public void setLocationBean(AddressBean locationBean) {
        this.locationBean = locationBean;
        notifyItemChanged(1);
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public int getItemViewType(int position) {
       if (position==0){
           return CURLOCATIONTITLE;
       }else  if (position==1){
           return CURLOCATION ;
       }else  if (position==2){
           return HISTORYTITLE;
       }else {
           return  HISTORY;
       }
    }

    //点击接口
    public interface OnRecyclerViewClickListener{
        void onItemClickListener(View view, int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType==CURLOCATIONTITLE){
            view = inflater.inflate(R.layout.holder_view_text,parent,false);
            TextViewHolder textViewHolder = new TextViewHolder(view);
            String str = context.getResources().getString(R.string.customized_location);
            textViewHolder.getTextView().setText(str);
            viewHolder = textViewHolder;
        }else if (viewType==CURLOCATION){
            view = inflater.inflate(R.layout.holder_view_address,parent,false);
            AddressHolder addressHolder = new AddressHolder(view);
            viewHolder = addressHolder;
        }else if (viewType==HISTORYTITLE){
            view = inflater.inflate(R.layout.holder_view_text,parent,false);
            TextViewHolder textViewHolder = new TextViewHolder(view);
            String str = context.getResources().getString(R.string.customized_linehistory);
            textViewHolder.getTextView().setText(str);
            viewHolder = textViewHolder;
        }else {
            view = inflater.inflate(R.layout.holder_view_address,parent,false);
            AddressHolder addressHolder = new AddressHolder(view);
            viewHolder = addressHolder;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position==0||position==2){
            return;
        }
        AddressHolder addressHolder = (AddressHolder) holder;
        AddressBean addressBean = null;

        if (position==1){
            addressHolder.imageView.setImageResource(R.drawable.lyf_icon_loacation);
            addressBean = locationBean;
        }else  if (position>2){
            addressHolder.imageView.setImageResource(R.drawable.icon_history_address);
            int i = position - 3;
           addressBean = this.dataList.get(i);

        }
        if (position==1||position>2){
            String deatail = addressBean.getAddr();
            String name = addressBean.getName();
            addressHolder.getDeatailTV().setText(deatail);
            addressHolder.getNameTV().setText(name);
        }

    }

    @Override
    public int getItemCount() {
        if (dataList == null)
        {
            return 3;
        }
        return dataList.size()+3;
    }//

    public class AddressHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTV;
        private TextView deatailTV;

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNameTV() {
            return nameTV;
        }

        public TextView getDeatailTV() {
            return deatailTV;
        }

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewClickListener != null)
                    {
                        onRecyclerViewClickListener.onItemClickListener(view,getAdapterPosition());
                    }
                }
            });

            imageView  = itemView.findViewById(R.id.id_icon_img);
            nameTV = itemView.findViewById(R.id.id_name_tv);
            deatailTV = itemView.findViewById(R.id.id_detail_tv);
//            imageView.sets
        }


    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
          textView =   itemView.findViewById(R.id.id_textview);
        }

        public TextView getTextView() {
            return textView;
        }
    }

}
