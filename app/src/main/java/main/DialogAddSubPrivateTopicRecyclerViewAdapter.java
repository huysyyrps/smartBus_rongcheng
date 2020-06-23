package main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import main.smart.rcgj.R;

/**
 * Created by Administrator on 2019/2/14.
 */

public class DialogAddSubPrivateTopicRecyclerViewAdapter extends RecyclerView.Adapter<DialogAddSubPrivateTopicRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<String> dataList;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_dialog_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public DialogAddSubPrivateTopicRecyclerViewAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titleTextView.setText(dataList.get(position));

        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.tv_title);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}