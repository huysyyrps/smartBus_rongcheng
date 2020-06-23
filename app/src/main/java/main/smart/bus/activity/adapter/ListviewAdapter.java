package main.smart.bus.activity.adapter;

import java.util.ArrayList;

import main.smart.rcgj.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListviewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list;
	public ListviewAdapter(Context context,ArrayList<String> list){
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
		String allEnCodes="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		if(arg1 == null&&list.size() != 0){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			arg1 = inflater.inflate(R.layout.item, null);
			viewHolder.textView = (TextView)arg1.findViewById(R.id.itemText);
			arg1.setTag(viewHolder);
		}else
			viewHolder = (ViewHolder) arg1.getTag();
		    viewHolder.textView.setText(list.get(arg0));
		    if(allEnCodes.contains(list.get(arg0))){
				 arg1.setBackgroundColor(Color.parseColor("#99CCFF"));
			 }else {

			}
		 
		return arg1;
	}
	class ViewHolder {
		public TextView textView;
	}


}
