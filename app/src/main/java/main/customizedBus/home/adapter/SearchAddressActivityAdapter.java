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

import java.util.List;

public class SearchAddressActivityAdapter extends RecyclerView.Adapter<SearchAddressActivityAdapter.AddressHolder> {
    private final Context context;
    private  List<AddressBean> dataList;
    public OnRecyclerViewClickListener onRecyclerViewClickListener;

    public void setDataList(List<AddressBean> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }



    public SearchAddressActivityAdapter(Context context, List<AddressBean>  dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    //点击接口
    public interface OnRecyclerViewClickListener{
        void onItemClickListener(View view, int position);
    }
    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.holder_view_address,parent,false);
        AddressHolder addressHolder = new AddressHolder(view);
        return addressHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        if (position>=this.dataList.size()){
            return;
        }
        AddressBean addressBean = this.dataList.get(position);
        String deatail = addressBean.getAddr();
        String name = addressBean.getName();
        holder.getDeatailTV().setText(deatail);
        holder.getNameTV().setText(name);
    }

    @Override
    public int getItemCount() {
        return this.dataList == null ? 0 : dataList.size();
    }

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
        }

    }
}
