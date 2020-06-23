package main.other.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.other.SwrlListActivity;
import main.smart.rcgj.R;

public class MySpinnerAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    List<String> beanList = new ArrayList<>();

    public MySpinnerAdapter(SwrlListActivity context, List<String> typeList) {
        this.context = context;
        this.beanList = typeList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //����ÿ��item�Ĳ��ֶ��󣨽�item_user_layoutתΪRelativeLayout����
        View layout = mInflater.inflate(R.layout.spinner_easy_item,null);
        //��ʼ�������е�Ԫ��
        TextView tvNick = layout.findViewById(R.id.textView);
        tvNick.setText(beanList.get(position));
        return layout;
    }
}
