package main.smart.bus.activity.adapter;

import java.util.ArrayList;

import main.smart.rcgj.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeInfoAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String> list;
	private ArrayList<String> list2;
	public NoticeInfoAdapter(Context context,ArrayList<String> list,ArrayList<String> list2){
		this.context = context;
		this.list = list;
		if (list.size() == list2.size())
		{
			this.list2 = list2;
		}
		
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
		NoticeViewHolder viewHolder = null;
		if(arg1 == null&&list.size() != 0){
			viewHolder = new NoticeViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			arg1 = inflater.inflate(R.layout.notice_list_item, null);
			viewHolder.textView = (TextView)arg1.findViewById(R.id.notice_title_text);	
			viewHolder.textView2 = (TextView)arg1.findViewById(R.id.notice_time_text);	

			arg1.setTag(viewHolder);
		}else
		{
			viewHolder = (NoticeViewHolder) arg1.getTag();
		}
		viewHolder.textView.setText(list.get(arg0));
		if (list2 != null)
		{	
			viewHolder.textView2.setText(list2.get(arg0));
		}
		return arg1;
	}
	class NoticeViewHolder {
		public TextView textView;
		public TextView textView2;
	}
}
