package main.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public abstract class ConsumptionAdapter extends  RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private int mLayoutid;
    private List<Map<String, String>> mDatas;
    private AdapterView.OnItemClickListener monItemClickListener = null;


    public ConsumptionAdapter(Context mContext, int mLayoutid, List<Map<String, String>> mDatas) {
        this.mContext = mContext;
        this.mLayoutid = mLayoutid;
        this.mDatas = mDatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutid);
        return viewHolder;
    }


    public abstract void convert(ViewHolder holder, Map<String, String> position);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder,  mDatas.get(position));
    }

    /**
     * 获取高度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

}
