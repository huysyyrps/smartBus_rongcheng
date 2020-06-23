package main.smart.bus.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import main.smart.bus.bean.HCXX;
import main.smart.rcgj.R;

public class ListViewAdapterM extends BaseAdapter {
    private Context context;
    private List<HCXX> list;
    public ListViewAdapterM(Context context, List<HCXX> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if(arg1 == null&&list.size() != 0){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            arg1 = inflater.inflate(R.layout.zdxq, null);
            viewHolder.textView = (TextView)arg1.findViewById(R.id.text1);
            arg1.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) arg1.getTag();
        viewHolder.textView.setText(list.get(arg0).getName());
        return arg1;
    }
    class ViewHolder {
        public TextView textView;
    }


}


